package com.jpmsoft.projectConsumer.controller.v1.funPlan;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.feign.FundPlanFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 太仓资金拨付
 *
 * @Author: 李文涛 aaron
 * @date: 2024/9/24 19:14
 */
@Slf4j
@RestController
@Api(tags = "太仓资金拨付", value = "太仓资金拨付")
@RequestMapping("/v1/fundAllocation")
public class FundAllocationController {

    @Resource
    private FundPlanFeign fundPlanFeign;

    @ApiOperation(value = "合同ID查询资金拨付+拨付子表")
    @GetMapping("/getFundAllocationByDtlCon")
    public BaseResponse getFundAllocationByDtlCon(@RequestParam String conId) {
        return fundPlanFeign.getFundAllocationByDtlCon(conId);
    }

}
