package com.jpmsoft.projectConsumer.feign;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.ClassInfoAnnotation;
import com.jpmsoft.common.model.request.InvestmentControl.*;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.*;
import com.jpmsoft.common.model.response.InvestmentControl.GetCreateFieldDataRequest;
import com.jpmsoft.common.model.response.InvestmentControl.InvCompleteMonthResponse;
import com.jpmsoft.common.model.response.InvestmentControl.InvPlanYearResponse;
import com.jpmsoft.common.model.response.InvestmentControl.TransferStatusUpdateRequest;
import com.jpmsoft.projectConsumer.config.CommonResponse;
import com.jpmsoft.projectConsumer.config.PageResult;
import com.jpmsoft.projectConsumer.pojo.dto.investment.*;
import com.jpmsoft.projectConsumer.pojo.vo.contract.FinalAccountingApprovalChangeVo;
import com.jpmsoft.projectConsumer.pojo.vo.contract.FinalAccountingApprovalVo;
import com.jpmsoft.projectConsumer.pojo.vo.contract.FinalAccountingReportVo;
import com.jpmsoft.projectConsumer.pojo.vo.contract.SettlementAuditVo;
import com.jpmsoft.projectConsumer.request.investment.SettlementFirstAuditRequest;
import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * 投资控制
 */
@FeignClient(name = "project-producer", contextId = "investment")
public interface InvFeign {
    /***********************************************概算申报***************************************************/

    @PostMapping("/v1/invBudgetDeclare/createOrUpdateInvBudgetDeclare")
    BaseResponse createOrUpdateInvBudgetDeclare(@RequestBody InvBudgetDeclareRequest request);

    @PostMapping("/v1/invBudgetDeclare/getInvBudgetDeclareList")
    BaseResponse getInvBudgetDeclareList(@RequestBody InvBudgetDeclareRequest request, @RequestHeader String token);

    @GetMapping("/v1/invBudgetDeclare/getInvBudgetDeclareByBdId")
    BaseResponse getInvBudgetDeclareByBdId(@RequestParam String bdId);

    @GetMapping("/v1/invBudgetDeclare/deleteInvBudgetDeclareByBdId")
    BaseResponse deleteInvBudgetDeclareByBdId(@RequestParam String bdId);

    @PostMapping("/v1/invBudgetDeclare/getInvBudgetDeclareDtlByPIds")
    BaseResponse getInvBudgetDeclareDtlByPIds(@RequestBody List<String> pIds);


    @GetMapping("/v1/invBudgetDeclare/getInvBudgetDeclareByPId")
    BaseResponse getInvBudgetDeclareByPId(@RequestParam String pId);

    @GetMapping("/v1/invBudgetDeclare/getInvBudegetCompanyByPid")
    BaseResponse getInvBudegetCompanyByPid(@RequestParam String pId);

    @GetMapping("/v1/invBudgetDeclare/getProjectBudgetBalance")
    BaseResponse getProjectBudgetBalance(@RequestParam String pId);

    @GetMapping("/v1/invBudgetDeclare/getConBudgetRate")
    BaseResponse getConBudgetRate(@RequestParam String pId, @RequestParam BigDecimal conMoney);

    @GetMapping("/v1/invBudgetDeclare/getInvBudgetDeclareAndDtlByPid")
    BaseResponse getInvBudgetDeclareAndDtlByPid(@RequestParam String pId);

    @GetMapping("/v1/invBudgetDeclare/approve2PApproveBudget")
    BaseResponse approve2PApproveBudget(@RequestParam String bdId);

    @GetMapping("/v1/invBudgetDeclare/havingBudgetWithPid")
    BaseResponse havingBudgetWithPid(@RequestParam String pId,@RequestParam(value = "type", required = false) String type);

    @GetMapping("/v1/invBudgetDeclare/getPreStageListByPIdAndType")
    BaseResponse getPreStageListByPIdAndType(@RequestParam String pId,@RequestParam String type);

    @PostMapping("/v1/invBudgetDeclare/approvalInvBudgetDeclare")
    BaseResponse approvalInvBudgetDeclare(@RequestBody InvBudgetDeclareRequest request);

    @GetMapping("/v1/invBudgetDeclare/getInvBudgetDeclareDtoById")
    BaseResponse getInvBudgetDeclareDtoById(@RequestParam String id);

    @GetMapping("/v1/invBudgetDeclare/getInvTypeBudgetDeclareDtlTreeByPid")
    BaseResponse getInvTypeBudgetDeclareDtlTreeByPid(@RequestParam String pId);

    /***********************************************概算申报清单***************************************************/

    @GetMapping("/v1/invBudgetDeclareDtl/getInvBudgetDeclareDtlTreeByPid")
    BaseResponse getInvBudgetDeclareDtlTreeByPid(@RequestParam String pId);

    @GetMapping("/v1/invBudgetDeclareDtl/getInvBudgetDeclareDtlListByPid")
    BaseResponse getInvBudgetDeclareDtlListByPid(@RequestParam String pId);

    @GetMapping("/v1/invBudgetDeclareDtl/getInvBudgetConPayInfo")
    BaseResponse getInvBudgetConPayInfo(@RequestParam String pId);

    /***********************************************合同结算申请***************************************************/

    @PostMapping("/v1/invSettlementApply/createOrUpdateInvSettlementApply")
    BaseResponse createOrUpdateInvSettlementApply(@RequestBody InvSettlementApplyRequest request);

    @PostMapping("/v1/invSettlementApply/updateInvSettlementApplyOnlyInfo")
    BaseResponse updateInvSettlementApplyOnlyInfo(@RequestBody InvSettlementApplyRequest request);

