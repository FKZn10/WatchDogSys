package com.jpmsoft.projectConsumer.feign;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.NoticeInfoCloseModel;
import com.jpmsoft.common.model.export.request.GanttPlanExportRequest;
import com.jpmsoft.common.model.request.Account.BankAccountChangeRequest;
import com.jpmsoft.common.model.request.Account.BankAccountRequest;
import com.jpmsoft.common.model.request.Claim.PcAwardTicketRequest;
import com.jpmsoft.common.model.request.Claim.PcClaimRequest;
import com.jpmsoft.common.model.request.Completion.CompletionAcceptanceAllRequest;
import com.jpmsoft.common.model.request.Completion.CompletionFilingRequest;
import com.jpmsoft.common.model.request.Completion.CompletionHandoverRequest;
import com.jpmsoft.common.model.request.Construction.CompletionAcceptanceRequest;
import com.jpmsoft.common.model.request.Construction.ConConstructionOrgDesignRequest;
import com.jpmsoft.common.model.request.Contract.ConContractRequest;
import com.jpmsoft.common.model.request.Contract.ConEcRequest;
import com.jpmsoft.common.model.request.Design.DesignConstructionRequest;
import com.jpmsoft.common.model.request.Design.DesignInitialRequest;
import com.jpmsoft.common.model.request.Design.DesignQrCodeRequest;
import com.jpmsoft.common.model.request.Design.DesignResultsIssuedRequest;
import com.jpmsoft.common.model.request.Integrity.FunctionalIntegrityWarningRequest;
import com.jpmsoft.common.model.request.InvestmentControl.InvCompleteMonthRequest;
import com.jpmsoft.common.model.request.InvestmentControl.InvPlanYearRequest;
import com.jpmsoft.common.model.request.Maintenance.*;
import com.jpmsoft.common.model.request.*;
import com.jpmsoft.common.model.request.Plan.*;
import com.jpmsoft.common.model.request.Qs.*;
import com.jpmsoft.common.model.request.RiskWarn.RiskWarQueryRequest;
import com.jpmsoft.common.model.request.RiskWarn.RiskWarnIndexRequest;
import com.jpmsoft.common.model.request.RiskWarn.RiskWarnRequest;
import com.jpmsoft.common.model.request.Subcontract.SubcontractApplyRequest;
import com.jpmsoft.common.model.request.Subcontract.SubcontractRecordRequest;
import com.jpmsoft.common.model.request.Subcontract.SubcontractorRecordRequest;
import com.jpmsoft.common.model.request.Supervisor.ConSupDisclosureRequest;
import com.jpmsoft.common.model.request.Supervisor.ConSupOnSiteRequest;
import com.jpmsoft.common.model.request.Supervisor.ConSupPlanRequest;
import com.jpmsoft.common.model.request.Supervisor.PunishRequest;
import com.jpmsoft.common.model.request.challengePlan.ChallengePlanAnnualRequest;
import com.jpmsoft.common.model.request.challengePlan.ChallengePlanMonthlyPageRequest;
import com.jpmsoft.common.model.request.challengePlan.ChallengePlanMonthlyRequest;
import com.jpmsoft.common.model.request.ganttPlan.GanttMilestoneStatisticsRequest;
import com.jpmsoft.common.model.request.ganttPlan.GanttRemarkRequest;
import com.jpmsoft.common.model.request.milestones.ProjectMilestoneRequest;
import com.jpmsoft.common.model.request.milestones.ProjectMilestonesRequest;
import com.jpmsoft.common.model.request.pay.ConPayRequest;
import com.jpmsoft.common.model.requestAndResponse.Audit.AuditBudgetModel;
import com.jpmsoft.common.model.requestAndResponse.Audit.AuditSettlementModel;
import com.jpmsoft.common.model.requestAndResponse.Construction.ConCompleteModel;
import com.jpmsoft.common.model.requestAndResponse.Construction.ConDelayModel;
import com.jpmsoft.common.model.requestAndResponse.Construction.ConExamineModel;
import com.jpmsoft.common.model.requestAndResponse.Construction.ConStartApplyModel;
import com.jpmsoft.common.model.requestAndResponse.Design.*;
import com.jpmsoft.common.model.requestAndResponse.NoticeInfoModel;
import com.jpmsoft.common.model.requestAndResponse.Plan.GanttPlanTemplateModel;
import com.jpmsoft.common.model.requestAndResponse.Project.ProjectActivityDto;
import com.jpmsoft.common.model.requestAndResponse.Project.ProjectProfileModel;
import com.jpmsoft.common.model.requestAndResponse.Qs.QsGroundCollapseModel;
import com.jpmsoft.common.model.requestAndResponse.Qs.QsPlanModel;
import com.jpmsoft.common.model.requestAndResponse.Qs.QsTypeModel;
import com.jpmsoft.common.model.requestAndResponse.ReplyInfoModel;
import com.jpmsoft.common.model.requestAndResponse.SafeResponsibility.*;
import com.jpmsoft.common.model.requestAndResponse.SmartSite.ProjectPanoramaOverviewModel;
import com.jpmsoft.common.model.requestAndResponse.SmartSite.SmartSiteCameraModel;
import com.jpmsoft.common.model.requestAndResponse.dh.MonthlyInspectionReportDTO;
import com.jpmsoft.common.model.requestAndResponse.siteform.DocDistributionUnitDTO;
import com.jpmsoft.common.model.requestAndResponse.yw.GroupProjectIssueModel;
import com.jpmsoft.common.model.requestAndResponse.zjt.ProjectUpStatisticsDTO;
import com.jpmsoft.projectConsumer.pojo.dto.construction.BuildQualitySafetyLogDto;
import com.jpmsoft.projectConsumer.pojo.dto.construction.NightConstructionFilingDTO;
import com.jpmsoft.projectConsumer.pojo.dto.construction.TechnicalCheckDTO;
import com.jpmsoft.projectConsumer.pojo.dto.design.DesignDocReviewDTO;
import com.jpmsoft.projectConsumer.pojo.dto.design.DesignDrawReviewDTO;
import com.jpmsoft.projectConsumer.pojo.dto.design.DesignPileDto;
import com.jpmsoft.projectConsumer.pojo.dto.ganttPlan.*;
import com.jpmsoft.projectConsumer.pojo.dto.other.OuterUserRegisterDTO;
import com.jpmsoft.projectConsumer.pojo.dto.other.TrainStaff;
import com.jpmsoft.projectConsumer.pojo.dto.project.ProManagePlanDTO;
import com.jpmsoft.projectConsumer.pojo.dto.project.ProjectManagementOutlineDto;
import com.jpmsoft.projectConsumer.pojo.dto.project.ProjectReportViolationsDTO;
import com.jpmsoft.projectConsumer.pojo.dto.qualitySafety.*;
import com.jpmsoft.projectConsumer.pojo.dto.riskWarn.RiskPreventionMeasureDTO;
import com.jpmsoft.projectConsumer.pojo.dto.riskWarn.RiskTemplateRelationDTO;
import com.jpmsoft.projectConsumer.request.qs.QsTypeRecordRequest;
import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Component
@FeignClient(name = "project-producer", contextId = "other")
public interface BaseFeign {

    /*********************我的事务(PM)*********************/

    /**
     * 获取用户代办信息
     *
     * @param token   用户ID
     * @param request 请求参数
     * @return
     */
    @PostMapping("/v1/todoCenter/getUserTaskList")
    BaseResponse getUserTaskList(@RequestHeader String token, @RequestBody UserTaskRequest request);

    /**
     * 获取用户已办信息
     *
     * @param token   用户ID
     * @param request 请求参数
     * @return
     */
    @PostMapping("/v1/todoCenter/getUserTaskProcessList")
    BaseResponse getUserTaskProcessList(@RequestHeader String token, @RequestBody UserTaskProcessinRequest request);

    /**
     * 我的事务PM
     * 待反馈流程
     *
     * @param token
     * @param request
     * @return
     */
    @PostMapping("/v1/todoCenter/getUserCoordinationTaskList")
    BaseResponse getUserCoordinationTaskList(@RequestHeader String token, @RequestBody UserCoordinationTaskRequest request);


    /**
     * 用户抄送流程
     *
     * @param token
     * @param request
     * @return
     */
    @PostMapping("/v1/todoCenter/getUserCarbonCopyList")
    BaseResponse getUserCarbonCopyList(@RequestHeader String token, @RequestBody UserCarbonCopyRequest request);

    /**
     * 我的事务
     * 流程反馈记录
     *
     * @param token   用户ID
     * @param request 请求参数
     * @return
     */
    @PostMapping("/v1/todoCenter/getUserCoordinationProcessList")
    BaseResponse getUserCoordinationProcessList(@RequestHeader String token, @RequestBody UserCoordinationProcessRequest request);


    /**
     * 获取用户代办数量
     * 待办数量、待阅数量、待反馈任务数量
     *
     * @param token
     * @return
     */
    @GetMapping("/v1/todoCenter/getUserToDoSummary")
    BaseResponse getUserToDoSummary(@RequestHeader String token);


    /***********************************************进度计划***************************************************/
    @PostMapping("/v1/ganttPlan/getGanttPlanList")
    BaseResponse getGanttPlanList(@RequestBody GanttPlanRequest request, @RequestHeader String token);

    @PostMapping("/v1/ganttPlan/getGanttPlanListNJCJ")
    BaseResponse getGanttPlanListNJCJ(@RequestBody GanttPlanRequest request, @RequestHeader String token);

    @PostMapping("/v1/ganttPlan/getGanttPlanClassInfo")
    BaseResponse getGanttPlanClassInfo(@RequestBody GanttPlanRequest request, @RequestHeader String token);

    @PostMapping("/v1/ganttPlan/ganttPlanMilestoneList")
    BaseResponse ganttPlanMilestoneList(@RequestBody GanttPlanRequest request, @RequestHeader String token);

    @PostMapping("/v1/ganttPlan/createOrUpdateGanttPlan")
    BaseResponse createOrUpdateGanttPlan(@RequestBody GanttPlanRequest request);

    @PostMapping("/v1/ganttPlan/createOrUpdateGanttPlanProjectOnly")
    BaseResponse createOrUpdateGanttPlanProjectOnly(@RequestBody GanttPlanRequest request);


    @GetMapping("/v1/ganttPlan/getGanttPlanByPlanId")
    BaseResponse getGanttPlanByPlanId(@RequestParam String planId);

    @GetMapping("/v1/ganttPlan/deleteUnStartGanttPlan")
    BaseResponse deleteUnStartGanttPlan(@RequestParam String planId);


    @GetMapping("/v1/ganttPlan/approved2GenerateProjectCplTemp")
    BaseResponse approved2GenerateProjectCplTemp(@RequestParam String planId);

    @GetMapping("/v1/ganttPlan/statisticsProjectPlan")
    BaseResponse statisticsProjectPlan(@RequestHeader String token);

    @PostMapping("/v1/ganttPlan/statisticsProJectPlanRanking")
    BaseResponse statisticsProJectPlanRanking(@RequestBody GanttPlanRequest request, @RequestHeader String token);

    @GetMapping("/v1/ganttPlan/statisticsPlanTotalBypId")
    BaseResponse statisticsPlanTotalBypId(@RequestParam String pId);

    @GetMapping("/v1/ganttPlan/statisticsPlanTotal")
    BaseResponse statisticsPlanTotal();


    @GetMapping("/v1/ganttPlan/getGanttPlanAndTaskBypId")
    BaseResponse getGanttPlanAndTaskBypId(@RequestParam String pId, @RequestParam(required = false) String type);

    @GetMapping("/v1/ganttPlan/projectExistGanttPlan")
    BaseResponse projectExistGanttPlan(@RequestParam String pId, @RequestParam(required = false) String planId);

    @PostMapping("/v1/ganttPlan/GanttPlanStatisticsList")
    BaseResponse GanttPlanStatisticsList(@RequestBody GanttPlanRequest request, @RequestHeader String token);

    @PostMapping("/v1/projectMilestones/createOrUpdateProjectMilestones")
    BaseResponse createOrUpdateProjectMilestones(@RequestBody ProjectMilestonesRequest request);

    @PostMapping("/v1/projectMilestones/getProjectMilestonesList")
    BaseResponse getProjectMilestonesList(@RequestBody ProjectMilestonesRequest request, @RequestHeader String token);

    @GetMapping("/v1/projectMilestones/getProjectMilestonesByPmsId")
    BaseResponse getProjectMilestonesByPmsId(@RequestParam String pmsId);

    @PostMapping("/v1/projectMilestones/updateProjectMilestone")
    BaseResponse updateProjectMilestone(@RequestBody ProjectMilestoneRequest request);

    @GetMapping("/v1/projectMilestones/getProjectMilestoneByPmId")
    BaseResponse getProjectMilestoneByPmId(@RequestParam String pmId);

    /***********************************************进度计划-任务 ***************************************************/
    /**
     * 统计逾期情况
     *
     * @return
     */
    @PostMapping("/v1/ganttTasks/GanttPlanStatisticsByOverdue")
    BaseResponse GanttPlanStatisticsByOverdue(@RequestBody(required = false) GanttTasksRequest request, @RequestHeader String token);

