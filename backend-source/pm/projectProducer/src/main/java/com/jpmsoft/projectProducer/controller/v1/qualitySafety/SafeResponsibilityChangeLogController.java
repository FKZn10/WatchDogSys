package com.jpmsoft.projectProducer.controller.v1.qualitySafety;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SafeResponsibility.SafeResponsibilityChangeLogDTO;
import com.jpmsoft.projectProducer.service.qualitySafety.ISafeResponsibilityChangeLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 安全责任体系变更台账接口。
 */
@Slf4j
@RestController
@RequestMapping("/v1/safeResponsibilityChangeLog")
public class SafeResponsibilityChangeLogController {
    @Resource
    private ISafeResponsibilityChangeLogService changeLogService;

    /**
     * 多条件筛选安全责任体系变更台账。
     */
    @PostMapping("/getSafeResponsibilityChangeLogList")
    public BaseResponse getSafeResponsibilityChangeLogList(@RequestBody SafeResponsibilityChangeLogDTO request) {
        log.info("getSafeResponsibilityChangeLogList");
        return changeLogService.getSafeResponsibilityChangeLogList(request);
    }
}
