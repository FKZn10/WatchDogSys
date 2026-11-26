package com.jpmsoft.fileUpload.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.HttpUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.fileUpload.constants.FileMsg;
import com.jpmsoft.fileUpload.mapper.OnlyOfficeFileChangesMapper;
import com.jpmsoft.fileUpload.oss.crc.CrcOssUtil;
import com.jpmsoft.fileUpload.pojo.dto.OssClientParamsDto;
import com.jpmsoft.fileUpload.pojo.entity.OnlyOfficeFileChanges;
import com.jpmsoft.fileUpload.pojo.entity.SysFileAnnex;
import com.jpmsoft.fileUpload.pojo.entity.SysUser;
import com.jpmsoft.fileUpload.service.IOnlyOfficeFileChangesService;
import com.jpmsoft.fileUpload.service.ISysConfigService;
import com.jpmsoft.fileUpload.service.ISysFileAnnexService;
import com.jpmsoft.fileUpload.service.ISysUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OnlyOfficeFileChangesImpl extends ServiceImpl<OnlyOfficeFileChangesMapper, OnlyOfficeFileChanges> implements IOnlyOfficeFileChangesService {
    private final ISysConfigService sysConfigService;
    private final ISysFileAnnexService sysFileAnnexService;
    private final ISysUserService sysUserService;
    @Value("${file.upload.dirPath}")
    private String dirPath;

    /**
     * 变更记录URL处理
     *
     * @param changesUrl
     * @param fileId     传入的必须是当前更改的文件ID
     * @param uId
     */
    @Override
    public void handleChanegsUrl(String changesUrl, String fileId, String uId) {
        if (ValidateUtil.isEmpty(changesUrl)) {
            return;
        }
        log.info("OnlyOffice变更记录处理，fileId:{}，changesUrl:{}", fileId, changesUrl);

        String root = getRootDirectory();

        // 旧的变更记录
        List<OnlyOfficeFileChanges> oldFiles = this.list(Wrappers.<OnlyOfficeFileChanges>lambdaQuery().eq(OnlyOfficeFileChanges::getFileId, fileId));

        if (ValidateUtil.isNotEmpty(oldFiles)) {
            // 旧的文件id
            List<String> list = oldFiles.stream().map(OnlyOfficeFileChanges::getId).collect(Collectors.toList());
            // 删除
            this.remove(Wrappers.<OnlyOfficeFileChanges>lambdaQuery().in(OnlyOfficeFileChanges::getFileId, fileId));
            sysFileAnnexService.update(Wrappers.<SysFileAnnex>lambdaUpdate().set(SysFileAnnex::getUploadStatus, 0).in(SysFileAnnex::getFileId, list));
        }

        // 下载zip
        // 拼接下载路径
        String fileName = GenerateUUID.getUUID();
        // 时间路径，只需要年/月
        String timePath = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM"));
        String filePath = dirPath + "/OnlyOfficeChanges/" + timePath + "/" + fileName + ".zip";
        String fullPath = root + filePath;
        long fileSize = HttpUtil.downloadFile(changesUrl, fullPath);

        // 构建一个文件上传记录
        if (fileSize > 0) {
            // 保存文件上传记录
            SysFileAnnex fileAnnex = new SysFileAnnex();
            fileAnnex.setFileId(GenerateUUID.getUUID());
            fileAnnex.setFileRelationId(GenerateUUID.getUUID());
            fileAnnex.setFilePath(filePath);
            fileAnnex.setFileSize(fileSize);
            fileAnnex.setFileSuffix("zip");
            fileAnnex.setFileName(fileName + ".zip");
            fileAnnex.setFileUploadTime(new Date());
            fileAnnex.setFileCreateUId(uId);
            SysUser user = sysUserService.getById(uId);
            if (ValidateUtil.isNotNull(user)) {
                fileAnnex.setFileCreateUName(user.getuName());
            }
            fileAnnex.setFileCode("undefined");
            fileAnnex.setUploadStatus(1);
            fileAnnex.setArchive(0);
            fileAnnex.setFileLocation(0);
            fileAnnex.setFileReplace(0);
            File file = FileUtil.file(fullPath);
            fileAnnex.setFileMd5Code(SecureUtil.md5(file));
            fileAnnex.setFileInputName(fileName);
            OssClientParamsDto params = sysConfigService.getOssParams();
            if (params != null) {
                String dirKey = params.getOssRootDir() + "OnlyOfficeChanges/" + timePath + "/";
                String key = CrcOssUtil.upload(params, fullPath, dirKey);
                fileAnnex.setFileUpOss(ValidateUtil.isNotEmpty(key));
            } else {
                fileAnnex.setFileUpOss(Boolean.FALSE);
            }
            fileAnnex.setFileNotRemove(false);
            sysFileAnnexService.save(fileAnnex);

            // 保存变更记录
            OnlyOfficeFileChanges changes = new OnlyOfficeFileChanges();
            changes.setId(fileAnnex.getFileId());
            changes.setFileId(fileId);
            changes.setPath(filePath);
            changes.setCreateTime(new Date());
            this.save(changes);
            log.info("OnlyOffice变更记录文件保存成功，fileId:{}，path:{}", fileId, filePath);
        } else {
            throw new JPMException(FileMsg.fileParsingFail);
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

    /**
     * 获取变更记录文件的下载url
     *
     * @param fileId
     * @return
     */
    @Override
    public String getChangesFileUrl(String fileId) {
        if (ValidateUtil.isEmpty(fileId)) {
            return null;
        }
        OnlyOfficeFileChanges changes = this.getOne(Wrappers.<OnlyOfficeFileChanges>lambdaQuery()
                .eq(OnlyOfficeFileChanges::getFileId, fileId)
                .orderByDesc(OnlyOfficeFileChanges::getCreateTime)
                .last("limit 1"));
        if (ValidateUtil.isNotNull(changes)) {
            return buildDownloadUrl(changes.getId());
        }
        return null;
    }

    /**
     * 生成下载URL
     *
     * @param fileId
     * @return
     */
    private String buildDownloadUrl(String fileId) {
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
        return base + fileId;
    }
}
