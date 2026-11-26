package com.jpmsoft.fileUpload.controller;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.model.response.Archive.ArchiveFileResponse;
import com.jpmsoft.common.model.response.LogUserInfoVo;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.fileUpload.constants.FileMsg;
import com.jpmsoft.fileUpload.enums.FileSourceEnum;
import com.jpmsoft.fileUpload.pojo.dto.ArchiveCopyMoveDTO;
import com.jpmsoft.fileUpload.pojo.dto.ArchiveDirTreeDTO;
import com.jpmsoft.fileUpload.pojo.dto.ArchiveFileDirDTO;
import com.jpmsoft.fileUpload.pojo.dto.FileAnnexInfoDto;
import com.jpmsoft.fileUpload.pojo.entity.Archive;
import com.jpmsoft.fileUpload.pojo.entity.ArchiveDirectory;
import com.jpmsoft.fileUpload.pojo.entity.ArchiveFile;
import com.jpmsoft.fileUpload.pojo.entity.SysFileAnnex;
import com.jpmsoft.fileUpload.service.*;
import com.jpmsoft.fileUpload.utils.FileUtil;
import com.jpmsoft.fileUpload.utils.MD5File;
import com.jpmsoft.fileUpload.utils.MathUtil;
import com.jpmsoft.fileUpload.utils.RedisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/4/17 9:17
 */
@Slf4j
@RestController
@Api(tags = "档案归档", value = "档案归档")
@RequestMapping("/archive")
public class ArchiveController {
    @Resource
    private ISysConfigService sysConfigService;
    @Resource
    private IFileUploadService fileUploadService;
    @Resource
    private RedisUtils redisUtils;

    @Resource
    private IArchiveFileService archiveFileService;
    @Resource
    private IArchiveDirectoryService archiveDirectoryService;
    @Resource
    private IArchiveService archiveService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Value("${file.upload.dirPath}")
    private String dirPath;

    private static final String roName = "档案归档目录";


    @ApiOperation(value = "自动归档")
    @GetMapping("/autoArchive")
    public BaseResponse autoArchive() {
        return archiveService.autoArchive();
    }

