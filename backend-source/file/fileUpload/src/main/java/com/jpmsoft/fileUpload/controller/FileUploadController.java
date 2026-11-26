package com.jpmsoft.fileUpload.controller;


import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.enums.AuditTypeEnum;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.model.export.request.GanttPlanExportRequest;
import com.jpmsoft.common.model.request.Contract.ConContractRequest;
import com.jpmsoft.common.model.requestAndResponse.Design.DesignDrawingModel;
import com.jpmsoft.common.model.requestAndResponse.InnerFileUploadAppointDTO;
import com.jpmsoft.common.model.response.LogUserInfoVo;
import com.jpmsoft.common.rabbit.AuditSender;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.fileUpload.constants.FileMsg;
import com.jpmsoft.fileUpload.pojo.dto.*;
import com.jpmsoft.fileUpload.pojo.dto.billofquantities.BillOfQuantitiesContext;
import com.jpmsoft.fileUpload.pojo.dto.billofquantities.BillOfQuantitiesExportDTO;
import com.jpmsoft.fileUpload.scheduled.AutoUpOssScheduled;
import com.jpmsoft.fileUpload.service.*;
import com.jpmsoft.fileUpload.utils.AsposeUtil;
import com.jpmsoft.fileUpload.utils.ExcelUtil;
import com.jpmsoft.fileUpload.utils.FileUtil;
import com.jpmsoft.fileUpload.utils.RedisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@RestController
@Api(tags = "附件上传下载", value = "附件上传下载")
@RequestMapping("/jpm")
public class FileUploadController {

    @Resource
    private IFileUploadService fileUploadService;

    @Resource
    private RedisUtils redisUtils;

    @Resource
    private IEasyExcelService easyExcelService;

    @Resource
    private AutoUpOssScheduled autoUpOssScheduled;
    @Resource
    private ISysConfigService sysConfigService;
    @Resource
    private AuditSender auditSender;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private IGanttTasksService ganttTasksService;
    @Resource
    private ISysUserService sysUserService;

    @Resource
    private IArchiveService archiveService;

    @Resource
    private IBillOfQuantitiesParseService billOfQuantitiesParseService;

    @Resource
    private IBillOfQuantitiesExportService billOfQuantitiesExportService;
    @Resource
    private IDesignDrawingService designDrawingService;

    @Resource
    private IMonthlyOutputService monthlyOutputService;

    @GetMapping("/testAutoUp")
    public BaseResponse encrySyExcel() {
        autoUpOssScheduled.autoUpOss();
        return BaseResponse.ok();
    }

    @ApiIgnore
    @ApiOperation(value = "删除已上传到OSS的文件")
    @GetMapping("/deleteOssLocalFile")
    public BaseResponse deleteOssLocalFile() {
        sysFileAnnexService.deleteOssLocalFile();
        return BaseResponse.ok();
    }

    @ApiIgnore
    @ApiOperation(value = "删除未确认保存的实体文件以及文件记录")
    @GetMapping("/deleteUnSaveFile")
    public BaseResponse deleteUnSaveFile() {
        sysFileAnnexService.deleteUnSaveFile();
        return BaseResponse.ok();
    }


    @GetMapping("/test")
    public void test(String a, String b, double w, double h) throws Exception {
        AsposeUtil.imgToPdf(a, b, w, h);
    }

    /**
     * 流程节点配置使用
     * 流程审批结束将积木报表附件生成并保存到Sys_File表 fileRelationId
     *
     * @param fileId
     * @return
     */
    @GetMapping("/jimuReportGenerate")
    public BaseResponse jimuReportGenerate(@RequestParam String fileId) {
        return fileUploadService.jimuReportGenerate(fileId);
    }

    //@GetMapping("/encrySyExcel")
    //public BaseResponse encrySyExcel(@RequestParam String in, @RequestParam String pwd) {
    //    CellsUtil.encrySyExcel(in, pwd);
    //    return BaseResponse.ok();
    //}
    //
    //@GetMapping("/encryWord")
    //public BaseResponse encryWord(@RequestParam String in, @RequestParam String out) throws Exception {
    //    AsposeUtil.encrySyWord(in, out, "123456");
    //    return BaseResponse.ok();
    //}


    /**
     * 内部使用
     * 上传文件到指定位置
     * 进保存文件到指定位置 不创建文件记录(已提前生成记录)
     *
     * @param file
     * @param path
     * @return
     */
    @ApiIgnore
    @ApiOperation(value = "上传文件到指定位置")
    @PostMapping("/innerAppointUpload")
    public BaseResponse innerAppointUpload(@RequestParam MultipartFile file, @Validated InnerFileUploadAppointDTO path) {
        return fileUploadService.innerAppointUpload(file, path);
    }

