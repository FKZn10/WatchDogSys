package com.jpmsoft.projectProducer.controller.v1.riskWarn;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.RiskWarn.RiskWarnIndexRequest;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.riskWarn.IKeyIndicatorQueryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("riskWarn/v1/keyIndicatorQuery")
public class KeyIndicatorQueryController {
    @Resource
    private IKeyIndicatorQueryService keyIndicatorQueryService;


    @TenantId
    @PostMapping("/getKeyIndicatorQuery")
    public BaseResponse getKeyIndicatorQuery(@RequestBody RiskWarnIndexRequest request, @RequestHeader String token) {
        log.info("getKeyIndicatorQuery");
        return keyIndicatorQueryService.getKeyIndicatorQuery(request);
    }
}
