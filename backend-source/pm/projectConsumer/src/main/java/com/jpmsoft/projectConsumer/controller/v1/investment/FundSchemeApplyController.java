package com.jpmsoft.projectConsumer.controller.v1.investment;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.FundSchemeApplyModel;
import com.jpmsoft.projectConsumer.feign.InvFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@Api(tags = "资金计划申请", value = "资金计划申请")
@RequestMapping("/v1/fsApply")
public class FundSchemeApplyController {
    @Resource
    private InvFeign invFeign;


    @ApiOperation(value = "新增或修改资金计划申请")
    @PostMapping("/createOrUpdateFundSchemeApply")
    public BaseResponse createOrUpdateFundSchemeApply(@RequestBody FundSchemeApplyModel request) {
        log.info("createOrUpdateFundSchemeApply");
        return invFeign.createOrUpdateFundSchemeApply(request);
    }

    @ApiOperation(value = "分页查询资金计划申请")
    @PostMapping("/getFundSchemeApplyList")
    public BaseResponse getFundSchemeApplyList(@RequestBody FundSchemeApplyModel request, @RequestHeader String token) {
        log.info("getFundSchemeApplyList");
        return invFeign.getFundSchemeApplyList(request, token);
    }

    @ApiOperation(value = "Id查询资金计划申请")
    @GetMapping("/getFundSchemeApplyById")
    public BaseResponse getFundSchemeApplyById(@RequestParam String fsaId) {
        log.info("getFundSchemeApplyById");
        return invFeign.getFundSchemeApplyById(fsaId);
    }

    @ApiOperation(value = "项目ID查询概算批复金额以及已申请金额")
    @GetMapping("/getFundSchemeApplyAmtSumByPid")
    public BaseResponse getFundSchemeApplyAmtSumByPid(@RequestParam String pId) {
        log.info("getFundSchemeApplyAmtSumByPid");
        return invFeign.getFundSchemeApplyAmtSumByPid(pId);
    }

    @ApiOperation(value = "合同ID查询已申请金额")
    @GetMapping("/getFundSchemeApplyDtlAmtSumByConId")
    public BaseResponse getFundSchemeApplyDtlAmtSumByConId(@RequestParam String conId) {
        log.info("getFundSchemeApplyDtlAmtSumByConId");
        return invFeign.getFundSchemeApplyDtlAmtSumByConId(conId);
    }
}