    @ApiIgnore
    @PostMapping("/innerUpload")
    public BaseResponse innerUpload(@RequestBody InnerFileUploadAppointDTO request) {
        return BaseResponse.ok(FileUtil.postInnerAppoint(request.isArchiveDir(), request.getFileName(), request.getPath(), request.getNewPath(), request.getUrl()));
    }

    @ApiOperation(value = "文件上传")
    @PostMapping("/uploadFile")
    public BaseResponse uploadFile(@RequestParam MultipartFile[] files, @Validated UploadDto req, @RequestHeader String token, HttpServletRequest request) {
        return fileUploadService.uploadFile(files, req, "root", 0, redisUtils.getTeId(token));
    }

    @ApiOperation(value = "图片上传转换为PDF")
    @PostMapping("/uploadImage2Pdf")
    public BaseResponse uploadImage2Pdf(@RequestParam MultipartFile[] files, @Validated UploadDto req, @RequestHeader String token, HttpServletRequest request) {
        return fileUploadService.uploadImage2Pdf(files, req, "root", 0, redisUtils.getTeId(token));
    }

    @PostMapping("/uploadFileById")
    public BaseResponse uploadFileById(@RequestParam MultipartFile files, @Validated UploadDtoWithNoBlank req, @RequestHeader String token, HttpServletRequest request) {
        return fileUploadService.uploadFileById(files, req);
    }

    @ApiOperation(value = "文件上传(公开上传免token)")
    @PostMapping("/openUpload")
    public BaseResponse openUpload(@RequestParam MultipartFile[] files, @Validated UploadDto req, HttpServletRequest request) {
        return fileUploadService.openUpload(files, req, "root", 0);
    }

    @ApiOperation(value = "断点续传上传文件（分片上传）")
    @PostMapping("/chunkUpload")
    public BaseResponse chunkUpload(@RequestParam MultipartFile file, @Validated ChunkUploadDto req, @RequestHeader String token, HttpServletRequest request) {
        try {
            return fileUploadService.chunkUpload(file, req, redisUtils.getTeId(token));
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            ex.printStackTrace();
            return BaseResponse.fail(FileMsg.fileUpError);
        }
    }

    @ApiOperation(value = "文件上传(上传即标记为已上传，无需刷新附件上传状态)")
    @PostMapping("/upload2Save")
    public BaseResponse upload2Save(@RequestParam MultipartFile[] files, @Validated UploadDto req, @RequestHeader String token, HttpServletRequest request) {
        if (ValidateUtil.isEmpty(req.getFileId())) return BaseResponse.fail(FileMsg.fileIdMust);
        BaseResponse res = fileUploadService.uploadFile(files, req, "root", 1, redisUtils.getTeId(token));
        auditSender.sendAudit("后台上传附件", redisUtils.getUserInfoByToken(token), req.getFileId(), AuditTypeEnum.add.getkey());
        return res;
    }

    /**
     * 上传并替换文件
     * 网关层不校验token 到此处校验（前端第三方框架文档未明确指出如何添加header）
     *
     * @param oldId
     * @param token
     * @param request
     * @param response
     */
    @ApiOperation(value = "上传并替换文件")
    @PostMapping("/uploadReplace")
    public void uploadReplace(@RequestParam String oldId, @RequestParam String token, HttpServletRequest request, HttpServletResponse response) {
        try {
            fileUploadService.uploadReplace(oldId, redisUtils.getUserInfoByToken(token), request, response);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            ex.printStackTrace();
            throw new JPMException(FileMsg.fileUpError);
        }
    }

    @ApiOperation(value = "富文本编辑器文件上传")
    @PostMapping("/uploadRTEFile")
    public BaseResponse uploadRTEFile(@RequestParam MultipartFile[] files, @Validated UploadDto req, @RequestHeader String token, HttpServletRequest request) {
        try {
            return fileUploadService.uploadRTEFile(files, req, redisUtils.getTeId(token));
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            ex.printStackTrace();
            return BaseResponse.fail(FileMsg.fileUpError);
        }
    }


    @ApiOperation(value = "归档文件上传（归档上传文件上传即保存（upload_status=1））")
    @PostMapping("/uploadArchiveFile")
    public BaseResponse uploadArchiveFile(@RequestParam MultipartFile[] files, @Validated UploadDto req, @RequestHeader String token, HttpServletRequest request) {
        return fileUploadService.uploadFile(files, req, "archive", 1, redisUtils.getTeId(token));
    }


    @ApiOperation(value = "获取断点下载URL")
    @GetMapping("/breakpointDownload")
    public String breakpointDownload(@RequestParam String fileIds) {
        try {
            String downloadUrl = sysConfigService.getDownloadUrl();
            if (ValidateUtil.isEmpty(downloadUrl)) throw new JPMException(FileMsg.missSysConfig);
            return downloadUrl + fileUploadService.breakpointDownload(fileIds);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            ex.printStackTrace();
            throw new JPMException(FileMsg.getDownUrlFail);
        }
    }

