package com.jpmsoft.projectProducer.controller.v1.contract;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.contract.ConPayApplyDto;
import com.jpmsoft.projectProducer.pojo.dto.contract.MarketInvestmentPaymentDto;
import com.jpmsoft.projectProducer.service.contract.IConPayApplyService;
import com.jpmsoft.projectProducer.service.contract.IMarketInvestmentPaymentService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/ConPayApply")
public class ConPayApplyController {
    @Resource
    private IConPayApplyService conPayApplyService;
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private IMarketInvestmentPaymentService marketInvestmentPaymentService;

    @GetMapping("/createConPayApply")
    public BaseResponse createConPayApply(@RequestHeader String token) {
        log.info("createConPayApply");
        return conPayApplyService.createConPayApply(redisUtils.getTeId(token));
    }

    @GetMapping("/getConPayApplyById")
    public BaseResponse getConPayApplyById(@RequestParam String id) {
        log.info("getConPayApplyById");
        return conPayApplyService.getConPayApplyById(id);
    }

    @PostMapping("/getConPayApplyList")
    public BaseResponse getConPayApplyList(@RequestBody ConPayApplyDto request) {
        log.info("getConPayApplyList");
        return conPayApplyService.getConPayApplyList(request);
    }

    /**
     * 同步接口，根据合同支付表同步市投项目支付申请
     */
    @GetMapping("/syncMarketInvestmentPayment")
    public BaseResponse syncMarketInvestmentPayment() {
        log.info("syncMarketInvestmentPayment");
        return marketInvestmentPaymentService.syncMarketInvestmentPayment();
    }


    @PostMapping("/getMarketInvestmentPaymentList")
    public BaseResponse getMarketInvestmentPaymentList(@RequestBody MarketInvestmentPaymentDto request) {
        log.info("getMarketInvestmentPaymentList");
        return marketInvestmentPaymentService.getMarketInvestmentPaymentList(request);
    }
}
