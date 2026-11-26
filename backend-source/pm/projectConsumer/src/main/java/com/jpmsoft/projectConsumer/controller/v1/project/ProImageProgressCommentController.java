package com.jpmsoft.projectConsumer.controller.v1.project;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Project.ProImageProgressCommentModel;
import com.jpmsoft.projectConsumer.feign.PreFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "形象进度", value = "形象进度")
@RequestMapping("/v1/imageProgressComment")
public class ProImageProgressCommentController {

    @Autowired
    private PreFeign preFeign;

    @ApiOperation(value = "新增修改形象进度评论")
    @PostMapping("/createProImageProgressComment")
    public BaseResponse createProImageProgressComment(@RequestBody ProImageProgressCommentModel request) {
        log.info("createProImageProgressComment");
        return preFeign.createProImageProgressComment(request);
    }

    @ApiOperation(value = "获取形象进度评论列表")
    @PostMapping("/getProImageProgressCommentListByIpId")
    public BaseResponse getProImageProgressCommentListByIpId(@RequestParam String ipId) {
        log.info("getProImageProgressCommentListByIpId");
        return preFeign.getProImageProgressCommentListByIpId(ipId);
    }

    @ApiOperation(value = "获取形象进度评论详情")
    @GetMapping("/getProImageProgressCommentById")
    public BaseResponse getProImageProgressCommentById(@RequestParam String id) {
        log.info("getProImageProgressCommentById");
        return preFeign.getProImageProgressCommentById(id);
    }

    @ApiOperation(value = "删除形象进度评论")
    @GetMapping("/deleteProImageProgressCommentById")
    public BaseResponse deleteProImageProgressCommentById(@RequestParam String id) {
        log.info("getProImageProgressCommentById");
        return preFeign.deleteProImageProgressCommentById(id);
    }
}