    /**
     * 删除文件
     * 批量
     *
     * @param fileIds
     * @return
     */
    @ApiOperation(value = "批量删除文件")
    @GetMapping("/removeFileByIds")
    public BaseResponse removeFileByIds(String fileIds) {
        try {
            return fileUploadService.removeFileByIds(fileIds);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            ex.printStackTrace();
            return BaseResponse.fail(FileMsg.delFileFail);
        }
    }

    @ApiOperation(value = "批量删除文件(会生成审计日志)")
    @GetMapping("/backDelFileByIds")
    public BaseResponse backDelFileByIds(@RequestParam String id, @RequestParam String fileIds, @RequestHeader String token) {
        try {
            BaseResponse res = fileUploadService.removeFileByIds(fileIds);
            auditSender.sendAudit("后台删除附件", redisUtils.getUserInfoByToken(token), id, AuditTypeEnum.del.getkey());
            return res;
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            ex.printStackTrace();
            return BaseResponse.fail(FileMsg.delFileFail);
        }
    }

    @ApiOperation(value = "批量删除文件(会生成审计日志)")
    @PostMapping("/backDelFileByRelations")
    public BaseResponse backDelFileByRelations(@RequestParam String id, @RequestHeader String token, @RequestBody List<String> relationIds) {
        try {
            BaseResponse res = fileUploadService.removeFileByIds(sysFileAnnexService.getFileIdsByRelationIds(relationIds));
            auditSender.sendAudit("后台删除附件", redisUtils.getUserInfoByToken(token), id, AuditTypeEnum.del.getkey());
            return res;
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            ex.printStackTrace();
            return BaseResponse.fail(FileMsg.delFileFail);
        }
    }

    /**
     * 下载文件， 断点下载
     *
     * @param downId
     */
    @ApiOperation(value = "下载文件， 断点下载")
    @GetMapping("/downloadFile/{downId}/{token}")
    public void downloadFile(HttpServletRequest request, HttpServletResponse response, @PathVariable("downId") String downId, @PathVariable("token") String token, @RequestParam(required = false) Boolean down) {
        try {
            fileUploadService.downloadFile(request, response, downId, redisUtils.getUserInfoByToken(token), down);
        } catch (JPMException jp) {
            throw jp;
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            ex.printStackTrace();
            throw new JPMException(FileMsg.downFail);
        }
    }

    @ApiOperation(value = "下载文件（确认为单文件下载）")
    @GetMapping("/downloadAloneFile/{downId}/{token}")
    public void downloadAloneFile(HttpServletRequest request, HttpServletResponse response, @PathVariable("downId") String downId, @PathVariable("token") String token, @RequestParam(required = false) Boolean down) {
        try {
            fileUploadService.downloadAloneFile(request, response, downId, redisUtils.getUserInfoByToken(token), down);
        } catch (JPMException jp) {
            throw jp;
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            ex.printStackTrace();
            throw new JPMException(FileMsg.downFail);
        }
    }

    @ApiOperation(value = "下载文件（确认为单文件下载,不验证token）")
    @GetMapping("/downloadAloneOpenFile/{fileId}")
    public void downloadAloneOpenFile(HttpServletRequest request, HttpServletResponse response, @PathVariable("fileId") String fileId) {
        try {
            fileUploadService.downloadAloneFile(request, response, fileId, null, null);
        } catch (JPMException jp) {
            throw jp;
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            ex.printStackTrace();
            throw new JPMException(FileMsg.downFail);
        }
    }


    @ApiOperation(value = "下载归档文件（确认为单文件下载）")
    @GetMapping("/downloadArchivesFile/{downId}/{token}")
    public void downloadArchivesFile(HttpServletRequest request, HttpServletResponse response, @PathVariable("downId") String downId, @PathVariable("token") String token, @RequestParam(required = false) Boolean down) {
        try {
            fileUploadService.downloadArchivesFile(request, response, downId, redisUtils.getUserInfoByToken(token), down);
        } catch (JPMException jp) {
            throw jp;
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            ex.printStackTrace();
            throw new JPMException(FileMsg.downFail);
        }
    }

    @ApiOperation(value = "doc或docx转pdf")
    @GetMapping("/doc2Pdf2View/{downId}")
    public void doc2Pdf2View(HttpServletRequest request, HttpServletResponse response, @RequestParam(required = false) String watermarkId, @PathVariable("downId") String downId, @RequestHeader String token) {
        try {
            LogUserInfoVo user = redisUtils.getUserInfoByToken(token);
            fileUploadService.doc2Pdf2View(request, response, watermarkId, downId, user);
        } catch (JPMException jp) {
            throw jp;
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            ex.printStackTrace();
            throw new JPMException(FileMsg.docx2PdfFail);
        }
    }


