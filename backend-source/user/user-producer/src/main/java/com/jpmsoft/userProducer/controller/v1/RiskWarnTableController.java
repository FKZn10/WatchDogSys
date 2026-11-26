package com.jpmsoft.userProducer.controller.v1;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.service.IRiskWarnTableService;
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
@Api(tags = "风险预警指标（相关表记录）", value = "风险预警指标（相关表记录）")
@RequestMapping("/v1/riskWarnTable")
public class RiskWarnTableController {
    @Resource
    private IRiskWarnTableService riskWarnTableService;

    @ApiOperation(value = "id获取关联表信息")
    @GetMapping("/getRiskWarnTableList")
    public BaseResponse getRiskWarnTableList(@RequestParam String rwiId) {
        log.info("getRiskWarnTableList");
        return riskWarnTableService.getRiskWarnTableList(rwiId);
    }
}
