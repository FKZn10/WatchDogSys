package com.jpmsoft.userProducer.controller.v1;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.service.IRiskWarnTableMapService;
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
@Api(tags = "风险预警指标（相关表映射）", value = "风险预警指标（相关表映射）")
@RequestMapping("/v1/riskWarnTableMap")
public class RiskWarnTableMapController {
    @Resource
    private IRiskWarnTableMapService riskWarnTableMapService;

    @ApiOperation(value = "id获取风险指标映射信息")
    @GetMapping("/getRiskWarnTableMap")
    public BaseResponse getRiskWarnTableMap(@RequestParam String rwiId) {
        log.info("getRiskWarnTableMap");
        return riskWarnTableMapService.getRiskWarnTableMap(rwiId);
    }
}