    @PostMapping("/v1/invSettlementApply/getInvSettlementApplyList")
    BaseResponse getInvSettlementApplyList(@RequestBody InvSettlementApplyRequest request, @RequestHeader String token);

    @GetMapping("/v1/invSettlementApply/getInvSettlementApplyBysaId")
    BaseResponse getInvSettlementApplyBysaId(@RequestParam String saId, @RequestHeader String token);

    @GetMapping("/v1/invSettlementApply/deleteInvSettlementApplyBysaId")
    BaseResponse deleteInvSettlementApplyBysaId(@RequestParam String saId);

    @GetMapping("/v1/invSettlementApply/getLastApprovalInvSettlementApplyByConId")
    BaseResponse getLastApprovalInvSettlementApplyByConId(@RequestParam String conId);

    /***********************************************结算二审***************************************************/
    @PostMapping("/v1/invSettlementSecond/createOrUpdateInvSettlementSecond")
    BaseResponse createOrUpdateInvSettlementSecond(@RequestBody InvSettlementSecondDto request);

    @PostMapping("/v1/invSettlementSecond/getInvSettlementSecondList")
    BaseResponse getInvSettlementSecondList(@RequestBody InvSettlementSecondDto request, @RequestHeader String token);

    @GetMapping("/v1/invSettlementSecond/getInvSettlementSecondById")
    BaseResponse getInvSettlementSecondById(@RequestParam String id);

    /***********************************************合同结算申请***************************************************/

    @PostMapping("/v1/invFinal/createOrUpdateInvFinalAccountsApply")
    BaseResponse createOrUpdateInvFinalAccountsApply(@RequestBody InvFinalAccountsApplyRequest request);

    @PostMapping("/v1/invFinal/getInvFinalAccountsApplyList")
    BaseResponse getInvFinalAccountsApplyList(@RequestBody InvFinalAccountsApplyRequest request, @RequestHeader String token);

    @GetMapping("/v1/invFinal/getInvFinalAccountsApplyByfaId")
    BaseResponse getInvFinalAccountsApplyByfaId(@RequestParam String faId);

    @GetMapping("/v1/invFinal/getInvFinalAccountsApplyAndDtlByPid")
    BaseResponse getInvFinalAccountsApplyAndDtlByPid(@RequestParam String pId);

    @GetMapping("/v1/invFinal/havingFinalApplyWithPid")
    BaseResponse havingFinalApplyWithPid(@RequestParam String pId);

    @GetMapping("/v1/invFinal/deleteInvFinalAccountsApplyByfaId")
    BaseResponse deleteInvFinalAccountsApplyByfaId(@RequestParam String faId);

    /***********************************************年度投资计划***************************************************/
    @PostMapping("/v1/invPlanYear/createOrUpdateInvPlanYear")
    BaseResponse createOrUpdateInvPlanYear(@RequestBody InvPlanYearRequest request);

    @PostMapping("/v1/invPlanYear/getInvPlanYearList")
    BaseResponse getInvPlanYearList(@RequestBody InvPlanYearRequest request, @RequestHeader String token);

    @PostMapping("/v1/invPlanYear/getInvPlanYearSearchList")
    CommonResponse<PageResult<InvPlanYearResponse>> getInvPlanYearSearchList(@RequestBody InvPlanYearSearchRequest request, @RequestHeader String token);

    @PostMapping("/v1/invPlanYear/getSearchTableList")
    CommonResponse<List<ClassInfoAnnotation>> getSearchTableList();

    @GetMapping("/v1/invPlanYear/getInvPlanYearByIpvId")
    BaseResponse getInvPlanYearByIpvId(@RequestParam String ipvId);

    @PostMapping("/v1/invPlanYear/canToCreateInvPlanYear")
    BaseResponse canToCreateInvPlanYear(@RequestBody InvPlanYearRequest request);

    @PostMapping("/v1/invPlanYear/getInvPlanYearByPDYear")
    BaseResponse getInvPlanYearByPDYear(@RequestBody InvPlanYearRequest request);

    @PostMapping("/v1/invPlanYear/getInvPlanYearByPIdAndYear")
    BaseResponse getInvPlanYearByPIdAndYear(@RequestBody InvPlanYearRequest request);

    /***********************************************投资台账***************************************************/
    @GetMapping("/v1/InvestmentAccount/getInvestmentDate")
    BaseResponse getInvestmentDate(@RequestHeader String token);


    /***********************************************月完成情况***************************************************/

    @PostMapping("/v1/invCompleteMonth/createOrUpdateInvCompleteMonth")
    BaseResponse createOrUpdateInvCompleteMonth(@RequestBody InvCompleteMonthRequest request);

    @PostMapping("/v1/invCompleteMonth/createOrUpdateInvCompleteMonthXz")
    BaseResponse createOrUpdateInvCompleteMonthXz(@RequestBody InvCompleteMonthRequest request);

    @PostMapping("/v1/invCompleteMonth/getInvCompleteMonthList")
    BaseResponse getInvCompleteMonthList(@RequestBody InvCompleteMonthRequest request, @RequestHeader String token);

    @PostMapping("/v1/invCompleteMonth/getInvCompleteMonthSearchList")
    CommonResponse<PageResult<InvCompleteMonthResponse>> getInvCompleteMonthSearchList(@RequestBody InvCompleteMonthSearchRequest request, @RequestHeader String token);

    @PostMapping("/v1/invCompleteMonth/getInvSearchTableList")
    CommonResponse<List<ClassInfoAnnotation>> getInvSearchTableList();

    @GetMapping("/v1/invCompleteMonth/getInvCompleteMonthByIcmId")
    BaseResponse getInvCompleteMonthByIcmId(@RequestParam String icmId, @RequestHeader String token);

