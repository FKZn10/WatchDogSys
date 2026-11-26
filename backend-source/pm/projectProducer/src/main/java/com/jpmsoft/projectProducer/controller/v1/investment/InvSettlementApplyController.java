package com.jpmsoft.projectProducer.controller.v1.investment;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.InvestmentControl.InvSettlementApplyRequest;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.investment.IInvSettlementApplyService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/invSettlementApply")
public class InvSettlementApplyController {

    @Resource
    private IInvSettlementApplyService invSettlementApplyService;
    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createOrUpdateInvSettlementApply")
    public BaseResponse createOrUpdateInvSettlementApply(@RequestBody InvSettlementApplyRequest request) {
        log.info("createOrUpdateInvSettlementApply");
        return invSettlementApplyService.createOrUpdateInvSettlementApply(request);
    }


    @PostMapping("/updateInvSettlementApplyOnlyInfo")
    public BaseResponse updateInvSettlementApplyOnlyInfo(@RequestBody InvSettlementApplyRequest request) {
        log.info("updateInvSettlementApplyOnlyInfo");
        return invSettlementApplyService.updateInvSettlementApplyOnlyInfo(request);
    }

    @TenantId
    @PostMapping("/getInvSettlementApplyList")
    public BaseResponse getInvSettlementApplyList(@RequestBody InvSettlementApplyRequest request, @RequestHeader String token) {
        log.info("getInvSettlementApplyList");
        return invSettlementApplyService.getInvSettlementApplyList(request, redisUtils.getUid(token));
    }

    @GetMapping("/getInvSettlementApplyBysaId")
    public BaseResponse getInvSettlementApplyBysaId(@RequestParam String saId) {
        log.info("getInvSettlementApplyBysaId");
        return invSettlementApplyService.getInvSettlementApplyBysaId(saId);
    }

    @GetMapping("/deleteInvSettlementApplyBysaId")
    public BaseResponse deleteInvSettlementApplyBysaId(@RequestParam String saId) {
        log.info("deleteInvSettlementApplyBysaId");
        return invSettlementApplyService.deleteInvSettlementApplyBysaId(saId);
    }

    @GetMapping("/getLastApprovalInvSettlementApplyByConId")
    public BaseResponse getLastApprovalInvSettlementApplyByConId(@RequestParam String conId) {
        log.info("getLastApprovalInvSettlementApplyByConId");
        return invSettlementApplyService.getLastApprovalInvSettlementApplyByConId(conId);
    }

}
