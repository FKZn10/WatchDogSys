package com.jpmsoft.userProducer.controller.v1;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.MessageReceiveBathDto;
import com.jpmsoft.userProducer.pojo.dto.SysMessageReceiveDto;
import com.jpmsoft.userProducer.service.ISysMessageReceiveService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author: 李文涛 aaron
 * @date: 2022/11/11 15:05
 */
@Slf4j
@Api(tags = "消息接收人员", value = "消息接收人员")
@RestController
@RequestMapping("/msg/v1/messageReceive")
public class SysMessageReceiveController {

    @Resource
    private ISysMessageReceiveService sysMessageReceiveService;

    @ApiOperation(value = "批量新增消息接收人员")
    @PostMapping("/createMessageReceiveBath")
    public BaseResponse createMessageReceiveBath(@RequestBody @Validated MessageReceiveBathDto request) {
        log.info("createMessageReceiveBath");
        return sysMessageReceiveService.createMessageReceiveBath(request);
    }

    @ApiOperation(value = "新增消息接收人员")
    @PostMapping("/createMessageReceive")
    public BaseResponse createMessageReceive(@RequestBody @Validated SysMessageReceiveDto request) {
        log.info("createMessageReceive");
        return sysMessageReceiveService.createMessageReceive(request);
    }

    @ApiOperation(value = "分页查询消息接收人员")
    @PostMapping("/getMessageReceiveList")
    public BaseResponse getMessageReceiveList(@RequestBody SysMessageReceiveDto request) {
        log.info("getMessageReceiveList");
        return sysMessageReceiveService.getMessageReceiveList(request);
    }

    @ApiOperation(value = "更新消息读取状态")
    @GetMapping("/updateMessageRead")
    public BaseResponse updateMessageRead(@RequestParam String id) {
        log.info("updateMessageRead");
        return sysMessageReceiveService.updateMessageRead(id);
    }


}
