package com.jpmsoft.projectConsumer.feign;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Contract.ConEstimateMaintenanceRequest;
import com.jpmsoft.common.model.request.NewOA.PreProjectRegister_NewOaRequest;
import com.jpmsoft.common.model.request.project.*;
import com.jpmsoft.common.model.requestAndResponse.Contract.ConEcAndVisFeeModel;
import com.jpmsoft.common.model.requestAndResponse.Project.*;
import com.jpmsoft.projectConsumer.pojo.dto.project.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 前期管理
 */
@FeignClient(name = "project-producer", contextId = "project")
public interface PreFeign {

    /*********************项目注册:NewOA*********************/

    @PostMapping("/v1/NewOA/CreateOrUpdatePreProjectRegisterNewOA")
    BaseResponse CreateOrUpdatePreProjectRegister_NewOa(@RequestBody PreProjectRegister_NewOaRequest request);

    @PostMapping("/v1/NewOA/deletePreProjectRegisterNewOa")
    BaseResponse deletePreProjectRegister_NewOa(@RequestBody PreProjectRegister_NewOaRequest request);

    @PostMapping("/v1/NewOA/approvalPreProjectRegisterNewOa")
    BaseResponse approvalPreProjectRegister_NewOa(@RequestBody PreProjectRegister_NewOaRequest request);

    @PostMapping("/v1/NewOA/getPreProjectRegisterListNewOa")
    BaseResponse getPreProjectRegister_NewOaList(@RequestBody PreProjectRegister_NewOaRequest request);

    @PostMapping("/v1/NewOA/getPreProjectRegisterStatisticsListNewOa")
    BaseResponse getPreProjectRegister_NewOaStatisticsList(@RequestBody PreProjectRegister_NewOaRequest request);


    @PostMapping("/v1/NewOA/getPreProjectRegisterByDepCompanyNewOa")
    BaseResponse getPreProjectRegister_NewOaByDepCompany(@RequestBody PreProjectRegister_NewOaRequest request, @RequestHeader String token);

    @PostMapping("/v1/NewOA/getPreProjectRegisterByIdNewOa")
    BaseResponse getPreProjectRegister_NewOaById(@RequestBody PreProjectRegister_NewOaRequest request);

    @PostMapping("/v1/NewOA/getPreProjectRegisterWYByIdNewOa")
    BaseResponse getPreProjectRegister_NewOaWYById(@RequestBody PreProjectRegister_NewOaRequest request);


    /*********************项目注册*********************/

    @PostMapping("/v1/projectRegister/CreateOrUpdatePreProjectRegister")
    BaseResponse CreateOrUpdatePreProjectRegister(@RequestBody PreProjectRegisterRequest request);

    @PostMapping("/v1/projectRegister/deletePreProjectRegister")
    BaseResponse deletePreProjectRegister(@RequestBody PreProjectRegisterRequest request);

    @PostMapping("/v1/projectRegister/approvalPreProjectRegister")
    BaseResponse approvalPreProjectRegister(@RequestBody PreProjectRegisterRequest request);

    @PostMapping("/v1/projectRegister/getPreProjectRegisterList")
    BaseResponse getPreProjectRegisterList(@RequestBody PreProjectRegisterRequest request);

    @PostMapping("/v1/projectRegister/getPreProjectRegisterStatisticsList")
    BaseResponse getPreProjectRegisterStatisticsList(@RequestBody PreProjectRegisterRequest request);


    @PostMapping("/v1/projectRegister/getPreProjectRegisterByDepCompany")
    BaseResponse getPreProjectRegisterByDepCompany(@RequestBody PreProjectRegisterRequest request, @RequestHeader String token);

    @PostMapping("/v1/projectRegister/getPreProjectRegisterById")
    BaseResponse getPreProjectRegisterById(@RequestBody PreProjectRegisterRequest request);

    @PostMapping("/v1/projectRegister/getPreProjectRegisterWYById")
    BaseResponse getPreProjectRegisterWYById(@RequestBody PreProjectRegisterRequest request);


    /*********************项目维护*********************/
    @GetMapping("/v1/project/getProjectDivision")
    BaseResponse getProjectDivision(@RequestParam(required = false) String province,@RequestParam(required = false) String city);

    @GetMapping("/v1/project/existProjectName")
    BaseResponse existProjectName(@RequestParam String pName,@RequestParam(required = false) String pId);

    @PostMapping("/v1/project/getPreProjectAmtSum")
    BaseResponse getPreProjectAmtSum(@RequestBody PreProjectRequest request, @RequestHeader String token);

