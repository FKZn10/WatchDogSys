package com.jpmsoft.projectConsumer.controller.v1.funPlan;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.pojo.dto.fundPlan.FundAppropriationDtlDto;
import com.jpmsoft.projectConsumer.pojo.dto.fundPlan.FundAppropriationDto;
import com.jpmsoft.projectConsumer.feign.FundPlanFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: 李文涛 aaron
 * @date: 2022/8/17 10:36
 */
@Slf4j
@RestController
@Api(tags = "资金拨付申请", value = "资金拨付申请")
@RequestMapping("/v1/fundAppropriation")
public class FundAppropriationController {

    @Autowired
    private FundPlanFeign fundPlanFeign;

    @ApiOperation(value = "新增或修改资金拨付申请")
    @PostMapping("/createOrUpdateFundAppropriation")
    public BaseResponse createOrUpdateFundAppropriation(@RequestBody FundAppropriationDto request) {
        log.info("createOrUpdateFundAppropriation");
        return fundPlanFeign.createOrUpdateFundAppropriation(request);
    }

    @ApiOperation(value = "分页查询资金拨付申请")
    @PostMapping("/getFundAppropriationList")
    public BaseResponse getFundAppropriationList(@RequestBody FundAppropriationDto request, @RequestHeader String token) {
        log.info("getFundAppropriationList");
        return fundPlanFeign.getFundAppropriationList(request, token);
    }

    @ApiOperation(value = "ID查询资金拨付申请")
    @GetMapping("/getFundAppropriationById")
    public BaseResponse getFundAppropriationById(@RequestParam String id) {
        log.info("getFundAppropriationById");
        return fundPlanFeign.getFundAppropriationById(id);
    }

    @ApiOperation(value = "获取项目前期已完成投资=本期完成投资合计")
    @GetMapping("/getProCompletedInvSum")
    public BaseResponse getProCompletedInvSum(@RequestParam String pId) {
        log.info("getProCompletedInvSum");
        return fundPlanFeign.getProCompletedInvSum(pId);
    }

    @ApiOperation(value = "统计项目拨付本期拨付资金合计")
    @GetMapping("/getFaDemandSumByPid")
    public BaseResponse getFaDemandSumByPid(@RequestParam String pId) {
        log.info("getFaDemandSumByPid");
        return fundPlanFeign.getFaDemandSumByPid(pId);
    }

    @ApiOperation(value = "查询资金拨付详情指定合同前期已完成投资、前期已付资金和其对应资金计划详情的本期资金需求")
    @GetMapping("/getConAppropriationDtl")
    public BaseResponse getConAppropriationDtl(@RequestParam(required = false) String fpId, @RequestParam String conId) {
        log.info("getConAppropriationDtl");
        return fundPlanFeign.getConAppropriationDtl(fpId, conId);
    }


    @ApiOperation(value = "资金计划ID获取资金计划清单表（资金拨付申请选择资金计划后带出的清单数据）")
    @GetMapping("/getAllFundPlanDtlByFpId")
    public BaseResponse getAllFundPlanDtlByFpId(@RequestParam String fpId) {
        log.info("getAllFundPlanDtlByFpId");
        return fundPlanFeign.getAllFundPlanDtlByFpId(fpId);
    }

    @ApiOperation(value = "查询指定合同历史审批通过的拨付清单")
    @PostMapping("/getFundAppropriationDtlList")
    public BaseResponse getFundAppropriationDtlList(@RequestBody FundAppropriationDtlDto request) {
        log.info("getFundAppropriationDtlList");
        return fundPlanFeign.getFundAppropriationDtlList(request);
    }
}
