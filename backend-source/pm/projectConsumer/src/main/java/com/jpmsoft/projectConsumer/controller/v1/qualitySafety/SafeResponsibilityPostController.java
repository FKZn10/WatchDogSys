package com.jpmsoft.projectConsumer.controller.v1.qualitySafety;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SafeResponsibility.SafeResponsibilityPostDTO;
import com.jpmsoft.projectConsumer.feign.BaseFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 安全责任岗位对外接口。
 */
@RestController
@Api(tags = "安全责任岗位", value = "安全责任岗位")
@RequestMapping("/safety/v1/safeResponsibilityPost")
public class SafeResponsibilityPostController {
    @Resource
    private BaseFeign baseFeign;

    /**
     * 新增或修改岗位及职责。
     */
    @ApiOperation("新增或修改安全责任岗位")
    @PostMapping("/createOrUpdateSafeResponsibilityPost")
    public BaseResponse createOrUpdateSafeResponsibilityPost(@RequestBody SafeResponsibilityPostDTO request,
                                                             @RequestHeader String token) {
        return baseFeign.createOrUpdateSafeResponsibilityPost(request, token);
    }

    /**
     * 多条件筛选岗位列表。
     */
    @ApiOperation("筛选安全责任岗位列表")
    @PostMapping("/getSafeResponsibilityPostList")
    public BaseResponse getSafeResponsibilityPostList(@RequestBody SafeResponsibilityPostDTO request) {
        return baseFeign.getSafeResponsibilityPostList(request);
    }

    /**
     * 删除岗位及全部职责。
     */
    @ApiOperation("删除安全责任岗位")
    @PostMapping("/deleteSafeResponsibilityPost")
    public BaseResponse deleteSafeResponsibilityPost(@RequestBody SafeResponsibilityPostDTO request,
                                                     @RequestHeader String token) {
        return baseFeign.deleteSafeResponsibilityPost(request, token);
    }
}
