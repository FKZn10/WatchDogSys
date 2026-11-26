package com.jpmsoft.fileUpload.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.fileUpload.constants.FileMsg;
import com.jpmsoft.fileUpload.oss.crc.CrcOssUtil;
import com.jpmsoft.fileUpload.pojo.dto.OssClientParamsDto;
import com.jpmsoft.fileUpload.pojo.entity.SysFileAnnex;
import com.jpmsoft.fileUpload.pojo.entity.SysUser;
import com.jpmsoft.fileUpload.pojo.vo.OnlyOfficeCallbackVo;
import com.jpmsoft.fileUpload.service.*;
import com.onlyoffice.model.common.User;
import com.onlyoffice.model.documenteditor.Callback;
import com.onlyoffice.model.documenteditor.History;
import com.onlyoffice.model.documenteditor.HistoryData;
import com.onlyoffice.model.documenteditor.callback.Status;
import com.onlyoffice.model.documenteditor.history.Version;
import com.onlyoffice.model.documenteditor.historydata.Previous;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.StreamUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * OnlyOffice相关接口
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OnlyOfficeServiceImpl implements IOnlyOfficeService {
    private final ISysConfigService sysConfigService;
    private final ISysFileAnnexService sysFileAnnexService;
    private final ISysUserService sysUserService;
    private final ISysFileDownloadService sysFileDownloadService;
    private final IOnlyOfficeFileChangesService onlyOfficeFileChangesService;

    /**
     * 初始化OnlyOffice配置
     */
    public Map<String, Object> getOnlyOfficeConfig() {
        Map<String, Object> config = new HashMap<>();
        String sysConfByCode = sysConfigService.getSysConfByCode("onlyOfficeConfig");
        if (ValidateUtil.isNull(sysConfByCode) || !JSONUtil.isTypeJSON(sysConfByCode)) {
            config.put("keepHistoryOnMustSave", false);
            config.put("maxHistoryLimit", 64);
        }
        config = JSONUtil.toBean(sysConfByCode, Map.class);
        if (ValidateUtil.isEmpty(config)) {
            config = new HashMap<>();
            config.put("keepHistoryOnMustSave", false);
            config.put("maxHistoryLimit", 64);
        }
        return config;
    }

    /**
     * OnlyOffice文档编辑
     *
     * @param callback
     * @param oldId
     * @param uId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public OnlyOfficeCallbackVo uploadReplace(Callback callback, String oldId, String uId) {
        // 默认成功
        OnlyOfficeCallbackVo res = new OnlyOfficeCallbackVo();
        res.setError(0);

        // 回调状态判断
        Status status = callback.getStatus();
        if (Status.EDITING.equals(status) || Status.CLOSED.equals(status)) {
            // 编辑中或关闭，不处理
            log.info("OnlyOffice回调无需保存，status={}", status);
            return res;
        }
        if (Status.SAVE_CORRUPTED.equals(status) || Status.FORCESAVE_CORRUPTED.equals(status)) {
            // 保存或强制保存时发生异常，标记失败
            log.error("OnlyOffice回调状态异常：{}", status);
            res.setError(1);
            return res;
        }

        try {
            String root = getRootDirectory();

            SysFileAnnex oldFile = sysFileAnnexService.getById(oldId);
            if (oldFile == null) {
                log.error("未找到可替换的文件记录，id={}", oldId);
                throw new JPMException(FileMsg.noneReplaceFile);
            }

            // 确保有fileHistoryId
            String relId = oldFile.getFileHistoryId();
            if (ValidateUtil.isEmpty(relId)) {
                // 如果没有关联ID，就新建一个
                relId = GenerateUUID.getUUID();
                oldFile.setFileHistoryId(relId);
                sysFileAnnexService.updateById(oldFile);
            }

            String fileUrl = callback.getUrl();

            if (!(Status.SAVE.equals(status) || Status.FORCESAVE.equals(status)) || ValidateUtil.isEmpty(fileUrl)) {
                log.warn("OnlyOffice无需保存或参数不全，status={}，url={}", status, fileUrl);
                // 无需保存，或参数不全
                return res;
            }

            // 是否所有保存都留历史
            Map<String, Object> onlyOfficeConfig = getOnlyOfficeConfig();
            boolean keepHistoryOnMustSave = (boolean) onlyOfficeConfig.get("keepHistoryOnMustSave");
            boolean needHistory = (Status.FORCESAVE.equals(status)) || (Status.SAVE.equals(status) && keepHistoryOnMustSave);

            // 先落一条历史，指向旧物理文件
            SysFileAnnex history = new SysFileAnnex();
            if (needHistory) {
                BeanUtils.copyProperties(oldFile, history);
                history.setFileRelationId(GenerateUUID.getUUID());
                history.setFileIsHistory(1);
                history.setFileId(GenerateUUID.getUUID());
                sysFileAnnexService.save(history);
            }
            // 既然不留历史，那把老物理文件清掉
            if (!needHistory) {
                try {
                    // 删除老文件的下载记录
                    sysFileDownloadService.deleteFileDownLoadByFileId(oldFile.getFileId());
                } catch (Exception e) {
                    log.warn("删除旧文件下载记录失败 oldFileId={}, err={}", oldFile.getFileId(), e.getMessage());
                }

                // 删本地
                try {
                    if (ValidateUtil.isNotEmpty(root) && ValidateUtil.isNotEmpty(oldFile.getFilePath())) {
                        Files.deleteIfExists(Paths.get(root, oldFile.getFilePath()).normalize());
                    }
                } catch (Exception e) {
                    log.warn("删除旧本地文件失败 path={}, err={}", oldFile.getFilePath(), e.getMessage());
                }

                // 删OSS
                try {
                    OssClientParamsDto oss = sysConfigService.getOssParams();
                    if (oss != null && oldFile.isFileUpOss()) {
                        String relPath = oldFile.getFilePath().replace("\\", "/");
                        String key = oss.getOssRootDir() + relPath;
                        CrcOssUtil.deleteFile(oss, key);
                    }
                } catch (Exception e) {
                    log.warn("删除旧OSS文件失败 path={}, err={}", oldFile.getFilePath(), e.getMessage());
                }
            }

            // 基于“旧相对路径”，在同目录生成“新版本相对路径”
            String ooFilename = GenerateUUID.getUUID();
            String suffix = oldFile.getFileSuffix();
            if (ValidateUtil.isEmpty(suffix)) {
                suffix = FileUtil.extName(oldFile.getFileName());
            }
            String newRelativePath = buildNewVersionRelativePathFromOld(oldFile.getFilePath(), ooFilename, suffix);
            Path absPath = Paths.get(root, newRelativePath).normalize();
            Files.createDirectories(absPath.getParent());

            // 下载OnlyOffice内容 -> 新路径
            writeUrlToPathAtomic(fileUrl, absPath);

            // 回写现行版本（仍用 oldId）
            SysFileAnnex newFile = new SysFileAnnex();
            BeanUtils.copyProperties(oldFile, newFile);
            newFile.setFilePath(newRelativePath);
            newFile.setFileUploadTime(new Date());
            newFile.setFileName(oldFile.getFileName());
            newFile.setFileSuffix(suffix);
            try {
                newFile.setFileSize(Files.size(absPath));
                newFile.setFileMd5Code(SecureUtil.md5(absPath.toFile()));
            } catch (IOException ignore) {
                res.setError(1);
                return res;
            }

            // 保存变更记录
            onlyOfficeFileChangesService.handleChanegsUrl(callback.getChangesurl(), newFile.getFileId(), uId);

            if (ValidateUtil.isNotEmpty(uId)) {
                SysUser user = sysUserService.getById(uId);
                if (user != null) {
                    newFile.setFileCreateUId(user.getuId());
                    newFile.setFileCreateUName(user.getuName());
                    if (ValidateUtil.isNotEmpty(user.getTeId())) {
                        newFile.setTeId(user.getTeId());
                    }
                } else {
                    newFile.setFileCreateUId(uId);
                }
            }

            // OSS 同步（用新路径）
            OssClientParamsDto params = sysConfigService.getOssParams();
            if (params != null) {
                String dirKey = params.getOssRootDir() + newRelativePath.substring(0, newRelativePath.lastIndexOf("/") + 1);
                String key = CrcOssUtil.upload(params, absPath.toString(), dirKey);
                newFile.setFileUpOss(ValidateUtil.isNotEmpty(key));
            } else {
                newFile.setFileUpOss(Boolean.FALSE);
            }

            sysFileAnnexService.updateById(newFile);
            sysFileDownloadService.deleteFileDownLoadByFileId(newFile.getFileId());

            // 事务提交后再做历史修剪，避免回滚-文件已删
            final String relIdFinal = oldFile.getFileHistoryId();
            final int maxHistory = getMaxHistoryLimit();
            TransactionSynchronizationManager.registerSynchronization(
                    new TransactionSynchronization() {
                        @Override
                        public void afterCommit() {
                            try {
                                pruneHistoryOverLimit(relIdFinal, root, maxHistory);
                            } catch (Exception ex) {
                                log.error("历史版本修剪失败fileHistoryId={}, err={}", relIdFinal, ex.getMessage(), ex);
                            }
                        }
                    }
            );

            log.info("OnlyOffice保存成功，旧版本oldId={}，现在版本newId={}，路径path={}", history.getFileId(), newFile.getFileId(), newRelativePath);
            return res;
        } catch (Exception e) {
            log.error("OnlyOffice保存失败：{}", e.getMessage(), e);
            res.setError(1);
            return res;
        }
    }

    /**
     * 基于“旧相对路径”，在同目录生成一个同级新文件名（UUID + 后缀）
     *
     * @param oldRelativePath 旧文件相对路径
     * @param uuid            新文件名（UUID，不带后缀）
     * @param suffix          文件后缀（不带点），可为空
     * @return 新版本相对路径：/xxx/yyy/{uuid}.ext
     */
    private String buildNewVersionRelativePathFromOld(String oldRelativePath, String uuid, String suffix) {
        if (ValidateUtil.isEmpty(oldRelativePath)) {
            throw new IllegalArgumentException("oldRelativePath empty");
        }
        String norm = oldRelativePath.replace("\\", "/");
        int slash = norm.lastIndexOf('/');
        String dir = (slash >= 0) ? norm.substring(0, slash) : "";

        // 确保目录以 "/" 开头
        if (!dir.startsWith("/")) {
            dir = "/" + dir;
        }

        StringBuilder newName = new StringBuilder(uuid);
        if (ValidateUtil.isNotEmpty(suffix)) {
            newName.append(".").append(suffix.toLowerCase());
        }

        return dir + "/" + newName;
    }

    /**
     * 原子写入：先写到.tmp，再原子移动到目标，尽量避免并发/中断导致的半文件
     *
     * @param fileUrl
     * @param target
     * @throws IOException
     */
    private void writeUrlToPathAtomic(String fileUrl, Path target) throws IOException {
        log.info("下载OnlyOffice文件，url={}，path={}", fileUrl, target);
        Path tmp = target.resolveSibling(target.getFileName().toString() + ".tmp");
        try (InputStream in = new URL(fileUrl).openStream();
             OutputStream out = Files.newOutputStream(tmp)) {
            StreamUtils.copy(in, out);
        }
        try {
            Files.move(tmp, target,
                    StandardCopyOption.REPLACE_EXISTING,
                    StandardCopyOption.ATOMIC_MOVE);
        } catch (AtomicMoveNotSupportedException e) {
            // 某些FS不支持原子移动，退化为普通替换
            Files.move(tmp, target, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    /**
     * 版本列表：{ currentVersion: n, history: [ ... ] }
     *
     * @param fileId
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public History getHistoryList(String fileId) {
        SysFileAnnex current = sysFileAnnexService.getById(fileId);
        if (current == null) {
            throw new JPMException(FileMsg.noneReplaceFile);
        }

        List<SysFileAnnex> all = loadAllVersions(current);
        // 按时间升序，保证 version 从 1 开始到 N
        all.sort(Comparator
                .comparing((SysFileAnnex f) -> defaultDate(f.getFileUploadTime()))
                .thenComparing(SysFileAnnex::getFileId));

        List<Version> versions = new ArrayList<>();
        for (int i = 0; i < all.size(); i++) {
            SysFileAnnex f = all.get(i);
            int ver = i + 1;

            // user参数
            User onlyOfficeUser = new User();
            onlyOfficeUser.setId(emptyToDash(f.getFileCreateUId()));
            onlyOfficeUser.setName(emptyToDash(f.getFileCreateUName()));

            // changes 是可选的，这里给一个简单“保存”记录
            Map<String, Object> change = new HashMap<>();
            change.put("created", toIso8601(defaultDate(f.getFileUploadTime())));
            change.put("user", onlyOfficeUser);
            change.put("comment", "保存");

            // 版本
            Version version = Version.builder()
                    .version(String.valueOf(ver))
                    .key(buildRevisionKey(f, ver))
                    .created(toIso8601(defaultDate(f.getFileUploadTime())))
                    .user(onlyOfficeUser)
                    .changes(Collections.singletonList(change)).build();

            versions.add(version);
        }

        // 当前版本
        String currentVersion = String.valueOf(versions.size());
        log.info("OnlyOffice版本列表，fileId={}，currentVersion={}", fileId, currentVersion);

        return History.builder()
                .currentVersion(currentVersion)
                .history(versions)
                .build();
    }

    /**
     * 指定版本的数据：{ key, url, fileType, previous: { key, url }, changesUrl? }
     *
     * @param fileId
     * @param version
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public HistoryData getHistoryData(String fileId, Integer version) {
        if (version == null || version < 1) {
            throw new IllegalArgumentException("invalid version");
        }

        SysFileAnnex current = sysFileAnnexService.getById(fileId);
        if (current == null) {
            throw new JPMException(FileMsg.noneReplaceFile);
        }

        List<SysFileAnnex> all = loadAllVersions(current);
        all.sort(Comparator
                .comparing((SysFileAnnex f) -> defaultDate(f.getFileUploadTime()))
                .thenComparing(SysFileAnnex::getFileId));

        if (version > all.size()) {
            throw new IllegalArgumentException("version out of range");
        }

        SysFileAnnex target = all.get(version - 1);

        String changesFileUrl = onlyOfficeFileChangesService.getChangesFileUrl(fileId);

        HistoryData historyData = HistoryData.builder()
                .key(buildRevisionKey(target, version))
                .fileType(safeSuffix(target.getFileSuffix()))
                // 该版本的直连下载URL（OnlyOffice用它来打开此版本）
                .url(buildDownloadUrl(target))
                .version(String.valueOf(version))
                .changesUrl(changesFileUrl)
                .build();

        // 上一版本（如果有）
        if (version > 1) {
            SysFileAnnex prev = all.get(version - 2);
            Previous previous = Previous.builder()
                    .key(buildRevisionKey(prev, version - 1))
                    .url(buildDownloadUrl(prev))
                    .build();
            historyData.setPrevious(previous);
        }

        return historyData;
    }

    /**
     * 取同一 fileHistoryId 下的所有版本（含当前 + 历史 fileReplace=1）
     *
     * @param current
     * @return
     */
    private List<SysFileAnnex> loadAllVersions(SysFileAnnex current) {
        String fileHistoryId = current.getFileHistoryId();
        if (ValidateUtil.isEmpty(fileHistoryId)) {
            // 没有就只返回当前
            return Collections.singletonList(current);
        }
        return sysFileAnnexService.list(
                Wrappers.<SysFileAnnex>lambdaQuery()
                        .eq(SysFileAnnex::getFileHistoryId, fileHistoryId)
        );
    }

    /**
     * 生成下载URL
     *
     * @param f
     * @return
     */
    private String buildDownloadUrl(SysFileAnnex f) {
        // 获取文件服务器前缀
        String base = sysConfigService.getDownloadUrl();
        if (ValidateUtil.isEmpty(base)) {
            base = "http://localhost/fileServer/";
        }
        if (base.endsWith("/")) {
            base = base + "jpm/downloadAloneOpenFile/";
        } else {
            base = base + "/jpm/downloadAloneOpenFile/";
        }
        return base + f.getFileId();
    }

    /**
     * 修正后缀
     *
     * @param suffix
     * @return
     */
    private String safeSuffix(String suffix) {
        if (ValidateUtil.isEmpty(suffix)) {
            return "docx";
        }
        return suffix.toLowerCase();
    }

    /**
     * 版本键
     *
     * @param f
     * @param ver
     * @return
     */
    private String buildRevisionKey(SysFileAnnex f, int ver) {
        String root = getRootDirectory();
        File file = Paths.get(root, f.getFilePath()).normalize().toFile();
        String md5 = SecureUtil.md5(file);
        return md5 + "-" + ver;
    }

    /**
     * 空日期转 1970-01-01
     *
     * @param d
     * @return
     */
    private Date defaultDate(Date d) {
        return d != null ? d : new Date(0L);
    }

    /**
     * 空字符串转 "-"
     *
     * @param s
     * @return
     */
    private String emptyToDash(String s) {
        return ValidateUtil.isEmpty(s) ? "-" : s;
    }

    /**
     * 转ISO 8601格式
     *
     * @param date
     * @return
     */
    private String toIso8601(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        return sdf.format(date);
    }

    /**
     * 读取历史保留上限
     */
    private int getMaxHistoryLimit() {
        try {
            Map<String, Object> onlyOfficeConfig = getOnlyOfficeConfig();
            Integer v = (Integer) onlyOfficeConfig.get("getOnlyOfficeConfig");
            // 默认保留 64 条历史
            return (v != null && v > 0) ? v : 64;
        } catch (Exception ignore) {
            return 64;
        }
    }

    /**
     * 只保留最近 limit 条历史（fileIsHistory=1），超出的删除；现行版本(fileIsHistory=0)不动
     *
     * @param fileHistoryId
     * @param root
     * @param limit
     */
    private void pruneHistoryOverLimit(String fileHistoryId, String root, int limit) {
        if (ValidateUtil.isEmpty(fileHistoryId) || limit < 0) {
            return;
        }

        // 历史按时间倒序（新→旧）
        List<SysFileAnnex> histories = sysFileAnnexService.list(
                Wrappers.<SysFileAnnex>lambdaQuery()
                        .eq(SysFileAnnex::getFileHistoryId, fileHistoryId)
                        .eq(SysFileAnnex::getFileIsHistory, 1)
                        .orderByDesc(SysFileAnnex::getFileUploadTime)
                        .orderByDesc(SysFileAnnex::getFileId)
        );
        if (ValidateUtil.isEmpty(histories)) {
            return;
        }

        if (histories.size() <= limit) {
            return;
        }

        List<SysFileAnnex> toRemove = histories.subList(limit, histories.size());
        log.info("历史版本修剪：fileHistoryId={}, limit={}, totalHistory={}, toDelete={}",
                fileHistoryId, limit, histories.size(), toRemove.size());

        // 批量删除：文件、下载记录、DB
        OssClientParamsDto oss = null;
        try {
            oss = sysConfigService.getOssParams();
        } catch (Exception ignore) {
        }

        for (SysFileAnnex f : toRemove) {
            try {
                // 删除下载记录
                sysFileDownloadService.deleteFileDownLoadByFileId(f.getFileId());
            } catch (Exception e) {
                log.error("删除下载记录失败 fileId={}, err={}", f.getFileId(), e.getMessage());
            }

            // 删除本地文件
            try {
                if (!ValidateUtil.isEmpty(root) && !ValidateUtil.isEmpty(f.getFilePath())) {
                    Path p = Paths.get(root, f.getFilePath()).normalize();
                    Files.deleteIfExists(p);
                }
            } catch (Exception e) {
                log.error("删除本地历史文件失败 fileId={}, path={}, err={}", f.getFileId(), f.getFilePath(), e.getMessage());
            }

            // 删除 OSS
            try {
                if (oss != null && f.isFileUpOss()) {
                    String relPath = f.getFilePath().replace("\\", "/");
                    String key = oss.getOssRootDir() + relPath;
                    CrcOssUtil.deleteFile(oss, key);
                }
            } catch (Exception e) {
                log.error("删除OSS历史文件失败 fileId={}, err={}", f.getFileId(), e.getMessage());
            }
        }

        // 删除数据库记录
        try {
            List<String> ids = new ArrayList<>(toRemove.size());
            for (SysFileAnnex f : toRemove) {
                ids.add(f.getFileId());
            }
            if (!ids.isEmpty()) {
                sysFileAnnexService.removeBatchByIds(ids);
            }
        } catch (Exception e) {
            log.error("删除历史记录DB失败 fileHistoryId={}, err={}", fileHistoryId, e.getMessage(), e);
        }
    }

    /**
     * 取文件根目录
     *
     * @return
     */
    private String getRootDirectory() {
        String root = sysConfigService.getRootDirectory();
        if (root == null) {
            log.error("未配置文件根目录");
            throw new JPMException(FileMsg.missSysConfig);
        }
        return root;
    }
}