    @PostMapping("/v1/invCompleteMonth/canToCreateInvCompleteMonth")
    BaseResponse canToCreateInvCompleteMonth(@RequestBody InvCompleteMonthRequest request);

    @PostMapping("/v1/invCompleteMonth/exportInvCompleteMonthStatistics")
    BaseResponse exportInvCompleteMonthStatistics(@RequestBody InvCompleteMonthRequest request);


    /**********************************************资金计划下达***************************************************/
    @PostMapping("/v1/invFundRelease/createOrUpdateInvFundRelease")
    BaseResponse createOrUpdateInvFundRelease(@RequestBody InvFundReleaseRequest request);

    @GetMapping("/v1/invFundRelease/getInvFundReleaseByIfrId")
    BaseResponse getInvFundReleaseByIfrId(@RequestParam String ifrId);

    @PostMapping("/v1/invFundRelease/getInvFundReleaseList")
    BaseResponse getInvFundReleaseList(@RequestBody InvFundReleaseRequest request);

    /**********************************************投资项目计划***************************************************/
    @PostMapping("/v1/invProjectPlan/getInvProjectPlanList")
    BaseResponse getInvProjectPlanList(@RequestBody InvProjectPlanRequest request, @RequestHeader String token);

    @GetMapping("/v1/invProjectPlan/getInvProPlanHistoryAmt")
    BaseResponse getInvProPlanHistoryAmt(@RequestParam String pId, @RequestParam String year);

    @PostMapping("/v1/invProjectPlan/createOrUpdateInvProjectPlan")
    BaseResponse createOrUpdateInvProjectPlan(@RequestBody InvProjectPlanRequest request);

    @GetMapping("/v1/invProjectPlan/getInvProjectPlanById")
    BaseResponse getInvProjectPlanById(@RequestParam String ippId);

    @GetMapping("/v1/invProjectPlan/getInvProjectPlanByPIdAndYear")
    BaseResponse getInvProjectPlanByPIdAndYear(@RequestParam String pId, @RequestParam Integer year, @RequestParam String fundsType);

    /***************************************投资控制***************************************/

    @PostMapping("/v1/invControlManage/createOrUpdateInvControlManage")
    BaseResponse createOrUpdateInvControlManage(@RequestBody InvControlManageModel request);

    @PostMapping("/v1/invControlManage/getInvControlManageList")
    BaseResponse getInvControlManageList(@RequestBody InvControlManageModel request, @RequestHeader String token);

    @GetMapping("/v1/invControlManage/getInvControlManageById")
    BaseResponse getInvControlManageById(@RequestParam String icmId);

    /***************************************资金计划申请***************************************/

    @PostMapping("/v1/fsApply/createOrUpdateFundSchemeApply")
    public BaseResponse createOrUpdateFundSchemeApply(@RequestBody FundSchemeApplyModel request);

    @PostMapping("/v1/fsApply/getFundSchemeApplyList")
    public BaseResponse getFundSchemeApplyList(@RequestBody FundSchemeApplyModel request, @RequestHeader String token);

    @GetMapping("/v1/fsApply/getFundSchemeApplyById")
    public BaseResponse getFundSchemeApplyById(@RequestParam String fsaId);

    @GetMapping("/v1/fsApply/getFundSchemeApplyAmtSumByPid")
    public BaseResponse getFundSchemeApplyAmtSumByPid(@RequestParam String pId);

    @GetMapping("/v1/fsApply/getFundSchemeApplyDtlAmtSumByConId")
    public BaseResponse getFundSchemeApplyDtlAmtSumByConId(@RequestParam String conId);


    /***************************************资金计划下达浏览***************************************/
    @GetMapping("/v1/fundPlanModify/createFundPlanModify")
    public BaseResponse createFundPlanModify(@RequestParam String ifrId);

    @GetMapping("/v1/fundPlanModify/getFundPlanModifyBypPid")
    public BaseResponse getFundPlanModifyBypPid(@RequestParam String pId);

    @PostMapping("/v1/fundPlanModify/getFundPlanModifyList")
    public BaseResponse getFundPlanModifyList(@RequestBody FundPlanModifyDto request, @RequestHeader String token);

    @PostMapping("/v1/fundPlanModify/updateFundPlanModify")
    public BaseResponse updateFundPlanModify(@RequestBody FundPlanModifyDto request);

    @GetMapping("/v1/fundPlanModify/getFundPlanModifyByPidAndYear")
    public BaseResponse getFundPlanModifyByPidAndYear(@RequestParam String pId, @RequestParam Integer year, @RequestParam String newDate);

    @GetMapping("/v1/fundPlanModify/getFundPlanModifyHandleBypPid")
    public BaseResponse getFundPlanModifyHandleBypPid(@RequestParam String pId);


    /***************************************基建投资月报***************************************/

    @PostMapping("/v1/invInfrastructureMonth/createOrUpdateInvInfrastructureMonth")
    BaseResponse createOrUpdateInvInfrastructureMonth(@RequestBody InvInfrastructureMonthDto request);

    @GetMapping("/v1/invInfrastructureMonth/getInvInfrastructureMonthById")
    BaseResponse getInvInfrastructureMonthById(@RequestParam String id);

    @PostMapping("/v1/invInfrastructureMonth/getInvInfrastructureMonthDtlList")
    BaseResponse getInvInfrastructureMonthDtlList(@RequestBody InvInfrastructureQueryDto request, @RequestHeader String token);

    /***********************************************合同结算申请（终审）***************************************************/

    @PostMapping("/v1/invSettlementEnd/createOrUpdateInvSettlementEnd")
    BaseResponse createOrUpdateInvSettlementEnd(@RequestBody InvSettlementEndDTO request);

