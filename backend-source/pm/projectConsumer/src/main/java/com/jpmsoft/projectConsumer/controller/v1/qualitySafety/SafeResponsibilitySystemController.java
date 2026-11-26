package com.jpmsoft.projectConsumer.controller.v1.qualitySafety;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SafeResponsibility.SafeResponsibilityCopyDTO;
import com.jpmsoft.common.model.requestAndResponse.SafeResponsibility.SafeResponsibilitySystemDTO;
import com.jpmsoft.projectConsumer.feign.BaseFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 安全责任体系对外接口。
 */
@RestController
@Api(tags = "安全责任体系", value = "安全责任体系")
@RequestMapping("/safety/v1/safeResponsibilitySystem")
public class SafeResponsibilitySystemController {
    @Resource
    private BaseFeign baseFeign;

    /**
     * 新增或修改安全责任体系。
     */
    @ApiOperation("新增或修改安全责任体系")
    @PostMapping("/createOrUpdateSafeResponsibilitySystem")
    public BaseResponse createOrUpdateSafeResponsibilitySystem(@RequestBody SafeResponsibilitySystemDTO request,
                                                               @RequestHeader String token) {
        return baseFeign.createOrUpdateSafeResponsibilitySystem(request, token);
    }

    /**
     * 多条件筛选安全责任体系列表。
     */
    @ApiOperation("筛选安全责任体系列表")
    @PostMapping("/getSafeResponsibilitySystemList")
    public BaseResponse getSafeResponsibilitySystemList(@RequestBody SafeResponsibilitySystemDTO request) {
        return baseFeign.getSafeResponsibilitySystemList(request);
    }

    /**
     * 多条件筛选安全责任体系树。
     */
    @ApiOperation("筛选安全责任体系树")
    @PostMapping("/getSafeResponsibilitySystemTree")
    public BaseResponse getSafeResponsibilitySystemTree(@RequestBody SafeResponsibilitySystemDTO request) {
        return baseFeign.getSafeResponsibilitySystemTree(request);
    }

    /**
     * 删除安全责任体系。
     */
    @ApiOperation("删除安全责任体系")
    @PostMapping("/deleteSafeResponsibilitySystem")
    public BaseResponse deleteSafeResponsibilitySystem(@RequestBody SafeResponsibilitySystemDTO request,
                                                       @RequestHeader String token) {
        return baseFeign.deleteSafeResponsibilitySystem(request, token);
    }

    /**
     * 复制安全责任体系模板。
     */
    @ApiOperation("复制安全责任体系模板")
    @PostMapping("/copySafeResponsibilitySystem")
    public BaseResponse copySafeResponsibilitySystem(@RequestBody SafeResponsibilityCopyDTO request,
                                                     @RequestHeader String token) {
        return baseFeign.copySafeResponsibilitySystem(request, token);
    }
}
