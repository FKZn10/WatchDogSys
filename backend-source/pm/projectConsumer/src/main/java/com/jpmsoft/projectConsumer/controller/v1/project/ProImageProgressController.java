package com.jpmsoft.projectConsumer.controller.v1.project;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Project.ProImageProgressModel;
import com.jpmsoft.projectConsumer.feign.PreFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "形象进度", value = "形象进度")
@RequestMapping("/v1/imageProgress")
public class ProImageProgressController {

    @Autowired
    private PreFeign preFeign;

    @ApiOperation(value = "新增修改形象进度")
    @PostMapping("/createOrUpdateProImageProgress")
    public BaseResponse createOrUpdateProImageProgress(@RequestBody ProImageProgressModel request) {
        log.info("createOrUpdateProImageProgress");
        return preFeign.createOrUpdateProImageProgress(request);
    }

    @ApiOperation(value = "获取形象进度列表")
    @PostMapping("/getProImageProgressList")
    public BaseResponse getProImageProgressList(@RequestBody ProImageProgressModel request, @RequestHeader String token) {
        log.info("getProImageProgressList");
        return preFeign.getProImageProgressList(request, token);
    }

    @ApiOperation(value = "获取形象进度详情")
    @GetMapping("/getProImageProgressById")
    public BaseResponse getProImageProgressById(@RequestParam String id, @RequestHeader String token) {
        log.info("getProImageProgressById");
        return preFeign.getProImageProgressById(id, token);
    }

    @ApiOperation(value = "删除形象进度")
    @GetMapping("/deleteProImageProgressById")
    public BaseResponse deleteProImageProgressById(@RequestParam String id) {
        log.info("getProImageProgressById");
        return preFeign.deleteProImageProgressById(id);
    }
}
