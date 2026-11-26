package com.jpmsoft.userProducer.controller.v1;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.SysSequenceSetDtlDto;
import com.jpmsoft.userProducer.service.ISysSequenceSetDtlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@Api(tags = "编码规则-编码规则配置详情", value = "编码规则-编码规则配置详情")
@RequestMapping("/v1/seqSetDtl")
public class SysSequenceSetDtlController {

    @Resource
    private ISysSequenceSetDtlService sysSequenceSetDtlService;

    @ApiOperation(value = "新增修改编码规则配置详情")
    @PostMapping("/createOrUpdateSysSequenceSetDtl")
    public BaseResponse createOrUpdateSysSequenceSetDtl(@RequestBody SysSequenceSetDtlDto request) {
        log.info("createOrUpdateSysSequenceSetDtl");
        return sysSequenceSetDtlService.createOrUpdateSysSequenceSetDtl(request);
    }

    @ApiOperation(value = "分页查询编码规则配置详情")
    @PostMapping("/getSysSequenceSetDtlList")
    public BaseResponse getSysSequenceSetDtlList(@RequestBody SysSequenceSetDtlDto request) {
        log.info("getSysSequenceSetDtlList");
        return sysSequenceSetDtlService.getSysSequenceSetDtlList(request);
    }

    @ApiOperation(value = "ID删除编码规则配置详情")
    @GetMapping("/deleteSysSequenceSetDtlById")
    public BaseResponse deleteSysSequenceSetDtlById(@RequestParam String sdId) {
        log.info("deleteSysSequenceSetDtlById");
        return sysSequenceSetDtlService.deleteSysSequenceSetDtlById(sdId);
    }
}
