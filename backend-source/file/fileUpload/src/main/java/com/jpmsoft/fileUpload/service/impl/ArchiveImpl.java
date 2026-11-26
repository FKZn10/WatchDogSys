package com.jpmsoft.fileUpload.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.response.LogUserInfoVo;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.fileUpload.constants.FileMsg;
import com.jpmsoft.fileUpload.mapper.ArchiveMapper;
import com.jpmsoft.fileUpload.oss.crc.CrcOssUtil;
import com.jpmsoft.fileUpload.pojo.dto.JMExportDTO;
import com.jpmsoft.fileUpload.pojo.dto.JimuExportAutoArchiveDTO;
import com.jpmsoft.fileUpload.pojo.dto.OssClientParamsDto;
import com.jpmsoft.fileUpload.pojo.entity.*;
import com.jpmsoft.fileUpload.pojo.vo.AutoArchiveDirectoryVo;
import com.jpmsoft.fileUpload.pojo.vo.AutoArchiveFileVo;
import com.jpmsoft.fileUpload.service.*;
import com.jpmsoft.fileUpload.utils.FileUtil;
import com.jpmsoft.fileUpload.utils.MD5File;
import com.jpmsoft.fileUpload.utils.MathUtil;
import com.jpmsoft.fileUpload.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created By:李文涛 aaron
 * Created Time:2022/6/19 13:05
 */
@Slf4j
@Service
public class ArchiveImpl extends ServiceImpl<ArchiveMapper, Archive> implements IArchiveService {

    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ISysConfigService sysConfigService;
    @Resource
    private IArchiveFileService archiveFileService;
    @Resource
    private ISysUserService sysUserService;
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private ISysFileTemplatePrintService sysFileTemplatePrintService;
    @Resource
    private ISysFileService sysFileService;
    @Resource
    private IArchiveFileNameService archiveFileNameService;
    @Autowired
    private IArchiveDirectoryService iArchiveDirectoryService;

    @Override
    public List<AutoArchiveDirectoryVo> getAutoArchiveDirectory() {
        return baseMapper.getAutoArchiveDirectory();
    }