    @PostMapping("/v1/project/getProjectList")
    BaseResponse getProjectList(@RequestBody PreProjectRequest request, @RequestHeader String token);

    @PostMapping("/v1/project/getProjectProgress")
    BaseResponse getProjectProgress(@RequestBody PreProjectRequest request, @RequestHeader String token);

    @PostMapping("/v1/project/getProjectStatisticsList")
    BaseResponse getProjectStatisticsList(@RequestBody PreProjectRequest request, @RequestHeader String token);

    @PostMapping("/v1/project/getProjectStreamlineList")
    BaseResponse getProjectStreamlineList(@RequestBody PreProjectRequest request, @RequestHeader String token);

    @PostMapping("/v1/project/getProjectListForInvPlan")
    BaseResponse getProjectListForInvPlan(@RequestBody PreProjectRequest request, @RequestHeader String token);

    @PostMapping("/v1/project/getDepartmentListForInvPlan")
    BaseResponse getDepartmentListForInvPlan(@RequestBody PreProjectRequest request, @RequestHeader String token);

    @PostMapping("/v1/project/getProjectCjBiddingPlanList")
    BaseResponse getProjectCjBiddingPlanList(@RequestBody PreProjectRequest request, @RequestHeader String token);

    @PostMapping("/v1/project/getProjectByProOrganization")
    BaseResponse getProjectByProOrganization(@RequestBody PreProjectRequest request, @RequestHeader String token);


    @PostMapping("/v1/project/getProjectListNoAuth")
    BaseResponse getProjectListNoAuth(@RequestBody PreProjectRequest request);

    @PostMapping("/v1/project/getProjectListBackstage")
    BaseResponse getProjectListBackstage(@RequestBody PreProjectRequest request, @RequestHeader String token);

    @PostMapping("/v1/project/createOrUpdatePreProject")
    BaseResponse createOrUpdatePreProject(@RequestBody PreProjectRequest request, @RequestHeader String token);

    @PostMapping("/v1/project/createOrUpdatePreProjectCopy")
    BaseResponse createOrUpdatePreProjectCopy(@RequestBody PreProjectRequest request, @RequestHeader String token);

    @PostMapping("/v1/project/saveForGZ")
    BaseResponse saveForGZ(@RequestBody PreProjectRequestDto request);

    @PostMapping("/v1/project/BjCreateOrUpdatePreProject")
    BaseResponse BjCreateOrUpdatePreProject(@RequestBody PreProjectRequest request, @RequestHeader String token);

    @PostMapping("/v1/project/getProjectSelect")
    BaseResponse getProjectSelect(@RequestBody PreProjectRequest request, @RequestHeader String token);

    @PostMapping("/v1/project/getAllPreProject")
    BaseResponse getAllPreProject(@RequestBody PreProjectRequest request, @RequestHeader String token);

    @PostMapping("/v1/project/getAllOutOrgPreProject")
    BaseResponse getAllOutOrgPreProject(@RequestBody PreProjectRequest request, @RequestHeader String token);

    @PostMapping("/v1/project/getProjectByPid")
    BaseResponse getProjectByPid(@RequestBody PreProjectRequest request);


    @PostMapping("/v1/project/getProjectByPidCopy")
    BaseResponse getProjectByPidCopy(@RequestBody PreProjectRequest request);




    @GetMapping("/v1/project/deleteProjectByPid")
    BaseResponse deleteProjectByPid(@RequestParam String pId);

    @GetMapping("/v1/project/statisticsProject")
    BaseResponse statisticsProject();

    @PostMapping("/v1/project/getProjectWYList")
    BaseResponse getProjectWYList(@RequestBody PreProjectRequest request, @RequestHeader String token);

    @PostMapping("/v1/project/getProjectWYByPid")
    BaseResponse getProjectWYByPid(@RequestBody PreProjectRequest request);

    @GetMapping("/v1/project/updateProjectCodeById")
    BaseResponse updateProjectCodeById(@RequestParam String pId, @RequestParam String pCode, @RequestHeader String token);


    @GetMapping("/v1/project/getProInvInfoByPid")
    BaseResponse getProInvInfoByPid(@RequestParam String pId);

    @PostMapping("/v1/project/getProMonthlyReport")
    BaseResponse getProMonthlyReport(@RequestBody ProMonthlyReportDto request);

    @PostMapping("/v1/project/getProjectInvStatistics")
    BaseResponse getProjectInvStatistics(@RequestBody ProMonthlyReportDto request);

