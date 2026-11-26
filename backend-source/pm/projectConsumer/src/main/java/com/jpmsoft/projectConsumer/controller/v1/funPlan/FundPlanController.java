package com.jpmsoft.projectConsumer.controller.v1.funPlan;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.pojo.dto.fundPlan.FundPlanDtlDto;
import com.jpmsoft.projectConsumer.pojo.dto.fundPlan.FundPlanDto;
import com.jpmsoft.projectConsumer.feign.FundPlanFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 资金计划表
 *
 * @author: 李文涛 aaron
 * @date: 2022/8/16 15:52
 */
@Slf4j
@RestController
@Api(tags = "资金计划表", value = "资金计划表")
@RequestMapping("/v1/fundPlan")
public class FundPlanController {

    @Resource
    private FundPlanFeign fundPlanFeign;

    @ApiOperation(value = "新增或修改资金计划表")
    @PostMapping("/createOrUpdateFundPlan")
    public BaseResponse createOrUpdateFundPlan(@RequestBody FundPlanDto request) {
        log.info("createOrUpdateFundPlan");
        return fundPlanFeign.createOrUpdateFundPlan(request);
    }

    @ApiOperation(value = "分页查询资金计划表")
    @PostMapping("/getFundPlanList")
    public BaseResponse getFundPlanList(@RequestBody FundPlanDto request, @RequestHeader String token) {
        log.info("getFundPlanList");
        return fundPlanFeign.getFundPlanList(request, token);
    }

    @ApiOperation(value = "ID查询资金计划表")
    @GetMapping("/getFundPlanById")
    public BaseResponse getFundPlanById(@RequestParam String id) {
        log.info("getFundPlanById");
        return fundPlanFeign.getFundPlanById(id);
    }

    @ApiOperation(value = "统计资金计划（审批通过的）【最终】本期资金需求")
    @GetMapping("/getProFpDemandFinalSum")
    public BaseResponse getProFpDemandFinalSum(@RequestParam String pId) {
        log.info("getProFpDemandFinalSum");
        return fundPlanFeign.getProFpDemandFinalSum(pId);
    }

    @ApiOperation(value = "分页查询合同下历史审批通过的资金计划清单")
    @PostMapping("/getFundPlanDtlList")
    public BaseResponse getFundPlanDtlList(@RequestBody FundPlanDtlDto request) {
        log.info("getFundPlanDtlList");
        return fundPlanFeign.getFundPlanDtlList(request);
    }


    @ApiOperation(value = "ID查询资金计划清单")
    @GetMapping("/getFundPlanDtlByFpId")
    public BaseResponse getFundPlanDtlByFpId(@RequestParam String fpId) {
        log.info("getFundPlanDtlByFpId");
        return fundPlanFeign.getFundPlanDtlByFpId(fpId);
    }

}
