package com.jpmsoft.projectConsumer.feign;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Contract.EngineeringFundApplicationRejectRequest;
import com.jpmsoft.common.model.request.fund.ClaimIncomingFundsRequest;
import com.jpmsoft.projectConsumer.pojo.dto.fund.*;
import com.jpmsoft.projectConsumer.request.ReturnInitiatorDto;
import com.jpmsoft.projectConsumer.request.fund.*;
import com.jpmsoft.projectConsumer.response.EFAResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Component
@FeignClient(name = "project-producer", contextId = "pay")
public interface FundFeign {
    @PostMapping("/v1/paymentReceipt/createOrUpdatePaymentReceipt")
    BaseResponse createOrUpdatePaymentReceipt(@RequestBody PaymentReceiptDto request);
    @PostMapping("/v1/paymentReceipt/getPaymentReceiptList")
    BaseResponse getPaymentReceiptList(@RequestBody PaymentReceiptRequest request);
    @GetMapping("/v1/paymentReceipt/getPaymentReceiptById")
    BaseResponse getPaymentReceiptById(@RequestParam String id);
    @PostMapping("/v1/paymentReceipt/approvePaymentReceipt")
    BaseResponse approvePaymentReceipt(@RequestBody PaymentReceiptRequest request);
    @PostMapping("/v1/paymentReceipt/setPaymentReceiptVerify")
    BaseResponse setPaymentReceiptVerify(@RequestBody PaymentReceiptRequest request);
    @PostMapping("/v1/paymentReceipt/setPaymentReceiptVerifys")
    BaseResponse setPaymentReceiptVerifys(@RequestBody List<PaymentReceiptRequest> requests, @RequestHeader String token);
    @PostMapping("/v1/paymentReceipt/setPaymentReceiptIssued")
    BaseResponse setPaymentReceiptIssued(@RequestBody PaymentReceiptRequest request);
    @PostMapping("/v1/paymentReceipt/setPaymentReceiptRecovery")
    BaseResponse setPaymentReceiptRecovery(@RequestBody PaymentReceiptRequest request);
    @PostMapping("/v1/paymentReceipt/getFundPaymentReceiptList")
    BaseResponse getFundPaymentReceiptList(@RequestBody PaymentReceiptRequest request);


    @PostMapping("/v1/bankAccountInfo/getBankAccountInfoList")
    BaseResponse getBankAccountInfoList(@RequestBody BankAccountInfoRequest request,@RequestHeader String token);
    @PostMapping("/v1/bankAccountInfo/getBankAccountInfoByID")
    BaseResponse getBankAccountInfoByID(@RequestBody BankAccountInfoRequest request,@RequestHeader String token);


    @PostMapping("/v1/fundNumberInfo/createOrUpdateFundNumberInfo")
    BaseResponse createOrUpdateFundNumberInfo(@RequestBody FundNumberInfoDto request);
    @PostMapping("/v1/fundNumberInfo/getFundNumberInfoList")
    BaseResponse getFundNumberInfoList(@RequestBody FundNumberInfoRequest request,@RequestHeader String token);
    @PostMapping("/v1/fundNumberInfo/getFundNumberInfoById")
    BaseResponse getFundNumberInfoById(@RequestBody FundNumberInfoRequest request);



    @PostMapping("/v1/engineeringFundApplication/createOrUpdateEngineeringFundApplication")
    BaseResponse createOrUpdateEngineeringFundApplication(@RequestBody EngineeringFundApplicationDto request);
    @PostMapping("/v1/engineeringFundApplication/getEngineeringFundApplicationById")
    BaseResponse getEngineeringFundApplicationById(@RequestBody EngineeringFundApplicationRequest request);
    @PostMapping("/v1/engineeringFundApplication/getEngineeringFundApplicationList")
    BaseResponse getEngineeringFundApplicationList(@RequestBody EngineeringFundApplicationRequest request,@RequestHeader String token);
    @PostMapping("/v1/engineeringFundApplication/approvalData")
    BaseResponse approvalData(@RequestBody EngineeringFundApplicationRequest request);
    @PostMapping("/v1/engineeringFundApplication/getFundNumberInfo")
    BaseResponse getFundNumberInfo(@RequestBody EngineeringFundApplicationRequest request);
    @PostMapping("/v1/engineeringFundApplication/getBankInfo")
    BaseResponse getBankInfo(@RequestBody EngineeringFundApplicationRequest request);