    @GetMapping("/v1/project/getProApprovalInfo")
    BaseResponse getProApprovalInfo(@RequestParam String pId);

    @GetMapping("/v1/project/getUserAllPid")
    BaseResponse getUserAllPid(@RequestHeader String token);

    @GetMapping("/v1/project/getUserAllPidByUid")
    BaseResponse getUserAllPidByUid(@RequestParam String uId);

    @GetMapping("/v1/project/getAllProjectByOrganization")
    BaseResponse getAllProjectByOrganization(@RequestParam String uid);

    @PostMapping("/v1/project/getProjectFundStatistics")
    BaseResponse getProjectFundStatistics(@RequestBody ProjectFundStatisticsDTO request, @RequestHeader String token);

    @PostMapping("/v1/project/getProjectFundStatisticsTree")
    BaseResponse getProjectFundStatisticsTree(@RequestBody ProjectFundStatisticsDTO request, @RequestHeader String token);

    @GetMapping("/v1/project/getAllAuthProjectId")
    BaseResponse getAllAuthProjectId(@RequestParam String uid);

    @GetMapping("/v1/project/getApproveBudgetAndRate")
    BaseResponse getApproveBudgetAndRate(@RequestParam String pId);

    @PostMapping("/v1/project/getPreProjectDtlByIds")
    BaseResponse getPreProjectDtlByIds(@RequestBody List<String> pIds);

    @PostMapping("/v1/project/getProjectCompetentDepartmentByPid")
    BaseResponse getProjectCompetentDepartmentByPid(@RequestBody PreProjectRequest request);

    @GetMapping("/v1/project/updateProjectAuthType")
    BaseResponse updateProjectAuthType(@RequestParam String id, @RequestParam Integer type);

    /*********************项目更名*********************/

    @PostMapping("/v1/proChangeName/CreateOrUpdateProChangeName")
    BaseResponse CreateOrUpdateProChangeName(@RequestBody PreProjectChangeNameRequest request);

    @PostMapping("/v1/proChangeName/getPreProjectChangeNameByPage")
    BaseResponse getPreProjectChangeNameByPage(@RequestBody PreProjectChangeNameRequest request);

    @GetMapping("/v1/proChangeName/getPreProjectChangeNameById")
    BaseResponse getPreProjectChangeNameById(@RequestParam String pcnId);

    @GetMapping("/v1/proChangeName/approve2RefreshProjectName")
    BaseResponse approve2RefreshProjectName(@RequestParam String pcnId);

    @PostMapping("/v1/proChangeName/deleteProChangeNameById")
    BaseResponse deleteProChangeNameById(@RequestBody PreProjectChangeNameRequest request);

    @PostMapping("/v1/proChangeName/updateProChangeNameFlowState")
    BaseResponse updateProChangeNameFlowState(@RequestBody PreProjectChangeNameRequest request);

    @GetMapping("/v1/proChangeName/getProjectNameUsedBeforeListById")
    BaseResponse getProjectNameUsedBeforeListById(@RequestParam String pId);

    /**********************************************项目建议书(项目信息源)***************************************************/
    @PostMapping("/v1/proProposal/createOrUpdatePreProProposal")
    BaseResponse createOrUpdatePreProProposal(@RequestBody PreProProposalModel request);

    @PostMapping("/v1/proProposal/getPreProProposalList")
    BaseResponse getPreProProposalList(@RequestBody PreProProposalModel request, @RequestHeader String token);

    @PostMapping("/v1/proProposal/getPreProProposalStatisticsList")
    BaseResponse getPreProProposalStatisticsList(@RequestBody PreProProposalModel request, @RequestHeader String token);

    @GetMapping("/v1/proProposal/getPreProProposalById")
    BaseResponse getPreProProposalById(@RequestParam String ppId);

    @GetMapping("/v1/proProposal/approvePreProProposal")
    BaseResponse approvePreProProposal(@RequestParam String ppId);

    /**********************************************项目立项***************************************************/
    @PostMapping("/v1/proApproval/createOrUpdatePreProApproval")
    BaseResponse createOrUpdatePreProApproval(@RequestBody PreProApprovalModel request);

    @PostMapping("/v1/proApproval/getPreProApprovalList")
    BaseResponse getPreProApprovalList(@RequestBody PreProApprovalModel request, @RequestHeader String token);

    @PostMapping("/v1/proApproval/getPreProApprovalStatisticsList")
    BaseResponse getPreProApprovalStatisticsList(@RequestBody PreProApprovalModel request, @RequestHeader String token);

