package com.jpmsoft.userProducer.controller.v1;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.SysPayTemplateDto;
import com.jpmsoft.userProducer.service.ISysPayTemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 支付模板
 * Created By:李文涛 aaron
 * Created Time:2022/7/5 10:34
 */
@Slf4j
@RestController
@Api(tags = "支付模板", value = "支付模板")
@RequestMapping("/v1/payTemp")
public class SysPayTemplateController {

    @Resource
    private ISysPayTemplateService sysPayTemplateService;

    @ApiOperation(value = "创建修改支付模板")
    @PostMapping("/createOrUpdateSysPayTemplate")
    public BaseResponse createOrUpdateSysPayTemplate(@RequestBody SysPayTemplateDto request) {
        return sysPayTemplateService.createOrUpdateSysPayTemplate(request);
    }

    @ApiOperation(value = "分页查询支付模板")
    @PostMapping("/getSysPayTemplateList")
    public BaseResponse getSysPayTemplateList(@RequestBody SysPayTemplateDto request) {
        return sysPayTemplateService.getSysPayTemplateList(request);
    }

    @ApiOperation(value = "ID查询支付模板")
    @GetMapping("/getSysPayTemplateById")
    public BaseResponse getSysPayTemplateById(@RequestParam String id) {
        return sysPayTemplateService.getSysPayTemplateById(id);
    }

    @ApiOperation(value = "ID删除支付模板")
    @GetMapping("/deleteSysPayTemplateById")
    public BaseResponse deleteSysPayTemplateById(@RequestParam String id) {
        return sysPayTemplateService.deleteSysPayTemplateById(id);
    }

}
