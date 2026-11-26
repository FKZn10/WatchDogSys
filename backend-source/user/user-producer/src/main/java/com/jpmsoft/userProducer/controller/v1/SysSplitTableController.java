package com.jpmsoft.userProducer.controller.v1;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.SysSplitTableDTO;
import com.jpmsoft.userProducer.service.ISysSplitTableService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;

/**
 * 流程分表配置表
 *
 * @Author: 李文涛 aaron
 * @date: 2025/1/3 14:32
 */
@Slf4j
@RestController
@Api(tags = "流程分表配置表", value = "流程分表配置表")
@RequestMapping("/v1/splitTable")
public class SysSplitTableController {

    @Resource
    private ISysSplitTableService sysSplitTableService;


    @ApiOperation(value = "新增流程分表配置")
    @PostMapping("/createSysSplitTable")
    public BaseResponse createSysSplitTable(@RequestBody SysSplitTableDTO request) {
        log.info("createSysSplitTable");
        return sysSplitTableService.createSysSplitTable(request);
    }

    @ApiIgnore
    @Deprecated
    @ApiOperation(value = "ID删除流程分表配置")
    @GetMapping("/deleteSysSplitTableById")
    public BaseResponse deleteSysSplitTableById(@RequestParam String id) {
        log.info("deleteSysSplitTableById");
        return sysSplitTableService.deleteSysSplitTableById(id);
    }

    @ApiOperation(value = "分页查询流程分表配置")
    @PostMapping("/getSysSplitTableList")
    public BaseResponse getSysSplitTableList(@RequestBody SysSplitTableDTO request) {
        log.info("getSysSplitTableList");
        return sysSplitTableService.getSysSplitTableList(request);
    }

    @ApiOperation(value = "ID查询流程分表配置")
    @GetMapping("/getSysSplitTableById")
    public BaseResponse getSysSplitTableById(@RequestParam String id) {
        log.info("getSysSplitTableById");
        return sysSplitTableService.getSysSplitTableById(id);
    }

}