    @PostMapping("/v1/proApproval/getPreProApprovalDtlByPIds")
    BaseResponse getPreProApprovalDtlByPIds(@RequestBody List<String> pIds);

    @GetMapping("/v1/proApproval/getPreProApprovalById")
    BaseResponse getPreProApprovalById(@RequestParam String paId);

    @GetMapping("/v1/proApproval/getPreProApprovalByPid")
    BaseResponse getPreProApprovalByPid(@RequestParam String pId);

    @GetMapping("/v1/proApproval/getPreProApprovalWYById")
    BaseResponse getPreProApprovalWYById(@RequestParam String paId);

    /**********************************************指挥部架构***************************************************/
    @PostMapping("/v1/commandPost/createOrUpdatePreCommandPost")
    BaseResponse createOrUpdatePreCommandPost(@RequestBody PreCommandPostModel request);

    @PostMapping("/v1/commandPost/getPreCommandPostList")
    BaseResponse getPreCommandPostList(@RequestBody PreCommandPostModel request, @RequestHeader String token);

    @PostMapping("/v1/commandPost/getPreCommandPostStatisticsList")
    BaseResponse getPreCommandPostStatisticsList(@RequestBody PreCommandPostModel request, @RequestHeader String token);

    @GetMapping("/v1/commandPost/getPreCommandPostById")
    BaseResponse getPreCommandPostById(@RequestParam String comId);

    /**********************************************开工许可证***************************************************/
    @PostMapping("/v1/constructionPermit/createOrUpdatePreConstructionPermit")
    BaseResponse createOrUpdatePreConstructionPermit(@RequestBody PreConstructionPermitModel request);

    @PostMapping("/v1/constructionPermit/getPreConstructionPermitList")
    BaseResponse getPreConstructionPermitList(@RequestBody PreConstructionPermitModel request, @RequestHeader String token);

    @PostMapping("/v1/constructionPermit/getPreConstructionPermitStatisticsList")
    BaseResponse getPreConstructionPermitStatisticsList(@RequestBody PreConstructionPermitModel request, @RequestHeader String token);

    @GetMapping("/v1/constructionPermit/getPreConstructionPermitById")
    BaseResponse getPreConstructionPermitById(@RequestParam String cpId);

    @GetMapping("/v1/constructionPermit/approval2RefershProjectInfo")
    BaseResponse approval2RefershProjectInfo(@RequestParam String cpId);

    @GetMapping("/v1/constructionPermit/getPreConstructionPermitUnitListByPid")
    BaseResponse getPreConstructionPermitUnitListByPid(@RequestParam String pId);

    /**********************************************项目分派单***************************************************/
    @PostMapping("/v1/proAssignment/createOrUpdateProAssignment")
    BaseResponse createOrUpdateProAssignment(@RequestBody ProAssignmentModel request);

    @PostMapping("/v1/proAssignment/getProAssignmentList")
    BaseResponse getProAssignmentList(@RequestBody ProAssignmentModel request, @RequestHeader String token);

    @GetMapping("/v1/proAssignment/getProAssignmentById")
    BaseResponse getProAssignmentById(@RequestParam String paId);

    @GetMapping("/v1/proAssignment/syncPCompetentDepartment")
    BaseResponse syncPCompetentDepartment(@RequestParam String paId);

    /**********************************************项目资金来源***************************************************/
    @PostMapping("/v1/proSourcesFunds/getSourcesFundsSumByPids")
    BaseResponse getSourcesFundsSumByPids(@RequestBody List<String> pIds);

    @GetMapping("/v1/proSourcesFunds//getProSourcesFundsByPid")
    BaseResponse getProSourcesFundsByPid(@RequestParam String pId);

    /**********************************************项目建设计划***************************************************/
    @PostMapping("/v1/proConstPlan/createOrUpdateProConstructionPlan")
    BaseResponse createOrUpdateProConstructionPlan(@RequestBody ProConstructionPlanModel request);

    @PostMapping("/v1/proConstPlan/getProConstructionPlanList")
    BaseResponse getProConstructionPlanList(@RequestBody ProConstructionPlanModel request);

    @GetMapping("/v1/proConstPlan/getProConstructionPlanById")
    BaseResponse getProConstructionPlanById(@RequestParam String pcpId);

    /**********************************************项目建设计划清单***************************************************/

    @PostMapping("/v1/proConstPlanList/createOrUpdateProConstructionPlanList")
    BaseResponse createOrUpdateProConstructionPlanList(@RequestBody ProConstructionPlanListModel request);