    @PostMapping("/v1/engineeringFundApplication/sendData")
    BaseResponse sendData(@RequestBody EngineeringFundApplicationRequest request);


    @PostMapping("/v1/engineeringFundApplication/receiveCallback")
    BaseResponse receiveCallback(@RequestBody EFAResponse request);

    @PostMapping("/v1/engineeringFundApplication/reject")
    BaseResponse reject(@RequestBody EngineeringFundApplicationRejectRequest engineeringFundApplicationRejectRequest);


    @PostMapping("/v1/groupFundPlan/createOrUpdateGroupFundPlan")
    BaseResponse createOrUpdateGroupFundPlan(@RequestBody GroupFundPlanDto dto);
    @PostMapping("/v1/groupFundPlan/getGroupFundPlanList")
    BaseResponse getGroupFundPlanList(@RequestBody GroupFundPlanRequest request,@RequestHeader String token);
    @PostMapping("/v1/groupFundPlan/getGroupFundPlanById")
    BaseResponse getGroupFundPlanById(@RequestBody GroupFundPlanRequest request);
    @PostMapping("/v1/groupFundPlan/getFundInfo")
    BaseResponse getFundInfo(@RequestBody GroupFundPlanRequest request);
    @PostMapping("/v1/groupFundPlan/getFundPlanInfo")
    BaseResponse getFundPlanInfo(GroupFundPlanRequest request);
    @PostMapping("/v1/groupFundPlan/getContractFundInfo")
    BaseResponse getContractFundInfo(GroupFundPlanRequest request);

    @PostMapping("/v1/groupFundPlanSummary/createOrUpdateGroupFundPlanSummary")
    BaseResponse createOrUpdateGroupFundPlanSummary(@RequestBody GroupFundPlanSummaryDto request);
    @PostMapping("/v1/groupFundPlanSummary/getGroupFundPlanSummaryList")
    BaseResponse getGroupFundPlanSummaryList(@RequestBody GroupFundPlanSummaryRequest request,@RequestHeader String token);
    @PostMapping("/v1/groupFundPlanSummary/getGroupFundPlanSummaryById")
    BaseResponse getGroupFundPlanSummaryById(@RequestBody GroupFundPlanSummaryRequest request);
    @PostMapping("/v1/groupFundPlanSummary/approvalData")
    BaseResponse approvalData(@RequestBody GroupFundPlanSummaryRequest request);
    @PostMapping("/v1/groupFundPlanSummary/getGroupFundPlanSummaryInfo")
    BaseResponse getGroupFundPlanSummaryInfo(@RequestBody GroupFundPlanSummaryRequest request,@RequestHeader String token);
    @PostMapping("/v1/groupFundPlanSummary/rollbackData")
    BaseResponse rollbackData(@RequestBody GroupFundPlanSummaryRequest request);

    @PostMapping("/v1/payAmountInit/checkPayAmountInit")
    BaseResponse checkPayAmountInit(@RequestBody PayAmountInitRequest request,@RequestHeader String token);
    @PostMapping("/v1/payAmountInit/updatePayAmountInit")
    BaseResponse updatePayAmountInit(@RequestBody PayAmountInitRequest request,@RequestHeader String token);
    @PostMapping("/v1/payAmountInit/getPayAmountInitList")
    BaseResponse getPayAmountInitList(@RequestBody PayAmountInitRequest request,@RequestHeader String token);

