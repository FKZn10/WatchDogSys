package com.jpmsoft.fileUpload.controller;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.RefershFileDto;
import com.jpmsoft.common.model.request.AchievementsRequest;
import com.jpmsoft.common.model.request.Archive.FileServerToArchiveRequest;
import com.jpmsoft.common.model.request.ImgCompressAndRefreshStateDTO;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.fileUpload.constants.FileMsg;
import com.jpmsoft.fileUpload.pojo.dto.*;
import com.jpmsoft.fileUpload.pojo.entity.SysFileAnnex;
import com.jpmsoft.fileUpload.pojo.vo.FileResVo;
import com.jpmsoft.fileUpload.service.IArchiveFileService;
import com.jpmsoft.fileUpload.service.IArchiveService;
import com.jpmsoft.fileUpload.service.ISysFileAnnexService;
import com.jpmsoft.fileUpload.service.ISysJiMuReportService;
import com.jpmsoft.fileUpload.utils.RedisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@Api(tags = "文件记录", value = "文件记录")
@RequestMapping("/fileRecord")
public class SysFileAnnexController {

    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private IArchiveService archiveService;
    @Resource
    private IArchiveFileService archiveFileService;
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private ISysJiMuReportService sysJiMuReportService;

    @ApiOperation(value = "根据流程提交生成记录生成积木报表文件")
    @GetMapping("/generateJimuReportPdf")
    public BaseResponse generateJimuReportPdf() {
        sysJiMuReportService.generateJimuReportPdf();
        return BaseResponse.ok();
    }

    //@GetMapping("/getFileMD5Str")
    //public BaseResponse getFileMD5Str(@RequestParam String path) {
    //    try {
    //        return BaseResponse.ok(MD5FileCheck.getFileMD5Str(new File(path)));
    //    } catch (Exception ex) {
    //        throw new JPMException("文件MD5失败");
    //    }
    //}
    @ApiOperation(value = "归档指定项目下归档上传的问题（档案归档模块 归档上传+系统文件归档上传 不包含业务上传）")
    @GetMapping("/archiveProjectFile")
    public BaseResponse archiveProjectFile(@RequestParam String faId) {
        return archiveFileService.archiveProjectFile(faId);
    }

    @ApiOperation(value = "全文检索搜索归档文件信息")
    @PostMapping("/fullSearchArchiveFile")
    public BaseResponse fullSearchArchiveFile(@RequestBody FullTextDTO request) {
        return archiveFileService.fullSearch(request);
    }

    @ApiOperation(value = "自动同步归档文件")
    @GetMapping("/autoSyncArchive")
    public BaseResponse autoSyncArchive() {
        archiveService.autoSyncArchive();
        return BaseResponse.ok();
    }

    @ApiOperation(value = "刷新库内历史文件md5值")
    @GetMapping("/refershFileMd5Code")
    public BaseResponse refershFileMd5Code(@RequestParam int num) {
        sysFileAnnexService.refershFileMd5Code(num);
        return BaseResponse.ok();
    }

    /**
     * 对内接口
     * 修改表单时 刷新表单附件
     *
     * @param data
     * @return
     */
    @ApiOperation(value = "修改表单时 刷新表单附件")
    @PostMapping("/refreshRelationFile")
    public BaseResponse refreshRelationFile(@RequestBody List<Map<String, String>> data, @RequestParam String entityId) {
        try {
            return sysFileAnnexService.refreshRelationFile(data, entityId);
        } catch (Exception ex) {
            log.error(ex.getMessage(),ex);
            ex.printStackTrace();
            return BaseResponse.fail(FileMsg.refreshFail);
        }
    }

    @ApiOperation(value = "修改表单时 刷新表单附件(批量)")
    @PostMapping("/refreshRelationFileBath")
    public BaseResponse refreshRelationFileBath(@RequestBody List<RefershFileDto> request) {
        return sysFileAnnexService.refreshRelationFileBath(request);
    }


