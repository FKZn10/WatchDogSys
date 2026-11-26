package com.jpmsoft.projectProducer.controller.v1.evaluate.contract;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import com.jpmsoft.projectProducer.pojo.request.evaluate.BaseEvaluateRequest;
import com.jpmsoft.projectProducer.pojo.response.IndicatorDataResponse;
import com.jpmsoft.projectProducer.service.contract.IContractEvaluateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/dataEvaluate/ContractEvaluate")
@Slf4j
public class ContractEvaluateController {
    @Autowired
    private IContractEvaluateService contractEvaluateService;
    @PostMapping("/getContractScMoney")
    public IndicatorDataResponse getContractScMoney(@RequestBody BaseEvaluateRequest request){
        log.info("getContractScMoney：{}", JSON.toJSONString(request, JSONWriter.Feature.ReferenceDetection));
        return  contractEvaluateService.getContractScMoney(request);
    }

    @PostMapping("/getConPayTotalScMoney")
    public IndicatorDataResponse getConPayTotalScMoney(@RequestBody BaseEvaluateRequest request){
        log.info("getConPayTotalScMoney：{}", JSON.toJSONString(request, JSONWriter.Feature.ReferenceDetection));
        return  contractEvaluateService.getConPayTotalScMoney(request);
    }
}
