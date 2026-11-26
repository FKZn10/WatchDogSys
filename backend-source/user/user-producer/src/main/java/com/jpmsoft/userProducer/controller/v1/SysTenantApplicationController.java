package com.jpmsoft.userProducer.controller.v1;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.SysTenantApplicationDTO;
import com.jpmsoft.userProducer.service.ISysTenantApplicationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/3/7 10:03
 */
@Slf4j
@RestController
@Api(tags = "租户-已开通应用", value = "租户-已开通应用")
@RequestMapping("/v1/tenant")
public class SysTenantApplicationController {
    @Resource
    private ISysTenantApplicationService sysTenantApplicationService;


    @ApiOperation(value = "创建修改已开通应用")
    @PostMapping("/createOrUpdateSysTenantApplication")
    public BaseResponse createOrUpdateSysTenantApplication(@RequestBody SysTenantApplicationDTO request) {
        log.info("createOrUpdateSysTenantApplication");
        return sysTenantApplicationService.createOrUpdateSysTenantApplication(request);
    }

    @ApiOperation(value = "分页查询已开通应用")
    @PostMapping("/getSysTenantApplicationList")
    public BaseResponse getSysTenantApplicationList(@RequestBody SysTenantApplicationDTO request) {
        log.info("getSysTenantApplicationList");
        return sysTenantApplicationService.getSysTenantApplicationList(request);
    }

    @ApiOperation(value = "删除已开通应用")
    @GetMapping("/delSysTenantApp")
    public BaseResponse delSysTenantApp(@RequestParam String taId) {
        log.info("delSysTenantApp");
        return sysTenantApplicationService.delSysTenantApp(taId);
    }
}