    @PostMapping("/v1/ganttTasks/ganttPlanTaskStatisticsList")
    BaseResponse ganttPlanTaskStatisticsList(@RequestHeader String token, @RequestBody GanttTasksRequest request);

    @PostMapping("/v1/ganttTasks/getGanttTasksByOverdue")
    BaseResponse getGanttTasksByOverdue(@RequestBody GanttTasksRequest request, @RequestHeader String token);

    @PostMapping("/v1/ganttTasks/getGanttTasksByComplete")
    BaseResponse getGanttTasksByComplete(@RequestBody GanttTasksRequest request, @RequestHeader String token);

    @PostMapping("/v1/ganttTasks/getSecondaryNodesByTaskId")
    BaseResponse getSecondaryNodesByTaskId(@RequestBody GanttTasksRequest request);

    @PostMapping("/v1/ganttTasks/getGanttTasksExport")
    BaseResponse getGanttTasksExport(@RequestBody GanttPlanExportRequest request);


    @PostMapping("/v1/ganttTasks/updateBeginTime")
    BaseResponse updateBeginTime(@RequestBody GanttTasksRequest request);

    @GetMapping("/v1/ganttTasks/milestone2Complete")
    BaseResponse milestone2Complete(@RequestParam String taskId, @RequestParam(required = false) String finishTime, @RequestHeader String token);

    @PostMapping("/v1/ganttTasks/milestoneYjComplete")
    BaseResponse milestoneYjComplete(@RequestBody MilestoneSubmitDTO request, @RequestHeader String token);

    /***********************************************进度计划-任务完成情况 ***************************************************/
    @PostMapping("/v1/ganttTasksProcess/createOrUpdateGanttTasksProcess")
    BaseResponse createOrUpdateGanttTasksProcess(@RequestBody GanttTasksProcessRequest request);

    @PostMapping("/v1/ganttTasksProcess/getGanttTasksProcessList")
    BaseResponse getGanttTasksProcessList(@RequestBody GanttTasksProcessRequest request);

    @GetMapping("/v1/ganttTasksProcess/getGanttTasksProcessById")
    BaseResponse getGanttTasksProcessById(@RequestParam String gtpId);

    @GetMapping("/v1/ganttTasksProcess/approve2RefreshGanttTask")
    BaseResponse approve2RefreshGanttTask(@RequestParam String gtpId);

    @PostMapping("/v1/ganttTasksProcess/getGanttTasksProcessExport")
    BaseResponse getGanttTasksProcessExport(@RequestBody GanttPlanExportRequest request);

    /***********************************************初步设计***************************************************/

    @PostMapping("/v1/designInitial/createOrUpdateDesignInitial")
    BaseResponse createOrUpdateDesignInitial(@RequestBody DesignInitialRequest request);

    @PostMapping("/v1/designInitial/getDesignInitialList")
    BaseResponse getDesignInitialList(@RequestBody DesignInitialRequest request, @RequestHeader String token);

    @PostMapping("/v1/designInitial/getDesignInitialStatisticsList")
    BaseResponse getDesignInitialStatisticsList(@RequestBody DesignInitialRequest request, @RequestHeader String token);

    @PostMapping("/v1/designInitial/getDesignInitialListNoAuth")
    BaseResponse getDesignInitialListNoAuth(@RequestBody DesignInitialRequest request);

    @GetMapping("/v1/designInitial/deleteDesignInitialBydiId")
    BaseResponse deleteDesignInitialBydiId(@RequestParam String diId);

    @GetMapping("/v1/designInitial/getDesignInitialBydiId")
    BaseResponse getDesignInitialBydiId(@RequestParam String diId);

    @PostMapping("/v1/designInitial/updateDesignInitialApproveStatus")
    BaseResponse updateDesignInitialApproveStatus(@RequestBody DesignInitialRequest request);

    /***********************************************施工图设计***************************************************/
    @PostMapping("/v1/designConstruction/createOrUpdateDesignConstruction")
    BaseResponse createOrUpdateDesignConstruction(@RequestBody DesignConstructionRequest request);

    @PostMapping("/v1/designConstruction/getDesignConstructionList")
    BaseResponse getDesignConstructionList(@RequestBody DesignConstructionRequest request, @RequestHeader String token);

    @PostMapping("/v1/designConstruction/getDesignConstructionStatisticsList")
    BaseResponse getDesignConstructionStatisticsList(@RequestBody DesignConstructionRequest request, @RequestHeader String token);

    @PostMapping("/v1/designConstruction/getDesignConstructionListNoAuth")
    BaseResponse getDesignConstructionListNoAuth(@RequestBody DesignConstructionRequest request);

    @GetMapping("/v1/designConstruction/deleteDesignConstructionBydcId")
    BaseResponse deleteDesignConstructionBydcId(@RequestParam String dcId);

    @GetMapping("/v1/designConstruction/getDesignConstructionBydcId")
    BaseResponse getDesignConstructionBydcId(@RequestParam String dcId);

    @PostMapping("/v1/designConstruction/updateDesignConstructionApproveStatus")
    BaseResponse updateDesignConstructionApproveStatus(@RequestBody DesignConstructionRequest request);


    /***********************************************质量安全类别***************************************************/
    @PostMapping("/v1/qsType/createOrUpdateQsType")
    BaseResponse createOrUpdateQsType(@RequestBody QsTypeModel request);

    @PostMapping("/v1/qsType/getGroupSameType")
    BaseResponse getGroupSameType(@RequestBody QsTypeModel request);

    @GetMapping("/v1/qsType/deleteQsTypeById")
    BaseResponse deleteQsTypeById(@RequestParam String qstId);

    @PostMapping("/v1/qsType/getQsTypeTreeList")
    BaseResponse getQsTypeTreeList(@RequestBody QsTypeModel request, @RequestHeader String token);

    @PostMapping("/v1/qsType/getQsTypeList")
    BaseResponse getQsTypeList(@RequestBody QsTypeModel request, @RequestHeader String token);

    @GetMapping("/v1/qsType/getQsTypeById")
    BaseResponse getQsTypeById(@RequestParam String qstId);


    @GetMapping("/v1/qsType/upOrDownQsType")
    BaseResponse upOrDownQsType(@RequestParam String qstId, @RequestParam String action);

    @GetMapping("/v1/qsType/getQsTypeByParentId")
    BaseResponse getQsTypeByParentId(@RequestParam String parentId);

    @GetMapping("/v1/qsType/generateQsTypePath")
    BaseResponse generateQsTypePath();


    @PostMapping("/v1/qsType/exportQsType")
    Response exportQsType(@RequestBody QsTypeModel request, @RequestHeader String token);


    /***********************************************质量安全检查记录***************************************************/

    @PostMapping("/v1/qsRecord/createOrUpdateQsRecord")
    BaseResponse createOrUpdateQsRecord(@RequestBody QsRecordRequest request);

    @PostMapping("/v1/qsRecord/getQsRecordList")
    BaseResponse getQsRecordList(@RequestBody QsRecordRequest request, @RequestHeader String token);

    @PostMapping("/v1/qsRecord/getQsRecordLedgerList")
    BaseResponse getQsRecordLedgerList(@RequestBody QsRecordRequest request, @RequestHeader String token);

    @GetMapping("/v1/qsRecord/getQsRecordByQsrId")
    BaseResponse getQsRecordByQsrId(@RequestParam String qsrId);

    @PostMapping("/v1/qsRecord/updateRectifyPic")
    BaseResponse updateRectifyPic(@RequestBody QsRecordRequest request);

    @PostMapping("/v1/qsRecord/updateRecord")
    BaseResponse updateRecord(@RequestBody QsRecordRequest request);

    /**********************************************质量安全违规处罚***************************************************/
    @PostMapping("/v1/qsViolation/createOrUpdateQsViolationPunishment")
    BaseResponse createOrUpdateQsViolationPunishment(@RequestBody QsViolationPunishmentRequest request);

    @PostMapping("/v1/qsViolation/getQsViolationPunishmentList")
    BaseResponse getQsViolationPunishmentList(@RequestBody QsViolationPunishmentRequest request, @RequestHeader String token);

    @GetMapping("/v1/qsViolation/getQsViolationPunishmentById")
    BaseResponse getQsViolationPunishmentById(@RequestParam String vpId);


    /**********************************************分包单位备案***************************************************/

    @PostMapping("/v1/subcontractorRecord/createOrUpdateSubcontractorRecord")
    BaseResponse createOrUpdateSubcontractorRecord(@RequestBody SubcontractorRecordRequest request);

    @PostMapping("/v1/subcontractorRecord/getSubcontractorRecordList")
    BaseResponse getSubcontractorRecordList(@RequestBody SubcontractorRecordRequest request);

    @GetMapping("/v1/subcontractorRecord/getSubcontractorRecordBySrId")
    BaseResponse getSubcontractorRecordBySrId(@RequestParam String srId);

    @GetMapping("/v1/subcontractorRecord/checkCreditCodeIsExist")
    BaseResponse checkCreditCodeIsExist(@RequestParam String crditCode);

    @GetMapping("/v1/subcontractorRecord/syncSubcontractorRecordComapny")
    BaseResponse syncSubcontractorRecordComapny(@RequestParam String srId);

    /**********************************************分包合同备案***************************************************/
    @PostMapping("/v1/subcontractRecord/createOrUpdateSubcontractRecord")
    BaseResponse createOrUpdateSubcontractRecord(@RequestBody SubcontractRecordRequest request);

    @PostMapping("/v1/subcontractRecord/getSubcontractRecordList")
    BaseResponse getSubcontractRecordList(@RequestBody SubcontractRecordRequest request, @RequestHeader String token);

    @GetMapping("/v1/subcontractRecord/getSubcontractRecordByScId")
    BaseResponse getSubcontractRecordByScId(@RequestParam String scId);

    @GetMapping("/v1/subcontractRecord/getSubcontractRecordByScMasterConId")
    BaseResponse getSubcontractRecordByScMasterConId(@RequestParam String scMasterConId);

    @PostMapping("/v1/subcontractRecord/getSubcontractRecordByScIds")
    BaseResponse getSubcontractRecordByScIds(@RequestParam List<String> scIds);

    /**********************************************分包申请***************************************************/
    @PostMapping("/v1/subcontractApply/createOrUpdateSubcontractApply")
    BaseResponse createOrUpdateSubcontractApply(@RequestBody SubcontractApplyRequest request);

    @PostMapping("/v1/subcontractApply/getSubcontractApplyList")
    BaseResponse getSubcontractApplyList(@RequestBody SubcontractApplyRequest request);

    @GetMapping("/v1/subcontractApply/getSubcontractApplyBySaId")
    BaseResponse getSubcontractApplyBySaId(@RequestParam String saId);


    /**********************************************单位账户管理***************************************************/
    @PostMapping("/v1/bankAccount/createOrUpdateBankAccount")
    BaseResponse createOrUpdateBankAccount(@RequestBody BankAccountRequest request);

    @PostMapping("/v1/bankAccount/getBankAccountList")
    BaseResponse getBankAccountList(@RequestBody BankAccountRequest request);

    @PostMapping("/v1/bankAccount/getBankAccountAuthList")
    BaseResponse getBankAccountAuthList(@RequestBody BankAccountRequest request, @RequestHeader String token);

    @GetMapping("/v1/bankAccount/getBankAccountById")
    BaseResponse getBankAccountById(@RequestParam String baId);

    /**********************************************合同账户变更***************************************************/
    @PostMapping("/v1/accountChange/createOrUpdateBankAccountChange")
    BaseResponse createOrUpdateBankAccountChange(@RequestBody BankAccountChangeRequest request);

    @PostMapping("/v1/accountChange/getBankAccountChangeList")
    BaseResponse getBankAccountChangeList(@RequestBody BankAccountChangeRequest request, @RequestHeader String token);

    @GetMapping("/v1/accountChange/getBankAccountChangeById")
    BaseResponse getBankAccountChangeById(@RequestParam String bacId);


    @GetMapping("/v1/accountChange/getBankAccountChangeByConId")
    BaseResponse getBankAccountChangeByConId(@RequestParam String conId);


    /**********************************************监理规划及细则***************************************************/
    @PostMapping("/v1/conSupPlan/createOrUpdateConSupPlan")
    BaseResponse createOrUpdateConSupPlan(@RequestBody ConSupPlanRequest request);

    @PostMapping("/v1/conSupPlan/getConSupPlanList")
    BaseResponse getConSupPlanList(@RequestBody ConSupPlanRequest request, @RequestHeader String token);

    @GetMapping("/v1/conSupPlan/getConSupPlanById")
    BaseResponse getConSupPlanById(@RequestParam String spId);

    @PostMapping("/v1/punish/getPunishList")
    BaseResponse getPunishList(@RequestBody PunishRequest request, @RequestHeader String token);

    /**********************************************工程交底***************************************************/
    @PostMapping("/v1/conSupDisclosure/createOrUpdateConSupDisclosure")
    BaseResponse createOrUpdateConSupDisclosure(@RequestBody ConSupDisclosureRequest request);

    @PostMapping("/v1/conSupDisclosure/getConSupDisclosureList")
    BaseResponse getConSupDisclosureList(@RequestBody ConSupDisclosureRequest request, @RequestHeader String token);

