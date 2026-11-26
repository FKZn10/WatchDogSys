package com.jpmsoft.projectConsumer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecProvisionalEstimateModel;
import com.jpmsoft.projectConsumer.feign.SecFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "暂估价", value = "暂估价")
@RequestMapping("/v1/provisionalEstimate")
public class SecProvisionalEstimateController {
    @Autowired
    private SecFeign secFeign;

    @ApiOperation(value = "创建或修改暂估价")
    @PostMapping("/createOrUpdateSecProvisionalEstimate")
    public BaseResponse createOrUpdateSecProvisionalEstimate(@RequestBody SecProvisionalEstimateModel request) {
        log.info("createOrUpdateSecProvisionalEstimate");
        return secFeign.createOrUpdateSecProvisionalEstimate(request);
    }

    @ApiOperation(value = "分页查询暂估价")
    @PostMapping("/getSecProvisionalEstimateModelList")
    public BaseResponse getSecProvisionalEstimateModelList(@RequestBody SecProvisionalEstimateModel request, @RequestHeader String token) {
        log.info("getSecProvisionalEstimateModelList");
        return secFeign.getSecProvisionalEstimateModelList(request, token);
    }

    @ApiOperation(value = "ID查询查询暂估价")
    @GetMapping("/getSecProvisionalEstimateByPeId")
    public BaseResponse getSecProvisionalEstimateByPeId(@RequestParam String peId) {
        log.info("getSecProvisionalEstimateByPeId");
        return secFeign.getSecProvisionalEstimateByPeId(peId);
    }
}
