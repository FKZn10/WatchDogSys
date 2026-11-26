package com.jpmsoft.projectConsumer.controller.v1.project;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Project.ProImageProgressLikeModel;
import com.jpmsoft.common.model.requestAndResponse.Project.ProImageProgressModel;
import com.jpmsoft.projectConsumer.feign.PreFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "形象进度点赞", value = "形象进度点赞")
@RequestMapping("/v1/imageProgressLike")
public class ProImageProgressLikeController {

    @Autowired
    private PreFeign preFeign;

    @ApiOperation(value = "新增修改形象进度点赞")
    @PostMapping("/createProImageProgressLike")
    public BaseResponse createProImageProgressLike(@RequestBody ProImageProgressLikeModel request) {
        log.info("createProImageProgressLike");
        return preFeign.createProImageProgressLike(request);
    }

    @ApiOperation(value = "删除形象进度点赞")
    @GetMapping("/deleteProImageProgressLikeById")
    public BaseResponse deleteProImageProgressLikeById(@RequestParam String id) {
        log.info("deleteProImageProgressLikeById");
        return preFeign.deleteProImageProgressLikeById(id);
    }
}