    @PostMapping("/v1/invSettlementEnd/updateInvSettlementEndOnlyInfo")
    BaseResponse updateInvSettlementEndOnlyInfo(@RequestBody InvSettlementEndDTO request);

    @PostMapping("/v1/invSettlementEnd/getInvSettlementEndList")
    BaseResponse getInvSettlementEndList(@RequestBody InvSettlementEndDTO request, @RequestHeader String token);

    @PostMapping("/v1/invSettlementEnd/getInvSettlementEndNoAuthList")
    BaseResponse getInvSettlementEndNoAuthList(@RequestBody InvSettlementEndDTO request, @RequestHeader String token);

    @GetMapping("/v1/invSettlementEnd/getInvSettlementEndBySaeId")
    BaseResponse getInvSettlementEndBySaeId(@RequestParam String saeId);

    @GetMapping("/v1/invSettlementEnd/deleteInvSettlementEndBySaeId")
    BaseResponse deleteInvSettlementEndBySaeId(@RequestParam String saeId);

    @GetMapping("/v1/invSettlementEnd/getLastApprovalInvSettlementEndByConId")
    BaseResponse getLastApprovalInvSettlementEndByConId(@RequestParam String conId);

    /***********************************************概算追加***************************************************/

    @PostMapping("/v1/budgetAdd/createOrUpdateInvBudgetDeclareAdd")
    BaseResponse createOrUpdateInvBudgetDeclareAdd(@RequestBody InvBudgetDeclareAddDTO request);

    @PostMapping("/v1/budgetAdd/getInvBudgetDeclareAddList")
    BaseResponse getInvBudgetDeclareAddList(@RequestBody InvBudgetDeclareAddDTO request, @RequestHeader String token);

    @GetMapping("/v1/budgetAdd/getInvBudgetDeclareAddById")
    BaseResponse getInvBudgetDeclareAddById(@RequestParam String id);

    @GetMapping("/v1/budgetAdd/approve2AddProBudget")
    BaseResponse approve2AddProBudget(@RequestParam String id);

    @PostMapping("/v1/invCompleteMonth/updateTransferStatus")
    BaseResponse updateTransferStatus(@RequestBody TransferStatusUpdateRequest request, @RequestHeader String token);

    @PostMapping("/v1/invCompleteMonth/getCreateFieldData")
    BaseResponse getCreateFieldData(@RequestBody GetCreateFieldDataRequest request);

    @PostMapping("/v1/invCompleteMonth/getNearCompleteMonthData")
    BaseResponse getNearCompleteMonthData(@RequestBody GetCreateFieldDataRequest request);

    @GetMapping("/v1/invCompleteMonth/getMonthlyInvestmentSum")
    BaseResponse getMonthlyInvestmentSum(@RequestParam String pid, @RequestParam String year, @RequestParam Integer month);

    @PostMapping("/v1/invCompleteMonth/countInvCompleteMonth")
    BaseResponse countInvCompleteMonth(@RequestBody InvCompleteMonthRequest request, @RequestHeader String token);

    @PostMapping("/v1/invCompleteMonth/countInvCompleteMonthForAll")
    BaseResponse countInvCompleteMonthForAll(@RequestBody InvCompleteMonthRequest request, @RequestHeader String token);

    @PostMapping("/v1/invCompleteMonth/countInvCompleteMonthExportExcel")
    Response countInvCompleteMonthExportExcel(@RequestBody InvCompleteMonthRequest request, @RequestHeader String token);

    @PostMapping("/v1/invCompleteMonth/countInvCompleteMonthForAllExportExcel")
    Response countInvCompleteMonthForAllExportExcel(@RequestBody InvCompleteMonthRequest request, @RequestHeader String token);

    @PostMapping("/v1/invCompleteMonth/invCompleteMonthListExportExcel")
    Response invCompleteMonthListExportExcel(@RequestBody InvCompleteMonthRequest request, @RequestHeader String token);

    /***************************************跟踪审计月报***************************************/
    @GetMapping("/v1/trackAuditMonth/getTrackAuditMonthByConId")
    BaseResponse getTrackAuditMonthByConId(@RequestParam("conId") String conId,@RequestParam(value = "month", required = false) String month);

    @GetMapping("/v1/trackAuditMonth/getTrackAuditMonthListByPId")
    BaseResponse getTrackAuditMonthListByPId(@RequestParam("pId") String pId);

    @GetMapping("/v1/trackAuditMonth/approvalTrackAuditMonth")
    BaseResponse approvalTrackAuditMonth(@RequestParam("tamId") String tamId);


    /***********************************************匡算申报***************************************************/

    @PostMapping("/v1/invRoughDeclare/createOrUpdateInvRoughDeclare")
    BaseResponse createOrUpdateInvRoughDeclare(@RequestBody InvRoughDeclareRequest request);

    @PostMapping("/v1/invRoughDeclare/getInvRoughDeclareList")
    BaseResponse getInvRoughDeclareList(@RequestBody InvRoughDeclareRequest request, @RequestHeader String token);

    @GetMapping("/v1/invRoughDeclare/getInvRoughDeclareByRdId")
    BaseResponse getInvRoughDeclareByRdId(@RequestParam String rdId);

    @GetMapping("/v1/invRoughDeclare/deleteInvRoughDeclareByRdId")
    BaseResponse deleteInvRoughDeclareByRdId(@RequestParam String rdId);

    @PostMapping("/v1/invRoughDeclare/getInvRoughDeclareDtlByPIds")
    BaseResponse getInvRoughDeclareDtlByPIds(@RequestBody List<String> pIds);


