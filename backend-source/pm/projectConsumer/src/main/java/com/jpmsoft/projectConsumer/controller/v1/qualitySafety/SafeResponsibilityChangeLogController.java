package com.jpmsoft.projectConsumer.controller.v1.qualitySafety;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SafeResponsibility.SafeResponsibilityChangeLogDTO;
import com.jpmsoft.projectConsumer.feign.BaseFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 安全责任体系变更台账对外接口。
 */
@RestController
@Api(tags = "安全责任体系变更台账", value = "安全责任体系变更台账")
@RequestMapping("/safety/v1/safeResponsibilityChangeLog")
public class SafeResponsibilityChangeLogController {

    @Resource
    private BaseFeign baseFeign;

    /**
     * 多条件筛选变更台账。
     */
    @ApiOperation("筛选安全责任体系变更台账")
    @PostMapping("/getSafeResponsibilityChangeLogList")
    public BaseResponse getSafeResponsibilityChangeLogList(@RequestBody SafeResponsibilityChangeLogDTO request) {
        return baseFeign.getSafeResponsibilityChangeLogList(request);
    }
}
