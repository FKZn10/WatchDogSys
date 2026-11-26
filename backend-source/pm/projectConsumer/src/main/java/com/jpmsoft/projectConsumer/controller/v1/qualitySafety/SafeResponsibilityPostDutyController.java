package com.jpmsoft.projectConsumer.controller.v1.qualitySafety;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SafeResponsibility.SafeResponsibilityPostDutyDTO;
import com.jpmsoft.projectConsumer.feign.BaseFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 安全责任岗位职责明细对外接口。
 */
@RestController
@Api(tags = "安全责任岗位职责", value = "安全责任岗位职责")
@RequestMapping("/safety/v1/safeResponsibilityPostDuty")
public class SafeResponsibilityPostDutyController {
    @Resource
    private BaseFeign baseFeign;

    /**
     * 新增或修改岗位职责。
     */
    @ApiOperation("新增或修改安全责任岗位职责")
    @PostMapping("/createOrUpdateSafeResponsibilityPostDuty")
    public BaseResponse createOrUpdateSafeResponsibilityPostDuty(@RequestBody SafeResponsibilityPostDutyDTO request,
                                                                 @RequestHeader String token) {
        return baseFeign.createOrUpdateSafeResponsibilityPostDuty(request, token);
    }

    /**
     * 多条件筛选岗位职责。
     */
    @ApiOperation("筛选安全责任岗位职责")
    @PostMapping("/getSafeResponsibilityPostDutyList")
    public BaseResponse getSafeResponsibilityPostDutyList(@RequestBody SafeResponsibilityPostDutyDTO request) {
        return baseFeign.getSafeResponsibilityPostDutyList(request);
    }

    /**
     * 删除岗位职责。
     */
    @ApiOperation("删除安全责任岗位职责")
    @PostMapping("/deleteSafeResponsibilityPostDuty")
    public BaseResponse deleteSafeResponsibilityPostDuty(@RequestBody SafeResponsibilityPostDutyDTO request,
                                                         @RequestHeader String token) {
        return baseFeign.deleteSafeResponsibilityPostDuty(request, token);
    }
}
