package com.jpmsoft.userProducer.controller.v1;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.annotations.TenantId;
import com.jpmsoft.userProducer.pojo.dto.SysApplicationDTO;
import com.jpmsoft.userProducer.service.ISysApplicationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/3/2 9:50
 */
@Slf4j
@RestController
@Api(tags = "租户-应用", value = "租户-应用")
@RequestMapping("/v1/tenant")
public class SysApplicationController {

    @Resource
    private ISysApplicationService sysApplicationService;

    @TenantId
    @ApiOperation(value = "创建修改租户信息")
    @PostMapping("/createOrUpdateSysApplication")
    public BaseResponse createOrUpdateSysApplication(@RequestBody SysApplicationDTO request) {
        log.info("createOrUpdateSysApplication");
        return sysApplicationService.createOrUpdateSysApplication(request);
    }

    @ApiOperation(value = "分页查询租户信息")
    @PostMapping("/getSysApplicationList")
    public BaseResponse getSysApplicationList(@RequestBody SysApplicationDTO request) {
        log.info("getSysApplicationList");
        return sysApplicationService.getSysApplicationList(request);
    }

    @ApiOperation(value = "ID查询租户信息")
    @GetMapping("/getSysApplicationById")
    public BaseResponse getSysApplicationById(@RequestParam String id) {
        log.info("getSysApplicationById");
        return sysApplicationService.getSysApplicationById(id);
    }
}