    @GetMapping("/v1/conSupDisclosure/getConSupDisclosureById")
    BaseResponse getConSupDisclosureById(@RequestParam String disId);

    /**********************************************旁站记录***************************************************/
    @PostMapping("/v1/conSupOnSite/createOrUpdateConSupOnSite")
    BaseResponse createOrUpdateConSupOnSite(@RequestBody ConSupOnSiteRequest request);

    @PostMapping("/v1/conSupOnSite/getConSupOnSiteList")
    BaseResponse getConSupOnSiteList(@RequestBody ConSupOnSiteRequest request, @RequestHeader String token);

    @GetMapping("/v1/conSupOnSite/getConSupOnSiteById")
    BaseResponse getConSupOnSiteById(@RequestParam String osId);


    /**********************************************质量安全检查记录***************************************************/
    @PostMapping("/v1/qsPlan/createOrUpdateQsPlan")
    BaseResponse createOrUpdateQsPlan(@RequestBody QsPlanModel request);

    @PostMapping("/v1/qsPlan/getQsPlanList")
    BaseResponse getQsPlanList(@RequestBody QsPlanModel request);

    @GetMapping("/v1/qsPlan/getQsPlanById")
    BaseResponse getQsPlanById(@RequestParam String qspId);

    @PostMapping("/v1/qsPlan/setQsPlanReplyState")
    BaseResponse setQsPlanReplyState(@RequestBody QsPlanModel request);

    /**********************************************施工组织设计***************************************************/
    @PostMapping("/v1/conCOD/createOrUpdateConConstructionOrgDesign")
    BaseResponse createOrUpdateConConstructionOrgDesign(@RequestBody ConConstructionOrgDesignRequest request);

    @PostMapping("/v1/conCOD/getConConstructionOrgDesignList")
    BaseResponse getConConstructionOrgDesignList(@RequestBody ConConstructionOrgDesignRequest request, @RequestHeader String token);

    @GetMapping("/v1/conCOD/getConConstructionOrgDesignById")
    BaseResponse getConConstructionOrgDesignById(@RequestParam String codId);

    /**********************************************工程验收***************************************************/
    @PostMapping("/v1/acceptance/createOrUpdateCompletionAcceptance")
    BaseResponse createOrUpdateCompletionAcceptance(@RequestBody CompletionAcceptanceRequest request);

    @PostMapping("/v1/acceptance/getCompletionAcceptanceList")
    BaseResponse getCompletionAcceptanceList(@RequestBody CompletionAcceptanceRequest request, @RequestHeader String token);

    @PostMapping("/v1/acceptance/getCompletionAcceptanceStatisticsList")
    BaseResponse getCompletionAcceptanceStatisticsList(@RequestBody CompletionAcceptanceRequest request, @RequestHeader String token);

    @PostMapping("/v1/acceptance/getCompletionAcceptanceListNoAuth")
    BaseResponse getCompletionAcceptanceListNoAuth(@RequestBody CompletionAcceptanceRequest request);

    @PostMapping("/v1/acceptance/getCompletionAcceptanceLastByPidAndType")
    BaseResponse getCompletionAcceptanceLastByPidAndType(@RequestBody CompletionAcceptanceRequest request);

    @GetMapping("/v1/acceptance/getCompletionAcceptanceById")
    BaseResponse getCompletionAcceptanceById(@RequestParam String caId);

    @GetMapping("/v1/acceptance/approve2RefreshProStage")
    BaseResponse approve2RefreshProStage(@RequestParam String caId);

    @GetMapping("/v1/acceptance/checkCompletionAcceptance")
    BaseResponse checkCompletionAcceptance(@RequestParam String conId);

    /**********************************************质量保修书***************************************************/
    @PostMapping("/v1/maintenanceWarranty/createOrUpdateMaintenanceWarranty")
    BaseResponse createOrUpdateMaintenanceWarranty(@RequestBody MaintenanceWarrantyRequest request);

    @PostMapping("/v1/maintenanceWarranty/getMaintenanceWarrantyList")
    BaseResponse getMaintenanceWarrantyList(@RequestBody MaintenanceWarrantyRequest request, @RequestHeader String token);

    @PostMapping("/v1/maintenanceWarranty/getMaintenanceWarrantyListNoAuth")
    BaseResponse getMaintenanceWarrantyListNoAuth(@RequestBody MaintenanceWarrantyRequest request);

    @GetMapping("/v1/maintenanceWarranty/getMaintenanceWarrantyById")
    BaseResponse getMaintenanceWarrantyById(@RequestParam String mwId);

    /**********************************************维保记录***************************************************/
    @PostMapping("/v1/maintenanceRecord/createOrUpdateMaintenanceRecord")
    BaseResponse createOrUpdateMaintenanceRecord(@RequestBody MaintenanceRecordRequest request);


    @PostMapping("/v1/maintenanceRecord/getMaintenanceRecordGgList")
    BaseResponse getMaintenanceRecordGgList(@RequestBody MaintenanceRecordRequest request, @RequestHeader String token);

    @PostMapping("/v1/maintenanceRecord/getMaintenanceRecordList")
    BaseResponse getMaintenanceRecordList(@RequestBody MaintenanceRecordRequest request, @RequestHeader String token);

    @PostMapping("/v1/maintenanceRecord/getMaintenanceRecordListNoAuth")
    BaseResponse getMaintenanceRecordListNoAuth(@RequestBody MaintenanceRecordRequest request);

    @GetMapping("/v1/maintenanceRecord/getMaintenanceRecordById")
    BaseResponse getMaintenanceRecordById(@RequestParam String mrId);

    @PostMapping("/v1/maintenanceRecord/exportMaintenanceRecordGgExcel")
    Response exportMaintenanceRecordGgExcel(@RequestBody MaintenanceRecordRequest request, @RequestHeader String token);

    @GetMapping("/v1/maintenanceRecord/refreshRepairStateGg")
    BaseResponse refreshRepairStateGg(@RequestParam("mrId") String mrId);

    /**
     * 调用服务端全量刷新广工零星维修状态。
     *
     * @return 全量刷新结果
     */
    @GetMapping("/v1/maintenanceRecord/refreshRepairStateGgAll")
    BaseResponse refreshRepairStateGgAll();

    @GetMapping("/v1/maintenanceRecord/getRepairPersonByMrId")
    BaseResponse getRepairPersonByMrId(@RequestParam("mrId") String mrId);

    /**********************************************维保联系人***************************************************/
    @PostMapping("/v1/maintenanceContact/createOrUpdateMaintenanceContact")
    BaseResponse createOrUpdateMaintenanceContact(@RequestBody MaintenanceContactRequest request);

    @PostMapping("/v1/maintenanceContact/getMaintenanceContactList")
    BaseResponse getMaintenanceContactList(@RequestBody MaintenanceContactRequest request, @RequestHeader String token);

    @GetMapping("/v1/maintenanceContact/getMaintenanceContactById")
    BaseResponse getMaintenanceContactById(@RequestParam String mcId);

    /**********************************************零星维修申报***************************************************/
    @PostMapping("/v1/sporadicSettlement/getSporadicSettlementeList")
    BaseResponse getSporadicSettlementeList(@RequestBody SporadicSettlementRequest sporadicSettlementRequest);

    @GetMapping("/v1/sporadicSettlement/getSporadicSettlementByMrId")
    BaseResponse getSporadicSettlementByMrId(@RequestParam String mrId);

    @GetMapping("/v1/sporadicSettlement/checkSporadicSettlement")
    BaseResponse checkSporadicSettlement(@RequestBody SporadicSettlementRequest request);

    /**********************************************零星维修支付***************************************************/
    @PostMapping("/v1/sporadicPayments/getSporadicPaymentsList")
    BaseResponse getSporadicPaymentsList(SporadicPaymentsRequest request,@RequestHeader String token);

    @PostMapping("/v1/sporadicPayments/checkSporadicPaymentsByMfCode")
    BaseResponse checkSporadicPaymentsByMfCode(List<String> sporadicSettlementIds);

    @GetMapping("/v1/sporadicPayments/modifyState")
    BaseResponse setModifymrRepairState(@RequestParam String id, @RequestParam(required = false) String mrRepairState);

    @GetMapping("/v1/sporadicPayments/getSporadicPaymentsBySeId")
    BaseResponse getSporadicPaymentsBySeId(@RequestParam String id);

    /**********************************************竣工备案***************************************************/
    @PostMapping("/v1/filing/createOrUpdateCompletionFiling")
    BaseResponse createOrUpdateCompletionFiling(@RequestBody CompletionFilingRequest request);

    @PostMapping("/v1/filing/getCompletionFilingList")
    BaseResponse getCompletionFilingList(@RequestBody CompletionFilingRequest request, @RequestHeader String token);

    @PostMapping("/v1/filing/getCompletionFilingListNoAuth")
    BaseResponse getCompletionFilingListNoAuth(@RequestBody CompletionFilingRequest request);

    @GetMapping("/v1/filing/getCompletionFilingById")
    BaseResponse getCompletionFilingById(@RequestParam String cfId);

    /**********************************************项目移交***************************************************/

    @PostMapping("/v1/handover/createOrUpdateCompletionHandover")
    BaseResponse createOrUpdateCompletionHandover(@RequestBody CompletionHandoverRequest request);

    @PostMapping("/v1/handover/getCompletionHandoverList")
    BaseResponse getCompletionHandoverList(@RequestBody CompletionHandoverRequest request, @RequestHeader String token);


    @GetMapping("/v1/handover/getCompletionHandoverById")
    BaseResponse getCompletionHandoverById(@RequestParam String chId);

    /**
     * 定时任务轮询接口：重新同步失败的数据
     */
    @GetMapping("/v1/handover/retrySyncFailedBuildingInfo")
    BaseResponse retrySyncFailedBuildingInfo();

    /**********************************************索赔***************************************************/

    @PostMapping("/v1/pcClaim/createOrUpdatePcClaim")
    BaseResponse createOrUpdatePcClaim(@RequestBody PcClaimRequest request);

    @PostMapping("/v1/pcClaim/getPcClaimList")
    BaseResponse getPcClaimList(@RequestBody PcClaimRequest request, @RequestHeader String token);

    @GetMapping("/v1/pcClaim/getPcClaimById")
    BaseResponse getPcClaimById(@RequestParam String claimId);

    /**********************************************奖罚单***************************************************/

    @PostMapping("/v1/awardTicket/createOrUpdatePcAwardTicket")
    BaseResponse createOrUpdatePcAwardTicket(@RequestBody PcAwardTicketRequest request);

    @PostMapping("/v1/awardTicket/getPcAwardTicketList")
    BaseResponse getPcAwardTicketList(@RequestBody PcAwardTicketRequest request, @RequestHeader String token);

    @GetMapping("/v1/awardTicket/getPcAwardTicketById")
    BaseResponse getPcAwardTicketById(@RequestParam String atId);


    /**********************************************竣工验收（合并版）***************************************************/

    @PostMapping("/v1/acceptanceAll/createOrUpdateCompletionAcceptanceAll")
    BaseResponse createOrUpdateCompletionAcceptanceAll(@RequestBody CompletionAcceptanceAllRequest request);

    @PostMapping("/v1/acceptanceAll/getCompletionAcceptanceAllList")
    BaseResponse getCompletionAcceptanceAllList(@RequestBody CompletionAcceptanceAllRequest request, @RequestHeader String token);

    @PostMapping("/v1/acceptanceAll/getCompletionAcceptanceAllListNoAuth")
    BaseResponse getCompletionAcceptanceAllListNoAuth(@RequestBody CompletionAcceptanceAllRequest request);

    @GetMapping("/v1/acceptanceAll/getCompletionAcceptanceAllById")
    BaseResponse getCompletionAcceptanceAllById(@RequestParam String caaId);


    /**********************************************风险预警信息***************************************************/

    @GetMapping("/v1/riskWarn/getRiskWarnById")
    BaseResponse getRiskWarnById(@RequestParam String rwId);

    @PostMapping("/v1/riskWarn/getRiskWarnRanking")
    BaseResponse getRiskWarnRanking(@RequestBody RiskWarnRequest request, @RequestHeader String token);

    @PostMapping("/v1/riskWarn/getRiskWarnList")
    BaseResponse getRiskWarnList(@RequestBody RiskWarnRequest request, @RequestHeader String token);

    @PostMapping("/v1/riskWarn/getRiskWarnListOutline")
    BaseResponse getRiskWarnListOutline(@RequestBody RiskWarnRequest request, @RequestHeader String token);

    @GetMapping("/riskWarn/v1/riskWarnCockpit/getRiskWarnCountByYearAndMonthly")
    BaseResponse getRiskWarnCountByYearAndMonthly(@RequestParam Integer year, @RequestParam Integer month, @RequestHeader String token);

    @PostMapping("/riskWarn/v1/keyIndicatorQuery/getKeyIndicatorQuery")
    BaseResponse getKeyIndicatorQuery(@RequestBody RiskWarnIndexRequest request, @RequestHeader String token);

    @PostMapping("/riskWarn/v1/projectTaskQuery/getProjectTaskQueryCount")
    BaseResponse getProjectTaskQueryCount(@RequestBody RiskWarQueryRequest request, @RequestHeader String token);