    @GetMapping("/v1/invRoughDeclare/getInvRoughDeclareByPId")
    BaseResponse getInvRoughDeclareByPId(@RequestParam String pId);



    @GetMapping("/v1/invRoughDeclare/getInvRoughDeclareAndDtlByPid")
    BaseResponse getInvRoughDeclareAndDtlByPid(@RequestParam String pId);


    /***********************************************匡算申报清单***************************************************/

    @GetMapping("/v1/invRoughDeclareDtl/getInvRoughDeclareDtlTreeByPid")
    BaseResponse getInvRoughDeclareDtlTreeByPid(@RequestParam String pId);

    @GetMapping("/v1/invRoughDeclareDtl/getInvRoughDeclareDtlListByPid")
    BaseResponse getInvRoughDeclareDtlListByPid(@RequestParam String pId);

    /***********************************************估算申报***************************************************/

    @PostMapping("/v1/invEstimateDeclare/createOrUpdateInvEstimateDeclare")
    BaseResponse createOrUpdateInvEstimateDeclare(@RequestBody InvEstimateDeclareRequest request);

    @PostMapping("/v1/invEstimateDeclare/getInvEstimateDeclareList")
    BaseResponse getInvEstimateDeclareList(@RequestBody InvEstimateDeclareRequest request, @RequestHeader String token);

    @GetMapping("/v1/invEstimateDeclare/getInvEstimateDeclareByEdId")
    BaseResponse getInvEstimateDeclareByEdId(@RequestParam String edId);

    @GetMapping("/v1/invEstimateDeclare/deleteInvEstimateDeclareByEdId")
    BaseResponse deleteInvEstimateDeclareByEdId(@RequestParam String edId);

    @PostMapping("/v1/invEstimateDeclare/getInvEstimateDeclareDtlByPIds")
    BaseResponse getInvEstimateDeclareDtlByPIds(@RequestBody List<String> pIds);


    @GetMapping("/v1/invEstimateDeclare/getInvEstimateDeclareByPId")
    BaseResponse getInvEstimateDeclareByPId(@RequestParam String pId);



    @GetMapping("/v1/invEstimateDeclare/getInvEstimateDeclareAndDtlByPid")
    BaseResponse getInvEstimateDeclareAndDtlByPid(@RequestParam String pId);


    /***********************************************估算申报清单***************************************************/

    @GetMapping("/v1/invEstimateDeclareDtl/getInvEstimateDeclareDtlTreeByPid")
    BaseResponse getInvEstimateDeclareDtlTreeByPid(@RequestParam String pId);

    @GetMapping("/v1/invEstimateDeclareDtl/getInvEstimateDeclareDtlListByPid")
    BaseResponse getInvEstimateDeclareDtlListByPid(@RequestParam String pId);

    /***********************************************调整事项模板***************************************************/

    @PostMapping("/v1/invAdjustmentTemplate/createOrUpdateInvAdjustmentTemplate")
    BaseResponse createOrUpdateInvAdjustmentTemplate(@RequestBody InvAdjustmentTemplateRequest request);

    @PostMapping("/v1/invAdjustmentTemplate/getInvAdjustmentTemplateList")
    BaseResponse getInvAdjustmentTemplateList(@RequestBody InvAdjustmentTemplateRequest request, @RequestHeader String token);

    @GetMapping("/v1/invAdjustmentTemplate/getInvAdjustmentTemplateByAtId")
    BaseResponse getInvAdjustmentTemplateByAtId(@RequestParam String atId);

    @GetMapping("/v1/invAdjustmentTemplate/deleteInvAdjustmentTemplateByAtId")
    BaseResponse deleteInvAdjustmentTemplateByAtId(@RequestParam String atId);

    /***********************************************调整事项***************************************************/

    @PostMapping("/v1/invAdjustment/createOrUpdateInvAdjustment")
    BaseResponse createOrUpdateInvAdjustment(@RequestBody InvAdjustmentRequest request);

    @PostMapping("/v1/invAdjustment/getInvAdjustmentList")
    BaseResponse getInvAdjustmentList(@RequestBody InvAdjustmentRequest request, @RequestHeader String token);

    @GetMapping("/v1/invAdjustment/getInvAdjustmentByIaId")
    BaseResponse getInvAdjustmentByIaId(@RequestParam String iaId);

    @GetMapping("/v1/invAdjustment/deleteInvAdjustmentByIaId")
    BaseResponse deleteInvAdjustmentByIaId(@RequestParam String iaId);


    @GetMapping("/v1/invPlanYear/getPlanOverview")
    BaseResponse getPlanOverview(@RequestParam String pName,@RequestParam String strYear,@RequestParam Integer page,@RequestParam Integer size,@RequestParam String pCompetentDepartment,@RequestParam String pSecType,@RequestParam String projectState);

    @PostMapping("/v1/invPlanYear/createOrUpdateInvPlanYearXz")
    BaseResponse createOrUpdateInvPlanYearXz( @RequestBody InvPlanYearRequest request);

    @PostMapping("/v1/invPlanYear/getInvPlanYearByPDYearXz")
    BaseResponse getInvPlanYearByPDYearXz(@RequestBody InvPlanYearRequest request);


    @GetMapping("/v1/invPlanYear/updatePreState")
    BaseResponse updatePreState(@RequestParam String pid,@RequestParam  String preState,@RequestParam String icmId);

    @GetMapping("/v1/invPlanYear/getInvPlanYearByIpvIdXz")
    BaseResponse getInvPlanYearByIpvIdXz(@RequestParam String ipvId);

    @GetMapping("/v1/invPlanYear/determineAnnualPlan")
    BaseResponse determineAnnualPlan(@RequestParam String pId);

