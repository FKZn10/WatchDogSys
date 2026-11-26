package com.jpmsoft.projectProducer.controller.v1.riskWarn;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.service.riskWarn.IRiskWarnCockpitService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("riskWarn/v1/riskWarnCockpit")
public class RiskWarnCockpitController {
    @Resource
    private IRiskWarnCockpitService riskWarnCockpitService;
    @Resource
    private RedisUtils redisUtils;

    @GetMapping("/getRiskWarnCountByYearAndMonthly")
    public BaseResponse getRiskWarnCountByYearAndMonthly(@RequestParam Integer year, @RequestParam Integer month, @RequestHeader String token) {
        log.info("getRiskWarnCountByYearAndMonthly");
        return riskWarnCockpitService.getRiskWarnCountByYearAndMonthly(year, month,redisUtils.getTeId(token));
    }
}