    @ApiOperation(value = "表单时复制(复制其他表单文件)并刷新(复制的文件和自己表单文件)表单附件")
    @PostMapping("/addFormCopyAndRefreshRelationFile")
    public BaseResponse addFormCopyAndRefreshRelationFile(@RequestBody List<Map<String, String>> data, @RequestParam String entityId) {
        try {
            return sysFileAnnexService.addFormCopyAndRefreshRelationFile(data, entityId);
        } catch (Exception ex) {
            log.error(ex.getMessage(),ex);
            ex.printStackTrace();
            return new BaseResponse("服务内部错误！");
        }
    }

    /**
     * 对内接口
     * 新增表单时 刷新表单附件
     *
     * @param ids
     * @return
     */
    @ApiOperation(value = "新增表单时 刷新表单附件")
    @PostMapping("/addFormRefreshRelationFile")
    public BaseResponse addFormRefreshRelationFile(@RequestBody List<String> ids, @RequestParam String entityId) {
        try {
            return sysFileAnnexService.addFormRefreshRelationFile(ids, entityId);
        } catch (Exception ex) {
            log.error(ex.getMessage(),ex);
            ex.printStackTrace();
            return BaseResponse.fail(FileMsg.refreshFail);
        }
    }

    /**
     * 获取文件信息
     *
     * @param fileRelationId
     * @return
     */
    @ApiOperation(value = "获取文件信息（未归档文件）")
    @GetMapping("/getFileByFileRelationId")
    public List<FileResVo> getFileByFileRelationId(@RequestParam String fileRelationId) {
        return sysFileAnnexService.getFileByFileRelationId(fileRelationId, 0, false);
    }

    /**
     * 获取文件信息（包含全部文件）
     *
     * @param fileRelationId
     * @return
     */
    @ApiOperation(value = "获取所有文件信息（未归档文件）")
    @GetMapping("/getAllFileByFileRelationId")
    public List<FileResVo> getAllFileByFileRelationId(@RequestParam String fileRelationId) {
        return sysFileAnnexService.getFileByFileRelationId(fileRelationId, 0, true);
    }

    @ApiOperation(value = "批量获取relation对应文件数量（未归档文件）")
    @PostMapping("/getFileCountByRelationIds")
    public BaseResponse getFileCountByRelationIds(@RequestBody List<String> fileRelationIds) {
        log.info("批量获取relation对应文件数量");
        return sysFileAnnexService.getFileCountByRelationIds(fileRelationIds, 0);
    }


    @ApiOperation(value = "查询表单积木报表附件信息")
    @GetMapping("/getJimuFileCountByFileId")
    public BaseResponse getJimuFileCountByFileId(@RequestParam String id) {
        log.info("查询表单积木报表附件信息");
        return sysFileAnnexService.getJimuFileCountByFileId(id, 0);
    }

    /**
     * 获取文件信息
     *
     * @param fileRelationId
     * @return
     */
    @ApiOperation(value = "批量获取文件信息(未归档文件)")
    @PostMapping("/getFileByFileRelationIds")
    public List<FileResVo> getFileByFileRelationIds(@RequestBody List<String> fileRelationId) {
        if (CollectionUtils.isEmpty(fileRelationId)) return null;
        return sysFileAnnexService.getFileByFileRelationIds(fileRelationId, 0);
    }


    @ApiOperation(value = "批量获取文件信息(未归档文件,不验证token)")
    @PostMapping("/getOpenFileByRelationIds")
    public BaseResponse getOpenFileByRelationIds(@RequestBody List<String> fileRelationId) {
        if (CollectionUtils.isEmpty(fileRelationId)) return BaseResponse.ok();
        return BaseResponse.ok(sysFileAnnexService.getFileByFileRelationIds(fileRelationId, 0));
    }


    @ApiOperation(value = "批量获取文件信息(非归档文件)")
    @PostMapping("/getFileByRelationIds")
    public BaseResponse getFileByRelationIds(@RequestBody List<String> relationIds) {
        if (CollectionUtils.isEmpty(relationIds)) return BaseResponse.fail();
        return sysFileAnnexService.getFileByRelationIds(relationIds, 0);
    }


