package com.jpmsoft.projectConsumer.controller.v1.evaluate.visaChange;

import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.projectConsumer.feign.EvaluateFeign;
import com.jpmsoft.projectConsumer.request.BaseEvaluateRequest;
import com.jpmsoft.projectConsumer.response.IndicatorDataResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/dataEvaluate/VisaChangeEvaluate")
@Slf4j
public class VisaChangeEvaluteController {
    @Autowired
    private EvaluateFeign evaluateFeign;
    @PostMapping("/getVisaChangeCompleteRate")
    public IndicatorDataResponse getVisaChangeCompleteRate(@RequestBody BaseEvaluateRequest request){
        log.info("getVisaChangeCompleteRate：{}", JacksonUtils.toJSONString(request));
        return evaluateFeign.getVisaChangeCompleteRate(request);
    }
    @PostMapping("/getVisaChangePotentialMoneyRate")
    public IndicatorDataResponse getVisaChangePotentialMoneyRate(@RequestBody BaseEvaluateRequest request){
        log.info("getVisaChangePotentialMoneyRate：{}", JacksonUtils.toJSONString(request));
        return  evaluateFeign.getVisaChangePotentialMoneyRate(request);
    }
    @PostMapping("/getVisaChange")
    public IndicatorDataResponse getVisaChange(@RequestBody BaseEvaluateRequest request){
        log.info("getVisaChange：{}", JacksonUtils.toJSONString(request));
        return evaluateFeign.getVisaChange(request);
    }
    @PostMapping("/getVisaChangeComplete")
    public IndicatorDataResponse getVisaChangeComplete(@RequestBody BaseEvaluateRequest request){
        log.info("getVisaChangeComplete：{}", JacksonUtils.toJSONString(request));
        return evaluateFeign.getVisaChangeComplete(request);
    }
    @PostMapping("/getVisaChangePotentialMoney")
    public IndicatorDataResponse getVisaChangePotentialMoney(@RequestBody BaseEvaluateRequest request){
        log.info("getVisaChangePotentialMoney：{}", JacksonUtils.toJSONString(request));
        return  evaluateFeign.getVisaChangePotentialMoney(request);
    }
}