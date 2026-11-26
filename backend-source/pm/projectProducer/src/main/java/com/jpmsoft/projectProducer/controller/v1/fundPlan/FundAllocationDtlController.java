package com.jpmsoft.projectProducer.controller.v1.fundPlan;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.service.fundPlan.IFundAllocationDtlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/9/24 19:14
 */
@Slf4j
@RestController
@RequestMapping("/v1/fundAllocationDtl")
public class FundAllocationDtlController {

    @Resource
    private IFundAllocationDtlService fundAllocationDtlService;


    @GetMapping("/getFundAllocationDtlList")
    public BaseResponse getFundAllocationDtlList(@RequestParam String conId)
    {
        return fundAllocationDtlService.getFundAllocationDtlList(conId);
    }

}