    @GetMapping("/v1/invPlanYear/determinemonthlyPlan")
    BaseResponse determinemonthlyPlan(@RequestParam String pId, @RequestParam Integer year, @RequestParam Integer month);

    @PostMapping("/v1/InvestmentEstimateBreakdown/createOrUpdateInvestmentEstimateBreakdown")
    BaseResponse createOrUpdateInvestmentEstimateBreakdown(@RequestBody InvestmentEstimateBreakdownModel request);
    @PostMapping("/v1/InvestmentEstimateBreakdown/getInvestmentEstimateBreakdownList")
    BaseResponse getInvestmentEstimateBreakdownList(@RequestBody InvestmentEstimateBreakdownModel request,@RequestHeader String token);
    @GetMapping("/v1/InvestmentEstimateBreakdown/getInvestmentEstimateBreakdownById")
    BaseResponse getInvestmentEstimateBreakdownById(@RequestParam String id);
    @GetMapping("/v1/InvestmentEstimateBreakdown/deleteInvestmentEstimateBreakdownById")
    BaseResponse deleteInvestmentEstimateBreakdownById(@RequestParam String id);
    @GetMapping("/v1/InvestmentEstimateBreakdown/removeInvestmentEstimateBreakdownById")
    BaseResponse removeInvestmentEstimateBreakdownById(@RequestParam String id);
    @PostMapping("/v1/InvestmentEstimateBreakdown/getProjectIdList")
    BaseResponse getProjectIdList(@RequestBody InvestmentEstimateBreakdownModel request);
    @GetMapping("/v1/InvestmentEstimateBreakdown/getInvestmentEstimateBreakdownByPId")
    BaseResponse getInvestmentEstimateBreakdownByPId(@RequestParam String pId);
    @PostMapping("/v1/InvestmentBudgetBreakdown/createOrUpdateInvestmentBudgetBreakdown")
    BaseResponse createOrUpdateInvestmentBudgetBreakdown(@RequestBody InvestmentBudgetBreakdownModel request);
    @PostMapping("/v1/InvestmentBudgetBreakdown/getInvestmentBudgetBreakdownList")
    BaseResponse getInvestmentBudgetBreakdownList(@RequestBody InvestmentBudgetBreakdownModel request,@RequestHeader String token);
    @GetMapping("/v1/InvestmentBudgetBreakdown/getInvestmentBudgetBreakdownById")
    BaseResponse getInvestmentBudgetBreakdownById(@RequestParam String id);
    @GetMapping("/v1/InvestmentBudgetBreakdown/deleteInvestmentBudgetBreakdownById")
    BaseResponse deleteInvestmentBudgetBreakdownById(@RequestParam String id);
    @GetMapping("/v1/InvestmentBudgetBreakdown/removeInvestmentBudgetBreakdownById")
    BaseResponse removeInvestmentBudgetBreakdownById(@RequestParam String id);
    @GetMapping("/v1/InvestmentEstimateBreakdown/getInvestmentEstimateBreakdownByProjectId")
    BaseResponse getInvestmentEstimateBreakdownByProjectId(@RequestParam String projectId);
    @GetMapping("/v1/InvestmentBudgetBreakdown/getInvestmentBudgetBreakdownByPId")
    BaseResponse getInvestmentBudgetBreakdownByPId(@RequestParam String pId);
    @GetMapping("/v1/InvestmentBudgetBreakdown/getInvestmentSecInfoByPId")
    BaseResponse getInvestmentSecInfoByPId(@RequestParam String pId);
    @GetMapping("/v1/invSettlementEnd/getLastApprovalInvSettlementEndByPayId")
    BaseResponse getLastApprovalInvSettlementEndByPayId(@RequestParam String id);

    @PostMapping("/v1/InvestmentEstimateBreakdown/getUnusedProjectIdList")
    BaseResponse getUnusedProjectIdList(InvestmentEstimateBreakdownModel request);

    @PostMapping("/v1/InvestmentEstimateBreakdownChange/createOrUpdateInvestmentEstimateBreakdownChange")
    BaseResponse createOrUpdateInvestmentEstimateBreakdownChange(@RequestBody InvestmentEstimateBreakdownChangeModel request);


    @PostMapping("/v1/InvestmentEstimateBreakdownChange/getInvestmentEstimateBreakdownChangeList")
    BaseResponse getInvestmentEstimateBreakdownChangeList(@RequestBody InvestmentEstimateBreakdownChangeModel request);

    @GetMapping("/v1/InvestmentEstimateBreakdownChange/getInvestmentEstimateBreakdownChangeById")
    BaseResponse getInvestmentEstimateBreakdownChangeById(@RequestParam String id);

    @PostMapping("/v1/InvestmentBudgetBreakdownChange/createOrUpdateInvestmentBudgetBreakdownChange")
    BaseResponse createOrUpdateInvestmentBudgetBreakdownChange(@RequestBody InvestmentBudgetBreakdownChangeModel request);

    @PostMapping("/v1/InvestmentBudgetBreakdownChange/getInvestmentBudgetBreakdownChangeList")
    BaseResponse getInvestmentBudgetBreakdownChangeList(@RequestBody InvestmentBudgetBreakdownChangeModel request);

    @GetMapping("/v1/InvestmentBudgetBreakdownChange/getInvestmentBudgetBreakdownChangeById")
    BaseResponse getInvestmentBudgetBreakdownChangeById(@RequestParam String id);


    @GetMapping("/v1/InvestmentEstimateBreakdown/getLatestInvestmentEstimateBreakdownById")
    BaseResponse getLatestInvestmentEstimateBreakdownById(@RequestParam String id);

    @GetMapping("/v1/InvestmentBudgetBreakdown/getLatestInvestmentBudgetBreakdownById")
    BaseResponse getLatestInvestmentBudgetBreakdownById(@RequestParam String id);

