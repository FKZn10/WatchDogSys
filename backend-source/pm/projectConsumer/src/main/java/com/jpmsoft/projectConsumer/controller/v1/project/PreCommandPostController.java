package com.jpmsoft.projectConsumer.controller.v1.project;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Project.PreCommandPostModel;
import com.jpmsoft.projectConsumer.feign.PreFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "指挥部架构", value = "指挥部架构")
@RequestMapping("/v1/commandPost")
public class PreCommandPostController {
    @Autowired
    private PreFeign preFeign;


    @ApiOperation(value = "新增修改指挥部架构")
    @PostMapping("/createOrUpdatePreCommandPost")
    public BaseResponse createOrUpdatePreCommandPost(@RequestBody PreCommandPostModel request) {
        log.info("createOrUpdatePreCommandPost");
        return preFeign.createOrUpdatePreCommandPost(request);
    }

    @ApiOperation(value = "获取指挥部架构列表")
    @PostMapping("/getPreCommandPostList")
    public BaseResponse getPreCommandPostList(@RequestBody PreCommandPostModel request, @RequestHeader String token) {
        log.info("getPreCommandPostList");
        return preFeign.getPreCommandPostList(request, token);
    }

    @ApiOperation(value = "获取指挥部架构列表(统计+列表)")
    @PostMapping("/getPreCommandPostStatisticsList")
    public BaseResponse getPreCommandPostStatisticsList(@RequestBody PreCommandPostModel request, @RequestHeader String token) {
        log.info("getPreCommandPostStatisticsList");
        return preFeign.getPreCommandPostStatisticsList(request, token);
    }

    @ApiOperation(value = "ID获取指挥部架构")
    @GetMapping("/getPreCommandPostById")
    public BaseResponse getPreCommandPostById(@RequestParam String comId) {
        log.info("getPreCommandPostById");
        return preFeign.getPreCommandPostById(comId);
    }
}
