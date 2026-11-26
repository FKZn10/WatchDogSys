package com.jpmsoft.userProducer.controller.v1;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.SysAuditLogQueryDto;
import com.jpmsoft.userProducer.service.ISysAuditLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author: 李文涛 aaron
 * @date: 2022/8/25 22:34
 */
@Slf4j
@Api(tags = "审计日志", value = "审计日志")
@RestController
@RequestMapping("/v1/auditLog")
public class SysAuditLogController {

    @Resource
    private ISysAuditLogService sysAuditLogService;

    @ApiOperation(value = "表单ID分页查询审计日志")
    @PostMapping("/getSysAuditLogList")
    public BaseResponse getSysAuditLogList(@RequestBody SysAuditLogQueryDto query) {
        log.info("getSysAuditLogList");
        return sysAuditLogService.getSysAuditLogList(query);
    }
}