    @PostMapping("/v1/proConstPlanList/getProConstructionPlanList")
    BaseResponse getProConstructionPlanList(@RequestBody ProConstructionPlanListModel request);

    @GetMapping("/v1/proConstPlanList/getProConstructionPlanListById")
    BaseResponse getProConstructionPlanListById(@RequestParam String cplId);

    @PostMapping("/v1/proConstPlanList/getProConstructionPlanSelectList")
    BaseResponse getProConstructionPlanSelectList(@RequestBody ProConstructionPlanListModel request);

    @GetMapping("/v1/proConstPlanList/getProConstructionPlanListByPcpId")
    BaseResponse getProConstructionPlanListByPcpId(@RequestParam String pcpId);

    /**********************************************项目信息变更***************************************************/
    @PostMapping("/v1/projectChange/createOrUpdatePreProjectChange")
    BaseResponse createOrUpdatePreProjectChange(@RequestBody PreProjectChangeModel request, @RequestHeader String token);

    @PostMapping("/v1/projectChange/getPreProjectChangeList")
    BaseResponse getPreProjectChangeList(@RequestBody PreProjectChangeModel request, @RequestHeader String token);

    @GetMapping("/v1/projectChange/getPreProjectChangeById")
    BaseResponse getPreProjectChangeById(@RequestParam String pcId);

    @GetMapping("/v1/projectChange/approvalSyncProInfo")
    BaseResponse approvalSyncProInfo(@RequestParam String pcId, @RequestHeader String token);

    /*********************项目经费号*********************/

    @GetMapping("/v1/proFundNo/getProFundNoByPid")
    BaseResponse getProFundNoByPid(@RequestParam String pId);

    /*********************形象进度*********************/

    @PostMapping("/v1/imageProgress/createOrUpdateProImageProgress")
    BaseResponse createOrUpdateProImageProgress(@RequestBody ProImageProgressModel request);

    @PostMapping("/v1/imageProgress/getProImageProgressList")
    BaseResponse getProImageProgressList(@RequestBody ProImageProgressModel request, @RequestHeader String token);

    @GetMapping("/v1/imageProgress/getProImageProgressById")
    BaseResponse getProImageProgressById(@RequestParam String id, @RequestHeader String token);

    @GetMapping("/v1/imageProgress/deleteProImageProgressById")
    BaseResponse deleteProImageProgressById(@RequestParam String id);

    @PostMapping("/v1/imageProgressLike/createProImageProgressLike")
    BaseResponse createProImageProgressLike(@RequestBody ProImageProgressLikeModel request);

    @GetMapping("/v1/imageProgressLike/deleteProImageProgressLikeById")
    BaseResponse deleteProImageProgressLikeById(@RequestParam String id);

    @PostMapping("/v1/imageProgressReply/createProImageProgressReply")
    BaseResponse createProImageProgressReply(@RequestBody ProImageProgressReplyModel request);

    @GetMapping("/v1/imageProgressReply/getProImageProgressReplyListByIpId")
    BaseResponse getProImageProgressReplyListByIpId(@RequestParam String ipId);

    @GetMapping("/v1/imageProgressReply/getProImageProgressReplyById")
    BaseResponse getProImageProgressReplyById(@RequestParam String id);

    @GetMapping("/v1/imageProgressReply/deleteProImageProgressReplyById")
    BaseResponse deleteProImageProgressReplyById(@RequestParam String id);

    @PostMapping("/v1/imageProgressComment/createProImageProgressComment")
    BaseResponse createProImageProgressComment(@RequestBody ProImageProgressCommentModel request);

    @GetMapping("/v1/imageProgressComment/getProImageProgressCommentListByIpId")
    BaseResponse getProImageProgressCommentListByIpId(@RequestParam String ipId);

    @GetMapping("/v1/imageProgressComment/getProImageProgressCommentById")
    BaseResponse getProImageProgressCommentById(@RequestParam String id);

    @GetMapping("/v1/imageProgressComment/deleteProImageProgressCommentById")
    BaseResponse deleteProImageProgressCommentById(@RequestParam String id);

    /*********************年度计划申报管理*********************/
    @PostMapping("/v1/proYearConsPlan/createOrUpdateProYearConsPlan")
    BaseResponse createOrUpdateProYearConsPlan(@RequestBody ProYearConsPlanDto request);

    @PostMapping("/v1/proYearConsPlan/getProYearConsPlanList")
    BaseResponse getProYearConsPlanList(@RequestBody ProYearConsPlanDto request);

