package com.jpmsoft.projectConsumer.controller.v1.investment;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.InvestmentControl.InvFundReleaseRequest;
import com.jpmsoft.projectConsumer.feign.InvFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "资金计划下达", value = "资金计划下达")
@RequestMapping("/inv/v1/invFundRelease")
public class InvFundReleaseController {
    @Autowired
    private InvFeign invFeign;


    @ApiOperation(value = "新增或修改资金计划下达")
    @PostMapping("/createOrUpdateInvFundRelease")
    public BaseResponse createOrUpdateInvFundRelease(@RequestBody InvFundReleaseRequest request) {
        log.info("createOrUpdateInvFundRelease");
        return invFeign.createOrUpdateInvFundRelease(request);
    }

    @ApiOperation(value = "ID获取资金计划下达")
    @GetMapping("/getInvFundReleaseByIfrId")
    public BaseResponse getInvFundReleaseByIfrId(@RequestParam String ifrId) {
        log.info("getInvFundReleaseByIfrId");
        return invFeign.getInvFundReleaseByIfrId(ifrId);
    }

    @ApiOperation(value = "分页查询资金计划下达")
    @PostMapping("/getInvFundReleaseList")
    public BaseResponse getInvFundReleaseList(@RequestBody InvFundReleaseRequest request) {
        log.info("getInvFundReleaseList");
        return invFeign.getInvFundReleaseList(request);
    }
}
