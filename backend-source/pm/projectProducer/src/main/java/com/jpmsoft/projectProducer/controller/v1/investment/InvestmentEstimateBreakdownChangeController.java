package com.jpmsoft.projectProducer.controller.v1.investment;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.InvestmentEstimateBreakdownChangeModel;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.projectProducer.service.investment.IInvestmentEstimateBreakdownChangeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 */
@Slf4j
@RestController
@RequestMapping("/v1/InvestmentEstimateBreakdownChange")
public class InvestmentEstimateBreakdownChangeController {
    @Resource
    private IInvestmentEstimateBreakdownChangeService InvestmentEstimateBreakdownChangeService;

    @PostMapping("/createOrUpdateInvestmentEstimateBreakdownChange")
    public BaseResponse createOrUpdateInvestmentEstimateBreakdownChange(@RequestBody InvestmentEstimateBreakdownChangeModel request) {
        log.info("createOrUpdateInvestmentEstimateBreakdownChange");
        log.info("InvestmentEstimateBreakdownChangeModel:{}", JacksonUtils.toJSONString(request));
        return InvestmentEstimateBreakdownChangeService.createOrUpdateInvestmentEstimateBreakdownChange(request);
    }

    @PostMapping("/getInvestmentEstimateBreakdownChangeList")
    public BaseResponse getInvestmentEstimateBreakdownChangeList(@RequestBody InvestmentEstimateBreakdownChangeModel request) {
        log.info("getInvestmentEstimateBreakdownChangeList");
        return InvestmentEstimateBreakdownChangeService.getInvestmentEstimateBreakdownChangeList(request);
    }


    @GetMapping("/getInvestmentEstimateBreakdownChangeById")
    public BaseResponse getInvestmentEstimateBreakdownChangeById(@RequestParam String id) {
        log.info("getInvestmentEstimateBreakdownChangeById");
        return InvestmentEstimateBreakdownChangeService.getInvestmentEstimateBreakdownChangeById(id);
    }

}
