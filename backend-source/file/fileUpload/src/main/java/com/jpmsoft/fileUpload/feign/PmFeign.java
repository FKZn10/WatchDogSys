package com.jpmsoft.fileUpload.feign;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Contract.ConContractRequest;
import com.jpmsoft.common.model.request.Qs.QsRecordDutyDTO;
import com.jpmsoft.common.model.request.Qs.QualitySafetyStatisticFowYWRequest;
import com.jpmsoft.common.model.request.project.PreProjectRequest;
import com.jpmsoft.common.model.requestAndResponse.Design.DesignDrawingModel;
import com.jpmsoft.common.model.requestAndResponse.Project.ProvPilotEnterpriseImportRequest;
import com.jpmsoft.common.model.requestAndResponse.Project.ProvPilotEnterpriseModel;
import com.jpmsoft.common.model.requestAndResponse.Project.ProvPilotProjectImportRequest;
import com.jpmsoft.common.model.requestAndResponse.Project.ProvPilotProjectModel;
import com.jpmsoft.common.model.response.Contract.ConContractResponse;
import com.jpmsoft.common.model.response.Project.PreProjectResponse;
import com.jpmsoft.common.model.response.pay.ConPayPrintResponse;
import com.jpmsoft.common.model.response.projectOutline.OutlineProjectManageResponse;
import com.jpmsoft.fileUpload.pojo.dto.ProSafetyLedgerDtlDTO;
import com.jpmsoft.fileUpload.pojo.dto.QsPlanTaskDto;
import com.jpmsoft.fileUpload.pojo.vo.QualitySafetyStatisticFowYWVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/10/10 10:06
 */
@FeignClient(name = "project-producer", contextId = "project-producer")
public interface PmFeign {

    @GetMapping("/v1/qsPlanTask/getQsPlanTaskById")
    BaseResponse getQsPlanTaskById(@RequestParam String id);

    @PostMapping("/v1/qsPlanTask/getQsRecordDutyByQsrIds")
    List<QsRecordDutyDTO> getQsRecordDutyByQsrIds(@RequestBody List<String> ids);

    @PostMapping("/v1/project/getProjectListExcel")
     List<PreProjectResponse> getProjectListExcel(@RequestBody PreProjectRequest request, @RequestHeader String token);

    @PostMapping("/v1/contract/getContractListExcel")
    List<ConContractResponse> getContractListExcel(@RequestBody ConContractRequest request, @RequestHeader String token);

    @PostMapping("/v1/qsPlanTask/getQsPlanTaskListExport")
    BaseResponse getQsPlanTaskListExport(@RequestBody QsPlanTaskDto request, @RequestHeader String token);

    @PostMapping("/v1/qsRecord/getQualitySafetyStatisticFowYWExport")
    List<QualitySafetyStatisticFowYWVO> getQualitySafetyStatisticFowYWExport(@RequestBody QualitySafetyStatisticFowYWRequest request);

    @PostMapping("/v1/qsPlanTask/getProSafetyLedgerPageYWExport")
    List<ProSafetyLedgerDtlDTO> getProSafetyLedgerPageYWExport(@RequestBody ProSafetyLedgerDtlDTO request, @RequestHeader String token);

    @PostMapping("/v1/designDrawing/getDesignDrawingExportRW")
    List<DesignDrawingModel> getDesignDrawingExportRW(@RequestBody DesignDrawingModel request, @RequestHeader String token);

    @PostMapping("/v1/contract/getContractListByName")
    List<ConContractResponse> getContractListByName(@RequestBody Set<String> request, @RequestHeader String token);
    @PostMapping("/v1/qsPlanTask/getProSafetyLedgerPageYWExportNew")
    List<ProSafetyLedgerDtlDTO> getProSafetyLedgerPageYWExportNew(@RequestBody ProSafetyLedgerDtlDTO request, @RequestHeader String token);

    @GetMapping("/v1/projectOutline/selectOutlineProjectManageById")
    OutlineProjectManageResponse selectOutlineProjectManageById(@RequestParam("id") String id);

    @PostMapping("/v1/projectOutline/importOutlineProjectWord")
    BaseResponse importOutlineProjectWord(@RequestBody LinkedHashMap<String, String> map, @RequestParam("osmId") String osmId, @RequestHeader String token);

    @PostMapping("/v1/contract/exportContractFiles")
    List<ConContractResponse> exportContractFiles(@RequestBody ConContractRequest request);

    /**
     * 按支付ID获取付款申请书打印数据（合同支付 + 审批链）
     * 复用 projectProducer 现有接口，此处仅新增 Feign 声明，不影响原逻辑
     *
     * @param id 支付ID(payId)
     * @return 打印数据（含合同/支付/审批记录）
     */
    @GetMapping("/v1/conPay/getConPayPrintByPayId")
    BaseResponse getConPayPrintByPayId(@RequestParam("id") String id);

    /**
     * 查询本周未填报的在建项目周进度清单。
     *
     * @return 未填报的项目、合同及责任人信息
     */
    @GetMapping("/v1/projectWeeklyProgressReport/getMissingWeeklyProgressReportList")
    List<LinkedHashMap<String, Object>> getMissingWeeklyProgressReportList();

    /**
     * 省试点项目 导入（Excel 解析后调用 PP 落库并建 sys_file）
     */
    @PostMapping("/v1/provPilotProject/importProvPilotProject")
    BaseResponse importProvPilotProject(@RequestBody ProvPilotProjectImportRequest request, @RequestHeader String token);

    /**
     * 省试点项目 导出数据
     */
    @PostMapping("/v1/provPilotProject/getExportList")
    List<ProvPilotProjectModel> getProvPilotProjectExportList(@RequestBody ProvPilotProjectModel request, @RequestHeader String token);

    /**
     * 省试点企业 导入
     */
    @PostMapping("/v1/provPilotEnterprise/importProvPilotEnterprise")
    BaseResponse importProvPilotEnterprise(@RequestBody ProvPilotEnterpriseImportRequest request, @RequestHeader String token);

    /**
     * 省试点企业 导出数据
     */
    @PostMapping("/v1/provPilotEnterprise/getExportList")
    List<ProvPilotEnterpriseModel> getProvPilotEnterpriseExportList(@RequestBody ProvPilotEnterpriseModel request, @RequestHeader String token);
}
