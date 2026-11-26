package com.jpmsoft.userProducer.controller.v1;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Sms.SysShortMessageModel;
import com.jpmsoft.userProducer.annotations.TenantId;
import com.jpmsoft.userProducer.service.ISysShortMessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@Slf4j
@RestController
@Api(tags = "短信待发池", value = "短信待发池")
@RequestMapping("/sms/v1/shortMessage")
public class SysShortMessageController {

    @Resource
    private ISysShortMessageService sysShortMessageService;

    @TenantId
    @ApiOperation(value = "获取短信待发池列表")
    @PostMapping("/getSysShortMessageList")
    public BaseResponse getSysShortMessageList(@RequestBody SysShortMessageModel request) {
        log.info("getSysShortMessageList");
        return sysShortMessageService.getSysShortMessageList(request);
    }

    @TenantId
    @ApiOperation(value = "新增修改短信待发池")
    @PostMapping("/createOrUpdateShortMessage")
    public BaseResponse createOrUpdateShortMessage(@RequestBody SysShortMessageModel request) {
        log.info("createOrUpdateShortMessage");
        return sysShortMessageService.createOrUpdateShortMessage(request);
    }

    @TenantId
    @ApiOperation(value = "新增修改短信待发池（去防抖）")
    @PostMapping("/addOrEditShortMessage")
    public BaseResponse addOrEditShortMessage(@RequestBody SysShortMessageModel request) {
        log.info("addOrEditShortMessage");
        return sysShortMessageService.createOrUpdateShortMessage(request);
    }

    @ApiOperation(value = "短信发送-光明项目")
    @GetMapping("/gmSendSmsByMessage")
    public BaseResponse gmSendSmsByMessage() {
        log.info("gmSendSmsByMessage");
        return sysShortMessageService.gmSendSmsByMessage();
    }


    @TenantId
    @ApiOperation(value = "批量新增短信待发池")
    @PostMapping("/batchCreateShortMessage")
    public BaseResponse batchCreateShortMessage(@RequestBody List<SysShortMessageModel> request) {
        log.info("batchCreateShortMessage");
        return sysShortMessageService.batchCreateShortMessage(request);
    }

}