    @PostMapping("/riskWarn/v1/riskWarnAnnualQuery/getRiskWarnAnnualQueryCount")
    BaseResponse getRiskWarnAnnualQueryCount(@RequestBody RiskWarQueryRequest request, @RequestHeader String token);

    @GetMapping("/riskWarn/v1/departmentTaskQuery/getDepartmentTaskQueryCount")
    BaseResponse getDepartmentTaskQueryCount(@RequestBody RiskWarQueryRequest request, @RequestHeader String token);


    /**********************************************风险点防控措施***************************************************/


    @PostMapping("/riskWarn/v1/riskPreventionMeasure/createOrUpdate")
    BaseResponse createOrUpdate(@RequestBody RiskPreventionMeasureDTO request);

    @GetMapping("/riskWarn/v1/riskPreventionMeasure/delete")
    BaseResponse delete(@RequestParam String id);

    @GetMapping("/riskWarn/v1/riskPreventionMeasure/getTree")
    BaseResponse getTree(@RequestHeader String token);

    @GetMapping("/riskWarn/v1/riskPreventionMeasure/enablePreventionMeasure")
    BaseResponse enablePreventionMeasure(@RequestParam String id, @RequestParam String status);

    @GetMapping("/riskWarn/v1/riskPreventionMeasure/upOrDown")
    BaseResponse upOrDown(@RequestParam String id, @RequestParam String action);


    /**********************************************风险点文档模板关联配置***************************************************/
    @PostMapping("/riskWarn/v1/riskTemplateRelation/createOrUpdateTmpRelation")
    BaseResponse createOrUpdateTmpRelation(@RequestBody RiskTemplateRelationDTO request);

    @PostMapping("/riskWarn/v1/riskTemplateRelation/createOrUpdateTmpRelationBath")
    BaseResponse createOrUpdateTmpRelationBath(@RequestBody List<RiskTemplateRelationDTO> request);

    @PostMapping("/riskWarn/v1/riskTemplateRelation/getTmpRelationList")
    BaseResponse getTmpRelationList(@RequestBody RiskTemplateRelationDTO request, @RequestHeader String token);

    @GetMapping("/riskWarn/v1/riskTemplateRelation/deleteTmpRelationById")
    BaseResponse deleteTmpRelationById(@RequestParam String id);

    /**********************************************方案设计报审***************************************************/
    @PostMapping("/v1/designConcept/createOrUpdateDesignConcept")
    BaseResponse createOrUpdateDesignConcept(@RequestBody DesignConceptModel request);

    @PostMapping("/v1/designConcept/getDesignConceptList")
    BaseResponse getDesignConceptList(@RequestBody DesignConceptModel request, @RequestHeader String token);

    @PostMapping("/v1/designConcept/getDesignConceptListNoAuth")
    BaseResponse getDesignConceptListNoAuth(@RequestBody DesignConceptModel request);

    @GetMapping("/v1/designConcept/getDesignConceptById")
    BaseResponse getDesignConceptById(@RequestParam String dcId);

    @PostMapping("/v1/designConcept/updateDesignConceptApproveStatus")
    BaseResponse updateDesignConceptApproveStatus(@RequestBody DesignConceptModel request);

    /**********************************************图纸管理***************************************************/
    @PostMapping("/v1/designDrawing/createOrUpdateDesignDrawing")
    BaseResponse createOrUpdateDesignDrawing(@RequestBody DesignDrawingModel request);

    @PostMapping("/v1/designDrawing/getDesignDrawingList")
    BaseResponse getDesignDrawingList(@RequestBody DesignDrawingModel request, @RequestHeader String token);

    @PostMapping("/v1/designDrawing/getDesignDrawingListNoAuth")
    BaseResponse getDesignDrawingListNoAuth(@RequestBody DesignDrawingModel request);

    @GetMapping("/v1/designDrawing/getDesignDrawingById")
    BaseResponse getDesignDrawingById(@RequestParam String ddId);

    @PostMapping("/v1/designDrawing/getDesignDrawingListRW")
    BaseResponse getDesignDrawingListRW(@RequestBody DesignDrawingModel request, @RequestHeader String token);

    /**********************************************可研报告***************************************************/
    @PostMapping("/v1/designFeasibility/createOrUpdateDesignFeasibility")
    BaseResponse createOrUpdateDesignFeasibility(@RequestBody DesignFeasibilityModel request);

    @PostMapping("/v1/designFeasibility/getDesignFeasibilityList")
    BaseResponse getDesignFeasibilityList(@RequestBody DesignFeasibilityModel request, @RequestHeader String token);

    @PostMapping("/v1/designFeasibility/getDesignFeasibilityListNoAuth")
    BaseResponse getDesignFeasibilityListNoAuth(@RequestBody DesignFeasibilityModel request);

    @GetMapping("/v1/designFeasibility/getDesignFeasibilityById")
    BaseResponse getDesignFeasibilityById(@RequestParam String dfId);

    @PostMapping("/v1/designFeasibility/updateDesignFeasibilityApproveStatus")
    BaseResponse updateDesignFeasibilityApproveStatus(@RequestBody DesignFeasibilityModel request);

    /**********************************************工程延期报审***************************************************/
    @PostMapping("/v1/conDelay/createOrUpdateConDelay")
    BaseResponse createOrUpdateConDelay(@RequestBody ConDelayModel request);

    @PostMapping("/v1/conDelay/getConDelayList")
    BaseResponse getConDelayList(@RequestBody ConDelayModel request, @RequestHeader String token);

    @GetMapping("/v1/conDelay/getConDelayById")
    BaseResponse getConDelayById(@RequestParam String deId);

    /**********************************************施工（准备）审核***************************************************/
    @PostMapping("/v1/conExamine/createOrUpdateConExamine")
    BaseResponse createOrUpdateConExamine(@RequestBody ConExamineModel request);

    @PostMapping("/v1/conExamine/getConExamineList")
    BaseResponse getConExamineList(@RequestBody ConExamineModel request, @RequestHeader String token);

    @GetMapping("/v1/conExamine/getConExamineById")
    BaseResponse getConExamineById(@RequestParam String exId);

    /**********************************************开工报审***************************************************/

    @PostMapping("/v1/conStartApply/createOrUpdateConStartApply")
    BaseResponse createOrUpdateConStartApply(@RequestBody ConStartApplyModel request);

    @PostMapping("/v1/conStartApply/getConStartApplyList")
    BaseResponse getConStartApplyList(@RequestBody ConStartApplyModel request, @RequestHeader String token);

    @GetMapping("/v1/conStartApply/getConStartApplyById")
    BaseResponse getConStartApplyById(@RequestParam String saId);

    /**********************************************完工申请***************************************************/

    @PostMapping("/v1/conComplete/createOrUpdateConComplete")
    BaseResponse createOrUpdateConComplete(@RequestBody ConCompleteModel request);

    @PostMapping("/v1/conComplete/getConCompleteList")
    BaseResponse getConCompleteList(@RequestBody ConCompleteModel request, @RequestHeader String token);

    @GetMapping("/v1/conComplete/getConCompleteById")
    BaseResponse getConCompleteById(@RequestParam String coId);

    /**********************************************进度计划模板***************************************************/
    @PostMapping("/v1/ganttPlanTemplate/createOrUpdateGanttPlanTemplate")
    BaseResponse createOrUpdateGanttPlanTemplate(@RequestBody GanttPlanTemplateModel request);

    @PostMapping("/v1/ganttPlanTemplate/getGanttPlanTemplateList")
    BaseResponse getGanttPlanTemplateList(@RequestBody GanttPlanTemplateModel request);

    @GetMapping("/v1/ganttPlanTemplate/getGanttPlanTemplateById")
    BaseResponse getGanttPlanTemplateById(@RequestParam String id);

    @GetMapping("/v1/ganttPlanTemplate/selectCurrentCplId")
    BaseResponse selectCurrentCplId(@RequestParam String ptId);

    @GetMapping("/v1/ganttPlanTemplate/getMilestoneGanttPlanTemp")
    BaseResponse getMilestoneGanttPlanTemp();

    /**********************************************地面坍塌情况***************************************************/
    @PostMapping("/v1/qsGroundCollapse/createOrUpdateQsGroundCollapse")
    BaseResponse createOrUpdateQsGroundCollapse(@RequestBody QsGroundCollapseModel request);

    @PostMapping("/v1/qsGroundCollapse/getQsGroundCollapseList")
    BaseResponse getQsGroundCollapseList(@RequestBody QsGroundCollapseModel request, @RequestHeader String token);

    @GetMapping("/v1/qsGroundCollapse/getQsGroundCollapseById")
    BaseResponse getQsGroundCollapseById(@RequestParam String gcId);


    /**********************************************项目建议书***************************************************/

    @PostMapping("/v1/designProposal/createOrUpdateDesignProposal")
    public BaseResponse createOrUpdateDesignProposal(@RequestBody DesignProposalModel request);

    @PostMapping("/v1/designProposal/getDesignProposalList")
    public BaseResponse getDesignProposalList(@RequestBody DesignProposalModel request, @RequestHeader String token);

    @PostMapping("/v1/designProposal/getDesignProposalListNoAuth")
    public BaseResponse getDesignProposalListNoAuth(@RequestBody DesignProposalModel request);

    @GetMapping("/v1/designProposal/getDesignProposalById")
    public BaseResponse getDesignProposalById(@RequestParam String dpId);

    /**********************************************领导看板***************************************************/

    @GetMapping("/v1/kb/getWorkbenchOverviewModel")
    public BaseResponse getWorkbenchOverviewModel();

    @GetMapping("/v1/kb/getWorkbenchDetailStatistics")
    BaseResponse getWorkbenchDetailStatistics(@RequestParam Integer qsrCategory);

    @GetMapping("/v1/kb/getQsrLastSevenDaysStatistics")
    BaseResponse getQsrLastSevenDaysStatistics(@RequestParam Integer qsrCategory);

    @PostMapping("/v1/kb/getQsrYearQuarterMonthStatistics")
    BaseResponse getQsrYearQuarterMonthStatistics(@RequestBody QsRecordRequest request);

    @GetMapping("/v1/kb/getContractStatisticsGourpConType")
    BaseResponse getContractStatisticsGourpConType();

    @GetMapping("/v1/kb/getContractStatisticsOverview")
    BaseResponse getContractStatisticsOverview();

    @GetMapping("/v1/kb/getContractStatisticsSigning")
    BaseResponse getContractStatisticsSigning();

    @GetMapping("/v1/kb/getContractStatisticsBiddingType")
    BaseResponse getContractStatisticsBiddingType();

    @GetMapping("/v1/kb/getConPayStatisticsOverview")
    BaseResponse getConPayStatisticsOverview();

    @GetMapping("/v1/kb/getConPayStatisticsGroupByDep")
    BaseResponse getConPayStatisticsGroupByDep();

    @GetMapping("/v1/kb/getConPayStatisticsByMonth")
    BaseResponse getConPayStatisticsByMonth();

    @GetMapping("/v1/kb/getConPayStatisticsRanking")
    BaseResponse getConPayStatisticsRanking(@RequestParam Integer size);

    @GetMapping("/v1/kb/getInvStatisticsOverview")
    BaseResponse getInvStatisticsOverview();

    @GetMapping("/v1/kb/getInvStatisticsGroupDep")
    BaseResponse getInvStatisticsGroupDep();

    @GetMapping("/v1/kb/getInvStatisticsByComplete")
    BaseResponse getInvStatisticsByComplete();

    @GetMapping("/v1/kb/getInvStatisticsByRanking")
    BaseResponse getInvStatisticsByRanking(@RequestParam Integer size);

    @GetMapping("/v1/kb/getConEcStatisticsOverview")
    BaseResponse getConEcStatisticsOverview();

    @GetMapping("/v1/kb/getConEcStatisticsByGrade")
    BaseResponse getConEcStatisticsByGrade();

    @GetMapping("/v1/kb/getConEcStatisticsChangeSituation")
    BaseResponse getConEcStatisticsChangeSituation();

    @GetMapping("/v1/kb/getConEcStatisticsByRanking")
    BaseResponse getConEcStatisticsByRanking(@RequestParam Integer size);

    @GetMapping("/v1/kb/getQsMonthlyStatisticsByYear")
    BaseResponse getQsMonthlyStatisticsByYear(@RequestParam Integer qsrCategory, @RequestHeader String token);



    /**********************************************摄像头信息***************************************************/
    @PostMapping("/SmartSite/v1/camera/createOrUpdateSmartSiteCamera")
    BaseResponse createOrUpdateSmartSiteCamera(@RequestBody SmartSiteCameraModel request);

    @PostMapping("/SmartSite/v1/camera/getSmartSiteCameraList")
    BaseResponse getSmartSiteCameraList(@RequestBody SmartSiteCameraModel request, @RequestHeader String token);

    @PostMapping("/SmartSite/v1/camera/getSmartSiteCameraRecordList")
    BaseResponse getSmartSiteCameraRecordList(@RequestBody com.jpmsoft.projectConsumer.pojo.dto.cockpit.CameraRecordingRequest request, @RequestHeader("token") String token);