    /**
     * 获取文件信息
     *
     * @param fileRelationId
     * @return
     */
    @ApiOperation(value = "获取文件信息（归档文件）")
    @GetMapping("/getArchiveFileByFileRelationId")
    public List<FileResVo> getArchiveFileByFileRelationId(@RequestParam String fileRelationId) {
        if (ValidateUtil.isEmpty(fileRelationId)) return null;
        return sysFileAnnexService.getFileByFileRelationId(fileRelationId, 1, false);
    }

    @ApiOperation(value = "批量获取relation对应文件数量（归档文件）")
    @PostMapping("/getArchiveFileCountByRelationIds")
    public BaseResponse getArchiveFileCountByRelationIds(@RequestBody List<String> fileRelationIds) {
        log.info("批量获取relation对应文件数量");
        return sysFileAnnexService.getFileCountByRelationIds(fileRelationIds, 1);
    }

    /**
     * 获取文件信息
     *
     * @param fileRelationId
     * @return
     */
    @ApiOperation(value = "批量获取文件信息(归档文件)")
    @PostMapping("/getArchiveFileByFileRelationIds")
    public List<FileResVo> getArchiveFileByFileRelationIds(@RequestBody List<String> fileRelationId) {
        if (CollectionUtils.isEmpty(fileRelationId)) return null;
        return sysFileAnnexService.getFileByFileRelationIds(fileRelationId, 1);
    }

    @ApiOperation(value = "批量获取文件信息(归档文件)")
    @PostMapping("/getArchiveFileByRelationIds")
    public BaseResponse getArchiveFileByRelationIds(@RequestBody List<String> relationIds) {
        if (ValidateUtil.isEmpty(relationIds)) return null;
        return sysFileAnnexService.getFileByRelationIds(relationIds, 1);
    }


    @ApiIgnore
    @ApiOperation(value = "获取成果文件信息")
    @PostMapping("/getAchievementsList")
    public BaseResponse getAchievementsList(@RequestBody AchievementsRequest request) {
        return sysFileAnnexService.getAchievementsList(request);
    }

    @ApiOperation(value = "获取未归档的文件信息根据项目ID")
    @PostMapping("/getUnArchiveFileRecordByPpId")
    public BaseResponse getUnArchiveFileRecordByPpId(@RequestBody ArchiveFileRecordDto request) {
        return sysFileAnnexService.getUnArchiveFileRecordByPpId(request);
    }

    @ApiOperation(value = "归档文件")
    @PostMapping("/archiveFile")
    public BaseResponse archiveFile(@RequestBody FileServerToArchiveRequest request) {
        return sysFileAnnexService.archiveFile(request.getDatas(), request.getApId(), request.getFileCode(), request.getApState());
    }

    /**
     * 复制文件上传记录到新的relationId
     * 同时复制文件
     *
     * @param model
     * @return
     */
    @ApiOperation(value = "复制文件上传记录到新的relationId")
    @PostMapping("/copyFileAnnex2NewRelation")
    public BaseResponse copyFileAnnex2NewRelation(@RequestBody CopyFileAnnexNewRelationDto model, @RequestParam String entityId,
                                                  @RequestParam(required = false, defaultValue = "false") Boolean skipArchive) {
        return sysFileAnnexService.copyFileAnnex2NewRelation(model.getOldIds(), model.getNewIds(), entityId, skipArchive);
    }

    @ApiOperation(value = "根据File信息复制文件到新目录")
    @PostMapping("/copyFileWithFileAnnex")
    public BaseResponse copyFileWithFileAnnex(@RequestBody List<SysFileAnnex> model, @RequestParam(required = false, defaultValue = "false") Boolean skipArchive) {
        return sysFileAnnexService.copyFileWithFileAnnex(model, skipArchive);
    }

    @ApiOperation(value = "复制文件上传记录到新的relationId")
    @PostMapping("/copyFile2NewRelation")
    public BaseResponse copyFile2NewRelation(@RequestBody CopyFileAnnexNewRelationDto model) {
        return sysFileAnnexService.copyFileAnnex2NewRelation(model.getOldIds(), model.getNewIds(), model.getEntityId());
    }

