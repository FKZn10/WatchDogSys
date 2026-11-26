package com.jpmsoft.projectConsumer.feign;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.project.PreProjectRequest;
import com.jpmsoft.projectConsumer.pojo.dto.fundPlan.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author: 李文涛 aaron
 * @date: 2022/8/16 15:58
 */
@FeignClient(name = "project-producer", contextId = "fundPlan")
public interface FundPlanFeign {
    /***********************资金计划表*************************/
    @PostMapping("/v1/fundPlan/createOrUpdateFundPlan")
    BaseResponse createOrUpdateFundPlan(@RequestBody FundPlanDto request);

    @PostMapping("/v1/fundPlan/getFundPlanList")
    BaseResponse getFundPlanList(@RequestBody FundPlanDto request, @RequestHeader String token);

    @GetMapping("/v1/fundPlan/getFundPlanById")
    BaseResponse getFundPlanById(@RequestParam String id);

    @GetMapping("/v1/fundPlan/getProFpDemandFinalSum")
    BaseResponse getProFpDemandFinalSum(@RequestParam String pId);

    @PostMapping("/v1/fundPlan/getFundPlanDtlList")
    BaseResponse getFundPlanDtlList(@RequestBody FundPlanDtlDto request);

    @GetMapping("/v1/fundPlan/getFundPlanDtlByFpId")
    BaseResponse getFundPlanDtlByFpId(@RequestParam String fpId);

    /***********************资金计划表审核记录*************************/
    @PostMapping("/v1/fundPlanDtlCheck/createOrUpdateFundPlanDtlCheck")
    BaseResponse createOrUpdateFundPlanDtlCheck(@RequestBody FundPlanDtlCheckDto request);

    @PostMapping("/v1/fundPlanDtlCheck/getFundPlanDtlCheckList")
    BaseResponse getFundPlanDtlCheckList(@RequestBody FundPlanDtlCheckDto request);

    @GetMapping("/v1/fundPlanDtlCheck/getFundPlanDtlCheckById")
    BaseResponse getFundPlanDtlCheckById(@RequestParam String id);

    /***********************资金拨付申请*************************/

    @PostMapping("/v1/fundAppropriation/createOrUpdateFundAppropriation")
    BaseResponse createOrUpdateFundAppropriation(@RequestBody FundAppropriationDto request);

    @PostMapping("/v1/fundAppropriation/getFundAppropriationList")
    BaseResponse getFundAppropriationList(@RequestBody FundAppropriationDto request, @RequestHeader String token);

    @GetMapping("/v1/fundAppropriation/getFundAppropriationById")
    BaseResponse getFundAppropriationById(@RequestParam String id);

    @GetMapping("/v1/fundAppropriation/getProCompletedInvSum")
    BaseResponse getProCompletedInvSum(@RequestParam String pId);

    @GetMapping("/v1/fundAppropriation/getFaDemandSumByPid")
    BaseResponse getFaDemandSumByPid(@RequestParam String pId);

    @GetMapping("/v1/fundAppropriation/getConAppropriationDtl")
    BaseResponse getConAppropriationDtl(@RequestParam(required = false) String fpId, @RequestParam String conId);

    @GetMapping("/v1/fundAppropriation/getAllFundPlanDtlByFpId")
    BaseResponse getAllFundPlanDtlByFpId(@RequestParam String fpId);

    @PostMapping("/v1/fundAppropriation/getFundAppropriationDtlList")
    BaseResponse getFundAppropriationDtlList(@RequestBody FundAppropriationDtlDto request);

    /***********************资金收款明细*************************/
    @PostMapping("/v1/fundCollectionDetails/createOrUpdateFundCollectionDetails")
    BaseResponse createOrUpdateFundCollectionDetails(@RequestBody FundCollectionDetailsDto request);

    @PostMapping("/v1/fundCollectionDetails/getFundCollectionDetailsList")
    BaseResponse getFundCollectionDetailsList(@RequestBody FundCollectionDetailsDto request, @RequestHeader String token);

    @PostMapping("/v1/fundCollectionDetails/getFundCollectionProjectList")
    BaseResponse getFundCollectionProjectList(@RequestBody PreProjectRequest request, @RequestHeader String token);

    @GetMapping("/v1/fundCollectionDetails/getFundCollectionDetailsById")
    BaseResponse getFundCollectionDetailsById(@RequestParam String id);

    @GetMapping("/v1/fundCollectionDetails/getLastFundCollectionDetailsByPid")
    BaseResponse getLastFundCollectionDetailsByPid(@RequestParam String pId);

    @GetMapping("/v1/fundCollectionDetails/statisticsBatchNoAndAmt")
    BaseResponse statisticsBatchNoAndAmt(@RequestParam String pId);

    @PostMapping("/v1/fundCollectionDetails/getProjectFundCollectionDetailsList")
    BaseResponse getProjectFundCollectionDetailsList(@RequestBody PreProjectRequest request, @RequestHeader String token);

    @GetMapping("/v1/fundAllocationDtl/getFundAllocationDtlList")
    BaseResponse getFundAllocationDtlList(@RequestParam String conId);

    @GetMapping("/v1/fundAllocation/getFundAllocationByDtlCon")
    BaseResponse getFundAllocationByDtlCon(@RequestParam String conId);

    @PostMapping("/v1/progressDeclaration/createOrUpdateProgressDeclaration")
    BaseResponse createOrUpdateProgressDeclaration(@RequestBody ProgressDeclarationDTO request);
    @GetMapping("/v1/progressDeclaration/getProgressDeclarationById")
    BaseResponse getProgressDeclarationById(@RequestParam String id);
    @PostMapping("/v1/progressDeclaration/getProgressDeclarationList")
    BaseResponse getProgressDeclarationList(@RequestBody ProgressDeclarationDTO request);

    @GetMapping("/v1/progressDeclaration/getPlanNodes")
    BaseResponse getPlanNodes(@RequestParam  String conId);
    @PostMapping("/v1/progressDeclaration/progressDeclarationMatch")
    BaseResponse progressDeclarationMatch(@RequestBody ProgressDeclarationMatchDTO dto);
    @PostMapping("/v1/progressDeclaration/getContractDeclarationList")
    BaseResponse getContractDeclarationList(@RequestBody ProgressDeclarationMatchDTO dto);

    @PostMapping("/v1/progressDeclaration/progerGeneratePayment")
    BaseResponse progerGeneratePayment(@RequestBody ProgressDeclarationMatchDTO dto);
    @PostMapping("/v1/progressDeclaration/updateContrCompletionData")
    BaseResponse updateContrCompletionData(@RequestBody ProgressDeclarationMatchDTO dto);


    @GetMapping("/v1/progressDeclaration/delAuditSettlementById")
    BaseResponse delAuditSettlementById(@RequestParam String id,@RequestParam String conId);
    @GetMapping("/v1/progressDeclaration/delPreliminaryAuditById")

    BaseResponse delPreliminaryAuditById(@RequestParam String id,@RequestParam String conId);
    @GetMapping("/v1/progressDeclaration/delInvFinalAccountsApplyById")

    BaseResponse delInvFinalAccountsApplyById(@RequestParam String id,@RequestParam String conId);
    @GetMapping("/v1/progressDeclaration/delBalancePaymentById")

    BaseResponse delBalancePaymentById(@RequestParam String id,@RequestParam String conId);

    @GetMapping("/v1/progressDeclaration/getAccordingThePlan")
    BaseResponse getAccordingThePlan(@RequestBody ProgressDeclarationMatchDTO dto);
}
