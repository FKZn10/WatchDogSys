package com.jpmsoft.userProducer.controller.v1;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.service.IRiskWarnTableFilterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@Slf4j
@RestController
@Api(tags = "风险预警指标（相关表字段条件）", value = "风险预警指标（相关表字段条件）")
@RequestMapping("/v1/riskWarnTableFilter")
public class RiskWarnTableFilterController {
    @Resource
    private IRiskWarnTableFilterService riskWarnTableFilterService;

    @ApiOperation(value = "id获取风险指标表字段条件信息")
    @GetMapping("/getRiskWarnTableFilter")
    public BaseResponse getRiskWarnTableFilter(@RequestParam String rwiId) {
        log.info("getRiskWarnTableFilter");
        return riskWarnTableFilterService.getRiskWarnTableFilter(rwiId);
    }
}
