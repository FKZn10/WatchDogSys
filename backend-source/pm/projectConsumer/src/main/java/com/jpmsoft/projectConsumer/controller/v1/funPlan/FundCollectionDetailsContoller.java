package com.jpmsoft.projectConsumer.controller.v1.funPlan;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.project.PreProjectRequest;
import com.jpmsoft.projectConsumer.pojo.dto.fundPlan.FundCollectionDetailsDto;
import com.jpmsoft.projectConsumer.feign.FundPlanFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author: 李文涛 aaron
 * @date: 2022/10/17 16:10
 */
@Slf4j
@RestController
@Api(tags = "资金收款明细", value = "资金收款明细")
@RequestMapping("/v1/fundCollectionDetails")
public class FundCollectionDetailsContoller {

    @Resource
    private FundPlanFeign fundPlanFeign;

    @ApiOperation(value = "新增或修改资金收款明细")
    @PostMapping("/createOrUpdateFundCollectionDetails")
    public BaseResponse createOrUpdateFundCollectionDetails(@RequestBody FundCollectionDetailsDto request) {
        log.info("createOrUpdateFundCollectionDetails");
        return fundPlanFeign.createOrUpdateFundCollectionDetails(request);
    }

    @ApiOperation(value = "分页查询资金收款明细")
    @PostMapping("/getFundCollectionDetailsList")
    public BaseResponse getFundCollectionDetailsList(@RequestBody FundCollectionDetailsDto request, @RequestHeader String token) {
        log.info("getFundCollectionDetailsList");
        return fundPlanFeign.getFundCollectionDetailsList(request, token);
    }

    @ApiOperation(value = "分页查询资金收款项目明细")
    @PostMapping("/getFundCollectionProjectList")
    public BaseResponse getFundCollectionProjectList(@RequestBody PreProjectRequest request, @RequestHeader String token) {
        log.info("getFundCollectionProjectList");
        return fundPlanFeign.getFundCollectionProjectList(request, token);
    }

    @ApiOperation(value = "id查询资金收款明细")
    @GetMapping("/getFundCollectionDetailsById")
    public BaseResponse getFundCollectionDetailsById(@RequestParam String id) {
        log.info("getFundCollectionDetailsById");
        return fundPlanFeign.getFundCollectionDetailsById(id);
    }

    @ApiOperation(value = "取该项目下最新的一条审批通过的资金收款明细单")
    @GetMapping("/getLastFundCollectionDetailsByPid")
    public BaseResponse getLastFundCollectionDetailsByPid(@RequestParam String pId) {
        log.info("getLastFundCollectionDetailsByPid");
        return fundPlanFeign.getLastFundCollectionDetailsByPid(pId);
    }

    @ApiOperation(value = "项目ID统计收款批次和已收款金额")
    @GetMapping("/statisticsBatchNoAndAmt")
    public BaseResponse statisticsBatchNoAndAmt(@RequestParam String pId) {
        log.info("statisticsBatchNoAndAmt");
        return fundPlanFeign.statisticsBatchNoAndAmt(pId);
    }

    @ApiOperation(value = "项目收款拨付台账")
    @PostMapping("/getProjectFundCollectionDetailsList")
    public BaseResponse getProjectFundCollectionDetailsList(@RequestBody PreProjectRequest request, @RequestHeader String token) {
        log.info("getProjectFundCollectionDetailsList");
        return fundPlanFeign.getProjectFundCollectionDetailsList(request, token);
    }
}
