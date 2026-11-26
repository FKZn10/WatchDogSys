package com.jpmsoft.fileUpload.service;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.export.request.GanttPlanExportRequest;
import com.jpmsoft.common.model.request.Contract.ConContractRequest;
import com.jpmsoft.common.model.requestAndResponse.InnerFileUploadAppointDTO;
import com.jpmsoft.common.model.response.LogUserInfoVo;
import com.jpmsoft.fileUpload.pojo.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

public interface IFileUploadService {

    /**
     * 内部使用
     * 上传文件到指定位置
     * 进保存文件到指定位置 不创建文件记录(已提前生成记录)
     *
     * @param file
     * @param path
     * @return
     */
    BaseResponse innerAppointUpload(@RequestParam MultipartFile file, @Validated InnerFileUploadAppointDTO path);

    BaseResponse uploadOss(MultipartFile file, HttpServletRequest request);

    /**
     * 上传文件
     *
     * @param files
     * @param updateType 暂定 普通上传和归档上传
     * @param status     文件上传状态 标记文件是否已上传保存
     * @return
     */
    BaseResponse uploadFile(MultipartFile[] files, UploadDto req, String updateType, int status, String teId);

    BaseResponse uploadImage2Pdf(MultipartFile[] files, UploadDto req, String updateType, int status, String teId);

    /**
     * 断点续传上传文件（分片上传）
     *
     * @param file 分片文件
     * @param req  断点上传请求参数
     * @param teId 租户ID
     * @return
     */
    BaseResponse chunkUpload(MultipartFile file, ChunkUploadDto req, String teId);

    /**
     * 上传文件 免token
     *
     * @param files
     * @param updateType 暂定 普通上传和归档上传
     * @param status     文件上传状态 标记文件是否已上传保存
     * @return
     */
    BaseResponse openUpload(MultipartFile[] files, UploadDto req, String updateType, int status);

    /**
     * 上传并替换文件
     *
     * @param oldId 原文件ID
     * @return
     */
    void uploadReplace(String oldId, LogUserInfoVo user, HttpServletRequest request, HttpServletResponse response) throws Exception;

    /**
     * 富文本编辑器上传文件
     *
     * @param files
     * @return
     */
    BaseResponse uploadRTEFile(MultipartFile[] files, UploadDto req, String teId);

    /**
     * 删除文件
     *
     * @param fileIds
     * @return
     */
    BaseResponse removeFileByIds(String fileIds);


    /**
     * 获取断点下载URL
     * 多文件打包下载 避免多次打包压缩
     *
     * @param fileIds
     * @return
     */
    String breakpointDownload(String fileIds);

    /**
     * 批量获取断点下载URL
     * 全部是单个文件
     *
     * @param files
     * @return
     */
    List<DownloadUrlDto> downloadAloneBath(List<ProFileAnnex> files);


    /**
     * 下载文件
     * 断点
     *
     * @param downId
     */
    void downloadFile(HttpServletRequest request, HttpServletResponse response, String downId, LogUserInfoVo user, Boolean down) throws Exception;

    /**
     * 下载文件
     * 确认为单文件下载
     *
     * @param downId
     */
    void downloadAloneFile(HttpServletRequest request, HttpServletResponse response, String downId, LogUserInfoVo user, Boolean down) throws Exception;

    /**
     * 下载文件
     * 归档文件
     *
     * @param request
     * @param response
     * @param downId
     * @throws Exception
     */
    void downloadArchivesFile(HttpServletRequest request, HttpServletResponse response, String downId, LogUserInfoVo user, Boolean down) throws Exception;


    void doc2Pdf2View(HttpServletRequest request, HttpServletResponse response, String watermarkId, String downId, LogUserInfoVo user) throws Exception;

    void watermarkDown(HttpServletRequest request, HttpServletResponse response, String downId, String waterId, LogUserInfoVo user) throws Exception;


    /**
     * 下载文件 单文件下载
     *
     * @param request
     * @param response
     * @param downId
     * @throws Exception
     */
    void downloadOpenFile(HttpServletRequest request, HttpServletResponse response, String downId) throws Exception;