    @ApiOperation(value = "doc或pdf加图片水印后下载")
    @GetMapping("/watermarkDown/{downId}/{waterId}")
    public void watermarkDown(HttpServletRequest request, HttpServletResponse response, @PathVariable("downId") String downId, @PathVariable("waterId") String waterId, @RequestHeader String token) {
        try {
            LogUserInfoVo user = redisUtils.getUserInfoByToken(token);
            fileUploadService.watermarkDown(request, response, downId, waterId, user);
        } catch (JPMException jp) {
            throw jp;
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            ex.printStackTrace();
            throw new JPMException(FileMsg.waterDownFail);
        }
    }


    @ApiOperation(value = "下载文件（确认为单文件下载，不验证token）")
    @GetMapping("/downloadOpenFile/{downId}")
    public void downloadOpenFile(HttpServletRequest request, HttpServletResponse response, @PathVariable("downId") String downId) {
        try {
            fileUploadService.downloadOpenFile(request, response, downId);
        } catch (JPMException jp) {
            throw jp;
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            ex.printStackTrace();
            throw new JPMException(FileMsg.downFail);
        }
    }

    @ApiOperation(value = "上传进度计划XML")
    @PostMapping("/uploadGanttPlanXml")
    public BaseResponse uploadGanttPlanXml(@RequestParam MultipartFile file, HttpServletRequest request) {
        try {
            return fileUploadService.uploadGanttPlanXml(request, file);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            ex.printStackTrace();
            throw new JPMException(FileMsg.fileParsingFail);
        }
    }


    @ApiOperation(value = "上传进度计划excel或者Mpp")
    @PostMapping("/uploadGanttPlan")
    public BaseResponse uploadGanttPlan(@RequestParam MultipartFile file, HttpServletRequest request) {
        return fileUploadService.uploadGanttPlan(file, request);
    }

    @ApiOperation(value = "上传进度计划excel或者Mpp,Xml")
    @PostMapping("/uploadGanttPlanOption")
    public BaseResponse uploadGanttPlanOption(@RequestParam MultipartFile file, HttpServletRequest request) {
        String name = ValidateUtil.isNotEmpty(file.getOriginalFilename()) ? file.getOriginalFilename() : file.getName();
        String suffx = name.substring(name.lastIndexOf(".") + 1);
        if (suffx.equalsIgnoreCase("xml")) return fileUploadService.uploadGanttPlanXml(request, file);
        return fileUploadService.uploadGanttPlan(file, request);
    }


    @ApiOperation(value = "上传投资计划excel")
    @PostMapping("/uploadInvPlanExcel")
    public BaseResponse uploadInvPlanExcel(@RequestParam MultipartFile file, HttpServletRequest request) {
        return fileUploadService.uploadInvPlanExcel(request, file);
    }

    @ApiOperation(value = "上传概算申报清单excel")
    @PostMapping("/uploadBudgetDeclareExcel")
    public BaseResponse uploadBudgetDeclareExcel(@RequestParam MultipartFile file, HttpServletRequest request) {
        try {
            return fileUploadService.uploadBudgetDeclareExcel(request, file);
        } catch (JPMException jp) {
            return BaseResponse.fail(jp.getCode(), jp.getMessage());
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            ex.printStackTrace();
            throw new JPMException(FileMsg.fileParsingFail);
        }
    }

    @ApiOperation(value = "上传估算分解子表execel")
    @PostMapping("/uploadEstimateBreakdownExecel")
    public BaseResponse uploadEstimateBreakdownExecel(@RequestParam MultipartFile file, HttpServletRequest request) {
        try {
            return fileUploadService.uploadEstimateBreakdownExecel(request, file);
        } catch (JPMException jp) {
            return BaseResponse.fail(jp.getCode(), jp.getMessage());
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            ex.printStackTrace();
            throw new JPMException(FileMsg.fileParsingFail);
        }
    }

    @ApiOperation("下载估算分解子表execl模板")
    @GetMapping("/downloadEstimateBreakdownExecelTemplate")
    public void downloadEstimateBreakdownExecelTemplate(HttpServletResponse response) {
        try {
            fileUploadService.downloadEstimateBreakdownExecelTemplate(response);
        } catch (Exception ex) {
            log.error("下载估算分解子表execl模板失败：{}", ex.getMessage());
            throw new JPMException(FileMsg.fileParsingFail);
        }
    }

    @ApiOperation(value = "（初设评审）上传新概算申报清单excel")
    @PostMapping("/uploadDrBudgetDeclareExcel")
    public BaseResponse uploadDrBudgetDeclareExcel(@RequestParam MultipartFile file, HttpServletRequest request) {
        try {
            return fileUploadService.uploadDrBudgetDeclareExcel(request, file);
        } catch (JPMException jp) {
            return BaseResponse.fail(jp.getCode(), jp.getMessage());
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            ex.printStackTrace();
            throw new JPMException(FileMsg.fileParsingFail);
        }
    }

