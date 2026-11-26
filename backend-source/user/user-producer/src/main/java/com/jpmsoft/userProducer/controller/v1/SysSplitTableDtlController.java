package com.jpmsoft.userProducer.controller.v1;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.SysSplitTableDtlDTO;
import com.jpmsoft.userProducer.service.ISysSplitTableDtlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: 李文涛 aaron
 * @date: 2025/2/7 10:19
 */
@Slf4j
@RestController
@Api(tags = "流程分表配置表详情", value = "流程分表配置表详情")
@RequestMapping("/v1/splitTableDtl")
public class SysSplitTableDtlController {

    @Resource
    private ISysSplitTableDtlService sysSplitTableDtlService;

    @ApiOperation(value = "新增流程分表配置表详情")
    @PostMapping("/createSysSplitTableDtl")
    public BaseResponse createSysSplitTableDtl(@RequestBody SysSplitTableDtlDTO request, @RequestHeader String token) {
        log.info("createSysSplitTableDtl");
        return sysSplitTableDtlService.createSysSplitTableDtl(request, token);
    }

    @ApiOperation(value = "ID删除流程分表配置表详情")
    @GetMapping("/deleteSysSplitTableDtlById")
    public BaseResponse deleteSysSplitTableDtlById(@RequestParam String id) {
        log.info("deleteSysSplitTableDtlById");
        return sysSplitTableDtlService.deleteSysSplitTableDtlById(id);
    }

    @ApiOperation(value = "分页查询流程分表配置表详情")
    @PostMapping("/getSysSplitTableDtlList")
    public BaseResponse getSysSplitTableDtlList(@RequestBody SysSplitTableDtlDTO request) {
        log.info("getSysSplitTableDtlList");
        return sysSplitTableDtlService.getSysSplitTableDtlList(request);
    }
}
