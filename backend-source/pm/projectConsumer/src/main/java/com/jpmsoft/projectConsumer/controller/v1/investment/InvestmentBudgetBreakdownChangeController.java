package com.jpmsoft.projectConsumer.controller.v1.investment;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.InvestmentBudgetBreakdownChangeModel;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.projectConsumer.feign.InvFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
    投资概算分解变更
 */
@Slf4j
@RestController
@RequestMapping("/v1/InvestmentBudgetBreakdownChange")
public class InvestmentBudgetBreakdownChangeController {
    @Autowired
    private InvFeign invFeign;

    @PostMapping("/createOrUpdateInvestmentBudgetBreakdownChange")
    public BaseResponse createOrUpdateInvestmentBudgetBreakdownChange(@RequestBody InvestmentBudgetBreakdownChangeModel request) {
        log.info("createOrUpdateInvestmentBudgetBreakdownChange");
        log.info("InvestmentBudgetBreakdownChangeModel:{}", JacksonUtils.toJSONString(request));
        return invFeign.createOrUpdateInvestmentBudgetBreakdownChange(request);
    }

    @PostMapping("/getInvestmentBudgetBreakdownChangeList")
    public BaseResponse getInvestmentBudgetBreakdownChangeList(@RequestBody InvestmentBudgetBreakdownChangeModel request) {
        log.info("getInvestmentBudgetBreakdownChangeList");
        log.info("InvestmentBudgetBreakdownChangeModel:{}", JacksonUtils.toJSONString(request));
        return invFeign.getInvestmentBudgetBreakdownChangeList(request);
    }


    @GetMapping("/getInvestmentBudgetBreakdownChangeById")
    public BaseResponse getInvestmentBudgetBreakdownChangeById(@RequestParam String id) {
        log.info("getInvestmentBudgetBreakdownChangeById");
        return invFeign.getInvestmentBudgetBreakdownChangeById(id);
    }

}