    @ApiOperation(value = "上传新概算申报清单excel")
    @PostMapping("/uploadNewBudgetDeclareExcel")
    public BaseResponse uploadNewBudgetDeclareExcel(@RequestParam MultipartFile file, HttpServletRequest request) {
        try {
            return fileUploadService.uploadNewBudgetDeclareExcel(request, file);
        } catch (JPMException jp) {
            return BaseResponse.fail(jp.getCode(), jp.getMessage());
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            ex.printStackTrace();
            throw new JPMException(FileMsg.fileParsingFail);
        }
    }

    @ApiOperation(value = "上传项目注册匡算excel")
    @PostMapping("/uploadProjectRegisterDtlExcel")
    public BaseResponse uploadProjectRegisterDtlExcel(@RequestParam MultipartFile file, HttpServletRequest request) {
        try {
            return fileUploadService.uploadProjectRegisterDtlExcel(request, file);
        } catch (JPMException jp) {
            return BaseResponse.fail(jp.getCode(), jp.getMessage());
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            ex.printStackTrace();
            throw new JPMException(FileMsg.fileParsingFail);
        }
    }


    @ApiOperation(value = "上传项目立项估算excel")
    @PostMapping("/uploadPreProApprovalDtlExcel")
    public BaseResponse uploadPreProApprovalDtlExcel(@RequestParam MultipartFile file, HttpServletRequest request) {
        try {
            return fileUploadService.uploadPreProApprovalDtlExcel(request, file);
        } catch (JPMException jp) {
            return BaseResponse.fail(jp.getCode(), jp.getMessage());
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            ex.printStackTrace();
            throw new JPMException(FileMsg.fileParsingFail);
        }
    }

    @ApiOperation(value = "上传项目信息匡算excel")
    @PostMapping("/uploadPreProjectDtlExcel")
    public BaseResponse uploadPreProjectDtlExcel(@RequestParam MultipartFile file, HttpServletRequest request) {
        try {
            return fileUploadService.uploadPreProjectDtlExcel(request, file);
        } catch (JPMException jp) {
            return BaseResponse.fail(jp.getCode(), jp.getMessage());
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            ex.printStackTrace();
            throw new JPMException(FileMsg.fileParsingFail);
        }
    }


    @ApiOperation(value = "上传决算申报清单excel")
    @PostMapping("/uploadFinalAccountsApplyExcel")
    public BaseResponse uploadFinalAccountsApplyExcel(@RequestParam MultipartFile file, HttpServletRequest request) {
        try {
            return fileUploadService.uploadFinalAccountsApplyExcel(request, file);
        } catch (JPMException jp) {
            return BaseResponse.fail(jp.getCode(), jp.getMessage());
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            ex.printStackTrace();
            throw new JPMException(FileMsg.fileParsingFail);
        }
    }

    @ApiIgnore
    @Deprecated
    @ApiOperation(value = "上传并压缩PDF")
    @PostMapping("/uploadAndCompressPdf")
    public BaseResponse uploadAndCompressPdf(HttpServletRequest request, @RequestParam MultipartFile file) {
        try {
            return fileUploadService.uploadAndCompressPdf(request, file);
        } catch (JPMException jp) {
            return BaseResponse.fail(jp.getCode(), jp.getMessage());
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            ex.printStackTrace();
            throw new JPMException("下载异常");
        }
    }

    @ApiOperation(value = "上传资金计划excel")
    @PostMapping("/uploadInvProPlanExcel")
    public BaseResponse uploadInvProPlanExcel(HttpServletRequest request, @RequestParam MultipartFile file, @RequestHeader String token) {
        try {
            log.info("uploadInvProPlanExcel");
            return fileUploadService.uploadInvProPlanExcel(request, file, redisUtils.getUid(token));
        } catch (JPMException jpm) {
            return BaseResponse.fail(jpm.getCode(), jpm.getMessage());
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            ex.printStackTrace();
            throw new JPMException(FileMsg.fileParsingFail);
        }
    }

    @ApiOperation(value = "上传工程报告excel")
    @PostMapping("/uploadOaProReportExcel")
    public BaseResponse uploadOaProReportExcel(HttpServletRequest request, @RequestParam Integer type, @RequestParam MultipartFile file, @RequestHeader String token) {
        log.info("uploadOaProReportExcel");
        return fileUploadService.uploadOaProReportExcel(request, type, file, redisUtils.getUid(token));
    }

    @ApiOperation(value = "上传工程报告excel(城建计划)")
    @PostMapping("/uploadCjOaProReportExcel")
    public BaseResponse uploadCjOaProReportExcel(HttpServletRequest request, @RequestParam MultipartFile file, @RequestHeader String token) {
        log.info("uploadCjOaProReportExcel");
        return fileUploadService.uploadCjOaProReportExcel(request, file, redisUtils.getUid(token));

    }


