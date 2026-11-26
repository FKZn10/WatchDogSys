package com.jpmsoft.projectConsumer.controller.v1.qualitySafety;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Qs.QsPlanModel;
import com.jpmsoft.projectConsumer.feign.BaseFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@Api(tags = "质量安全检查计划", value = "质量安全检查计划")
@RequestMapping("/safety/v1/qsPlan")
public class QsPlanController {
    @Autowired
    private BaseFeign baseFeign;

    @ApiOperation(value = "创建或修改质量安全检查计划")
    @PostMapping("/createOrUpdateQsPlan")
    public BaseResponse createOrUpdateQsPlan(@RequestBody QsPlanModel request) {
        log.info("createOrUpdateQsPlan");
        return baseFeign.createOrUpdateQsPlan(request);
    }

    @ApiOperation(value = "分页查询质量安全检查计划")
    @PostMapping("/getQsPlanList")
    public BaseResponse getQsPlanList(@RequestBody QsPlanModel request) {
        log.info("getQsPlanList");
        return baseFeign.getQsPlanList(request);
    }

    @ApiOperation(value = "ID查询质量安全检查计划")
    @GetMapping("/getQsPlanById")
    public BaseResponse getQsPlanById(@RequestParam String qspId) {
        log.info("getQsPlanById");
        return baseFeign.getQsPlanById(qspId);
    }
    @PostMapping("/setQsPlanReplyState")
    public BaseResponse setQsPlanReplyState(@RequestBody QsPlanModel request) {
        log.info("setQsPlanReplyState");
        return baseFeign.setQsPlanReplyState(request);
    }
}
