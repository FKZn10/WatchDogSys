package com.jpmsoft.projectProducer.controller.v1.investment;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.InvestmentBudgetBreakdownChangeModel;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.projectProducer.service.investment.IInvestmentBudgetBreakdownChangeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author yb.Sun
 * @date 2024/11/1 16:20
 */
@Slf4j
@RestController
@RequestMapping("/v1/InvestmentBudgetBreakdownChange")
public class InvestmentBudgetBreakdownChangeController {
    @Resource
    private IInvestmentBudgetBreakdownChangeService investmentBudgetBreakdownService;

    @PostMapping("/createOrUpdateInvestmentBudgetBreakdownChange")
    public BaseResponse createOrUpdateInvestmentBudgetBreakdownChange(@RequestBody InvestmentBudgetBreakdownChangeModel request) {
        log.info("createOrUpdateInvestmentBudgetBreakdownChange");
        log.info("InvestmentBudgetBreakdownChangeModel:{}", JacksonUtils.toJSONString(request));
        return investmentBudgetBreakdownService.createOrUpdateInvestmentBudgetBreakdownChange(request);
    }
    @PostMapping("/getInvestmentBudgetBreakdownChangeList")
    public BaseResponse getInvestmentBudgetBreakdownChangeList(@RequestBody InvestmentBudgetBreakdownChangeModel request) {
        log.info("getInvestmentBudgetBreakdownChangeList");
        log.info("InvestmentBudgetBreakdownChangeModel:{}", JacksonUtils.toJSONString(request));
        return investmentBudgetBreakdownService.getInvestmentBudgetBreakdownChangeList(request);
    }


    @GetMapping("/getInvestmentBudgetBreakdownChangeById")
    public BaseResponse getInvestmentBudgetBreakdownChangeById(@RequestParam String id) {
        log.info("getInvestmentBudgetBreakdownChangeById");
        return investmentBudgetBreakdownService.getInvestmentBudgetBreakdownChangeById(id);
    }

}