    @ApiOperation(value = "上传专家库excel")
    @PostMapping("/uploadSecExpertExcel")
    public BaseResponse uploadSecExpertExcel(HttpServletRequest request, @RequestParam MultipartFile file, @RequestHeader String token) {
        log.info("uploadSecExpertExcel");
        return fileUploadService.uploadSecExpertExcel(request, file, redisUtils.getUid(token));
    }


    @ApiOperation(value = "导出进度计划Word")
    @PostMapping("/exportGanttTasksProcessWord")
    public void exportGanttTasksProcessWord(HttpServletRequest request, HttpServletResponse response, @RequestBody GanttPlanExportRequest req, @RequestHeader String token) {
        try {
            fileUploadService.exportGanttTasksProcessWord(request, response, req, redisUtils.getUid(token));
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            ex.printStackTrace();
            throw new JPMException(FileMsg.exportGanttPlanWordFail);
        }
    }


    @ApiOperation(value = "导出专家选取Word(线上)")
    @GetMapping("/exportSecExpertSelectionRecordWord")
    public void exportSecExpertSelectionRecordWord(HttpServletRequest request, HttpServletResponse response, @RequestParam String srId) {
        fileUploadService.exportSecExpertSelectionRecordWord(request, response, srId);
    }

    @ApiOperation(value = "导出专家选取Word(线下)")
    @GetMapping("/exportExpertSelectionRecordWordOffline")
    public void exportExpertSelectionRecordWordOffline(HttpServletRequest request, HttpServletResponse response, @RequestParam String srId) {
        fileUploadService.exportExpertSelectionRecordWordOffline(request, response, srId);
    }

    @ApiOperation(value = "多表单ID打包下载文件")
    @PostMapping("/batchDownloadByEntityIds")
    public void batchDownloadByEntityIds(HttpServletRequest request, HttpServletResponse response, @RequestBody FormBatchDownDto dto, @RequestHeader String token) {
        fileUploadService.batchDownloadByEntityIds(request, response, dto, redisUtils.getUserInfoByToken(token));
    }

    @ApiOperation(value = "根据多文件ID打包下载文件")
    @PostMapping("/batchDownloadByFileIds")
    public void batchDownloadByFileIds(HttpServletRequest request, HttpServletResponse response, @RequestBody List<String> ids, @RequestHeader String token) {
        fileUploadService.batchDownloadByFileIds(request, response, ids, redisUtils.getUserInfoByToken(token));
    }

    @ApiOperation(value = "资金计划下达导入（光明）")
    @PostMapping("/importGmInvProjectPlan")
    public BaseResponse importGmInvProjectPlan(HttpServletRequest request, HttpServletResponse response, @RequestParam MultipartFile file) {
        try {
            return fileUploadService.importGmInvProjectPlan(request, response, file);
        } catch (JPMException ex) {
            return BaseResponse.fail(ex.getCode(), ex.getMessage());
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            ex.printStackTrace();
            throw new JPMException(FileMsg.importInvProjectPlanFail);
        }
    }

    @ApiOperation(value = "工程量清单导入")
    @PostMapping("/importConBoqDtl")
    public BaseResponse importConBoqDtl(HttpServletRequest request, HttpServletResponse response, @RequestParam MultipartFile file, @RequestParam String pId) {
        try {
            return fileUploadService.importConBoqDtl(request, response, file, pId, "index");
        } catch (JPMException ex) {
            return BaseResponse.fail(ex.getCode(), ex.getMessage());
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            ex.printStackTrace();
            throw new JPMException(FileMsg.importConBopDtlFail);
        }
    }

    @ApiOperation(value = "工程量清单导入(识别excel表头名称)")
    @PostMapping("/importConBoqDtlByName")
    public BaseResponse importConBoqDtlByName(HttpServletRequest request, HttpServletResponse response, @RequestParam MultipartFile file, @RequestParam String pId) {
        try {
            return fileUploadService.importConBoqDtl(request, response, file, pId, "name");
        } catch (JPMException ex) {
            return BaseResponse.fail(ex.getCode(), ex.getMessage());
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            ex.printStackTrace();
            throw new JPMException(FileMsg.importConBopDtlFail);
        }
    }

    /**
     * 第三方工程量清单excel解析-义乌城投（未来软件）
     *
     * @param file
     * @param response
     * @throws Exception
     */
    @PostMapping("/parseBillOfEngineeringQuantities")
    public void parseBillOfEngineeringQuantities(MultipartFile file, HttpServletResponse response) throws Exception {
        BillOfQuantitiesContext billOfQuantitiesContext = billOfQuantitiesParseService.parse(file);
        List<BillOfQuantitiesExportDTO> billOfQuantitiesExportDTOList = billOfQuantitiesExportService.export(billOfQuantitiesContext);
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("工程量清单-" + System.currentTimeMillis() + ".xlsx", StandardCharsets.UTF_8.name().replaceAll("\\+", "%20"));
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        ExcelUtil.export2OutputStream(billOfQuantitiesExportDTOList, BillOfQuantitiesExportDTO.class, response.getOutputStream(), "sheet1");
    }


