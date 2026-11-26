package com.jpmsoft.projectProducer.controller.v1.evaluate.visaChange;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import com.jpmsoft.projectProducer.pojo.request.evaluate.BaseEvaluateRequest;
import com.jpmsoft.projectProducer.pojo.response.IndicatorDataResponse;
import com.jpmsoft.projectProducer.service.visaChange.IVisaChangeComplateRateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/dataEvaluate/VisaChangeEvaluate")
@Slf4j
public class VisaChangeEvaluateController {
    @Autowired
    private IVisaChangeComplateRateService visaChangeComplateRateService;
    @PostMapping("/getVisaChangeCompleteRate")
    public IndicatorDataResponse getVisaChangeCompleteRate(@RequestBody BaseEvaluateRequest request){
        log.info("getVisaChangeCompleteRate：{}", JSON.toJSONString(request, JSONWriter.Feature.ReferenceDetection));
        return  visaChangeComplateRateService.getVisaChangeCompleteRate(request);
    }

    @PostMapping("/getVisaChangePotentialMoneyRate")
    public IndicatorDataResponse getVisaChangePotentialMoneyRate(@RequestBody BaseEvaluateRequest request){
        log.info("getVisaChangePotentialMoneyRate：{}", JSON.toJSONString(request, JSONWriter.Feature.ReferenceDetection));
        return  visaChangeComplateRateService.getVisaChangePotentialMoneyRate(request);
    }

    @PostMapping("/getVisaChange")
    public IndicatorDataResponse getVisaChange(@RequestBody BaseEvaluateRequest request){
        log.info("getVisaChange：{}", JSON.toJSONString(request, JSONWriter.Feature.ReferenceDetection));
        return  visaChangeComplateRateService.getVisaChange(request);
    }
    @PostMapping("/getVisaChangeComplete")
    public IndicatorDataResponse getVisaChangeComplete(@RequestBody BaseEvaluateRequest request){
        log.info("getVisaChangeComplete：{}", JSON.toJSONString(request, JSONWriter.Feature.ReferenceDetection));
        return  visaChangeComplateRateService.getVisaChangeComplete(request);
    }

    @PostMapping("/getVisaChangePotentialMoney")
    public IndicatorDataResponse getVisaChangePotentialMoney(@RequestBody BaseEvaluateRequest request){
        log.info("getVisaChangePotentialMoney：{}", JSON.toJSONString(request, JSONWriter.Feature.ReferenceDetection));
        return  visaChangeComplateRateService.getVisaChangePotentialMoney(request);
    }
}
