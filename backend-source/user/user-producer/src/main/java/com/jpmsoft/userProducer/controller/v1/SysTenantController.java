package com.jpmsoft.userProducer.controller.v1;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.SysTenantDTO;
import com.jpmsoft.userProducer.service.ISysTenantService;
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
@Api(tags = "租户信息", value = "租户信息")
@RequestMapping("/v1/tenant")
public class SysTenantController {

    @Resource
    private ISysTenantService sysTenantService;

    @ApiOperation(value = "创建修改租户信息")
    @PostMapping("/createOrUpdateSysTenant")
    public BaseResponse createOrUpdateSysTenant(@RequestBody SysTenantDTO request) {
        log.info("createOrUpdateSysTenant");
        return sysTenantService.createOrUpdateSysTenant(request);
    }

    @ApiOperation(value = "分页查询租户信息")
    @PostMapping("/getSysTenantList")
    public BaseResponse getSysTenantList(@RequestBody SysTenantDTO request) {
        log.info("getSysTenantList");
        return sysTenantService.getSysTenantList(request);
    }

    @ApiOperation(value = "ID查询租户信息")
    @GetMapping("/getSysTenantById")
    public BaseResponse getSysTenantById(@RequestParam String id) {
        log.info("getSysTenantById");
        return sysTenantService.getSysTenantById(id);
    }
}