    @PostMapping("/SmartSite/v1/camera/getSmartSiteCameraRecordCalendar")
    BaseResponse getSmartSiteCameraRecordCalendar(@RequestBody com.jpmsoft.projectConsumer.pojo.dto.cockpit.CameraRecordCalendarRequest request, @RequestHeader("token") String token);
    @PostMapping("/SmartSite/v1/camera/getSmartSiteCameraPlaybackConfig")
    BaseResponse getSmartSiteCameraPlaybackConfig(@RequestBody com.jpmsoft.projectConsumer.pojo.dto.cockpit.CameraRecordingRequest request, @RequestHeader("token") String token);
    @PostMapping("/SmartSite/v1/camera/getSmartSiteCameraCapabilities")
    BaseResponse getSmartSiteCameraCapabilities(@RequestBody SmartSiteCameraModel request, @RequestHeader("token") String token);
    @PostMapping("/SmartSite/v1/camera/controlSmartSiteCameraPtz")
    BaseResponse controlSmartSiteCameraPtz(@RequestBody com.jpmsoft.projectConsumer.pojo.dto.cockpit.CameraPtzRequest request, @RequestHeader("token") String token);

    @PostMapping("/SmartSite/v1/camera/getSmartSiteCameraStatuses")
    BaseResponse getSmartSiteCameraStatuses(@RequestBody SmartSiteCameraModel request, @RequestHeader("token") String token);

    @PostMapping("/SmartSite/v1/camera/getSmartSiteCameraPlayUrl")
    BaseResponse getSmartSiteCameraPlayUrl(@RequestBody SmartSiteCameraModel request,
                                          @RequestHeader("token") String token);

    @PostMapping("/SmartSite/v1/camera/getSmartSiteCameraNoAuthList")
    BaseResponse getSmartSiteCameraNoAuthList(@RequestBody SmartSiteCameraModel request);

    @GetMapping("/SmartSite/v1/camera/getSmartSiteCameraById")
    BaseResponse getSmartSiteCameraById(@RequestParam String sscId);


    @GetMapping("/SmartSite/v1/camera/deleteSmartSiteCameraById")
    BaseResponse deleteSmartSiteCameraById(@RequestParam String sscId);

    @GetMapping("/SmartSite/v1/camera/getSmartSiteCameraByPid")
    BaseResponse getSmartSiteCameraByPid(@RequestParam(required = false) String pId, @RequestParam(required = false) String sod);

    /**********************************************项目全景及工程概况***************************************************/
    @PostMapping("/SmartSite/v1/proPanorama/createOrUpdateProjectPanoramaOverview")
    BaseResponse createOrUpdateProjectPanoramaOverview(@RequestBody ProjectPanoramaOverviewModel request);

    @PostMapping("/SmartSite/v1/proPanorama/getProjectPanoramaOverviewList")
    BaseResponse getProjectPanoramaOverviewList(@RequestBody ProjectPanoramaOverviewModel request, @RequestHeader String token);

    @PostMapping("/SmartSite/v1/proPanorama/getProjectPanoramaList")
    BaseResponse getProjectPanoramaList(@RequestHeader String token);

    @GetMapping("/SmartSite/v1/proPanorama/getProjectPanoramaOverviewById")
    BaseResponse getProjectPanoramaOverviewById(@RequestParam String ppoId);

    @GetMapping("/SmartSite/v1/proPanorama/deleteProjectPanoramaOverviewById")
    BaseResponse deleteProjectPanoramaOverviewById(@RequestParam String ppoId);

    /**********************************************预算审计***************************************************/
    @PostMapping("/audit/v1/budget/createOrUpdateAuditBudget")
    BaseResponse createOrUpdateAuditBudget(@RequestBody AuditBudgetModel request);

    @PostMapping("/audit/v1/budget/getAuditBudgetList")
    BaseResponse getAuditBudgetList(@RequestBody AuditBudgetModel request, @RequestHeader String token);

    @GetMapping("/audit/v1/budget/getAuditBudgetById")
    BaseResponse getAuditBudgetById(@RequestParam String abId);

    @GetMapping("/audit/v1/budget/getAuditBudgetBySecId")
    BaseResponse getAuditBudgetBySecId(@RequestParam String secId);

    /**********************************************结算审计***************************************************/
    @PostMapping("/audit/v1/settlement/createOrUpdateAuditSettlement")
    BaseResponse createOrUpdateAuditSettlement(@RequestBody AuditSettlementModel request);

    @PostMapping("/audit/v1/settlement/getAuditSettlementList")
    BaseResponse getAuditSettlementList(@RequestBody AuditSettlementModel request, @RequestHeader String token);

    @GetMapping("/audit/v1/settlement/getAuditSettlementById")
    BaseResponse getAuditSettlementById(@RequestParam String asId);

    @GetMapping("/audit/v1/settlement/getApproveAuditSettlementByConId")
    BaseResponse getApproveAuditSettlementByConId(@RequestParam String conId);

    /**********************************************竣工图设计***************************************************/

    @PostMapping("/v1/designCompleted/createOrUpdateDesignCompleted")
    public BaseResponse createOrUpdateDesignCompleted(@RequestBody DesignCompletedModel request);

    @PostMapping("/v1/designCompleted/getDesignCompletedList")
    public BaseResponse getDesignCompletedList(@RequestBody DesignCompletedModel request, @RequestHeader String token);

    @PostMapping("/v1/designCompleted/getDesignCompletedListNoAuth")
    public BaseResponse getDesignCompletedListNoAuth(@RequestBody DesignCompletedModel request);

    @GetMapping("/v1/designCompleted/getDesignCompletedById")
    public BaseResponse getDesignCompletedById(@RequestParam String dcId);


    /**********************************************地质勘察***************************************************/
    @PostMapping("/v1/designGeology/createOrUpdateDesignGeology")
    BaseResponse createOrUpdateDesignGeology(@RequestBody DesignGeologyModel request);

    @PostMapping("/v1/designGeology/getDesignGeologyList")
    BaseResponse getDesignGeologyList(@RequestBody DesignGeologyModel request, @RequestHeader String token);

    @PostMapping("/v1/designGeology/getDesignGeologyListNoAuth")
    BaseResponse getDesignGeologyListNoAuth(@RequestBody DesignGeologyModel request);

    @GetMapping("/v1/designGeology/getDesignGeologyById")
    BaseResponse getDesignGeologyById(@RequestParam String dgId);

    /**********************************************桩基检测***************************************************/

    @PostMapping("/v1/designPile/createOrUpdateDesignPile")
    public BaseResponse createOrUpdateDesignPile(@RequestBody DesignPileDto request);

    @PostMapping("/v1/designPile/getDesignPileList")
    public BaseResponse getDesignPileList(@RequestBody DesignPileDto request, @RequestHeader String token);

    @PostMapping("/v1/designPile/getDesignPileListNoAuth")
    public BaseResponse getDesignPileListNoAuth(@RequestBody DesignPileDto request);

    @GetMapping("/v1/designPile/getDesignPileById")
    public BaseResponse getDesignPileById(@RequestParam String dpId);


    /**********************************************施工质量安全日志***************************************************/


    @PostMapping("/v1/buildQualitySafetyLog/createBuildQualitySafetyLog")
    BaseResponse createBuildQualitySafetyLog(@RequestBody BuildQualitySafetyLogDto request);

    @PostMapping("/v1/buildQualitySafetyLog/updateBuildQualitySafetyLog")
    BaseResponse updateBuildQualitySafetyLog(@RequestBody BuildQualitySafetyLogDto request);

    @PostMapping("/v1/buildQualitySafetyLog/getBuildQualitySafetyLogList")
    BaseResponse getBuildQualitySafetyLogList(@RequestBody BuildQualitySafetyLogDto request, @RequestHeader String token);

    @GetMapping("/v1/buildQualitySafetyLog/getBuildQualitySafetyLogById")
    BaseResponse getBuildQualitySafetyLogById(@RequestParam String id);


    /**********************************************巡检任务***************************************************/

    @PostMapping("/v1/qsPlanTask/createOrUpdateQsPlanTask")
    BaseResponse createOrUpdateQsPlanTask(@RequestBody QsPlanTaskDto request);

    @PostMapping("/v1/qsPlanTask/updateQsPlanTaskPartialInfo")
    BaseResponse updateQsPlanTaskPartialInfo(@RequestBody QsPlanTaskDto request);

    @PostMapping("/v1/qsPlanTask/updateQsPlanTaskRequireTime")
    BaseResponse updateQsPlanTaskRequireTime(@RequestBody QsPlanTaskDto request);

    @PostMapping("/v1/qsPlanTask/getQsPlanTaskList")
    BaseResponse getQsPlanTaskList(@RequestBody QsPlanTaskDto request, @RequestHeader String token);

    @PostMapping("/v1/qsPlanTask/getQsPlanTaskListNoAuth")
    BaseResponse getQsPlanTaskListNoAuth(@RequestBody QsPlanTaskDto request);

    @PostMapping("/v1/qsPlanTask/getQsPlanTaskPage")
    BaseResponse getQsPlanTaskPage(@RequestBody QsPlanTaskDto request, @RequestHeader String token);

    @PostMapping("/v1/qsPlanTask/getProSafetyLedgerPage")
    BaseResponse getProSafetyLedgerPage(@RequestBody ProSafetyLedgerDtlDTO request, @RequestHeader String token);

    @PostMapping("/v1/qsPlanTask/getProSafetyLedgerPageYW")
    BaseResponse getProSafetyLedgerPageYW(@RequestBody ProSafetyLedgerDtlDTO request, @RequestHeader String token);

    @GetMapping("/v1/qsPlanTask/getQsPlanTaskById")
    BaseResponse getQsPlanTaskById(@RequestParam String id);

    @PostMapping("/v1/qsPlanTask/qualitySafetyStatistics")
    BaseResponse qualitySafetyStatistics(@RequestBody QsRecordDto request, @RequestHeader String token);

    @PostMapping("/v1/qsPlanTask/getHighQsRecordList")
    BaseResponse getHighQsRecordList(@RequestBody QsRecordDto request, @RequestHeader String token);

    /**********************************************质量安全抽查***************************************************/

    @PostMapping("/v1/qsPTSpotCheck/createOrUpdateQsPTSpotCheck")
    BaseResponse createOrUpdateQsPTSpotCheck(@RequestBody QsPTSpotCheckDto request);

    @PostMapping("/v1/qsPTSpotCheck/getQsPTSpotCheckList")
    BaseResponse getQsPTSpotCheckList(@RequestBody QsPTSpotCheckDto request, @RequestHeader String token);

    @PostMapping("/v1/qsPTSpotCheck/getQsPTSpotCheckListNoAuth")
    BaseResponse getQsPTSpotCheckListNoAuth(@RequestBody QsPTSpotCheckDto request);

    @GetMapping("/v1/qsPTSpotCheck/getQsPTSpotCheckById")
    BaseResponse getQsPTSpotCheckById(@RequestParam String id);

    @PostMapping("/v1/qsPTSpotCheck/approvalSpotCheckData")
    BaseResponse approvalSpotCheckData(@RequestBody QsPTSpotCheckDto request);


    @PostMapping("/v1/qsPTSpotCheckRecord/updateSpotCheckRectifyPic")
    BaseResponse updateSpotCheckRectifyPic(@RequestBody QsPTSpotCheckRecordRequest request);

    /**********************************************进度计划变更***************************************************/


    @PostMapping("/v1/ganttPlanChange/createOrUpdateGanttPlanChange")
    BaseResponse createOrUpdateGanttPlanChange(@RequestBody GanttPlanChangeDTO request);

    @PostMapping("/v1/ganttPlanChange/getGanttPlanChangeList")
    BaseResponse getGanttPlanChangeList(@RequestBody GanttPlanChangeDTO request, @RequestHeader String token);

    @GetMapping("/v1/ganttPlanChange/getGanttPlanChangeById")
    BaseResponse getGanttPlanChangeById(@RequestParam String id);

    @GetMapping("/v1/ganttPlanChange/approved2SyncGanttPlan")
    BaseResponse approved2SyncGanttPlan(@RequestParam String id);


    @GetMapping("/v1/ganttPlanChange/approved2SyncGanttPlanAndProjectCp")
    BaseResponse approved2SyncGanttPlanAndProjectCp(@RequestParam String id);

    @GetMapping("/v1/ganttPlanChange/can2SubmitProcess")
    BaseResponse can2SubmitProcess(@RequestParam String id);

    /**********************************************设计文件审核***************************************************/


    @PostMapping("/v1/designDocReview/createOrUpdateDocReview")
    BaseResponse createOrUpdateDocReview(@RequestBody DesignDocReviewDTO request);

    @PostMapping("/v1/designDocReview/getDesignDocReviewList")
    BaseResponse getDesignDocReviewList(@RequestBody DesignDocReviewDTO request, @RequestHeader String token);

    @GetMapping("/v1/designDocReview/getDesignDocReviewById")
    BaseResponse getDesignDocReviewById(@RequestParam String id);

    /*******************************************施工图审查*******************************************/

    @PostMapping("/v1/designDrawReview/createOrUpdateDesignDrawReview")
    BaseResponse createOrUpdateDesignDrawReview(@RequestBody DesignDrawReviewDTO request, @RequestHeader String token);