    @ApiOperation(value = "计量单审批-计量单导入")
    @PostMapping("/importConMeasureRecord")
    public BaseResponse importConMeasureRecord(HttpServletRequest request, HttpServletResponse response, @RequestParam MultipartFile file, @RequestParam String conId) {
        try {
            return fileUploadService.importConMeasureRecord(request, response, file, conId);
        } catch (JPMException ex) {
            return BaseResponse.fail(ex.getCode(), ex.getMessage());
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            ex.printStackTrace();
            throw new JPMException(FileMsg.importConBopDtlFail);
        }
    }

    @ApiOperation(value = "导入基建投资月报（高校）")
    @PostMapping("/importInfrastructureMonth")
    public BaseResponse importInfrastructureMonth(HttpServletRequest request, HttpServletResponse response, @RequestParam MultipartFile file) {
        return fileUploadService.importInfrastructureMonth(request, response, file);

    }

    @ApiOperation(value = "上传合同工程量清单XML")
    @PostMapping("/uploadBoqdXml")
    public BaseResponse uploadBoqdXml(@RequestParam MultipartFile file, HttpServletRequest request) {
        try {
            return fileUploadService.uploadBoqdXml(request, file);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            ex.printStackTrace();
            throw new JPMException(FileMsg.fileParsingFail);
        }
    }

    @ApiOperation(value = "导出基础配置信息")
    @GetMapping("/exportSysConf")
    public void exportConf(HttpServletResponse response) {
        easyExcelService.exportConf(response);
    }

    @ApiOperation(value = "导入基础配置信息")
    @PostMapping("/importSysConf")
    public BaseResponse importSysConf(MultipartFile file, @RequestHeader String token) {
        return easyExcelService.importSysConf(file, redisUtils.getUserInfoByToken(token));
    }

    @ApiOperation(value = "导出文档模板信息")
    @GetMapping("/exportSysFileTemp")
    public void exportSysFileTemp(HttpServletResponse response) {
        easyExcelService.exportSysFileTemp(response);
    }

    @ApiOperation(value = "导入文档模板信息")
    @PostMapping("/importSysFileTemp")
    public BaseResponse importSysFileTemp(MultipartFile file, @RequestHeader String token) {
        return easyExcelService.importSysFileTemp(file, redisUtils.getUserInfoByToken(token));
    }

    @ApiOperation(value = "导入季报")
    @PostMapping("/importOaQuarterlyReport")
    public BaseResponse importOaQuarterlyReport(MultipartFile file, @RequestParam Integer year, @RequestParam Integer quarter) {
        return easyExcelService.importOaQuarterlyReport(file, year, quarter);
    }

    @ApiOperation(value = "通用导入表单数据仅支持ZIP")
    @PostMapping("/importFormData")
    public BaseResponse importFormData(MultipartFile[] files, FormDataImpDTO request, @RequestHeader String token) {
        if (ValidateUtil.isNotEmpty(request.getTopJson()))
            request.setTops(JacksonUtils.parseArray(request.getTopJson(), String.class));
        return fileUploadService.importFormData(files, request, redisUtils.getUserInfoByToken(token));
    }

    @ApiOperation(value = "通用导出")
    @PostMapping("/exportFormData")
    public void exportFormData(HttpServletResponse response, @RequestBody FormDataExpDTO req, @RequestHeader String token) {
        fileUploadService.exportFormData(response, req, redisUtils.getUserInfoByToken(token));
    }

    @ApiOperation(value = "导出进度计划执行反馈")
    @GetMapping("/exportGanttTaskFeedback")
    public void exportGanttTaskFeedback(HttpServletResponse response, @RequestParam String planId) {
        ganttTasksService.exportGanttTaskFeedback(planId, response);
    }

    @ApiOperation(value = "导出进度计划执行反馈ZIP")
    @GetMapping("/exportGanttTaskFeedBackZip")
    public void exportGanttTaskFeedBackZip(HttpServletResponse response, @RequestParam String planId) {
        ganttTasksService.exportGanttTaskFeedBackZip(planId, response);
    }

    @ApiOperation(value = "导入进度计划执行反馈")
    @PostMapping("/importGanttTaskFeedback")
    public BaseResponse importGanttTaskFeedback(@RequestParam MultipartFile file, @RequestHeader String token) {
        return ganttTasksService.importGanttTaskFeedback(file, redisUtils.getUserInfoByToken(token));
    }

