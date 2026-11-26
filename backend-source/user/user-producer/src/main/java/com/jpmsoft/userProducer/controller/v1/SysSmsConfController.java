package com.jpmsoft.userProducer.controller.v1;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.sms.SysSmsConfDto;
import com.jpmsoft.userProducer.service.ISysSmsConfService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author: 李文涛 aaron
 * @date: 2022/9/6 10:03
 */
@Slf4j
@RestController
@Api(tags = "短信配置", value = "短信配置")
@RequestMapping("/sms/v1/sysSmsConf")
public class SysSmsConfController {

    @Resource
    private ISysSmsConfService sysSmsConfService;

    @ApiOperation(value = "新增修改短信配置")
    @PostMapping("/createOrUpdateSmsConf")
    public BaseResponse createOrUpdateSmsConf(@RequestBody SysSmsConfDto request) {
        log.info("createOrUpdateSmsConf");
        return sysSmsConfService.createOrUpdateSmsConf(request);
    }

    @ApiOperation(value = "分页查询短信配置")
    @PostMapping("/getSmsConfList")
    public BaseResponse getSmsConfList(@RequestBody SysSmsConfDto request) {
        log.info("getSmsConfList");
        return sysSmsConfService.getSmsConfList(request);
    }

    @ApiOperation(value = "ID查询短信配置")
    @GetMapping("/getSmsConfById")
    public BaseResponse getSmsConfById(@RequestParam String id) {
        log.info("getSmsConfById");
        return sysSmsConfService.getSmsConfById(id);
    }

}