    @Override
    public List<AutoArchiveDirectoryVo> getAutoXzArchiveDirectory() {
        return baseMapper.getAutoXzArchiveDirectory();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public synchronized BaseResponse autoArchive() {
        String archiveCreateInfo = sysConfigService.getSysConfByCode("archiveUserInfo");
        if (ValidateUtil.isEmpty(archiveCreateInfo))
            return BaseResponse.ok("未开启自动归档");
        String root = sysConfigService.getRootDirectory();
        String archiveD = sysConfigService.getArchiveDirectory();
        if (ValidateUtil.isAnObjEmpty(root, archiveD))
            return BaseResponse.fail(FileMsg.missSysConfig);
        root = root.endsWith("/") ? root : root + "/";
        archiveD = archiveD.endsWith("/") ? archiveD : archiveD + "/";
        String[] createInfo = archiveCreateInfo.split(",");
        if (createInfo.length < 1) return BaseResponse.fail(FileMsg.missSysConfig);
        String uId = createInfo[0];
        String uName = createInfo[1];
        //获取文件信息（默认查询100条）
        List<AutoArchiveFileVo> files = sysFileAnnexService.getUnArchiveFiles();
        if (ValidateUtil.isEmpty(files)) return BaseResponse.ok();
        //获取流程编码对应目录ID信息
        List<AutoArchiveDirectoryVo> ads = getAutoArchiveDirectory();
        if (ValidateUtil.isEmpty(ads)) return BaseResponse.fail(FileMsg.missArchiveDirectory);
        OssClientParamsDto param = sysConfigService.getOssParams();
        //region 临时记录 归当前文件信息和归档后文件信息 防止归档时同文件重复复制生成

        //归档后文件信息
        List<SysFileAnnex> newFiles = new ArrayList<>();
        for (AutoArchiveFileVo file : files) {
            //文件是否真是存在
            String oldPath = file.getFilePath();
            Boolean oldStart = oldPath.startsWith("/");
            String newPath = oldStart ? file.getTCode() + oldPath : file.getTCode() + "/" + oldPath;
            String ossPath = root + oldPath;
            File theFile = new File(ossPath);
            if (!theFile.exists() || !theFile.isFile()) {
                if (CrcOssUtil.fileExist(param, ossPath)) {
                    //将OSS文件下载到本地
                    CrcOssUtil.download(param, oldPath, root, 0);
                } else {
                    //本地文件不存在,OSS文件找不到
                    log.info("文件不存在，不执行归档逻辑!FileId:" + file.getFileId());
                    continue;
                }
            }
            SysFileAnnex fileAnnex = new SysFileAnnex();
            BeanUtils.copyProperties(file, fileAnnex);
            //归档后文件信息
            fileAnnex.setArchive(1);
            fileAnnex.setFileLocation(1);
            fileAnnex.setFileUnarchiveId(fileAnnex.getFileId());
            fileAnnex.setFileId(GenerateUUID.getUUID());
            fileAnnex.setFilePath(newPath);
            newFiles.add(fileAnnex);
        }
        //endregion

        Date now = new Date();
        //region 归档记录信息
        //实际需要生成的新文件信息(归档文件)
        Set<SysFileAnnex> realCopyFiles = new HashSet<>();
        //实际需要更新的旧文件信息
        Set<SysFileAnnex> upArchiveFiles = new HashSet<>();
        //项目档案归档信息
        List<Archive> archives = new ArrayList<>();
        //归档文件信息
        List<ArchiveFile> archiveFiles = new ArrayList<>();
        //文件源地址
        Map<String, String> froms = new HashMap<>();
        //归档地址
        Map<String, String> tos = new HashMap<>();
        //所有归档目录ID
        List<String> adIds = ads.stream().map(AutoArchiveDirectoryVo::getAdId).collect(Collectors.toList());
        for (AutoArchiveDirectoryVo ad : ads) {
            //上传时选择归档到当前目录的文件信息
            List<AutoArchiveFileVo> fileVos = files.stream().filter(n -> ValidateUtil.isNotEmpty(n.getAdId()) && n.getAdId().equals(ad.getAdId())).collect(Collectors.toList());

            if (CollectionUtils.isEmpty(fileVos)) fileVos = new ArrayList<>();
            else files.removeAll(fileVos);//指定归档目录的文件归档后不再归档

            if (!ad.getAdParentId().equals("0") || ads.stream().filter(n -> n.getAdParentId().equals(ad.getAdId())).count() < 1) {
                //归档目录根节点 且存在子节点则不允许执行自动归档操作
                //流程编码相同的文件(且指定归档的目录不存在)则归档到当前目录(adId);
                if (ValidateUtil.isNotEmpty(ad.getTCode())) {
                    List<AutoArchiveFileVo> xtCodes = files.stream().filter(n -> {
                        if (n.getTCode().equals(ad.getTCode())) {
                            if (ValidateUtil.isEmpty(n.getAdId())) return true;
                            else return !adIds.contains(n.getAdId());
                        } else return false;
                    }).collect(Collectors.toList());
                    if (!ValidateUtil.isEmpty(xtCodes)) fileVos.addAll(xtCodes);
                }
            }

            int afOrder = 1;
            if (!CollectionUtils.isEmpty(fileVos)) {
                for (AutoArchiveFileVo vo : fileVos) {
                    //归档后文件信息（临时）
                    List<SysFileAnnex> realCopys = newFiles.stream().filter(n -> vo.getFileId().equals(n.getFileUnarchiveId())).collect(Collectors.toList());
                    //无记录则表示文件不存在
                    if (ValidateUtil.isEmpty(realCopys)) continue;
                    String pId = getPid(vo.getPId(), vo.getSecId(), vo.getConId());
                    //更新旧文件记录状态
                    SysFileAnnex upFile = new SysFileAnnex();
                    BeanUtils.copyProperties(vo, upFile);
                    upFile.setArchive(1);
                    upArchiveFiles.add(upFile);
                    //新增新归档后附件信息
                    SysFileAnnex newFile = realCopys.stream().findFirst().get();
                    //更新文件OSS状态
                    if (ValidateUtil.isNotNull(param) && param.isEnableOss()) newFile.setFileUpOss(Boolean.TRUE);
                    //根据规则生成归档附件名称
                    String fname = archiveFileNameService.name(newFile);
                    newFile.setFileName(fname);
                    realCopyFiles.add(newFile);
                    //记录文件新旧路径
                    froms.put(vo.getFileId(), vo.getFilePath());
                    tos.put(vo.getFileId(), newFile.getFilePath());

                    if (ValidateUtil.isEmpty(pId)) continue;
                    //项目档案归档信息
                    Archive archivePojo = new Archive();
                    archivePojo.setApId(GenerateUUID.getUUID());
                    archivePojo.setAdId(ad.getAdId());
                    archivePojo.setApDescCreateTime(now);
                    archivePojo.setPId(pId);
                    archivePojo.setApDocumentNo(vo.getFileInputCode());
                    archivePojo.setApTitle(ad.getAdName());
//                    archivePojo.setApTitle(ValidateUtil.isEmpty(vo.getFileInputName()) ? fname : vo.getFileInputName());
                    archivePojo.setApWrittenDate(vo.getFileInputTime());
                    archivePojo.setApReceiptNo(vo.getFileInputVersion());
                    archivePojo.setApReceiptTime(vo.getFileUploadTime());
                    archivePojo.setApState(1);
                    archivePojo.setApDescCreateUid(uId);
                    archivePojo.setApDescCreateUname(uName);
                    archivePojo.setApPersonUid(uId);
                    archivePojo.setApPersonUname(uName);
                    archives.add(archivePojo);
                    //归档文件信息
                    ArchiveFile afPojo = new ArchiveFile();
                    afPojo.setApId(archivePojo.getApId());
                    afPojo.setAfId(GenerateUUID.getUUID());
                    afPojo.setAfCreateTime(now);
                    afPojo.setAfFileId(vo.getSysFileId());
                    afPojo.setAfAnnexId(newFile.getFileId());
                    afPojo.setAfFileSize(MathUtil.FloatDivisionInt(Float.valueOf(newFile.getFileSize()), 1024 * 1024));
                    afPojo.setAfType(newFile.getFileSuffix());
                    afPojo.setAfOrder(afOrder);
                    afOrder += 1;
                    afPojo.setAfCode(vo.getFileInputCode());
                    afPojo.setAfName(fname);
                    afPojo.setAfState(1);
                    afPojo.setAfCreateUid(uId);
                    afPojo.setAfCreateUname(uName);
                    afPojo.setAfFrom(1);
                    archiveFiles.add(afPojo);
                }
            }
        }
        //endregion

        //创建项目档案归档信息
        if (ValidateUtil.isNotEmpty(archives)) saveBatch(archives);
        //创建归档文件信息
        if (ValidateUtil.isNotEmpty(archiveFiles)) archiveFileService.saveBatch(archiveFiles);
        //更新文件归档状态信息
        if (ValidateUtil.isNotEmpty(upArchiveFiles)) sysFileAnnexService.updateBatchById(upArchiveFiles);
        //新增归档文件信息
        if (ValidateUtil.isNotEmpty(realCopyFiles)) sysFileAnnexService.saveBatch(realCopyFiles);
        //复制实体文件
        for (Map.Entry<String, String> entry : froms.entrySet()) {
            String archivePath = tos.get(entry.getKey());
            FileUtil.copy(root + entry.getValue(), archiveD + archivePath);
            String ossDir = archivePath.substring(0, archivePath.lastIndexOf("/") + 1);
            //文件赋值后上传到OSS归档目录
            if (param != null)
                CrcOssUtil.upload(param, archiveD + tos.get(entry.getKey()), param.getOssArchiveDir() + ossDir);
        }
        return BaseResponse.ok();
    }

    /**
     * 不同归档目录但是是同源文件的归档记录指向同一个fileAnnex记录
     *
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public synchronized BaseResponse autoXzArchive() {
        String archiveCreateInfo = sysConfigService.getSysConfByCode("archiveUserInfo");
        if (ValidateUtil.isEmpty(archiveCreateInfo))
            return BaseResponse.ok("未开启自动归档");
        String root = sysConfigService.getRootDirectory();
        String archiveD = sysConfigService.getArchiveDirectory();
        if (ValidateUtil.isAnObjEmpty(root, archiveD))
            return BaseResponse.fail(FileMsg.missSysConfig);
        root = root.endsWith("/") ? root : root + "/";
        archiveD = archiveD.endsWith("/") ? archiveD : archiveD + "/";
        String[] createInfo = archiveCreateInfo.split(",");
        if (createInfo.length < 1) return BaseResponse.fail(FileMsg.missSysConfig);
        String uId = createInfo[0];
        String uName = createInfo[1];
        //获取流程编码对应目录ID信息
        List<AutoArchiveDirectoryVo> ads = getAutoXzArchiveDirectory();
        if (ValidateUtil.isEmpty(ads)) return BaseResponse.fail(FileMsg.missArchiveDirectory);
        //获取行政文件信息
        List<String> adIds = ads.stream().map(AutoArchiveDirectoryVo::getAdId).distinct().collect(Collectors.toList());
        List<AutoArchiveFileVo> files = sysFileAnnexService.getUnXzArchiveFiles(adIds);
        if (ValidateUtil.isEmpty(files)) return BaseResponse.ok();
        OssClientParamsDto param = sysConfigService.getOssParams();
        List<SysFileAnnex> newFiles = new ArrayList<>();//新归档后附件信息
        //region 临时记录 归当前文件信息和归档后文件信息 防止归档时同文件重复复制生成
        //归档后文件信息
        List<String> tCodes = new ArrayList<>();
        List<AutoArchiveDirectoryVo> haveCodes = ads.stream().filter(f -> ValidateUtil.isNotEmpty(f.getTCode())).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(haveCodes))
            tCodes = haveCodes.stream().map(AutoArchiveDirectoryVo::getTCode).distinct().collect(Collectors.toList());
        for (AutoArchiveFileVo file : files) {
            //无法确定归于那个行政档案目录的附件略过
            if ((ValidateUtil.isEmpty(file.getAdId()) || !adIds.contains(file.getAdId()))
                    && !tCodes.contains(file.getTCode())) continue;
            //文件是否真是存在
            String oldPath = file.getFilePath();
            Boolean oldStart = oldPath.startsWith("/");
            String newPath = oldStart ? file.getTCode() + oldPath : file.getTCode() + "/" + oldPath;
            String ossPath = root + oldPath;
            File theFile = new File(ossPath);
            if (!theFile.exists() || !theFile.isFile()) {
                if (CrcOssUtil.fileExist(param, ossPath)) {
                    //将OSS文件下载到本地
                    CrcOssUtil.download(param, oldPath, root, 0);
                } else {
                    //本地文件不存在,OSS文件找不到
                    log.info("文件不存在，不执行归档逻辑!FileId:" + file.getFileId());
                    continue;
                }
            }
            SysFileAnnex fileAnnex = new SysFileAnnex();
            BeanUtils.copyProperties(file, fileAnnex);
            //归档后文件信息
            fileAnnex.setArchive(1);
            fileAnnex.setFileLocation(1);
            fileAnnex.setFileUnarchiveId(fileAnnex.getFileId());
            fileAnnex.setFileId(GenerateUUID.getUUID());
            fileAnnex.setFilePath(newPath);
            newFiles.add(fileAnnex);
        }
        //endregion
        //没有文件<文档模板或归档目录>满足条件
        if (CollectionUtils.isEmpty(newFiles)) return BaseResponse.fail("无可归档的行政附件!");
        Date now = new Date();
        //实际需要生成的新文件信息(归档文件)
        Set<SysFileAnnex> realCopyFiles = new HashSet<>();
        //实际需要更新的旧文件信息
        Set<SysFileAnnex> upArchiveFiles = new HashSet<>();
        //项目档案归档信息
        List<Archive> archives = new ArrayList<>();
        //归档文件信息
        List<ArchiveFile> archiveFiles = new ArrayList<>();
        //文件源地址
        Map<String, String> froms = new HashMap<>();
        //归档地址
        Map<String, String> tos = new HashMap<>();
        int afOrder = 1;
        for (SysFileAnnex file : newFiles) {
            List<String> archiveDirIds = new ArrayList<>();
            List<AutoArchiveFileVo> olds = files.stream().filter(f -> f.getFileId().equals(file.getFileUnarchiveId())).collect(Collectors.toList());
            AutoArchiveFileVo af = olds.stream().findFirst().get();
            if (ValidateUtil.isNotEmpty(file.getAdId()) && adIds.contains(file.getAdId())) {
                archiveDirIds.add(file.getAdId());
            } else {
                List<AutoArchiveDirectoryVo> dirs = haveCodes.stream().filter(f -> f.getTCode().equals(af.getTCode())).collect(Collectors.toList());
                for (AutoArchiveDirectoryVo ad : dirs) {
                    //非根目录  或者根目录没有子目录才可以归档
                    if (!ad.getAdParentId().equals("0") || ads.stream().filter(n -> n.getAdParentId().equals(ad.getAdId())).count() < 1)
                        archiveDirIds.add(ad.getAdId());
                }
            }
            //记录文件新旧路径
            if (!CollectionUtils.isEmpty(archiveDirIds)) {
                realCopyFiles.add(file);
                SysFileAnnex gx = new SysFileAnnex();
                BeanUtils.copyProperties(af, gx);
                gx.setArchive(1);
                upArchiveFiles.add(gx);
                froms.put(af.getFileId(), af.getFilePath());
                tos.put(af.getFileId(), file.getFilePath());
                List<ArchiveDirectory> arDirs = iArchiveDirectoryService.getDirByIds(archiveDirIds);
                for (ArchiveDirectory ad : arDirs) {
                    String adId = ad.getAdId();
                    //项目档案归档信息
                    Archive archivePojo = new Archive();
                    archivePojo.setApId(GenerateUUID.getUUID());
                    archivePojo.setAdId(adId);
                    archivePojo.setApDescCreateTime(now);
                    archivePojo.setApDocumentNo(af.getFileInputCode());
                    archivePojo.setApTitle(ad.getAdName());
//                    archivePojo.setApTitle(ValidateUtil.isEmpty(af.getFileInputName()) ? af.getFileName() : af.getFileInputName());
                    archivePojo.setApWrittenDate(af.getFileInputTime());
                    archivePojo.setApReceiptNo(af.getFileInputVersion());
                    archivePojo.setApReceiptTime(af.getFileUploadTime());
                    archivePojo.setApState(1);
                    archivePojo.setApDescCreateUid(uId);
                    archivePojo.setApDescCreateUname(uName);
                    archivePojo.setApPersonUid(uId);
                    archivePojo.setApPersonUname(uName);
                    archives.add(archivePojo);
                    //归档文件信息
                    ArchiveFile afPojo = new ArchiveFile();
                    afPojo.setApId(archivePojo.getApId());
                    afPojo.setAfId(GenerateUUID.getUUID());
                    afPojo.setAfCreateTime(now);
                    afPojo.setAfFileId(af.getSysFileId());
                    afPojo.setAfAnnexId(file.getFileId());
                    afPojo.setAfFileSize(MathUtil.FloatDivisionInt(Float.valueOf(file.getFileSize()), 1024 * 1024));
                    afPojo.setAfType(file.getFileSuffix());
                    afPojo.setAfOrder(afOrder);
                    afOrder += 1;
                    afPojo.setAfCode(af.getFileInputCode());
                    afPojo.setAfName(af.getFileName());
                    afPojo.setAfState(1);
                    afPojo.setAfCreateUid(uId);
                    afPojo.setAfCreateUname(uName);
                    afPojo.setAfFrom(1);
                    archiveFiles.add(afPojo);
                }
            }
        }
        //创建项目档案归档信息
        if (ValidateUtil.isNotEmpty(archives)) saveBatch(archives);
        //创建归档文件信息
        if (ValidateUtil.isNotEmpty(archiveFiles)) archiveFileService.saveBatch(archiveFiles);
        //更新文件归档状态信息
        if (ValidateUtil.isNotEmpty(upArchiveFiles)) sysFileAnnexService.updateBatchById(upArchiveFiles);
        //新增归档文件信息
        if (ValidateUtil.isNotEmpty(realCopyFiles)) sysFileAnnexService.saveBatch(realCopyFiles);
        //复制实体文件
        for (Map.Entry<String, String> entry : froms.entrySet()) {
            String archivePath = tos.get(entry.getKey());
            FileUtil.copy(root + entry.getValue(), archiveD + archivePath);
            String ossDir = archivePath.substring(0, archivePath.lastIndexOf("/") + 1);
            //文件赋值后上传到OSS归档目录
            if (param != null)
                CrcOssUtil.upload(param, archiveD + tos.get(entry.getKey()), param.getOssArchiveDir() + ossDir);
        }
        return BaseResponse.ok();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse autoArchiveJimuReport(int size) {
        JimuExportAutoArchiveDTO conf = sysConfigService.getJimuExportArchive();
        if (conf != null && conf.isAutoArchive()) {
            if (ValidateUtil.isEmpty(conf.getExportPdfUrl()))
                return BaseResponse.fail("缺少积木报表导出PDF请求地址配置！");
            String root = sysConfigService.getArchiveDirectory();
            root = root.endsWith("/") ? new StringBuilder(root).deleteCharAt(root.length() - 1).toString() : root;
            List<SysFile> files = sysFileService.getUnJmArchive(size);
            if (!CollectionUtils.isEmpty(files)) {
                List<SysFileTemplatePrint> prints = sysFileTemplatePrintService.getJiMuPrint(files.stream().map(SysFile::gettId).distinct().collect(Collectors.toList()));
                if (!CollectionUtils.isEmpty(prints)) {
                    //文件信息
                    List<SysFileAnnex> fileAnnexes = new ArrayList<>();
                    //项目档案归档信息
                    List<Archive> archives = new ArrayList<>();
                    //归档文件信息
                    List<ArchiveFile> archiveFiles = new ArrayList<>();
                    //获取流程编码对应目录ID信息
                    List<AutoArchiveDirectoryVo> ads = getAutoArchiveDirectory();
                    ads = ads.stream().filter(f -> ValidateUtil.isNotEmpty(f.getTCode())).collect(Collectors.toList());
                    OssClientParamsDto ossConf = sysConfigService.getOssParams();
                    Date now = new Date();
                    List<SysFile> newFiles = new ArrayList<>();
                    for (SysFile file : files) {
                        List<SysFileTemplatePrint> pts = prints.stream().filter(n -> n.getTId().equals(file.gettId())).collect(Collectors.toList());
                        if (!CollectionUtils.isEmpty(pts)) {
                            //虚拟访问令牌
                            Object rd = redisUtils.get(file.getFilecreateuser());
                            String token = ValidateUtil.isNull(rd) ? GenerateUUID.getToekn() : rd.toString();
                            if (ValidateUtil.isNull(rd) || !token.equals(rd.toString())) {
                                redisUtils.set(file.getFilecreateuser(), token);
                                redisUtils.set(token, file.getFilecreateuser());
                                getLogInfo(sysUserService.getById(file.getFilecreateuser()), token);
                            }
                            String relationId = GenerateUUID.getUUID();
                            String pId = getPid(file.getFileproject(), file.getFilesectionid(), file.getFilecontract());
                            List<AutoArchiveDirectoryVo> adIds = ads.stream().filter(n -> ValidateUtil.isNotEmpty(n.getTCode()) && n.getTCode().equals(file.gettCode())).collect(Collectors.toList());
                            if (!CollectionUtils.isEmpty(adIds)) {
                                for (SysFileTemplatePrint pt : pts) {
                                    JMExportDTO param = new JMExportDTO();
                                    param.setExcelConfigId(getExcelConfigId(pt.getTpApiUrl()));
                                    JMExportDTO.QueryParam qm = new JMExportDTO.QueryParam();
                                    qm.setToken(token);
                                    qm.setId(file.getFileid());
                                    param.setQueryParam(qm);
                                    //region 生成文件信息fileAnnex,同时生成实体文件
                                    SysFileAnnex annex = new SysFileAnnex();
                                    annex.setFileId(GenerateUUID.getUUID());
                                    annex.setFileRelationId(relationId);
                                    annex.setTeId(file.getTeId());
                                    annex.setUploadStatus(1);
                                    annex.setFileEntityId(file.getFileid());
                                    annex.setFileCreateUId(file.getFilecreateuser());
                                    annex.setFileCreateUName(file.getFilecreateusername());
                                    annex.setFileName(pt.getTpName() + ".pdf");
                                    annex.setFilePath("/jimuReport/Archive/" + file.gettCode() + "/" + GenerateUUID.getUUID() + ".pdf");
                                    annex.setFileSuffix("pdf");
                                    annex.setFileLocation(1);
                                    annex.setFileReplace(0);
                                    annex.setArchive(1);
                                    annex.setFileSourceType("01");
                                    annex.setFileUpOss(Boolean.FALSE);
                                    annex.setFileJimuReport(Boolean.TRUE);
                                    String path = root + annex.getFilePath();
                                    //通过jimu报表生成PDF保存到指定位置
                                    path = FileUtil.httpPost2File(conf.getExportPdfUrl(), JacksonUtils.toJSONString(param), path, token);
                                    if (ValidateUtil.isEmpty(path))
                                        continue;
                                    File f = new File(path);
                                    if (f.exists() && f.isFile()) {
                                        annex.setFileSize(f.length());
                                        annex.setFileMd5Code(MD5File.getFileMD5Str(f));
                                    }
                                    boolean createFile = false;
                                    //endregion
                                    for (AutoArchiveDirectoryVo ad : adIds) {
                                        if (!ad.getAdParentId().equals("0") || ads.stream().filter(n -> n.getAdParentId().equals(ad.getAdId())).count() < 1) {
                                            createFile = true;
                                            //归档目录根节点 且存在子节点则不允许执行自动归档操作
                                            //region 归档信息
                                            Archive archivePojo = new Archive();
                                            archivePojo.setApId(GenerateUUID.getUUID());
                                            archivePojo.setAdId(ad.getAdId());
                                            archivePojo.setApDescCreateTime(now);
                                            archivePojo.setPId(pId);
                                            archivePojo.setApTitle(ad.getAdName());
//                                            archivePojo.setApTitle(pt.getTpName());
                                            archivePojo.setApState(1);
                                            archivePojo.setApDescCreateUid(file.getFilecreateuser());
                                            archivePojo.setApDescCreateUname(file.getFilecreateusername());
                                            archivePojo.setApPersonUid(file.getFilecreateuser());
                                            archivePojo.setApPersonUname(file.getFilecreateusername());
                                            archives.add(archivePojo);
                                            //endregion
                                            //region 归档文件信息
                                            //归档文件信息
                                            ArchiveFile afPojo = new ArchiveFile();
                                            afPojo.setApId(archivePojo.getApId());
                                            afPojo.setAfId(GenerateUUID.getUUID());
                                            afPojo.setAfCreateTime(now);
                                            afPojo.setAfFileId(file.getFileid());
                                            afPojo.setAfAnnexId(annex.getFileId());
                                            afPojo.setAfFileSize(MathUtil.FloatDivisionInt(Float.valueOf(annex.getFileSize()), 1024 * 1024));
                                            afPojo.setAfType(annex.getFileSuffix());
//                                        afPojo.setAfOrder(afOrder);
//                                        afOrder += 1;
                                            afPojo.setAfCode(annex.getFileInputCode());
                                            afPojo.setAfName(annex.getFileName());
                                            afPojo.setAfState(1);
                                            afPojo.setAfCreateUid(annex.getFileCreateUId());
                                            afPojo.setAfCreateUname(annex.getFileCreateUName());
                                            afPojo.setAfFrom(1);
                                            archiveFiles.add(afPojo);
                                            //endregion
                                        }
                                    }
                                    if (createFile) {
                                        fileAnnexes.add(annex);
                                        file.setFileJmArchive(Boolean.TRUE);
                                    } else {
                                        if (f.exists() && f.isFile())
                                            f.delete();
                                    }
                                }
                            }
                        }
                        newFiles.add(file);
                    }
                    if (!CollectionUtils.isEmpty(archives)) this.saveBatch(archives);
                    if (!CollectionUtils.isEmpty(archiveFiles)) archiveFileService.saveBatch(archiveFiles);
                    if (!CollectionUtils.isEmpty(fileAnnexes)) {
                        String finalRoot = root;
                        fileAnnexes.forEach(f -> {
                            String ossDir = f.getFilePath().substring(0, f.getFilePath().lastIndexOf("/") + 1);
                            //文件赋值后上传到OSS归档目录
                            if (ossConf != null && ossConf.isEnableOss()) {
                                String key = CrcOssUtil.upload(ossConf, finalRoot + f.getFilePath(), ossConf.getOssArchiveDir() + ossDir);
                                if (ValidateUtil.isNotEmpty(key)) f.setFileUpOss(Boolean.TRUE);
                            }
                        });
                        sysFileAnnexService.saveBatch(fileAnnexes);
                    }
                    if (!CollectionUtils.isEmpty(newFiles)) sysFileService.updateBatchById(newFiles);
                }
            }
        }
        return BaseResponse.ok();
    }

    private void getLogInfo(SysUser user, String token) {
        LogUserInfoVo uf = new LogUserInfoVo();
        uf.setId(user.getuId());
        uf.setCode(user.getuCode());
        uf.setUType(user.getuType());
        uf.setTeId(user.getTeId());
        uf.setUFirstLogin(0);
        redisUtils.set("firstLogin_" + token, 0);
        redisUtils.set("userLoginInfo_" + user.getuId(), JacksonUtils.toJSONString(uf));
    }

    private String getExcelConfigId(String apiUrl) {
        if (ValidateUtil.isEmpty(apiUrl)) return null;
        String[] strs = apiUrl.split("/");
        return strs[strs.length - 1];
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse autoSyncArchive() {
        List<SysFileAnnex> archives = sysFileAnnexService.getAllArchiveFile();
        if (!CollectionUtils.isEmpty(archives)) {
            String root = sysConfigService.getRootDirectory();
            String archiveD = sysConfigService.getArchiveDirectory();
            root = root.endsWith("/") ? root : root + "/";
            archiveD = archiveD.endsWith("/") ? archiveD : archiveD + "/";
            List<String> oldFileIds = new ArrayList<>();
            List<SysFileAnnex> arcs = new ArrayList<>();
            //查看已归档文件本地文件找不到的数据
            for (SysFileAnnex archive : archives) {
                File f = new File(archiveD + archive.getFilePath());
                if (!f.exists() || !f.isFile()) {
                    oldFileIds.add(archive.getFileUnarchiveId());
                    arcs.add(archive);
                }
            }
            if (!CollectionUtils.isEmpty(oldFileIds)) {
                //未归档文件信息
                List<SysFileAnnex> oldFiles = sysFileAnnexService.getBaseMapper().selectBatchIds(oldFileIds);
                if (!CollectionUtils.isEmpty(oldFileIds)) {
                    for (SysFileAnnex file : oldFiles) {
                        File f = new File(root + file.getFilePath());
                        if (f.exists() && f.isFile()) {
                            List<SysFileAnnex> syncFiles = arcs.stream().filter(n -> n.getFileUnarchiveId().equals(file.getFileId())).collect(Collectors.toList());
                            if (!CollectionUtils.isEmpty(syncFiles)) {
                                for (SysFileAnnex syncFile : syncFiles) {//重新同步拷贝实体文件
                                    FileUtil.copy(root + file.getFilePath(), archiveD + syncFile.getFilePath());
                                }
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    @Override
    public void refreshApStateBath(List<String> apIds, Integer apState) {
        if (!CollectionUtils.isEmpty(apIds)) baseMapper.refreshApStateBath(apIds, apState);
    }


    private String getPid(String pId, String secId, String conId) {
        if (ValidateUtil.isNotEmpty(pId)) return pId;
        if (ValidateUtil.isNotEmpty(secId)) {
            pId = baseMapper.getPidBySecId(secId);
            if (ValidateUtil.isNotEmpty(pId)) return pId;
        }
        if (ValidateUtil.isNotEmpty(conId)) {
            pId = baseMapper.getPidByConId(conId);
            if (ValidateUtil.isNotEmpty(pId)) return pId;
        }
        return null;
    }

    /**
     * 在自动归档后单独处理project_cp_rpr里的文件的文件编码
     *
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse autoArchiveProCpRprFileInputCode() {
        String archiveCreateInfo = sysConfigService.getSysConfByCode("archiveUserInfo");
        if (ValidateUtil.isEmpty(archiveCreateInfo)) {
            return BaseResponse.ok("未开启自动归档");
        }
        Integer count = baseMapper.autoArchiveProCpRprFileInputCode();
        return BaseResponse.ok(count);
    }
}