    @GetMapping("/v1/proYearConsPlan/getProYearConsPlanById")
    BaseResponse getProYearConsPlanById(@RequestParam String id);

    /*********************项目设备坐标*********************/
    @PostMapping("/v1/projectDeviceCoordinate/createOrUpdate")
    BaseResponse projectDeviceCoordinateCreateOrUpdate(@RequestBody PreProjectDeviceCoordinateCreateOrUpdateRequest request);

    @GetMapping("/v1/projectDeviceCoordinate/list")
    BaseResponse projectDeviceCoordinateList(@RequestParam Integer pageNum, @RequestParam Integer pageSize);

    /**
     * 根据项目id筛选项目规划点
     *
     * @param projectId
     * @return
     */
    @GetMapping("/v1/projectDeviceCoordinate/listByProjectId")
    BaseResponse listByProjectId(@RequestParam String projectId);

    @GetMapping("/v1/projectDeviceCoordinate/getList")
    BaseResponse getProjectDeviceCoordinateList(@RequestBody PreProjectDeviceCoordinateRequest request);

    @PostMapping("/v1/projectDeviceCoordinate/deleteOne/{uuid}")
    BaseResponse projectDeviceCoordinateDeleteOne(@PathVariable("uuid") String uuid);


    /*********************合同概算维护*********************/
    @PostMapping("/v1/conEstimateMaintenance/createOrUpdateConEstimateMaintenance")
    BaseResponse createOrUpdateConEstimateMaintenance(@RequestBody ConEstimateMaintenanceRequest request);

    @PostMapping("/v1/conEstimateMaintenance/getConEstimateMaintenanceList")
    BaseResponse getConEstimateMaintenanceList(@RequestBody ConEstimateMaintenanceRequest request, @RequestHeader String token);

    @GetMapping("/v1/conEstimateMaintenance/getConEstimateMaintenanceById")
    BaseResponse getConEstimateMaintenanceById(@RequestParam String cemId);

    @GetMapping("/v1/conEstimateMaintenance/getConEstimateMaintenanceByConIdAndSecId")
    BaseResponse getConEstimateMaintenanceByConIdAndSecId(@RequestParam String conId, @RequestParam String secId);

    @GetMapping("/v1/conEstimateMaintenance/getConEstimateMaintenanceByIds")
    BaseResponse getConEstimateMaintenanceByIds(@RequestParam String pId, @RequestParam String type);

    @PostMapping("/v1/conEstimateMaintenance/updateConEstimate")
    BaseResponse updateConEstimate(@RequestBody ConEstimateMaintenanceRequest request);

    @GetMapping("/v1/conEstimateMaintenance/getConEstimateIsOver")
    BaseResponse getConEstimateIsOver(@RequestParam String pId, @RequestParam String secId);

    @GetMapping("/v1/conEstimateMaintenance/getInvestmentTableByPId")
    BaseResponse getInvestmentTableByPId(@RequestParam String pId);

    @GetMapping("/v1/conEstimateMaintenance/getInvestmentTableSecondByPId")
    BaseResponse getInvestmentTableSecondByPId(@RequestParam String pId);

    @GetMapping("/v1/project/getProjectClassInfo")
    BaseResponse getProjectClassInfo(@RequestBody PreProjectIDsRequest request, @RequestHeader String token);

    @PostMapping("/v1/project/getProjectTZList")
    BaseResponse getProjectTZList(@RequestBody PreProjectTZRequest request, @RequestHeader String token);

    @PostMapping("/v1/project/updateProjectInfo")
    BaseResponse updateProjectInfo(@RequestBody PreProjectRequest request, @RequestHeader String token);

    @PostMapping("/v1/project/projectApproved2ImpTempDepartment")
    BaseResponse projectApproved2ImpTempDepartment(@RequestParam String formPriKey);

    @PostMapping("/v1/project/approvalData")
    BaseResponse approvalData(@RequestBody ProjectRequest request, @RequestHeader String token);

    @PostMapping("/v1/preProjectOrgStruct/syncPreProjectOrgStruct")
    BaseResponse syncPreProjectOrgStruct(@RequestBody PreProjectOrgStructModel request, @RequestHeader String token);

    @PostMapping("/v1/preProjectOrgStruct/getPreProjectOrgStructList")
    BaseResponse getPreProjectOrgStructList(@RequestBody PreProjectOrgStructRequest request, @RequestHeader String token);

    @GetMapping("/v1/preProjectOrgStruct/getPreProjectOrgStructById")
    BaseResponse getPreProjectOrgStructById(@RequestParam String id);