    @ApiOperation(value = "复制文件上传记录到新的relationId")
    @PostMapping("/copyFile2NewRelationNoEntityId")
    public BaseResponse copyFile2NewRelationNoEntityId(@RequestBody CopyFileAnnexNewRelationDto model) {
        return sysFileAnnexService.copyFileAnnex2NewRelation(model.getOldIds(), model.getNewIds());
    }

    /**
     * 替换表单文件
     * 未归档的表单附件（不包含流程附件Sys_File）
     * 主要场景： 例：项目变更审批通过后同步文件到项目（同步后删除原文件）
     *
     * @param model
     * @param entityId
     * @return
     */
    @ApiOperation(value = "复制文件上传记录到新的relationId")
    @PostMapping("/replaceFormFile")
    public BaseResponse replaceFormFile(@RequestBody CopyFileAnnexNewRelationDto model, @RequestParam String entityId) {
        return sysFileAnnexService.replaceFormFile(model.getOldIds(), model.getNewIds(), entityId);
    }


    @ApiOperation(value = "查询系统文件（历史（来源）本地上传的文件） 默认流程审批结束的业务相关的本地上传的附件")
    @PostMapping("/getSystemFileList")
    public BaseResponse getSystemFileList(@RequestBody SystemFileQueryDto query, @RequestHeader String token) {
        return sysFileAnnexService.getSystemFileList(query, redisUtils.getUid(token));
    }

    /**
     * 表单操作 选择上传系统历史文件(选择关联历史记录，不上传实体文件)
     * 创建临时数据（暂未复制实体文件），保存表单时再刷新附件状态
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "表单操作 选择上传系统历史文件(选择关联历史记录，不上传实体文件) 创建临时数据（暂未复制实体文件），保存表单时再刷新附件状态")
    @PostMapping("/uploadSystemFile")
    public BaseResponse uploadSystemFile(@RequestBody SystemUploadDto request) {
        return sysFileAnnexService.uploadSystemFile(request);
    }

    @ApiOperation(value = "根据表单id获取所有相关的附件信息（包含表单附件、通用附件、流程附件）")
    @GetMapping("/getFileAnnexByFileId")
    public BaseResponse getFileAnnexByFileId(@RequestParam String id) {
        return BaseResponse.ok(sysFileAnnexService.getFileAnnexByFileId(id));
    }

    @PostMapping("/saveFileAnnex")
    public BaseResponse saveFileAnnex(@RequestBody SysFileAnnex sysFileAnnex) {
        sysFileAnnexService.save(sysFileAnnex);
        return BaseResponse.ok();
    }

    /**
     * 刷新图片上传状态，同时生成对应的压缩后的缩略图
     *
     * @param files
     * @return
     */
    @PostMapping("/refreshImgAndCompress")
    public BaseResponse refreshImgAndCompress(@RequestBody List<ImgCompressAndRefreshStateDTO> files,@RequestParam String entityId) {
        return sysFileAnnexService.refreshImgAndCompress(files,entityId);
    }

    /**
     * 校验文件是否存在以及是否符合，将符合的文件返回
     *
     * @param files
     * @return
     */
    @PostMapping("/validateFileExist")
    public BaseResponse validateFileExist(@RequestBody List<SysFileAnnex> files, @RequestParam(defaultValue = "all") String fileType) {
        log.info("validateFileExist");
        return sysFileAnnexService.validateFileExist(files, fileType);
    }

    /**
     * 根据文件id刷新文件上传状态
     *
     * @param fileId
     * @return
     */
    @ApiOperation("根据文件id刷新文件上传状态")
    @GetMapping("/refreshFileAnnexUploadStatusByFileId")
    public BaseResponse refreshFileAnnexUploadStatusByFileId(@RequestParam String fileId) {
        log.info("refreshFileAnnexUploadStatusByFileId");
        return sysFileAnnexService.refreshFileAnnexUploadStatusByFileId(fileId);
    }
}
