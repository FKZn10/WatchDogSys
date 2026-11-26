package com.jpmsoft.projectConsumer.controller.v1.evaluate.contract;

import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.projectConsumer.feign.EvaluateFeign;
import com.jpmsoft.projectConsumer.request.BaseEvaluateRequest;
import com.jpmsoft.projectConsumer.request.investment.InvPlanEvaluateRequest;
import com.jpmsoft.projectConsumer.response.IndicatorDataResponse;
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
    private EvaluateFeign evaluateFeign;
    @PostMapping("/getContractScMoney")
    public IndicatorDataResponse getContractScMoney(@RequestBody BaseEvaluateRequest request){
        log.info("getContractScMoney：{}", JacksonUtils.toJSONString(request));
        return evaluateFeign.getContractScMoney(request);
    }
    @PostMapping("/getConPayTotalScMoney")
    public IndicatorDataResponse getConPayTotalScMoney(@RequestBody BaseEvaluateRequest request){
        log.info("getConPayTotalScMoney：{}", JacksonUtils.toJSONString(request));
        return  evaluateFeign.getConPayTotalScMoney(request);
    }
}