    @PostMapping("/v1/designDrawReview/getDesignDrawReviewList")
    BaseResponse getDesignDrawReviewList(@RequestBody DesignDrawReviewDTO request, @RequestHeader String token);

    @GetMapping("/v1/designDrawReview/getDesignDrawReviewById")
    BaseResponse getDesignDrawReviewById(@RequestParam String id);

    /*******************************************建设管理方案*******************************************/

    @PostMapping("/v1/proManagePlan/createOrUpdateProManagePlan")
    BaseResponse createOrUpdateProManagePlan(@RequestBody ProManagePlanDTO request);

    @PostMapping("/v1/proManagePlan/getProManagePlanList")
    BaseResponse getProManagePlanList(@RequestBody ProManagePlanDTO request, @RequestHeader String token);

    @GetMapping("/v1/proManagePlan/getProManagePlanById")
    BaseResponse getProManagePlanById(@RequestParam String id);

    /*******************************************夜间施工备案*******************************************/

    @PostMapping("/v1/nightConstructionFiling/createOrUpdateNightConstructionFiling")
    BaseResponse createOrUpdateNightConstructionFiling(@RequestBody NightConstructionFilingDTO request);

    @PostMapping("/v1/nightConstructionFiling/getNightConstructionFilingList")
    BaseResponse getNightConstructionFilingList(@RequestBody NightConstructionFilingDTO request, @RequestHeader String token);

    @GetMapping("/v1/nightConstructionFiling/getNightConstructionFilingById")
    BaseResponse getNightConstructionFilingById(@RequestParam String id);

    /*******************************************pmp日志预览*******************************************/
    @GetMapping(value = "/v1/log/logView")
    BaseResponse getLogs(@RequestParam(required = false) Integer size);

    /*******************************************框架协议单位查询*******************************************/
    @GetMapping("/v1/MHCompanyController/getCompanyListByApplyType")
    BaseResponse getCompanyListByApplyType(@RequestParam String applyType, @RequestHeader("token") String token);

    /*******************************************技术核定单*******************************************/
    @PostMapping("/v1/technicalCheck/createOrUpdateTechnicalCheck")
    BaseResponse createOrUpdateTechnicalCheck(@RequestBody TechnicalCheckDTO request);

    @PostMapping("/v1/technicalCheck/getTechnicalCheckList")
    BaseResponse getTechnicalCheckList(@RequestBody TechnicalCheckDTO request, @RequestHeader String token);

    @GetMapping("/v1/technicalCheck/getTechnicalCheckById")
    BaseResponse getTechnicalCheckById(@RequestParam String id);


    @PostMapping("/v1/projectManagementOutline/createOrUpdateProjectManagementOutline")
    BaseResponse createOrUpdateProjectManagementOutline(@RequestBody ProjectManagementOutlineDto request);

    @PostMapping("/v1/projectManagementOutline/getProjectManagementOutlineList")
    BaseResponse getProjectManagementOutlineList(@RequestBody ProjectManagementOutlineDto request);

    @PostMapping("/v1/projectManagementOutline/getProjectManagementOutlineClassInfo")
    BaseResponse getProjectManagementOutlineClassInfo(@RequestBody ProjectManagementOutlineDto request);

    @GetMapping("/v1/projectManagementOutline/getProjectManagementOutlineById")
    BaseResponse getProjectManagementOutlineById(@RequestParam String id);

    @GetMapping("/v1/projectManagementOutline/deleteProjectManagementOutlineById")
    BaseResponse deleteProjectManagementOutlineById(@RequestParam String id);

    /***************************设计管理（项目信息）***********************/
    @PostMapping("/v1/designProjectInfo/getDesignProjectInfoList")
    BaseResponse getDesignProjectInfoList(@RequestBody DesignProjectInfoModel request, @RequestHeader String token);

    @PostMapping("/v1/designProjectInfo/getDesignProjectInfoByBusiness")
    BaseResponse getDesignProjectInfoByBusiness(@RequestBody DesignProjectInfoModel request);


    @PostMapping("/v1/replyInfo/createOrUpdateReplyInfo")
    BaseResponse createOrUpdateReplyInfo(@RequestBody ReplyInfoModel request);

    @PostMapping("/v1/replyInfo/getReplyInfoList")
    BaseResponse getReplyInfoList(@RequestBody ReplyInfoModel request, @RequestHeader String token);

    @GetMapping("/v1/replyInfo/getReplyInfoById")
    BaseResponse getReplyInfoById(@RequestParam String id);

    /***************************质量管理体系***********************/
    @PostMapping("/v1/qsManageSystem/createOrUpdateQsManageSystem")
    BaseResponse createOrUpdateQsManageSystem(@RequestBody QsManageSystemDto request);

    @PostMapping("/v1/qsManageSystem/getQsManageSystemList")
    BaseResponse getQsManageSystemList(@RequestBody QsManageSystemDto request, @RequestHeader String token);

    @GetMapping("/v1/qsManageSystem/getQsManageSystemById")
    BaseResponse getQsManageSystemById(@RequestParam String id);

    @GetMapping("/v1/qsManageSystem/getQsNodePerson")
    BaseResponse getQsNodePersonByMark(@RequestParam String pId);

    @PostMapping("/v1/NoticeInfo/saveOrUpdateNoticeInfo")
    BaseResponse saveOrUpdateNoticeInfo(@RequestBody NoticeInfoModel request);

    @PostMapping("/v1/NoticeInfo/approvalData")
    BaseResponse approvalData(@RequestBody NoticeInfoModel request);

    @PostMapping("/v1/NoticeInfo/getNoticeInfoList")
    BaseResponse getNoticeInfoList(@RequestBody NoticeInfoModel request, @RequestHeader String token);

    @GetMapping("/v1/NoticeInfo/getNoticeInfoDetail")
    BaseResponse getNoticeInfoDetail(@RequestParam String id);


    @GetMapping("/v1/awardTicket/countUnfinishedFlowByContractId")
    BaseResponse countUnFinishFlowByContractId(@RequestParam String contractId, @RequestParam Integer atType);

    @PostMapping("/measure/countEngineeringContactOrderMeasureUsed")
    BaseResponse countEngineeringContactOrderMeasureUsed(@RequestParam(required = false) String measureId);

    @PostMapping("/measure/countEngineeringQuantitiesVisaMeasureUsed")
    BaseResponse countEngineeringQuantitiesVisaMeasureUsed(@RequestParam(required = false) String measureId);

    @PostMapping("/measure/countClearSectionMeasureUsed")
    BaseResponse countClearSectionMeasureUsed(@RequestParam(required = false) String measureId);

    @PostMapping("/measure/countConstructionUnitOrderMeasureUsed")
    BaseResponse countConstructionUnitOrderMeasureUsed(@RequestParam(required = false) String measureId);

    @PostMapping("/measure/countEngineeringChangeMeasureUsed")
    BaseResponse countEngineeringChangeMeasureUsed(@RequestParam(required = false) String measureId);

    @PostMapping("/measure/countContractPriceMeasureUsed")
    BaseResponse countContractPriceMeasureUsed(@RequestParam(required = false) String measureId);

    @PostMapping("/measure/countSectionLowestPriceMeasureUsed")
    BaseResponse countSectionLowestPriceMeasureUsed(@RequestParam(required = false) String measureId);

    /***************************大事记***********************/

    @PostMapping("/v1/memoir/createOrUpdateMemoirInfo")
    BaseResponse createOrUpdateMemoirInfo(@RequestBody MemoirInfoDTO request);


    @PostMapping("/v1/memoir/getMemoirInfoList")
    BaseResponse getMemoirInfoList(@RequestBody MemoirInfoDTO request, @RequestHeader String token);

    @GetMapping("/v1/memoir/approve2ComplateGanntTask")
    BaseResponse approve2ComplateGanntTask(@RequestParam String id);

    @GetMapping("/v1/memoir/getMemoirInfoById")
    BaseResponse getMemoirInfoById(@RequestParam String id);

    @PostMapping("/v1/memoir/memoirComment")
    BaseResponse memoirComment(@RequestBody MemoirCommentDTO data, @RequestHeader String token);

    @GetMapping("/v1/memoir/deleteMemoirComment")
    BaseResponse deleteMemoirComment(@RequestParam String id, @RequestHeader String token);

    @GetMapping("/v1/memoir/memoirPraise")
    BaseResponse memoirPraise(@RequestParam String id, @RequestHeader String token);

    @GetMapping("/v1/memoir/deleteMemoirPraise")
    BaseResponse deleteMemoirPraise(@RequestParam String id, @RequestHeader String token);


    @PostMapping("/v1/qsRecord/qualitySafetyStatisticFowYW")
    BaseResponse getQualitySafetyStatisticFowYW(@RequestBody QualitySafetyStatisticFowYWRequest request);

    @PostMapping("/v1/qsRecord/createOrUpdateQsRecordRequest")
    BaseResponse createOrUpdateQsRecordRequest(@RequestBody QsRecordRequest request);

    @GetMapping("/v1/qsRecord/deleteByQsrId")
    BaseResponse deleteByQsrId(@RequestParam String qsrId);

    @PostMapping("/v1/qsPlanTask/export")
    Response exportQsPlanTask(@RequestBody QsPlanTaskDto request, @RequestHeader String token);

    @PostMapping("/v1/qsType/exportQsTypeAll")
    Response exportQsTypeAll(@RequestBody QsTypeModel request, @RequestHeader String token);

    @PostMapping("/v1/qsPlanTask/updateQsPlanTaskQsRecordType")
    BaseResponse updateQsPlanTaskQsRecordType(@RequestBody QsPlanTaskQsRecordTypeRequest request);


    @GetMapping("/v1/questionnaire/downloadFile/qrCode")
    byte[] qrCode(@RequestParam String reportId);

    @GetMapping("/v1/questionnaire/checkPhone")
    BaseResponse checkPhone(@RequestParam String phone);

    @GetMapping("/v1/questionnaire/checkStudentNum")
    BaseResponse checkStudentNum(@RequestParam String studentNum);

    @GetMapping("/v1/questionnaire/outMax")
    BaseResponse outMax();

    @GetMapping("/v1/qsPlanTask/copyHighQsRecord")
    BaseResponse copyHighQsRecord(@RequestParam String qstId, @RequestParam String qsrId, @RequestParam String ptId, @RequestHeader String token);

    @GetMapping("/v1/qsPlanTask/verifyqsPlanTask")
    BaseResponse verifyqsPlanTask(@RequestParam String pId, @RequestParam String ptType, @RequestHeader String token);

    @GetMapping("/v1/ganttTasks/Cj/queryGanttTasksProcessByTaskID")
    BaseResponse queryGanttTasksProcessByTaskID(@RequestParam String taskId);

    @PostMapping("/v1/ganttTasksProcess/Cj/createOrUpdateGanttTasksProcess")
    BaseResponse createOrUpdateGanttTasksProcessCj(@RequestBody GanttTasksProcessRequest ganttTasksProcess);

    @GetMapping("/v1/ganttTasks/Cj/rewriteGanttTasksByTaskID")
    BaseResponse rewriteGanttTasksByTaskID(@RequestParam String taskId);


    @PostMapping("/v1/ganttTasksOverdue/createOrUpdateGanttOverdue")
    BaseResponse createOrUpdateGanttPlanCj(@RequestBody GanttTasksOverdueDTO ganttTasksOverdueDTO);

    @GetMapping("/v1/ganttTasksOverdue/getGanttTasksOverdueByTaskId")
    BaseResponse getGanttTasksOverdueByTaskIdCj(@RequestParam String taskId);

    @GetMapping("/v1/ganttTasksOverdue/getGanttTasksOverdueByTaskIdByPage")
    BaseResponse getGanttTasksOverdueByTaskIdByPageCj(@RequestParam int page, @RequestParam int size, @RequestParam String taskId);


    @PostMapping("/v1/ganttTasks/Cj/getGanttTasksByProject")
    BaseResponse getGanttTasksByProject(@RequestBody GanttTasksCjDTO request);

    @PostMapping("/v1/ganttTasks/Cj/getGanttTasksByProjectClassInfo")
    BaseResponse getGanttTasksByProjectClassInfo(@RequestBody GanttTasksCjDTO request);


    @PostMapping("/v1/ganttTasks/Cj/getCjMilestonesGanttTasks")
    BaseResponse getCjMilestonesGanttTasks(@RequestBody GanttTasksRequest request);

    @GetMapping("/v1/ganttTasks/Cj/getCjGanttTasksById")
    BaseResponse getCjGanttTasksById(@RequestParam String taskId);

    @PostMapping("/v1/ganttTasksCancel/createOrUpdateGanttCannel")
    BaseResponse createOrUpdateGanttCannel(@RequestBody GanttTasksCancelDTO ganttTasksCancelDTO);

    @GetMapping("/v1/ganttTasksCancel/getGanttCannelByTaskId")
    BaseResponse getGanttTasksCannelByTaskId(@RequestParam String taskId);

    @GetMapping("/v1/ganttTasksCancel/getGanttTasksOverdueByTaskIdByPage")
    BaseResponse getGanttTasksCannelByTaskIdByPage(@RequestParam int page, @RequestParam int size, @RequestParam String taskId);

