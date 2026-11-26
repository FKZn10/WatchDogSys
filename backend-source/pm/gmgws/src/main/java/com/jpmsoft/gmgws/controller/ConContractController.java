package com.jpmsoft.gmgws.controller;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Contract.ConContractRequest;
import com.jpmsoft.gmgws.service.ConContractService;
import com.jpmsoft.gmgws.service.ContractStatisticsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: 李文涛 aaron
 * @date: 2025/1/14 14:37
 */

@RestController
@RequestMapping("/contract")
@Api(tags = "合同", value = "借还款")
public class ConContractController {

    @Resource
    private ConContractService conContractService;

    @Resource
    private ContractStatisticsService contractStatisticsService;


    @ApiOperation(value = "查询可以解除协议的合同")
    @PostMapping("/selectConContract")
    public  BaseResponse selectConContract(@RequestBody ConContractRequest request){
            return conContractService.selectConContract(request);
    }

    /**
     * 获取合同统计的列表
     */
    @ApiOperation(value = "获取合同统计的列表")
    @PostMapping("/getContractStatisticsList")
    public BaseResponse getContractStatisticsList(@RequestBody ConContractRequest request){
        return contractStatisticsService.getContractStatisticsList(request);
    }
}