    @PostMapping("/v1/claimIncomingFunds/createOrUpdateClaimIncomingFunds")
    BaseResponse createOrUpdateClaimIncomingFunds(@RequestBody ClaimIncomingFundsRequest request);

    @GetMapping("/v1/paymentReceipt/syncToClaimIncomingFunds")
    BaseResponse syncToClaimIncomingFunds(@RequestParam String id, @RequestHeader String token);

    @GetMapping("/v1/groupFundPlan/getProjectInfo")
    BaseResponse getProjectInfo(@RequestParam String pId);

    @PostMapping("/v1/groupFundPlan/getGroupFundPlanDetail")
    BaseResponse getGroupFundPlanDetail(@RequestBody GroupFundPlanRequest request);

    @PostMapping("/v1/groupFundPlanSummary/getCompanyProjectFundPlan")
    BaseResponse getCompanyProjectFundPlan(@RequestBody GroupFundPlanSummaryRequest request, @RequestHeader String token);

    @PostMapping("/v1/claimIncomingFunds/syncToPaymentReceipt")
    BaseResponse syncToPaymentReceipt(@RequestBody ClaimIncomingFundsRequest request);

    @PostMapping("/v1/claimIncomingFunds/getClaimIncomingFundsPage")
    BaseResponse getClaimIncomingFundsPage(@RequestBody ClaimIncomingFundsRequest request);

    @PostMapping("/v1/claimIncomingFunds/getClaimIncomingFundsByPid")
    BaseResponse getClaimIncomingFundsByPid(@RequestBody ClaimIncomingFundsRequest request);

    @GetMapping("/v1/groupFundPlanSummary/getCompanyGroupFundPlanSummaryById")
    BaseResponse getCompanyGroupFundPlanSummaryById(@RequestParam String id);

    @PostMapping("/v1/groupFundPlanSummary/getGroupProjectFundPlan")
    BaseResponse getGroupProjectFundPlan(@RequestBody GroupFundPlanSummaryRequest request);

    @GetMapping("/v1/groupFundPlanSummary/getBlocFundPlanSummaryById")
    BaseResponse getBlocFundPlanSummaryById(@RequestParam String id);

    @PostMapping("/v1/groupFundPlan/deleteGroupFundPlanById")
    BaseResponse deleteGroupFundPlanById(@RequestBody GroupFundPlanRequest request);

    @PostMapping("/v1/groupFundPlanSummary/getGroupFundPlanSummaryDetailByPid")
    BaseResponse getGroupFundPlanSummaryDetailByPid(@RequestBody GroupFundPlanSummaryDetailDto request);

    @PostMapping("/v1/groupFundPlan/refreshHistoryData")
    BaseResponse refreshHistoryData(@RequestBody GroupFundPlanDto dto);

    @PostMapping("/v1/groupFundPlanSummary/refreshHistoryData")
    BaseResponse refreshHistoryData2(@RequestBody GroupFundPlanSummaryDto request);

    @PostMapping("/v1/groupFundPlanSummary/deleteGroupFundPlanSummaryById")
    BaseResponse deleteGroupFundPlanSummaryById(@RequestBody GroupFundPlanSummaryRequest request);

    @PostMapping("/v1/groupFundPlan/returnInitiatorToTask")
    BaseResponse returnInitiatorToTask(@RequestBody ReturnInitiatorDto request, @RequestHeader String token);

    @PostMapping("/v1/groupFundPlan/existProject")
    BaseResponse existProject(@RequestBody GroupFundPlanRequest request);

    @PostMapping("/v1/groupFundPlanSummary/existProject")
    BaseResponse existProject2(@RequestBody GroupFundPlanSummaryRequest request);

    @GetMapping("/v1/claimIncomingFunds/getGroupFundsCumulative")
    BaseResponse getGroupFundsCumulative(@RequestParam String pId);
}
