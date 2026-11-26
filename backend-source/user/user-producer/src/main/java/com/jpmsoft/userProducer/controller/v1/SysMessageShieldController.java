package com.jpmsoft.userProducer.controller.v1;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.SysMessageShieldDto;
import com.jpmsoft.userProducer.service.ISysMessageShieldService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: 李文涛 aaron
 * @date: 2022/11/15 10:05
 */
@Slf4j
@Api(tags = "系统消息屏蔽", value = "系统消息屏蔽")
@RestController
@RequestMapping("/msg/v1/messageShield")
public class SysMessageShieldController {
    @Resource
    private ISysMessageShieldService sysMessageShieldService;

    @ApiOperation(value = "新增修改系统消息屏蔽")
    @PostMapping("/insertOrUpdateSysMessageShield")
    public BaseResponse insertOrUpdateSysMessageShield(@RequestBody @Validated SysMessageShieldDto request) {
        log.info("insertOrUpdateSysMessageShield");
        return sysMessageShieldService.createOrUpdateSysMessageShield(request);
    }

    @ApiOperation(value = "分页查询系统消息屏蔽")
    @PostMapping("/getSysMessageShieldList")
    public BaseResponse getSysMessageShieldList(@RequestBody SysMessageShieldDto request) {
        log.info("getSysMessageShieldList");
        return sysMessageShieldService.getSysMessageShieldList(request);
    }

    @GetMapping("/getSysMessageShieldBySmtId")
    public List<SysMessageShieldDto> getSysMessageShieldBySmtId(@RequestParam String smtId) {
        log.info("getSysMessageShieldBySmtId");
        return sysMessageShieldService.getSysMessageShieldBySmtId(smtId);
    }
}