    @GetMapping("/v1/ganttTasksCancel/rewriteTask")
    BaseResponse rewriteTask(@RequestParam String taskId);

    @GetMapping("/v1/ganttTasksProcess/Cj/getGanttTasksProcessById")
    BaseResponse getGanttTasksProcessByIdCj(@RequestParam String gtpId);

    @GetMapping("/v1/ganttTasksOverdue/getGanttTasksOverdueById")
    BaseResponse getGanttTaskOverdueById(@RequestParam String id);

    @GetMapping("/v1/ganttTasksOverdue/existInApprovalTaskOverdue")
    BaseResponse existInApprovalTaskOverdue(@RequestParam String taskId);

    @GetMapping("/v1/ganttTasksCancel/getGanttCannelById")
    BaseResponse getGanttCannelById(@RequestParam String id);

    @GetMapping("/v1/ganttTasksCancel/existApproveGanttCancel")
    BaseResponse existApproveGanttCancel(@RequestParam String taskId);


    /***************************进度里程碑统计***********************/
    @GetMapping("/v1/ganttDataImport/importData")
    BaseResponse importData();

    /**********************************************计划管理***************************************************/
    @PostMapping("/v1/ganttMilestoneStatistics/getGanttMilestoneList")
    BaseResponse getGanttMilestoneList(@RequestBody GanttMilestoneStatisticsRequest request, @RequestHeader String token);

    @PostMapping("/v1/ganttMilestoneStatistics/getGanttMilestoneClassInfo")
    BaseResponse getGanttMilestoneClassInfo(@RequestBody GanttMilestoneStatisticsRequest request, @RequestHeader String token);

    @PostMapping("/v1/ganttRemark/getGanttRemarkList")
    BaseResponse getGanttRemarkList(@RequestBody GanttRemarkRequest request);

    @PostMapping("/v1/ganttRemark/addGanttRemark")
    BaseResponse addGanttRemark(@RequestBody GanttRemarkRequest request, @RequestHeader String token);

    @PostMapping("/v1/ganttMilestoneStatistics/ganttMilestoneListExportExcel")
    Response ganttMilestoneListExportExcel(@RequestBody GanttMilestoneStatisticsRequest request, @RequestHeader String token);

    @PostMapping("/v1/ganttMilestoneStatistics/getMonthlyReport")
    BaseResponse getMonthlyReport(@RequestBody MonthlyReportDTO request, @RequestHeader String token);

    @PostMapping("/v1/qsType/getQsTypeInfo")
    BaseResponse getQsTypeInfo(@RequestBody QsTypeRecordRequest request);

    @PostMapping("/v1/qsPlanTask/getQsPlanTaskListExport")
    BaseResponse getQsPlanTaskListExport(@RequestBody QsPlanTaskDto request, @RequestHeader String token);

    @PostMapping("/v1/qsType/exportQsTypeInfo")
    Response exportQsTypeInfo(@RequestBody QsTypeRecordRequest request, @RequestHeader String token);

    @PostMapping("/v1/qsType/exportQsTypeAll")
    Response exportQsTypeInfoAll(@RequestBody QsTypeRecordRequest request, @RequestHeader String token);

    @PostMapping("/v1/ganttMilestoneYear/generateGanttMilestoneYear")
    BaseResponse generateGanttMilestoneYear(@RequestBody GanttMilestoneStatisticsRequest request, @RequestHeader String token);

    @GetMapping("/v1/ganttMilestoneYear/existsGanttMilestoneYear")
    BaseResponse existsGanttMilestoneYear(@RequestParam("year") String year);

    @PostMapping("/v1/ganttMilestoneYear/getGanttMilestoneYearList")
    BaseResponse getGanttMilestoneYearList(@RequestBody GanttMilestoneStatisticsRequest request, @RequestHeader String token);

    @PostMapping("/v1/ganttMilestoneYear/getGanttMilestoneYearClassInfo")
    BaseResponse getGanttMilestoneYearClassInfo(@RequestBody GanttMilestoneStatisticsRequest request, @RequestHeader String token);

    /**********************************************攻坚任务计划管理***************************************************/
    @PostMapping("/v1/challengePlanAnnual/createOrUpdateChallengePlanAnnual")
    BaseResponse createOrUpdateChallengePlanAnnual(@RequestBody ChallengePlanAnnualRequest request);

    @PostMapping("/v1/challengePlanAnnual/deleteChallengePlanAnnualById")
    BaseResponse deleteChallengePlanAnnualById(@RequestBody ChallengePlanAnnualRequest request);

    @PostMapping("/v1/challengePlanAnnual/getChallengePlanAnnualList")
    BaseResponse getChallengePlanAnnualList(@RequestBody ChallengePlanAnnualRequest request);

    @GetMapping("/v1/challengePlanAnnual/getChallengePlanAnnualById")
    BaseResponse getChallengePlanAnnualById(@RequestParam("id") String id);

    @PostMapping("/v1/challengePlanMonthly/createChallengePlanMonthly")
    BaseResponse createChallengePlanMonthly(@RequestBody ChallengePlanAnnualRequest request);

    @PostMapping("/v1/challengePlanMonthly/updateChallengePlanMonthly")
    BaseResponse updateChallengePlanMonthly(@RequestBody ChallengePlanMonthlyRequest request);

    @PostMapping("/v1/challengePlanMonthly/getChallengePlanMonthlyList")
    BaseResponse getChallengePlanMonthlyList(@RequestBody ChallengePlanMonthlyPageRequest request, @RequestHeader String token);

    @PostMapping("/v1/challengePlanMonthly/getChallengePlanMonthlyClassInfo")
    BaseResponse getChallengePlanMonthlyClassInfo(@RequestBody ChallengePlanMonthlyPageRequest request, @RequestHeader String token);

    @GetMapping("/v1/challengePlanMonthly/getChallengePlanMonthlyById")
    BaseResponse getChallengePlanMonthlyById(@RequestParam("id") String id, @RequestHeader String token);

    @PostMapping("/v1/challengePlanMonthly/batchUpdateStatus")
    BaseResponse batchUpdateStatus(@RequestBody ChallengePlanMonthlyRequest request);

    /**********************************************项目看板***************************************************/
    @GetMapping("/v1/projectFund/getProjectFund")
    BaseResponse getProjectFund(@RequestParam String pId);

    @PostMapping("/v1/yw/groupProjectIssue/saveOrUpdateGroupProjectIssue")
    BaseResponse saveOrUpdateGroupProjectIssue(@RequestBody GroupProjectIssueModel request);

    @PostMapping("/v1/yw/groupProjectIssue/getGroupProjectIssueList")
    BaseResponse getGroupProjectIssueList(@RequestBody GroupProjectIssueModel request, @RequestHeader String token);

    @GetMapping("/v1/yw/groupProjectIssue/getGroupProjectIssueDetail")
    BaseResponse getGroupProjectIssueDetail(@RequestParam String id);

    @PostMapping("/v1/yw/groupProjectIssue/approvalData")
    BaseResponse approvalGroupProjectIssuelData(@RequestBody GroupProjectIssueModel request);

    @PostMapping("/v1/designQrCode/createOrupdateDesignQrCode")
    BaseResponse createOrupdateDesignQrCode(@RequestBody DesignQrCodeModel request, @RequestHeader String token);

    @GetMapping("/v1/designQrCode/getDesignQrCodeById")
    BaseResponse getDesignQrCodeById(@RequestParam String id, @RequestHeader String token);

    @GetMapping("/v1/designQrCode/getDeleteDesignQrCodeById")
    BaseResponse getDeleteDesignQrCodeById(@RequestParam String id, @RequestHeader String token);

    @PostMapping("/v1/designQrCode/checkDqcuserd")
    BaseResponse checkDqcuserd(@RequestBody DesignQrCodeModel request, @RequestHeader String token);

    @PostMapping("/v1/designQrCode/getDesignQrCodeList")
    BaseResponse getDesignQrCodeList(@RequestBody DesignQrCodeRequest request, @RequestHeader String token);

    @PostMapping("/v1/designQrCode/getDesignQrCodeClassInfo")
    BaseResponse getDesignQrCodeClassInfo(@RequestBody DesignQrCodeRequest request, @RequestHeader String token);

    @PostMapping("/v1/designresult/createOrupdateDesignResultsIssued")
    BaseResponse createOrupdateDesignResultsIssued(@RequestBody DesignResultsIssuedModel request, @RequestHeader String token);

    @PostMapping("/v1/designresult/getDesignResultsIssued")
    BaseResponse getDesignResultsIssuedList(@RequestBody DesignResultsIssuedRequest request, @RequestHeader String token);

    @PostMapping("/v1/designresult/getDesignResultsIssuedClassInfo")
    BaseResponse getDesignResultsIssuedClassInfo(@RequestBody DesignResultsIssuedRequest request, @RequestHeader String token);

    @GetMapping("/v1/designresult/getDesignResultsIssuedById")
    BaseResponse getDesignResultsIssuedById(@RequestParam String id, @RequestHeader String token);

    @GetMapping("/v1/designresult/getDesignResultsIssuedTree")
    BaseResponse getDesignResultsLssuedTree(@RequestParam String dirType, @RequestHeader String token);

    @PostMapping("/v1/designresult/delDesignResultsIssuedById")
    BaseResponse delDesignResultsIssuedById(@RequestBody DesignResultsIssuedModel request, @RequestHeader String token);

    @GetMapping("/v1/designQrCode/getDesignQrCodeDateById")
    BaseResponse getDesignQrCodeDateById(@RequestParam String id, @RequestHeader String token);

    @PostMapping("/v1/qsPlanTask/getProSafetyLedgerPageYWNew")
    BaseResponse getProSafetyLedgerPageYWNew(@RequestBody ProSafetyLedgerDtlDTO request, @RequestHeader String token);

    @PostMapping("/v1/qsPlanTask/getProSafetyLedgerPageYWExportNew")
    BaseResponse getProSafetyLedgerPageYWExportNew(@RequestBody ProSafetyLedgerDtlDTO request, @RequestHeader String token);

    @GetMapping("/v1/designresult/getDesignIssuedInfoById")
    BaseResponse getDesignIssuedInfoById(@RequestParam String id, @RequestHeader String token);

    @PostMapping("/v1/designIssued/getDesignIssuedList")
    BaseResponse getDesignIssuedList(@RequestBody DesignIssuedModel request, @RequestHeader String token);

    @GetMapping("/v1/designIssued/getDesignIssuedById")
    BaseResponse getDesignIssuedById(@RequestParam String id, @RequestHeader String token);

    @PostMapping("/v1/qsPlanTask/approveData")
    BaseResponse approveQsPlanTaskData(@RequestBody QsPlanTaskDto request);

    @PostMapping("/v1/qsPlanTask/refreshQsPlanTask")
    BaseResponse refreshQsPlanTask(@RequestBody QsPlanTaskDto request);

    @PostMapping("/v1/designresult/getDownloadLogListForDesign")
    BaseResponse getDownloadLogListForDesign(@RequestBody DesignResultsIssuedModel request, @RequestHeader String token);

    @PostMapping("/v1/ganttPlan/getGanttPlanLedgerNJCJ")
    BaseResponse getGanttPlanLedgerNJCJ(@RequestBody GanttPlanRequest request);

    @PostMapping("/v1/ganttPlan/getPreprojectInfoLedgerNJCJ")
    BaseResponse getPreprojectInfoLedgerNJCJ(@RequestBody GanttPlanRequest request);

    @PostMapping("/v1/designresult/backWriteIssuedParent")
    BaseResponse backWriteIssuedParent(@RequestBody DesignResultsIssuedModel request, @RequestHeader String token);

    @GetMapping("/v1/ganttTasks/Cj/checkGanttTaskCompleted")
    BaseResponse checkGanttTaskCompleted(@RequestParam("pId") String pId, @RequestParam("taskName") String taskName, @RequestParam(value = "gsId", required = false) String gsId);

    @PostMapping("/v1/projectProfile/getProjectProfileList")
    BaseResponse getProjectProfileList(@RequestBody ProjectProfileModel request);

    @GetMapping("/v1/projectProfile/getProjectProfileListByPid")
    BaseResponse getProjectProfileListByPid(@RequestParam String pId);

    @GetMapping("/v1/projectProfile/getProjectProfileById")
    BaseResponse getProjectProfileById(@RequestParam String id);

    @PostMapping("/v1/majpro/getNoMajproWeeklyNewspaperList")
    BaseResponse getNoMajproWeeklyNewspaperList(@RequestBody NoMajproWeeklyNewspaperRequest request);

    @PostMapping("/v1/trainRecord/getTrainRecord")
    BaseResponse getTrainRecord(@RequestBody TrainStaff trainStaff);

    @GetMapping(value = "/v1/applyRole/approve2SyncDepartmentP")
    BaseResponse approve2SyncDepartmentP(@RequestParam String id, @RequestHeader String token);

    @GetMapping(value = "/v1/applyRole/syncAll")
    BaseResponse syncAll(@RequestHeader String token);