    //=============================义乌投资控制====================================
    @PostMapping("/v1/projectPresetYear/saveOrUpdatePresetYear")
    BaseResponse saveOrUpdatePresetYear(@RequestBody ProjectPresetYearRequest request, @RequestHeader String token);

    @PostMapping("/v1/projectPresetYear/getProjectPresetYearList")
    BaseResponse getProjectPresetYearList(@RequestBody ProjectPresetYearRequest request);

    @GetMapping("/v1/projectPresetYear/getProjectPresetYearId")
    BaseResponse getProjectPresetYearId(@RequestParam  String id);

    @PostMapping("/v1/projectPresetYear/createOrUpdateInvPlanYearYw")
    BaseResponse createOrUpdateInvPlanYearYw(@RequestBody InvPlanYearRequest request);

    @GetMapping("/v1/projectPresetYear/getInvPlanYearYwByIpvId")
    BaseResponse getInvPlanYearYwByIpvId(@RequestParam String ipvId);

    @PostMapping("/v1/projectPresetYear/getInvPlanYearYwList")
    BaseResponse getInvPlanYearYwList(@RequestBody InvPlanYearRequest request, @RequestHeader  String token);

    @GetMapping("/v1/projectPresetYear/getInvPlanYearYwBypId")
    BaseResponse getInvPlanYearYwBypId(@RequestParam String pId,@RequestParam(required = false) String year);

    @PostMapping("/v1/projectPresetYear/getInvPlanYearSummaryYwList")
    BaseResponse getInvPlanYearSummaryYwList(@RequestBody InvPlanYearRequest request, @RequestHeader  String token);

    @PostMapping("/v1/projectPresetYear/getInvPlanYearYwListExcel")
    ResponseEntity<byte[]> getInvPlanYearYwListExcel(@RequestBody InvPlanYearRequest request, @RequestHeader String token);

    @PostMapping("/v1/projectPresetYear/getInvPlanYearYwPreListExcel")
    ResponseEntity<byte[]> getInvPlanYearYwPreListExcel(@RequestBody InvPlanYearRequest request, @RequestHeader String token);

    @PostMapping("/v1/settlementFirstAudit/saveOrUpdateSettlementFirstAudit")
    BaseResponse saveOrUpdateSettlementFirstAudit(@RequestBody SettlementFirstAuditModel request,@RequestHeader String token);
    @PostMapping("/v1/settlementFirstAudit/getSettlementFirstAuditClassInfo")
    BaseResponse getSettlementFirstAuditClassInfo(@RequestBody SettlementFirstAuditRequest request);
    @PostMapping("/v1/settlementFirstAudit/getSettlementFirstAuditList")
    BaseResponse getSettlementFirstAuditList(@RequestBody SettlementFirstAuditRequest request);
    @GetMapping("/v1/settlementFirstAudit/getSettlementFirstAuditId")
    BaseResponse getSettlementFirstAuditId(@RequestParam String id);
    @PostMapping("/v1/settlementFirstAudit/saveOrUpdateSettlementFirstAuditChangeLog")
    BaseResponse saveOrUpdateSettlementFirstAuditChangeLog(@RequestBody SettlementFirstAuditChangeLogModel request,@RequestHeader String token);
    @PostMapping("/v1/settlementFirstAudit/getSettlementFirstAuditChangeLogList")
    BaseResponse getSettlementFirstAuditChangeLogList(@RequestBody SettlementFirstAuditModel request);
    @PostMapping("/v1/settlementFirstAudit/approveChangeLogData")
    BaseResponse approveChangeLogData(@RequestBody SettlementFirstAuditChangeLogModel request);
    @PostMapping("/v1/settlementFirstAudit/loadContractInfo")
    BaseResponse loadContractInfo(@RequestBody SettlementFirstAuditModel request);
    @GetMapping("/v1/settlementFirstAudit/getSettlementFirstAuditChangeLogId")
    BaseResponse getSettlementFirstAuditChangeLogId(@RequestParam String id);
    @PostMapping("/v1/settlementFirstAudit/deleteSettlementFirstAudit")
    BaseResponse deleteSettlementFirstAudit(@RequestBody SettlementFirstAuditModel request);

    //-------------------------------结算管理
    @GetMapping("/v1/settlement/deleteSettAuditById")
    BaseResponse deleteSettAuditById(@RequestParam String id);
    @PostMapping("/v1/settlement/querySettAuditList")
    BaseResponse querySettAuditList(@RequestBody SettlementAuditVo request);
    @PostMapping("/v1/settlement/createOrUpdateSettAudit")
    BaseResponse createOrUpdateSettAudit(@RequestBody SettlementAuditVo request, @RequestHeader String token);
    @GetMapping("/v1/settlement/getSettAuditDetailId")
    BaseResponse getSettAuditDetailId(@RequestParam String id);
    @GetMapping("/v1/settlement/getConReviewAmount")
    BaseResponse getConReviewAmount(@RequestParam String conId);
    @PostMapping("/v1/settlement/querySettAuditListClassInfo")
    BaseResponse querySettAuditListClassInfo(@RequestBody SettlementAuditVo request);
    @PostMapping("/v1/settlement/changeSettAuditChange")
    BaseResponse changeSettAuditChange(@RequestBody SettlementAuditVo request);
    @GetMapping("/v1/settlement/updateChangeSettAudit")
    BaseResponse updateChangeSettAudit(@RequestParam String id);
    @PostMapping("/v1/settlement/querySettAuditChangeList")
    BaseResponse querySettAuditChangeList(@RequestBody SettlementAuditVo request);
    @GetMapping("/v1/settlement/getSettAuditDetailChangeId")
    BaseResponse getSettAuditDetailChangeId(@RequestParam String id);

