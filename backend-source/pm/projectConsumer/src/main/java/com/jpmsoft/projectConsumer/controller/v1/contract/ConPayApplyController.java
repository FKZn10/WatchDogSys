package com.jpmsoft.projectConsumer.controller.v1.contract;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.pojo.dto.contract.ConPayApplyDto;
import com.jpmsoft.projectConsumer.pojo.dto.contract.MarketInvestmentPaymentDto;
import com.jpmsoft.projectConsumer.feign.ConFeign;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "资金支付申请", value = "资金支付申请")
@RequestMapping("/v1/ConPayApply")
public class ConPayApplyController {
    @Autowired
    private ConFeign conFeign;

    @GetMapping("/createConPayApply")
    public BaseResponse createConPayApply() {
        log.info("createConPayApply");
        return conFeign.createConPayApply();
    }

    @GetMapping("/getConPayApplyById")
    public BaseResponse getConPayApplyById(@RequestParam String id) {
        log.info("getConPayApplyById");
        return conFeign.getConPayApplyById(id);
    }

    @PostMapping("/getConPayApplyList")
    public BaseResponse getConPayApplyList(@RequestBody ConPayApplyDto request) {
        log.info("getConPayApplyList");
        return conFeign.getConPayApplyList(request);
    }

    @GetMapping("/syncMarketInvestmentPayment")
    public BaseResponse syncMarketInvestmentPayment() {
        log.info("syncMarketInvestmentPayment");
        return conFeign.syncMarketInvestmentPayment();
    }

    @PostMapping("/getMarketInvestmentPaymentList")
    public BaseResponse getMarketInvestmentPaymentList(@RequestBody MarketInvestmentPaymentDto request) {
        log.info("getMarketInvestmentPaymentList");
        return conFeign.getMarketInvestmentPaymentList(request);
    }
}
