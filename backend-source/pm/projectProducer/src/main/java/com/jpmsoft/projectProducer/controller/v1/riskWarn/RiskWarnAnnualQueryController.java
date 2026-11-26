package com.jpmsoft.projectProducer.controller.v1.riskWarn;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.RiskWarn.RiskWarQueryRequest;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.riskWarn.IRiskWarnAnnualQueryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("riskWarn/v1/riskWarnAnnualQuery")
public class RiskWarnAnnualQueryController {
    @Resource
    private IRiskWarnAnnualQueryService riskWarnAnnualQueryService;

    @TenantId
    @PostMapping("/getRiskWarnAnnualQueryCount")
    public BaseResponse getRiskWarnAnnualQueryCount(@RequestBody RiskWarQueryRequest request, @RequestHeader String token) {
        log.info("getRiskWarnAnnualQueryCount");
        return riskWarnAnnualQueryService.getRiskWarnAnnualQueryCount(request);
    }
}
