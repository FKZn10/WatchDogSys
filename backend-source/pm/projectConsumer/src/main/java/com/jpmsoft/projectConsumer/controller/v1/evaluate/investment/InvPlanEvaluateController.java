package com.jpmsoft.projectConsumer.controller.v1.evaluate.investment;

import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.projectConsumer.feign.EvaluateFeign;
import com.jpmsoft.projectConsumer.request.investment.InvPlanEvaluateRequest;
import com.jpmsoft.projectConsumer.response.IndicatorDataResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/dataEvaluate/InvPlanEvaluate")
@Slf4j
public class InvPlanEvaluateController {
    @Autowired
    private EvaluateFeign evaluateFeign;
    @PostMapping("/getInvPlanAnnualInvestment")
    public IndicatorDataResponse getInvPlanAnnualInvestment(@RequestBody InvPlanEvaluateRequest request){
        log.info("getInvPlanAnnualInvestment：{}", JacksonUtils.toJSONString(request));
        return  evaluateFeign.getInvPlanAnnualInvestment(request);
    }

    @PostMapping("/getInvPlanMonthlyInvestment")
    public IndicatorDataResponse getInvPlanMonthlyInvestment(@RequestBody InvPlanEvaluateRequest request){
        log.info("getInvPlanMonthlyInvestment：{}", JacksonUtils.toJSONString(request));
        return  evaluateFeign.getInvPlanMonthlyInvestment(request);
    }
    @PostMapping("/getInvPlanMonthlyPlanInvestment")
    public IndicatorDataResponse getInvPlanMonthlyPlanInvestment(@RequestBody InvPlanEvaluateRequest request){
        log.info("getInvPlanMonthlyPlanInvestment：{}", JacksonUtils.toJSONString(request));
        return  evaluateFeign.getInvPlanMonthlyPlanInvestment(request);
    }
     @PostMapping("/getInvPlanCumulativeInvestment")
    public IndicatorDataResponse getInvPlanCumulativeInvestment(@RequestBody InvPlanEvaluateRequest request){
        log.info("getInvPlanCumulativeInvestment：{}", JacksonUtils.toJSONString(request));
        return  evaluateFeign.getInvPlanCumulativeInvestment(request);
    }


    @PostMapping("/getInvPlanAnnualCompletion")
    public IndicatorDataResponse getInvPlanAnnualCompletion(@RequestBody InvPlanEvaluateRequest request){
        log.info("getInvPlanAnnualMonthCompletion：{}", JacksonUtils.toJSONString(request));
        return  evaluateFeign.getInvPlanAnnualCompletion(request);
    }

    @PostMapping("/getInvPlanMonthCompletion")
    public IndicatorDataResponse getInvPlanMonthCompletion(@RequestBody InvPlanEvaluateRequest request){
        log.info("getInvPlanMonthCompletion：{}", JacksonUtils.toJSONString(request));
        return  evaluateFeign.getInvPlanMonthCompletion(request);
    }
}