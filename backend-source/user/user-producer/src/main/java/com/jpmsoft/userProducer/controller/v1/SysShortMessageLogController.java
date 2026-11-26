package com.jpmsoft.userProducer.controller.v1;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Sms.SysShortMessageLogModel;
import com.jpmsoft.userProducer.service.ISysShortMessageLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@Api(tags = "短信发送记录", value = "短信发送记录")
@RequestMapping("/sms/v1/shortMessageLog")
public class SysShortMessageLogController {


    @Resource
    private ISysShortMessageLogService sysShortMessageLogService;

    @ApiOperation(value = "获取短信发送记录列表")
    @PostMapping("/getSysShortMessageLogList")
    public BaseResponse getSysShortMessageLogList(@RequestBody SysShortMessageLogModel request) {
        log.info("getSysShortMessageLogList");
        return sysShortMessageLogService.getSysShortMessageLogList(request);
    }
}
