package com.jpmsoft.projectProducer.controller.v1.investment;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.InvestmentControl.InvFinalAccountsApplyRequest;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.investment.IInvFinalAccountsApplyService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/invFinal")
public class InvFinalAccountsApplyController {

    @Resource
    private IInvFinalAccountsApplyService invFinalAccountsApplyService;
    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createOrUpdateInvFinalAccountsApply")
    public BaseResponse createOrUpdateInvFinalAccountsApply(@RequestBody InvFinalAccountsApplyRequest request) {
        log.info("createOrUpdateInvFinalAccountsApply");
        return invFinalAccountsApplyService.createOrUpdateInvFinalAccountsApply(request);
    }

    @TenantId
    @PostMapping("/getInvFinalAccountsApplyList")
    public BaseResponse getInvFinalAccountsApplyList(@RequestBody InvFinalAccountsApplyRequest request, @RequestHeader String token) {
        log.info("getInvFinalAccountsApplyList");
        return invFinalAccountsApplyService.getInvFinalAccountsApplyList(request, redisUtils.getUid(token));
    }

    @GetMapping("/getInvFinalAccountsApplyByfaId")
    public BaseResponse getInvFinalAccountsApplyByfaId(@RequestParam String faId) {
        log.info("getInvFinalAccountsApplyByfaId");
        return invFinalAccountsApplyService.getInvFinalAccountsApplyByfaId(faId);
    }

    @GetMapping("/getInvFinalAccountsApplyAndDtlByPid")
    public BaseResponse getInvFinalAccountsApplyAndDtlByPid(@RequestParam String pId) {
        log.info("getInvFinalAccountsApplyAndDtlByPid");
        return invFinalAccountsApplyService.getInvFinalAccountsApplyAndDtlByPid(pId);
    }

    @GetMapping("/deleteInvFinalAccountsApplyByfaId")
    public BaseResponse deleteInvFinalAccountsApplyByfaId(@RequestParam String faId) {
        log.info("deleteInvFinalAccountsApplyByfaId");
        return invFinalAccountsApplyService.deleteInvFinalAccountsApplyByfaId(faId);
    }

    @GetMapping("/havingFinalApplyWithPid")
    public BaseResponse havingFinalApplyWithPid(@RequestParam String pId) {
        log.info("havingFinalApplyWithPid");
        return BaseResponse.ok(invFinalAccountsApplyService.havingFinalApplyWithPid(pId));
    }

}
