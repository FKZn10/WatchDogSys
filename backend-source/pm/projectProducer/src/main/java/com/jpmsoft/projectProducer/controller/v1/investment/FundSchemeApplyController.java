package com.jpmsoft.projectProducer.controller.v1.investment;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.FundSchemeApplyModel;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.investment.IFundSchemeApplyService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/fsApply")
public class FundSchemeApplyController {

    @Resource
    private IFundSchemeApplyService fundSchemeApplyService;
    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createOrUpdateFundSchemeApply")
    public BaseResponse createOrUpdateFundSchemeApply(@RequestBody FundSchemeApplyModel request) {
        log.info("createOrUpdateFundSchemeApply");
        return fundSchemeApplyService.createOrUpdateFundSchemeApply(request);
    }

    @TenantId
    @PostMapping("/getFundSchemeApplyList")
    public BaseResponse getFundSchemeApplyList(@RequestBody FundSchemeApplyModel request, @RequestHeader String token) {
        log.info("getFundSchemeApplyList");
        return fundSchemeApplyService.getFundSchemeApplyList(request, redisUtils.getUid(token));
    }

    @GetMapping("/getFundSchemeApplyById")
    public BaseResponse getFundSchemeApplyById(@RequestParam String fsaId) {
        log.info("getFundSchemeApplyById");
        return fundSchemeApplyService.getFundSchemeApplyById(fsaId);
    }

    @GetMapping("/getFundSchemeApplyAmtSumByPid")
    public BaseResponse getFundSchemeApplyAmtSumByPid(@RequestParam String pId) {
        log.info("getFundSchemeApplyAmtSumByPid");
        return fundSchemeApplyService.getFundSchemeApplyAmtSumByPid(pId);
    }

    @GetMapping("/getFundSchemeApplyDtlAmtSumByConId")
    public BaseResponse getFundSchemeApplyDtlAmtSumByConId(@RequestParam String conId) {
        log.info("getFundSchemeApplyDtlAmtSumByConId");
        return fundSchemeApplyService.getFundSchemeApplyDtlAmtSumByConId(conId);
    }
}
