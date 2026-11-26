package com.jpmsoft.projectProducer.controller.v1.evaluate.investment;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import com.jpmsoft.projectProducer.pojo.request.evaluate.investment.InvPlanEvaluateRequest;
import com.jpmsoft.projectProducer.pojo.response.IndicatorDataResponse;
import com.jpmsoft.projectProducer.service.investment.IInvPlanEvaluateService;
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
    private IInvPlanEvaluateService invPlanEvaluateService;

    @PostMapping("/getInvPlanAnnualInvestment")
    public IndicatorDataResponse getInvPlanAnnualInvestment(@RequestBody InvPlanEvaluateRequest request){
        log.info("getInvPlanAnnualInvestment：{}", JSON.toJSONString(request, JSONWriter.Feature.ReferenceDetection));
        return  invPlanEvaluateService.getInvPlanAnnualInvestment(request);
    }

    @PostMapping("/getInvPlanMonthlyInvestment")
    public IndicatorDataResponse getInvPlanMonthlyInvestment(@RequestBody InvPlanEvaluateRequest request){
        log.info("getInvPlanMonthlyInvestment：{}", JSON.toJSONString(request, JSONWriter.Feature.ReferenceDetection));
        return  invPlanEvaluateService.getInvPlanMonthlyInvestment(request);
    }
    @PostMapping("/getInvPlanMonthlyPlanInvestment")
    public IndicatorDataResponse getInvPlanMonthlyPlanInvestment(@RequestBody InvPlanEvaluateRequest request){
        log.info("getInvPlanMonthlyPlanInvestment：{}", JSON.toJSONString(request, JSONWriter.Feature.ReferenceDetection));
        return  invPlanEvaluateService.getInvPlanMonthlyPlanInvestment(request);
    }
     @PostMapping("/getInvPlanCumulativeInvestment")
    public IndicatorDataResponse getInvPlanCumulativeInvestment(@RequestBody InvPlanEvaluateRequest request){
        log.info("getInvPlanCumulativeInvestment：{}", JSON.toJSONString(request, JSONWriter.Feature.ReferenceDetection));
        return  invPlanEvaluateService.getInvPlanCumulativeInvestment(request);
    }


    @PostMapping("/getInvPlanAnnualCompletion")
    public IndicatorDataResponse getInvPlanAnnualCompletion(@RequestBody InvPlanEvaluateRequest request){
        log.info("getInvPlanAnnualMonthCompletion：{}", JSON.toJSONString(request, JSONWriter.Feature.ReferenceDetection));
        return  invPlanEvaluateService.getInvPlanAnnualCompletion(request);
    }

    @PostMapping("/getInvPlanMonthCompletion")
    public IndicatorDataResponse getInvPlanMonthCompletion(@RequestBody InvPlanEvaluateRequest request){
        log.info("getInvPlanMonthCompletion：{}", JSON.toJSONString(request, JSONWriter.Feature.ReferenceDetection));
        return  invPlanEvaluateService.getInvPlanMonthCompletion(request);
    }
}