    @ApiOperation(value = "归档文件复制迁移到指定归档目录")
    @PostMapping("/archiveCopyMove")
    public BaseResponse archiveCopyMove(@RequestBody ArchiveCopyMoveDTO request) {
        if (CollectionUtils.isEmpty(request.getAfIds()) || ValidateUtil.isEmpty(request.getAdId()))
            return BaseResponse.fail(FileMsg.noReqParam);
        String root = sysConfigService.getArchiveDirectory();
        if (ValidateUtil.isEmpty(root)) return BaseResponse.fail(FileMsg.missSysConfig);
        List<ArchiveFile> archiveFiles = archiveFileService.listByIds(request.getAfIds());
        if (CollectionUtils.isEmpty(archiveFiles)) return BaseResponse.fail(FileMsg.notFoundArchiveFile);
        List<String> apIds = archiveFiles.stream().map(ArchiveFile::getApId).collect(Collectors.toList());
        List<String> fileIds = archiveFiles.stream().map(ArchiveFile::getAfAnnexId).collect(Collectors.toList());
        List<SysFileAnnex> curFile = sysFileAnnexService.getArchiveFile(request.getAdId());
        List<Archive> archives = archiveService.listByIds(apIds);
        List<SysFileAnnex> files = sysFileAnnexService.listByIds(fileIds);
        Map<String, String> apMap = new HashMap<>();
        archives.forEach(a -> {
            String old = a.getApId();
            a.setApId(GenerateUUID.getUUID());
            apMap.put(old, a.getApId());
            if (a.getAdId().equals(request.getAdId())) throw new JPMException(FileMsg.archiveMvTML);
            a.setAdId(request.getAdId());
        });
        Map<String, String> annexMap = new HashMap<>();
        archiveFiles.forEach(f -> {
            f.setAfId(GenerateUUID.getUUID());
            f.setApId(apMap.get(f.getApId()));
            String old = f.getAfAnnexId();
            f.setAfAnnexId(GenerateUUID.getUUID());
            annexMap.put(old, f.getAfAnnexId());
        });
        files.forEach(f -> {
            if (!CollectionUtils.isEmpty(curFile)) {
                List<SysFileAnnex> fileAnnexes = curFile.stream().filter(e -> e.getFileName().equals(f.getFileName())).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(fileAnnexes))
                    throw new JPMException(String.format(FileMsg.archiveTM, f.getFileName()));
            }
            f.setFileId(annexMap.get(f.getFileId()));
            f.setFileRelationId(GenerateUUID.getUUID());
            String path = f.getFilePath();
            f.setFilePath(archivePath(f.getFileCreateUId(), f.getFileSuffix(), request.getAdId()));
            //拷贝迁移文件
            FileUtil.copy(root + path, root + f.getFilePath());
        });
        if (!CollectionUtils.isEmpty(archives)) archiveService.saveBatch(archives);
        if (!CollectionUtils.isEmpty(archiveFiles)) archiveFileService.saveBatch(archiveFiles);
        if (!CollectionUtils.isEmpty(files)) sysFileAnnexService.saveBatch(files);
        return BaseResponse.ok();
    }

    @ApiOperation(value = "导出档案归档目录文件")
    @PostMapping("/archiveDirZip")
    public void archiveDirZip(@RequestBody List<ArchiveDirTreeDTO> data, HttpServletResponse response) {
        String root = sysConfigService.getRootDirectory();
        if (ValidateUtil.isEmpty(root)) throw new JPMException(FileMsg.missSysConfig);
        String gen = root + "/JPBPMTemporaryFolder/" + GenerateUUID.getUUID();
        File genF = new File(gen);
        if (!genF.isDirectory() || !genF.exists()) genF.mkdirs();
        for (ArchiveDirTreeDTO d : data) {
            String path = gen + "/" + roName + "/" + d.getAdName() + "(" + d.getAdId() + ")";
            mkdir(path);
            mkdirChildren(d.getChildNodes(), path);
        }
        String downZip = root + "/JPBPMTemporaryFolder/" + GenerateUUID.getUUID() + ".zip";
        FileUtil.zip(gen, downZip);
        FileUtil.downFileAndDelFile(response, downZip, "档案归档目录.zip");
        FileUtil.deleteFiles(genF, true);
    }


    @ApiOperation(value = "上传档案归档目录文件")
    @PostMapping("/importArchiveDirZip")
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse importArchiveDirZip(@RequestParam MultipartFile file, @RequestParam(required = false) String projectId, @RequestHeader String token) {
        String root = sysConfigService.getRootDirectory();
        String archive = sysConfigService.getArchiveDirectory();
        if (ValidateUtil.isAnObjEmpty(root, archive)) throw new JPMException(FileMsg.missSysConfig);
        String gen = root + "/JPBPMTemporaryFolder/" + GenerateUUID.getUUID();
        File genF = new File(gen);
        if (!genF.isDirectory() || !genF.exists()) genF.mkdirs();
        File tfFile = fileUploadService.temporaryStorageFile(root, file, ".*\\.(zip)$", FileMsg.onlyZip, "ArchiveDirUpload");
        String decPath = root + "/JPBPMTemporaryFolder/decompression/" + GenerateUUID.getUUID();
        FileUtil.decompression(tfFile.getPath(), decPath);
        //删除上传的压缩包
        tfFile.delete();
        String ro = decPath + "/" + roName;//解压后扫描文件根目录
        File genDir = new File(ro);
        if (!genDir.isDirectory() || !genDir.exists()) return BaseResponse.fail(FileMsg.dirJgError);
        File[] files = genDir.listFiles();
        if (files != null && files.length > 0) {
            List<ArchiveFileDirDTO> archiveFiles = new ArrayList<>();
            Set<String> adIds = new HashSet<>();
            String regex = sysConfigService.getSysConfByCode("fileUploadXzRegex");
            for (File e : files) {
                if (e.isDirectory())
                    scanFile(e, archiveFiles, regex, adIds);
            }
            if (!CollectionUtils.isEmpty(archiveFiles)) {
                List<ArchiveFileResponse> olds = archiveFileService.getByAdIds(new ArrayList<>(adIds), projectId);
                LogUserInfoVo user = redisUtils.getUserInfoByToken(token);
                List<Archive> archives = new ArrayList<>();
                List<ArchiveFile> ariFiles = new ArrayList<>();
                List<SysFileAnnex> fileAnnexes = new ArrayList<>();
                Date now = new Date();
                int order = 1;
                boolean outTm = false;
                List<String> names = new ArrayList<>();
                List<ArchiveDirectory> directories=archiveDirectoryService.getDirByIds(archiveFiles.stream().map(ArchiveFileDirDTO::getAdId).distinct().collect(Collectors.toList()));
                for (ArchiveFileDirDTO f : archiveFiles) {
                    File wj = new File(f.getPath());
                    String[] name = wj.getName().split("\\.");
                    String fileSuffix = name[name.length - 1];
                    String md5 = MD5File.getFileMD5Str(wj);
                    Boolean addFlag = true;
                    if (!CollectionUtils.isEmpty(olds)) {
                        List<ArchiveFileResponse> old = olds.stream().filter(d -> d.getFileMd5Code().equals(md5) && f.getAdId().equals(d.getAdId())).collect(Collectors.toList());
                        if (!CollectionUtils.isEmpty(old)) {
                            addFlag = false;
                            outTm = true;
                            names.add(wj.getName());
                        }
                    }
                    if (addFlag) {
                        Archive a = new Archive();
                        a.setAdId(f.getAdId());
                        a.setApId(GenerateUUID.getUUID());
                        a.setPId(projectId);
                        a.setApDescCreateUid(user.getId());
                        a.setApDescCreateUname(user.getName());
                        a.setApDescCreateTime(now);
                        a.setApState(1);
                        List<ArchiveDirectory> dirs=directories.stream().filter(d->d.getAdId().equals(f.getAdId())).collect(Collectors.toList());
                        if (!CollectionUtils.isEmpty(dirs))
                          a.setApTitle(dirs.get(0).getAdName());
                        else
                          a.setApTitle(wj.getName().substring(0, wj.getName().length() - fileSuffix.length() - 1));
                        archives.add(a);
                        SysFileAnnex annex = new SysFileAnnex();
                        annex.setFileSize(wj.length());
                        annex.setFileId(GenerateUUID.getUUID());
                        annex.setFileRelationId(GenerateUUID.getUUID());
                        annex.setFileSourceType(FileSourceEnum.UserUpload.getIndex());
                        annex.setArchive(1);
                        annex.setFileLocation(1);
                        annex.setUploadStatus(1);
                        annex.setFileReplace(0);
                        annex.setAdId(f.getAdId());
                        annex.setFileUploadTime(now);
                        annex.setFileName(wj.getName());
                        annex.setFileSuffix(fileSuffix);
                        annex.setTeId(user.getTeId());
                        annex.setFileUpOss(Boolean.FALSE);
                        annex.setFileCreateUId(user.getId());
                        annex.setFileCreateUName(user.getName());
                        annex.setFilePath(archivePath(user.getId(), fileSuffix, f.getAdId()));
                        annex.setFileMd5Code(md5);
                        //拷贝迁移文件
                        FileUtil.copy(f.getPath(), archive + annex.getFilePath());
                        fileAnnexes.add(annex);
                        ArchiveFile af = new ArchiveFile();
                        af.setAfId(GenerateUUID.getUUID());
                        af.setAfType(fileSuffix);
                        af.setAfOrder(order);
                        af.setApId(a.getApId());
                        af.setAfAnnexId(annex.getFileId());
                        af.setAfCreateUid(user.getId());
                        af.setAfCreateUname(user.getName());
                        af.setAfCreateTime(now);
                        af.setAfFrom(1);
                        af.setAfName(wj.getName());
                        af.setAfState(1);
                        af.setAfIndex(Boolean.FALSE);
                        af.setAfFileSize(MathUtil.FloatDivisionInt(Float.valueOf(annex.getFileSize()), 1024 * 1024));
                        ariFiles.add(af);
                        order += 1;
                    }
                }
                if (!CollectionUtils.isEmpty(fileAnnexes)) sysFileAnnexService.saveBatch(fileAnnexes);
                if (!CollectionUtils.isEmpty(archives)) archiveService.saveBatch(archives);
                if (!CollectionUtils.isEmpty(ariFiles)) archiveFileService.saveBatch(ariFiles);
                if (outTm) return BaseResponse.ok(FileMsg.tmFileOut, names);
            }
        }
        //执行完成删除解压的文件以及目录
        FileUtil.deleteFiles(genF, Boolean.TRUE);
        return BaseResponse.ok();
    }


    private String archivePath(String uid, String fileSuffix, String adId) {
        String dateDir = new SimpleDateFormat("yyyy/MM").format(new Date());
        String filePath = dirPath + adId + "/" + uid + "/" + dateDir;
        File fileDir = new File(filePath);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        String fileName = GenerateUUID.getUUID();
        return filePath + "/" + fileName + "." + fileSuffix;
    }

    private void scanFile(File e, List<ArchiveFileDirDTO> archiveFiles, String regex, Set<String> adIds) {
        File[] files = e.listFiles();
        if (ValidateUtil.notContains(e.getName(), "(", ")")) throw new JPMException(FileMsg.dirJgError);
        String adId = e.getName().substring(e.getName().indexOf("(") + 1, e.getName().indexOf(")"));
        for (File f : files) {
            if (f.isDirectory())//下级目录
                scanFile(f, archiveFiles, regex, adIds);
            else { //附件
                if (fileUploadService.checkFileType(f.getName(), regex)) {//合法文件
                    adIds.add(adId);
                    ArchiveFileDirDTO archive = new ArchiveFileDirDTO();
                    archive.setAdId(adId);
                    archive.setPath(f.getPath());
                    archiveFiles.add(archive);
                }
            }
        }
    }

    private void mkdirChildren(List<ArchiveDirTreeDTO> data, String gen) {
        if (!CollectionUtils.isEmpty(data)) {
            for (ArchiveDirTreeDTO d : data) {
                String path = gen + "/" + d.getAdName() + "(" + d.getAdId() + ")";
                mkdir(path);
                mkdirChildren(d.getChildNodes(), path);
            }
        }
    }

    private void mkdir(String path) {
        File genF = new File(path);
        if (!genF.isDirectory() || !genF.exists()) genF.mkdirs();
    }


    @ApiOperation(value = "查询文件上传集合")
    @PostMapping("/getSysfileAnnexList")
    public BaseResponse  getSysfileAnnexList(@RequestBody FileAnnexInfoDto vo){
        return  sysFileAnnexService.getSysfileAnnexList(vo);
    }


    @ApiOperation(value = "登录日志导出")
    @PostMapping("/ExportUserLoginLog")
    public ResponseEntity<byte[]> ExportSysfileAnnex(@RequestBody FileAnnexInfoDto dto, HttpServletResponse response){
        return  sysFileAnnexService.ExportSysfileAnnex(dto,response);
    }

    @ApiOperation(value = "补全文件MD5值")
    @GetMapping("/completeFileMd5")
    public BaseResponse completeFileMd5() {
        log.info("completeFileMd5");
        return sysFileAnnexService.completeFileMd5();
    }
}