    /**
     * 上传解析进度计划XML
     *
     * @param request
     * @param file
     * @return
     */
    BaseResponse uploadGanttPlanXml(HttpServletRequest request, MultipartFile file);


    /**
     * 上传进度计划excel或者Mpp
     *
     * @param file
     * @param request
     * @return
     */
    BaseResponse uploadGanttPlan(MultipartFile file, HttpServletRequest request);

    /**
     * 上传解析投资计划Excel
     *
     * @param request
     * @param file
     * @return
     */
    BaseResponse uploadInvPlanExcel(HttpServletRequest request, MultipartFile file);

    /**
     * 上传概算申报清单excel
     *
     * @param request
     * @param file
     * @return
     */
    BaseResponse uploadBudgetDeclareExcel(HttpServletRequest request, MultipartFile file) throws Exception;

    /**
     * 上传估算分解子表execl
     *
     * @param request
     * @param file
     * @return
     */
    BaseResponse uploadEstimateBreakdownExecel(HttpServletRequest request, MultipartFile file) throws Exception;

    /**
     * 下载估算分解子表execl模板
     *
     * @param response
     */
    void downloadEstimateBreakdownExecelTemplate(HttpServletResponse response);

    /**
     * 初设评审子系统：上传概算申报清单excel
     *
     * @param request
     * @param file
     * @return
     */
    BaseResponse uploadDrBudgetDeclareExcel(HttpServletRequest request, MultipartFile file);


    /**
     * 上传新版概算申报清单excel
     *
     * @param request
     * @param file
     * @return
     */
    BaseResponse uploadNewBudgetDeclareExcel(HttpServletRequest request, MultipartFile file) throws Exception;

    /**
     * 上传项目注册匡算
     *
     * @param request
     * @param file
     * @return
     */
    BaseResponse uploadProjectRegisterDtlExcel(HttpServletRequest request, MultipartFile file) throws Exception;


    /**
     * 上传项目立项估算
     *
     * @param request
     * @param file
     * @return
     */
    BaseResponse uploadPreProApprovalDtlExcel(HttpServletRequest request, MultipartFile file) throws Exception;

    /**
     * 上传项目注册概算
     *
     * @param request
     * @param file
     * @return
     */
    BaseResponse uploadPreProjectDtlExcel(HttpServletRequest request, MultipartFile file) throws Exception;

    /**
     * 决算清单上传
     *
     * @param request
     * @param file
     * @return
     * @throws Exception
     */
    BaseResponse uploadFinalAccountsApplyExcel(HttpServletRequest request, MultipartFile file) throws Exception;

    /**
     * 上传并压缩PDF
     *
     * @param request
     * @param file
     * @return
     */
    BaseResponse uploadAndCompressPdf(HttpServletRequest request, MultipartFile file);

    /**
     * 上传资金计划excel
     *
     * @param request
     * @param file
     * @param uId
     * @return
     */
    BaseResponse uploadInvProPlanExcel(HttpServletRequest request, MultipartFile file, String uId) throws Exception;

    /**
     * 上传工程报告excel
     *
     * @param request
     * @param type    1周报 2月报 3年度计划
     * @param file
     * @param uId
     * @return
     */
    @Deprecated
    BaseResponse uploadOaProReportExcel(HttpServletRequest request, Integer type, MultipartFile file, String uId);

    /**
     * 上传工程报告excel
     *
     * @param request
     * @param file
     * @param uId
     * @return
     */
    BaseResponse uploadCjOaProReportExcel(HttpServletRequest request, MultipartFile file, String uId);


    /**
     * 上传专家库excel
     *
     * @param request
     * @param file
     * @param uId
     * @return
     */
    BaseResponse uploadSecExpertExcel(HttpServletRequest request, MultipartFile file, String uId);

    /**
     * 导出进度计划word
     *
     * @param request
     * @param response
     * @param req
     */
    void exportGanttTasksProcessWord(HttpServletRequest request, HttpServletResponse response, GanttPlanExportRequest req, String uId);

