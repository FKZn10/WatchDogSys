package com.jpmsoft.projectConsumer.controller.v1.project;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Project.ProImageProgressModel;
import com.jpmsoft.common.model.requestAndResponse.Project.ProImageProgressReplyModel;
import com.jpmsoft.projectConsumer.feign.PreFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "形象进度回复表", value = "形象进度回复")
@RequestMapping("/v1/imageProgressReply")
public class ProImageProgressReplyController {

    @Autowired
    private PreFeign preFeign;

    @ApiOperation(value = "新增修改形象进度回复")
    @PostMapping("/createProImageProgressReply")
    public BaseResponse createProImageProgressReply(@RequestBody ProImageProgressReplyModel request) {
        log.info("createProImageProgressReply");
        return preFeign.createProImageProgressReply(request);
    }

    @ApiOperation(value = "删除形象进度回复")
    @GetMapping("/deleteProImageProgressReplyById")
    public BaseResponse deleteProImageProgressReplyById(@RequestParam String id) {
        log.info("deleteProImageProgressReplyById");
        return preFeign.deleteProImageProgressReplyById(id);
    }
}
