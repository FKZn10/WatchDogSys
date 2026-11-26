package com.jpmsoft.userProducer.controller.v1;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.SysTenantPayDTO;
import com.jpmsoft.userProducer.service.ISysTenantPayService;
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
@Api(tags = "租户-缴费信息", value = "租户-缴费信息")
@RequestMapping("/v1/tenant")
public class SysTenantPayController {

    @Resource
    private ISysTenantPayService sysTenantPayService;

    @ApiOperation(value = "创建修改缴费信息")
    @PostMapping("/createOrUpdateSysTenantPay")
    public BaseResponse createOrUpdateSysTenantPay(@RequestBody SysTenantPayDTO request) {
        log.info("createOrUpdateSysTenantPay");
        return sysTenantPayService.createOrUpdateSysTenantPay(request);
    }

    @ApiOperation(value = "分页查询缴费信息")
    @PostMapping("/getSysTenantPayList")
    public BaseResponse getSysTenantPayList(@RequestBody SysTenantPayDTO request) {
        log.info("getSysTenantPayList");
        return sysTenantPayService.getSysTenantPayList(request);
    }

    @ApiOperation(value = "ID查询缴费信息")
    @GetMapping("/getSysTenantPayById")
    public BaseResponse getSysTenantPayById(@RequestParam String id) {
        log.info("getSysTenantPayById");
        return sysTenantPayService.getSysTenantPayById(id);
    }
}