    /**
     * 导出专家库选取word(线上)
     *
     * @param request
     * @param response
     * @param srId
     */
    void exportSecExpertSelectionRecordWord(HttpServletRequest request, HttpServletResponse response, String srId);

    /**
     * 导出专家库选取word(线下)
     *
     * @param request
     * @param response
     * @param srId
     */
    void exportExpertSelectionRecordWordOffline(HttpServletRequest request, HttpServletResponse response, String srId);

    /**
     * 批量下载表单相关文件
     *
     * @param request
     * @param response
     * @param dto
     */
    void batchDownloadByEntityIds(HttpServletRequest request, HttpServletResponse response, FormBatchDownDto dto, LogUserInfoVo user);

    /**
     * 根据多文件ID打包下载文件
     *
     * @param request
     * @param response
     * @param ids
     */
    void batchDownloadByFileIds(HttpServletRequest request, HttpServletResponse response, List<String> ids, LogUserInfoVo user);

    /**
     * 导入资金计划下达
     *
     * @param request
     * @param response
     * @param file
     * @throws Exception
     */
    BaseResponse importGmInvProjectPlan(HttpServletRequest request, HttpServletResponse response
            , MultipartFile file) throws Exception;

    /**
     * 导入工程量清单
     *
     * @param read 数据读取方式 index:列顺序 name:列名称
     * @return
     * @throws Exception
     */
    BaseResponse importConBoqDtl(HttpServletRequest request, HttpServletResponse response
            , MultipartFile file, String pId, String read) throws Exception;

    /**
     * 计量单审批-计量单导入
     *
     * @param request
     * @param response
     * @param file
     * @return
     * @throws Exception
     */
    BaseResponse importConMeasureRecord(HttpServletRequest request, HttpServletResponse response
            , MultipartFile file, String conId) throws Exception;

    /**
     * 导入基建投资月报 高校需求
     *
     * @param request
     * @param response
     * @param file
     * @return
     */
    BaseResponse importInfrastructureMonth(HttpServletRequest request, HttpServletResponse response
            , MultipartFile file);

    /**
     * 合同工程量清单xml
     *
     * @param request
     * @param file
     * @return
     */
    BaseResponse uploadBoqdXml(HttpServletRequest request, MultipartFile file);

    /**
     * zip批量导入表单数据
     *
     * @param files zip文件(excel+表单附件)
     * @param user  用户信息
     * @return
     */
    BaseResponse importFormData(MultipartFile[] files, FormDataImpDTO request, LogUserInfoVo user);

    /**
     * 通用表单导出
     *
     * @param req  请求入参
     * @param user 用户信息
     * @return
     */
    void exportFormData(HttpServletResponse response, FormDataExpDTO req, LogUserInfoVo user);

    /**
     * 临时上传xml
     *
     * @param file
     * @return
     */
    File temporaryStorageXml(MultipartFile file);

    File temporaryStorageFile(String root, MultipartFile file, String regex, String msg, String code);

    Boolean checkFileType(String fileName);

    Boolean checkFileType(String fileName, String regex);

    /**
     * 建设程序成果文件打包下载
     *
     * @param request
     * @param response
     */
    void achievementPackageDownload(List<ArchievementsDTO> request, HttpServletResponse response);


    BaseResponse uploadBudgetDeclareExcelBl(HttpServletRequest request, MultipartFile file);

    BaseResponse importProjectBusinessMonthlyProgress(HttpServletRequest request, MultipartFile file);

    ResponseEntity<byte[]> getFileWithoutFileAnnex(UploadDto request, HttpServletResponse response);

    BaseResponse uploadFileById(MultipartFile file, UploadDtoWithNoBlank req);

    ResponseEntity<byte[]> getFiles(UploadDto request, HttpServletResponse response) throws IOException;

    BaseResponse importChangeMeasureApproval(MultipartFile file);

    void exportContractFiles(HttpServletResponse response, ConContractRequest request, String token);

    void getOaNoticeReport(HttpServletResponse response, String id, String type, String token);

    BaseResponse jimuReportGenerate(String fileId);
}