    @ApiOperation(value = "导入进度计划执行反馈zip")
    @PostMapping("/importGanttTaskFeedbackZip")
    public BaseResponse importGanttTaskFeedbackZip(@RequestParam MultipartFile file, @RequestHeader String token) throws Exception {
        return ganttTasksService.importGanttTaskFeedbackZip(file, redisUtils.getUserInfoByToken(token));
    }

    @ApiOperation(value = "建设程序成果文件打包下载")
    @PostMapping("/achievementPackageDownload")
    public void achievementPackageDownload(@RequestBody List<ArchievementsDTO> request, HttpServletResponse response) {
        fileUploadService.achievementPackageDownload(request, response);
    }

    @ApiOperation(value = "导出组织机构内用户信息excel")
    @PostMapping("/exportUserWithDepartment")
    public void exportUserWithDepartment(@RequestBody UserDTO user, HttpServletResponse response) {
        sysUserService.exportUserWithDepartment(user, response);
    }

    @ApiOperation(value = "导出项目组织机构内用户信息excel")
    @PostMapping("/exportUserWithDepartmentP")
    public void exportUserWithDepartmentP(@RequestBody UserDTO user, HttpServletResponse response) {
        sysUserService.exportUserWithDepartmentP(user, response);
    }

    @ApiOperation(value = "导出图纸管理zip文件-润微")
    @PostMapping("/exportDesignDrawing")
    public void exportDesignDrawing(HttpServletResponse response, @RequestBody DesignDrawingModel request, @RequestHeader String token) {
        log.info("exportDesignDrawing");
        designDrawingService.exportDesignDrawing(response, request, token);
    }

    @ApiOperation(value = "导入月度投资计划-河北")
    @PostMapping("/importMonthlyInvestmentPlan")
    public BaseResponse importMonthlyInvestmentPlan(@RequestParam MultipartFile file, @RequestHeader String token) {
        log.info("importMonthlyInvestmentPlan");
        return monthlyOutputService.importMonthlyOutput(file, token);
    }

    @PostMapping("/uploadBudgetDeclareExcelBl")
    public BaseResponse uploadBudgetDeclareExcelBl(@RequestParam MultipartFile file, HttpServletRequest request) {
        try {
            return fileUploadService.uploadBudgetDeclareExcelBl(request, file);
        } catch (JPMException jp) {
            return BaseResponse.fail(jp.getCode(), jp.getMessage());
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            ex.printStackTrace();
            throw new JPMException(FileMsg.fileParsingFail);
        }
    }

    // 导入项目业务情况月度进展 - 江苏省住建厅
    @PostMapping("/importProjectBusinessMonthlyProgress")
    public BaseResponse importProjectBusinessMonthlyProgress(@RequestParam MultipartFile file, HttpServletRequest request) {
        try {
            return fileUploadService.importProjectBusinessMonthlyProgress(request, file);
        } catch (JPMException jp) {
            return BaseResponse.fail(jp.getCode(), jp.getMessage());
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            ex.printStackTrace();
            throw new JPMException(FileMsg.fileParsingFail);
        }
    }

    @PostMapping("/getFileWithoutFileAnnex")
    public ResponseEntity<byte[]> getFileWithoutFileAnnex(@RequestBody UploadDto request, HttpServletResponse response) {
        return fileUploadService.getFileWithoutFileAnnex(request, response);
    }

    @PostMapping("/getFiles")
    public ResponseEntity<byte[]> getFiles(@RequestBody UploadDto request, HttpServletResponse response) throws IOException {
        return fileUploadService.getFiles(request, response);
    }

    @ApiOperation(value = "导出用户列表")
    @PostMapping("/exportUserList")
    public void exportUserList(@RequestBody UserDTO user, @RequestHeader String token, HttpServletResponse response) {
        log.info("exportUserList");
        sysUserService.exportUserList(user, redisUtils.getUid(token), response);
    }

    @ApiOperation(value = "导入变更计量审批单")
    @PostMapping("/importChangeMeasureApproval")
    public BaseResponse importChangeMeasureApproval(@RequestParam MultipartFile file, @RequestHeader String token) {
        return fileUploadService.importChangeMeasureApproval(file);
    }

    @ApiOperation("导出合同附件")
    @PostMapping("/exportContractFiles")
    public void exportContractFiles(HttpServletResponse response, @RequestBody ConContractRequest request, @RequestHeader String token) {
        log.info("exportContractFiles");
        fileUploadService.exportContractFiles(response, request, token);
    }

    @ApiOperation("发文管理报表")
    @GetMapping("/getOaNoticeReport")
    public void getOaNoticeReport(HttpServletResponse response, @RequestParam("id") String id, @RequestParam("type") String type, @RequestHeader String token) {
        log.info("getOaNoticeReport");
        fileUploadService.getOaNoticeReport(response, id, type, token);
    }
}