    @PostMapping("/v1/preProjectOrgStruct/saveAndsynchronouSorganization")
    BaseResponse saveAndsynchronouSorganization(@RequestBody PreProjectOrgStructRequest request, @RequestHeader String token);

    @PostMapping("/v1/preProjectOrgStruct/changesynchronouSorganization")
    BaseResponse changesynchronouSorganization(@RequestBody PreProjectOrgStructRequest request, @RequestHeader String token);

    @PostMapping("/v1/preProjectOrgStruct/getchangeRecords")
    BaseResponse getchangeRecords(@RequestBody PreProjectOrgStructRequest request);

    @GetMapping("/v1/preProjectOrgStruct/getPreProjectOrgStructChangeById")
    BaseResponse getPreProjectOrgStructChangeById(@RequestParam String id);

    @GetMapping("/v1/preProjectOrgStruct/saveProjectOrganization")
    BaseResponse saveProjectOrganization(@RequestParam String id);

    @GetMapping("/v1/preProjectOrgStruct/queryInspectionContract")
    BaseResponse queryInspectionContract(@RequestParam String pId);

    @GetMapping("/v1/project/queryDockptPreList")
    BaseResponse queryDockptPreList(@RequestParam String cId, @RequestParam String pName, @RequestParam Integer page, @RequestParam Integer size);

    @GetMapping("/v1/project/getProjectCjGsGsFenJie")
    BaseResponse getProjectCjGsGsFenJie(@RequestParam String pid);


    /*********************合同概算维护*********************/
    @PostMapping("/v1/projectReserve/createOrUpdateProjectReserve")
    BaseResponse createOrUpdateProjectReserve(@RequestBody PreProjectReserveDTO request);

    @PostMapping("/v1/projectReserve/getProjectReserveList")
    BaseResponse getProjectReserveList(@RequestBody PreProjectReserveDTO request, @RequestHeader String token);

    @GetMapping("/v1/projectReserve/getProjectReserveById")
    BaseResponse getProjectReserveById(@RequestParam String id);

    @GetMapping("/v1/projectReserve/updateReserveStatus")
    BaseResponse updateReserveStatus(@RequestParam String id,@RequestParam String status,@RequestParam(required = false) String pId);
    @GetMapping("/v1/projectReserve/deleteProjectReserveById")
    BaseResponse deleteProjectReserveById(@RequestParam String id);
    @PostMapping("/v1/projectReserveChange/createOrUpdateProjectReserveChange")
    BaseResponse createOrUpdateProjectReserveChange(@RequestBody PreProjectReserveChangeDTO request);

    @PostMapping("/v1/projectReserveChange/getProjectReserveChangeList")
    BaseResponse getProjectReserveChangeList(@RequestBody PreProjectReserveChangeDTO request, @RequestHeader String token);

    @GetMapping("/v1/projectReserveChange/getProjectReserveChangeById")
    BaseResponse getProjectReserveChangeById(@RequestParam String id);

    @GetMapping("/v1/projectReserveChange/syncProjectReserve")
    BaseResponse syncProjectReserve(@RequestParam String id);
    @PostMapping("/v1/projectReserve/queryReserveListClassInfo")
    BaseResponse queryReserveListClassInfo(@RequestBody PreProjectReserveDTO request, @RequestHeader String token);
    @GetMapping("/v1/projectReserve/getProjectReserveDtlById")
    BaseResponse getProjectReserveDtlById(@RequestParam String id);

    @PostMapping("/v1/project/getProjecListNJCJ")
    BaseResponse getProjecListNJCJ(@RequestBody PreProjectTZRequest request, @RequestHeader String token);

    @PostMapping("/v1/project/getProjecListNJCJAuth")
    BaseResponse getProjecListNJCJAuth(@RequestBody PreProjectTZRequest request, @RequestHeader String token);
    @PostMapping("/v1/applicationSta/getPreprojectStaList")
    BaseResponse getPreprojectStaList(@RequestBody PreProjectApplicationStaRequest request,  @RequestHeader String token);

    @PostMapping("/v1/applicationSta/getApplicationStaList")
    BaseResponse getApplicationStaList(@RequestBody PreProjectApplicationStaRequest request, @RequestHeader String token);

    @PostMapping("/v1/applicationSta/getPreprojectStaByPId")
    BaseResponse getPreprojectStaByPId(@RequestBody PreProjectApplicationStaRequest request);