    @PostMapping("/v1/noticeInfoClose/saveOrUpdateNoticeInfoClose")
    BaseResponse saveOrUpdateNoticeInfoClose(@RequestBody NoticeInfoCloseModel request);

    @PostMapping("/v1/noticeInfoClose/approvalDataNoticeInfoClose")
    BaseResponse approvalDataNoticeInfoClose(@RequestBody NoticeInfoCloseModel request);

    @PostMapping("/v1/noticeInfoClose/getNoticeInfoCloseList")
    BaseResponse getNoticeInfoCloseList(@RequestBody NoticeInfoCloseModel request, @RequestHeader String token);

    @GetMapping("/v1/noticeInfoClose/getNoticeInfoCloseDetail")
    BaseResponse getNoticeInfoCloseDetail(@RequestParam String id);

    /**********************************************住建厅项目业务情况月度进展***************************************************/
    @GetMapping("/v1/monthlyProgress/getContractAmount")
    BaseResponse getContractAmount(@RequestParam String year, @RequestParam String month, @RequestParam String pId);

    @GetMapping("/v1/monthlyProgress/selectAreaAndValue")
    BaseResponse selectAreaAndValue(@RequestParam String year, @RequestParam String month, @RequestParam String oId);

    @PostMapping("/v1/zjt/zjtStatistics/getProjectUpStatistics")
    BaseResponse getProjectUpStatistics(@RequestBody ProjectUpStatisticsDTO request);

    @PostMapping("/v1/zjt/zjtStatistics/selectExportProjectData")
    BaseResponse selectExportProjectData(@RequestBody Object request, @RequestHeader String token);

    @PostMapping("/v1/monthlyProgress/getProgressStatisticsList")
    BaseResponse getProgressStatisticsList(@RequestBody ProgressStatisticsRequest request, @RequestHeader String token);

    @PostMapping("/v1/monthlyProgress/getProjectActivity")
    BaseResponse getProjectActivity(@RequestBody ProjectActivityDto request, @RequestHeader String token);

    /**********************************************功能预警流程记录***************************************************/
    @PostMapping("/v1/integrityWarning/getIntegrityWarningLedger")
    BaseResponse getIntegrityWarningLedger(@RequestBody FunctionalIntegrityWarningRequest request);
    /**********************************************系统外部用户注册***************************************************/

    @PostMapping("/v1/integrityWarning/getIntegrityWarningPositionLedger")
    BaseResponse getIntegrityWarningPositionLedger(@RequestBody FunctionalIntegrityWarningRequest request);
    @PostMapping("/v1/outer/userRegister/registerUser")
    BaseResponse registerUser(@RequestBody OuterUserRegisterDTO request);

    @GetMapping("/v1/outer/userRegister/approve2CreateUser")
    BaseResponse approve2CreateUser(@RequestParam String id);

    @GetMapping("/v1/projectCpLibraryRisk/getProjectCpLibraryRiskByCplId")
    BaseResponse getProjectCpLibraryRiskByCplId(@RequestParam String cplId);

    @PostMapping("/v1/qsPlanTask/generateSubFileRecordsAndNotifyAssignees")
    BaseResponse generateSubFileRecordsAndNotifyAssignees(@RequestBody QsPlanTaskDto request, @RequestHeader String token);

    @GetMapping("/v1/qsPlanTask/delegateTodoAndNotifyCcUsers")
    BaseResponse delegateTodoAndNotifyCcUsers(@RequestParam String id, @RequestHeader String token);

    @GetMapping("/v1/qsPlanTask/syncQsRecordState")
    BaseResponse syncQsRecordState(@RequestParam String id);

    @GetMapping("/v1/integratedWork/getLastWeekOrders")
    BaseResponse getLastWeekOrders(
            @RequestParam("conConstructionId") String conConstructionId,
            @RequestParam("conSupervisionId") String conSupervisionId,
            @RequestParam("weeklyStartDate") String weeklyStartDate,
            @RequestParam(value = "id", required = false) String id
    );

    @PostMapping("/v1/kb/getWorkbenchDetailStatisticsList")
    BaseResponse getWorkbenchDetailStatisticsList(@RequestBody QsRecordRequest request, @RequestHeader String token);

    @PostMapping("/v1/kb/getContractStatisticsOverviewList")
    BaseResponse getContractStatisticsOverviewList(@RequestBody ConContractRequest request, @RequestHeader String token);

    @PostMapping("/v1/kb/getConEcStatisticsOverviewList")
    BaseResponse getConEcStatisticsOverviewList(@RequestBody ConEcRequest request, @RequestHeader String token);

    @PostMapping("/v1/kb/getConPayStatisticsOverviewList")
    BaseResponse getConPayStatisticsOverviewList(@RequestBody ConPayRequest request, @RequestHeader String token);

    @PostMapping("/v1/kb/getInvPlanYearStatisticsOverviewList")
    BaseResponse getInvPlanYearStatisticsOverviewList(@RequestBody InvPlanYearRequest request, @RequestHeader String token);

    @PostMapping("/v1/kb/getInvMonthlyScheduleStatisticsOverviewList")
    BaseResponse getInvMonthlyScheduleStatisticsOverviewList(@RequestBody InvPlanYearRequest request, @RequestHeader String token);

    @PostMapping("/v1/kb/getInvCompleteMonthStatisticsOverviewList")
    BaseResponse getInvCompleteMonthStatisticsOverviewList(@RequestBody InvCompleteMonthRequest request, @RequestHeader String token);

    @PostMapping("/v1/monthlyInspectionReport/getMonthlyInspectionReportList")
    BaseResponse getMonthlyInspectionReportList(@RequestBody MonthlyInspectionReportDTO request);

    @PostMapping("/v1/monthlyInspectionReport/getMonthlyInspectionReportListClassInfo")
    BaseResponse getMonthlyInspectionReportListClassInfo(@RequestBody MonthlyInspectionReportDTO request);

    @PostMapping("/v1/qsPlanTask/getQsPlanTaskListSH")
    BaseResponse getQsPlanTaskListSH(@RequestBody QsPlanTaskDto request, @RequestHeader String token);

    /**********************************************项目举报***************************************************/

    @PostMapping("/v1/ProjectReportViolations/createOrUpdateProjectReportViolations")
    BaseResponse createOrUpdateProjectReportViolations(@RequestBody ProjectReportViolationsDTO request);
    @PostMapping("/v1/ProjectReportViolations/createOrUpdateProjectReportViolationsBySms")
    BaseResponse createOrUpdateProjectReportViolationsBySms(@RequestBody ProjectReportViolationsDTO request);

    @GetMapping("/v1/ProjectReportViolations/getProjectReportViolationsById")
    BaseResponse getProjectReportViolationsById(@RequestParam("id") String id);
    /**
     * 分页查询
     */
    @PostMapping("/v1/ProjectReportViolations/getProjectReportViolationsList")
    BaseResponse getProjectReportViolationsList(@RequestBody ProjectReportViolationsDTO request);

    @GetMapping("/v1/designConstruction/updateDesignConstructionIssued")
    BaseResponse updateDesignConstructionIssued(@RequestParam String dcId, @RequestParam(required = false) String issued);

    /**
     * 更新建筑信息分项
     */
    @GetMapping("/v1/handover/updateBuildingInfoSub")
    BaseResponse updateBuildingInfoSub(@RequestParam String chId);

    /**
     *  检查保险是否过期并跟新状态
     * @return
     */
    @GetMapping("/v1/handover/checkInsuranceStatus")
    BaseResponse checkInsuranceStatus();

    @GetMapping("/v1/conStartApply/syncDataToContract")
    BaseResponse syncDataToContract(@RequestParam String saId);

    @PostMapping("/SmartSite/v1/proPanorama/getProjectPanoramaOverviewFile")
    BaseResponse getProjectPanoramaOverviewFile(@RequestBody ProjectPanoramaOverviewModel request);

    @PostMapping("/v1/docDistributionUnit/batchCreateOrUpdateDocDistributionUnit")
    BaseResponse batchCreateOrUpdateDocDistributionUnit(@RequestBody List<DocDistributionUnitDTO> requests);

    @PostMapping("/v1/qsType/getQsTypeTree")
    BaseResponse getQsTypeTree(@RequestBody QsTypeModel request);

    /***********************************************安全责任体系库***************************************************/
    @PostMapping("/v1/safeResponsibilitySystem/createOrUpdateSafeResponsibilitySystem")
    BaseResponse createOrUpdateSafeResponsibilitySystem(@RequestBody SafeResponsibilitySystemDTO request,
                                                         @RequestHeader String token);

    @PostMapping("/v1/safeResponsibilitySystem/getSafeResponsibilitySystemList")
    BaseResponse getSafeResponsibilitySystemList(@RequestBody SafeResponsibilitySystemDTO request);

    @PostMapping("/v1/safeResponsibilitySystem/getSafeResponsibilitySystemTree")
    BaseResponse getSafeResponsibilitySystemTree(@RequestBody SafeResponsibilitySystemDTO request);

    @PostMapping("/v1/safeResponsibilitySystem/deleteSafeResponsibilitySystem")
    BaseResponse deleteSafeResponsibilitySystem(@RequestBody SafeResponsibilitySystemDTO request,
                                                 @RequestHeader String token);

    @PostMapping("/v1/safeResponsibilitySystem/copySafeResponsibilitySystem")
    BaseResponse copySafeResponsibilitySystem(@RequestBody SafeResponsibilityCopyDTO request,
                                               @RequestHeader String token);

    @PostMapping("/v1/safeResponsibilityLevel/createOrUpdateSafeResponsibilityLevel")
    BaseResponse createOrUpdateSafeResponsibilityLevel(@RequestBody SafeResponsibilityLevelDTO request,
                                                        @RequestHeader String token);

    @PostMapping("/v1/safeResponsibilityLevel/getSafeResponsibilityLevelTree")
    BaseResponse getSafeResponsibilityLevelTree(@RequestBody SafeResponsibilityLevelDTO request);

    @PostMapping("/v1/safeResponsibilityLevel/deleteSafeResponsibilityLevel")
    BaseResponse deleteSafeResponsibilityLevel(@RequestBody SafeResponsibilityLevelDTO request,
                                                @RequestHeader String token);

    @PostMapping("/v1/safeResponsibilityPost/createOrUpdateSafeResponsibilityPost")
    BaseResponse createOrUpdateSafeResponsibilityPost(@RequestBody SafeResponsibilityPostDTO request,
                                                       @RequestHeader String token);

    @PostMapping("/v1/safeResponsibilityPost/getSafeResponsibilityPostList")
    BaseResponse getSafeResponsibilityPostList(@RequestBody SafeResponsibilityPostDTO request);

    @PostMapping("/v1/safeResponsibilityPost/deleteSafeResponsibilityPost")
    BaseResponse deleteSafeResponsibilityPost(@RequestBody SafeResponsibilityPostDTO request,
                                               @RequestHeader String token);

    @PostMapping("/v1/safeResponsibilityPostDuty/createOrUpdateSafeResponsibilityPostDuty")
    BaseResponse createOrUpdateSafeResponsibilityPostDuty(@RequestBody SafeResponsibilityPostDutyDTO request,
                                                           @RequestHeader String token);

    @PostMapping("/v1/safeResponsibilityPostDuty/getSafeResponsibilityPostDutyList")
    BaseResponse getSafeResponsibilityPostDutyList(@RequestBody SafeResponsibilityPostDutyDTO request);

    @PostMapping("/v1/safeResponsibilityPostDuty/deleteSafeResponsibilityPostDuty")
    BaseResponse deleteSafeResponsibilityPostDuty(@RequestBody SafeResponsibilityPostDutyDTO request,
                                                   @RequestHeader String token);

    @PostMapping("/v1/safeResponsibilityChangeLog/getSafeResponsibilityChangeLogList")
    BaseResponse getSafeResponsibilityChangeLogList(@RequestBody SafeResponsibilityChangeLogDTO request);

    @PostMapping("/v1/safeResponsibilityPostPerson/createOrUpdatePostPerson")
    BaseResponse createOrUpdatePostPerson(@RequestBody SafeResponsibilityPostPersonDTO request);

    @PostMapping("/v1/safeResponsibilityPostPerson/getPostPersonList")
    BaseResponse getPostPersonList(@RequestBody SafeResponsibilityPostPersonDTO request);

    @GetMapping("/v1/safeResponsibilityPostPerson/getPostPersonById")
    BaseResponse getPostPersonById(@RequestParam String id);

    @GetMapping("/v1/safeResponsibilityPostPerson/deletePostPersonById")
    BaseResponse deletePostPersonById(@RequestParam String id);

    @GetMapping("/v1/safeResponsibilityPostPerson/syncReportedPersonnel")
    BaseResponse syncReportedPersonnel(@RequestParam String rpId);

    @GetMapping("/v1/signSafetyRespon/createSignSafetyResponByReported")
    BaseResponse createSignSafetyResponByReported(@RequestParam String rpId);

    @PostMapping("/v1/signSafetyRespon/getSignSafetyResponList")
    BaseResponse getSignSafetyResponList(@RequestBody SignSafetyResponDTO request);

    @GetMapping("/v1/signSafetyRespon/getSignSafetyResponById")
    BaseResponse getSignSafetyResponById(@RequestParam String id);
}
