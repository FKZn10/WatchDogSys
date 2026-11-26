package com.jpmsoft.userProducer.controller.v1;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.SysMessageDto;
import com.jpmsoft.userProducer.service.ISysMessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 系统消息
 *
 * @author: 李文涛 aaron
 * @date: 2022/11/11 14:50
 */
@Slf4j
@Api(tags = "系统消息", value = "系统消息")
@RestController
@RequestMapping("/msg/v1/message")
public class SysMessageController {

    @Resource
    private ISysMessageService sysMessageService;


    @ApiOperation(value = "新增系统消息")
    @PostMapping("/createSysMessage")
    public BaseResponse createSysMessage(@RequestBody @Validated SysMessageDto request) {
        log.info("createSysMessage");
        return sysMessageService.createSysMessage(request);
    }

    @ApiOperation(value = "分页查询系统消息")
    @PostMapping("/getSysMessageList")
    public BaseResponse getSysMessageList(@RequestBody SysMessageDto request) {
        log.info("getSysMessageList");
        return sysMessageService.getSysMessageList(request);
    }

    @ApiOperation(value = "ID查询系统消息")
    @GetMapping("/getSysMessageById")
    public BaseResponse getSysMessageById(@RequestParam String id) {
        log.info("getSysMessageById");
        return sysMessageService.getSysMessageById(id);
    }
}
