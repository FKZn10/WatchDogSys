package com.jpmsoft.projectConsumer.controller.v1.investment;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.InvestmentEstimateBreakdownChangeModel;
import com.jpmsoft.projectConsumer.feign.InvFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
    投资估算分解变更
 */
@Slf4j
@RestController
@RequestMapping("/v1/InvestmentEstimateBreakdownChange")
public class InvestmentEstimateBreakdownChangeController {
    @Autowired
    private InvFeign invFeign;

    @PostMapping("/createOrUpdateInvestmentEstimateBreakdownChange")
    public BaseResponse createOrUpdateInvestmentEstimateBreakdownChange(@RequestBody InvestmentEstimateBreakdownChangeModel request) {
        log.info("createOrUpdateInvestmentEstimateBreakdownChange");
        return invFeign.createOrUpdateInvestmentEstimateBreakdownChange(request);
    }

    @PostMapping("/getInvestmentEstimateBreakdownChangeList")
    public BaseResponse getInvestmentEstimateBreakdownChangeList(@RequestBody InvestmentEstimateBreakdownChangeModel request) {
        log.info("getInvestmentEstimateBreakdownChangeList");
        return invFeign.getInvestmentEstimateBreakdownChangeList(request);
    }

    @GetMapping("/getInvestmentEstimateBreakdownChangeById")
    public BaseResponse getInvestmentEstimateBreakdownChangeById(@RequestParam String id) {
        log.info("getInvestmentEstimateBreakdownChangeById");
        return invFeign.getInvestmentEstimateBreakdownChangeById(id);
    }

}
