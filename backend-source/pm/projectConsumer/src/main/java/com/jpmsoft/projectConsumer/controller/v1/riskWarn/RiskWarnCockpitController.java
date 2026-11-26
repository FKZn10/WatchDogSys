package com.jpmsoft.projectConsumer.controller.v1.riskWarn;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.feign.BaseFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "（风险预警）年度风险统计", value = "(风险预警)年度风险统计")
@RequestMapping("/riskWarn/v1/riskWarnCockpit")
public class RiskWarnCockpitController {
    @Autowired
    private BaseFeign baseFeign;

    /**
     * 获取年度风险预警信息
     *
     * @return
     */
    @ApiOperation(value = "获取年度风险预警信息")
    @GetMapping("/getRiskWarnCountByYearAndMonthly")
    public BaseResponse getRiskWarnCountByYearAndMonthly(@RequestParam Integer year, @RequestParam Integer month, @RequestHeader String token) {
        log.info("getRiskWarnCountByYearAndMonthly");
        return baseFeign.getRiskWarnCountByYearAndMonthly(year, month, token);
    }
}