    //------------决算管理 决算行文上报
    @PostMapping("/v1/finalAccounts/getFinalAccountsList")
    BaseResponse getFinalAccountsList(@RequestBody FinalAccountingReportVo request,@RequestHeader String token);

    @PostMapping("/v1/finalAccounts/createOrUpdateFinalAccounts")
    BaseResponse createOrUpdateFinalAccounts(@RequestBody FinalAccountingReportVo request,@RequestHeader String token);

    @GetMapping("/v1/finalAccounts/getFinalAccountsById")
    BaseResponse getFinalAccountsById(@RequestParam String id);

    @GetMapping("/v1/finalAccounts/getInvestmentBudgetByPId")
    BaseResponse getInvestmentBudgetByPId(@RequestParam String pId);

    @GetMapping("/v1/finalAccounts/deleteFinalAccountsById")
    BaseResponse deleteFinalAccountsById(@RequestParam String id);

    @PostMapping("/v1/finalAccounts/queryFinalAccountsClassInfo")
    BaseResponse queryFinalAccountsClassInfo(@RequestBody FinalAccountingReportVo request);



    //---------------------------------决算批复
    @PostMapping("/v1/finalAccounts/getfinalApprovalList")
    BaseResponse getfinalApprovalList(@RequestBody FinalAccountingApprovalVo request);

    @GetMapping("/v1/finalAccounts/getfinalApprovalById")
    BaseResponse getfinalApprovalById(@RequestParam  String id);

    @GetMapping("/v1/finalAccounts/delFinalApprovalById")
    BaseResponse delFinalApprovalById(@RequestParam  String id);

    @PostMapping("/v1/finalAccounts/createOrUpdatefinalApproval")
    BaseResponse createOrUpdatefinalApproval(@RequestBody FinalAccountingApprovalVo request, @RequestHeader String token);


    @PostMapping("/v1/finalAccounts/createOrUpdatefinalApprovalChange")
    BaseResponse createOrUpdatefinalApprovalChange(@RequestBody FinalAccountingApprovalChangeVo request,@RequestHeader  String token);

    @GetMapping("/v1/finalAccounts/getfinalApprovalChangeById")

    BaseResponse getfinalApprovalChangeById(@RequestParam String id);

    @GetMapping("/v1/finalAccounts/changeFinalApproval")
    BaseResponse changeFinalApproval(@RequestParam String id);

    @PostMapping("/v1/finalAccounts/getfinalApprovalChangeList")
    BaseResponse getfinalApprovalChangeList(@RequestBody FinalAccountingApprovalChangeVo request, @RequestHeader String token);

    @PostMapping("/v1/finalAccounts/queryApprovalListClassInfo")
    BaseResponse queryApprovalListClassInfo(@RequestBody  FinalAccountingApprovalVo request);

    @PostMapping("/v1/InvestmentBudgetBreakdown/getInvestmentControlList")
    BaseResponse getInvestmentControlList(@RequestBody InvestmentControlRequest request);

    @GetMapping("/v1/InvestmentBudgetBreakdown/getInvestmentControlByPId")
    BaseResponse getInvestmentControlByPId(@RequestParam String pId);

    @PostMapping("/v1/InvestmentBudgetBreakdown/getInvestmentControlClassInfo")
    BaseResponse getInvestmentControlClassInfo(@RequestBody InvestmentControlRequest request);

    @GetMapping("/v1/invMonthen/writeBackMonthPlanId")
    BaseResponse writeBackMonthPlan(@RequestParam String ipvId);

    @PostMapping("/v1/invMonthen/getMonthplanClassInfo")
    BaseResponse getMonthplanClassInfo(@RequestBody InvCompleteMonthRequest request);

    @PostMapping("/v1/invMonthen/getCompleteMonthplanList")
    BaseResponse getMonthplanList(@RequestBody InvCompleteMonthRequest request);

    @GetMapping("/v1/invCompleteMonth/completeSubsequentInvCompleteMonth")
    BaseResponse completeSubsequentInvCompleteMonth(@RequestBody TransferStatusUpdateRequest request);

//    @PostMapping("/v1/invMonthen/importMonthlyOutput")
//    BaseResponse importMonthlyOutput(MultipartFile file, String token);
//
//    @GetMapping(("/v1/invMonthen/changeMonthlyOutputByInvId")
//    BaseResponse changeMonthlyOutputByInvId(String ipvId);

    @GetMapping("/v1/operationRecord/getOperationRecordList")
    BaseResponse getOperationRecordList(@RequestParam String id);

    @GetMapping("/v1/invCompleteMonth/getInvMonthVersionById")
    BaseResponse getInvMonthVersionById(@RequestParam String id);

    @PostMapping("/v1/invCompleteMonth/createInvMonthVersion")
    BaseResponse createInvMonthVersion(@RequestBody InvMonthVersionRequest request);

    @GetMapping("/v1/invCompleteMonth/initInvMonthVersion")
    BaseResponse initInvMonthVersion();

    @GetMapping("/v1/InvestmentBudgetBreakdown/getInvestmentBudgetBreakdownTreeByPId")
    BaseResponse getInvestmentBudgetBreakdownTreeByPId(@RequestParam String pId);

    @GetMapping("/v1/invPlanYear/syncToInvPlanYearYw")
    BaseResponse syncToInvPlanYearYw(@RequestParam String ipvId);

    @GetMapping("/v1/invBudgetDeclare/getInvBudgetDeclareDetailByPId")
    BaseResponse getInvBudgetDeclareDetailByPId(@RequestParam String pId);
}
