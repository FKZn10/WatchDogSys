package com.jpmsoft.projectConsumer.controller.v1.riskWarn;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.RiskWarn.RiskWarQueryRequest;
import com.jpmsoft.projectConsumer.feign.BaseFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "（风险预警）年度风险预警信息", value = "（风险预警）年度风险预警信息")
@RequestMapping("/riskWarn/v1/riskWarnAnnualQuery")
public class RiskWarnAnnualQueryController {
    @Autowired
    private BaseFeign baseFeign;

    /**
     * 获取年度风险预警信息
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "获取年度风险预警信息")
    @PostMapping("/getRiskWarnAnnualQueryCount")
    public BaseResponse getRiskWarnAnnualQueryCount(@RequestBody RiskWarQueryRequest request, @RequestHeader String token) {
        log.info("getRiskWarnAnnualQueryCount");
        return baseFeign.getRiskWarnAnnualQueryCount(request, token);
    }
}
