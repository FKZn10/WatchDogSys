package com.jpmsoft.projectConsumer.controller.v1.qualitySafety;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SafeResponsibility.SafeResponsibilityLevelDTO;
import com.jpmsoft.projectConsumer.feign.BaseFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 安全责任层级对外接口。
 */
@RestController
@Api(tags = "安全责任层级", value = "安全责任层级")
@RequestMapping("/safety/v1/safeResponsibilityLevel")
public class SafeResponsibilityLevelController {

    @Resource
    private BaseFeign baseFeign;

    /**
     * 新增或修改安全责任层级。
     */
    @ApiOperation("新增或修改安全责任层级")
    @PostMapping("/createOrUpdateSafeResponsibilityLevel")
    public BaseResponse createOrUpdateSafeResponsibilityLevel(@RequestBody SafeResponsibilityLevelDTO request,
                                                              @RequestHeader String token) {
        return baseFeign.createOrUpdateSafeResponsibilityLevel(request, token);
    }

    /**
     * 筛选层级树并保留命中节点的全部祖先。
     */
    @ApiOperation("筛选安全责任层级树")
    @PostMapping("/getSafeResponsibilityLevelTree")
    public BaseResponse getSafeResponsibilityLevelTree(@RequestBody SafeResponsibilityLevelDTO request) {
        return baseFeign.getSafeResponsibilityLevelTree(request);
    }

    /**
     * 删除空安全责任层级。
     */
    @ApiOperation("删除安全责任层级")
    @PostMapping("/deleteSafeResponsibilityLevel")
    public BaseResponse deleteSafeResponsibilityLevel(@RequestBody SafeResponsibilityLevelDTO request,
                                                      @RequestHeader String token) {
        return baseFeign.deleteSafeResponsibilityLevel(request, token);
    }
}