    @PostMapping("/v1/applicationSta/getApplicationStaByPId")
    BaseResponse getApplicationStaByPId(@RequestBody PreProjectApplicationStaRequest request);
    @PostMapping("/v1/projectAcceptanceSettlement/saveOrUpdateProjectAcceptanceSettlement")
    BaseResponse saveOrUpdateProjectAcceptanceSettlement(@RequestBody ProjectAcceptanceSettlementModel request);
    @PostMapping("/v1/projectAcceptanceSettlement/getProjectAcceptanceSettlementList")
    BaseResponse getProjectAcceptanceSettlementList(@RequestBody ProjectAcceptanceSettlementModel request,@RequestHeader String token);
    @GetMapping("/v1/projectAcceptanceSettlement/getProjectAcceptanceSettlementDetail")
    BaseResponse getProjectAcceptanceSettlementDetail(@RequestParam String id);
    @PostMapping("/v1/projectAcceptanceSettlement/approvalData")
    BaseResponse approvalProjectAcceptanceSettlementData(@RequestBody ProjectAcceptanceSettlementModel request);
    @PostMapping("/v1/projectAcceptanceSettlement/calculateProjectAcceptanceSettlement")
    BaseResponse calculateProjectAcceptanceSettlement(@RequestBody ProjectAcceptanceSettlementModel request);

    /*********************采购意向表*********************/

    @PostMapping("/v1/purchase/createOrUpdatePropurchase")
    BaseResponse createOrUpdatePropurchase(@RequestBody ProPurchaseDto request);

    @PostMapping("/v1/purchase/createPropurchase")
    BaseResponse createPropurchase(@RequestBody ProPurchaseDto request);

    @PostMapping("/v1/purchase/updatePropurchase")
    BaseResponse updatePropurchase(@RequestBody ProPurchaseDto request);

    @GetMapping("/v1/purchase/getPropurchaseById")
    BaseResponse getPropurchaseById(@RequestParam String id);

    @PostMapping("/v1/purchase/getPropurchaseList")
    BaseResponse getPropurchaseList(@RequestBody ProPurchaseDto request);
    @PostMapping("/v1/projectAcceptanceSettlement/calculateProjectAcceptanceSettlementScore")
    BaseResponse calculateProjectAcceptanceSettlementScore(@RequestBody ProjectAcceptanceSettlementModel request);
    @PostMapping(value = "/v1/projectAcceptanceSettlement/importProjectAcceptanceSettlement", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    BaseResponse importProjectAcceptanceSettlement(@RequestPart("files") MultipartFile files, @RequestHeader String token);

    @GetMapping("/v1/projectReserve/syncProjectReserveToProject")
    BaseResponse syncProjectReserveToProject(@RequestParam("id") String id, @RequestHeader String token);

    @PostMapping("/v1/project/getProjecListGDGY")
    BaseResponse getProjecListGDGY(@RequestBody PreProjectRequest request);

    @GetMapping("/v1/project/getPIdByPprId")
    BaseResponse getPIdByPprId(@RequestParam("pprId") String pprId);

    @PostMapping("/v1/project/getProjectContractChangeList")
    BaseResponse getProjectContractChangeList(@RequestBody PreProjectRequest request, @RequestHeader String token);

    @PostMapping("/v1/project/getProjectContractChangeListBypId")
    BaseResponse getProjectContractChangeListBypId(@RequestBody ConEcAndVisFeeModel request, @RequestHeader String token);

    @PostMapping("/v1/project/getProjectHHTZList")
    BaseResponse getProjectHHTZList(@RequestBody PreProjectRequest request, @RequestHeader String token);

    @GetMapping("/v1/projectReserve/changeReserveCodeToProjectCode")
    BaseResponse changeReserveCodeToProjectCode(@RequestParam("pId") String pId, @RequestHeader String token);

    @GetMapping("/v1/project/refreshProjectCode")
    BaseResponse refreshProjectCode(@RequestParam String pId, @RequestParam String oldPBuildModel);

    @GetMapping("/v1/projectReserve/refreshProjectReserveCode")
    BaseResponse refreshProjectReserveCode(@RequestParam("id") String id, @RequestParam("oldPprModel") String oldPprModel);

    /*********************项目中标明细表*********************/
    @GetMapping("/v1/proWnDetail/getProWnDetailById")
    BaseResponse getProWnDetailById(@RequestParam String pwId);

    @GetMapping("/v1/proWnDetail/getProWnDetailByPid")
    BaseResponse getProWnDetailByPid(@RequestParam String pwPid);
}
