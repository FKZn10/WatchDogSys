package com.jpmsoft.jpbpm.controller.v1;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.jpbpm.service.SysTaskAutoCompleteLogService;
import com.jpmsoft.jpbpm.util.RedisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@Api(tags = "流程任务自动完成日志", value = "流程任务自动完成日志")
@RequestMapping("/v1/sysTaskAutoCompleteLog")
public class SysTaskAutoCompleteLogController {
    @Autowired
    SysTaskAutoCompleteLogService sysTaskAutoCompleteLogService;
    @Autowired
    private RedisUtils redisUtils;
    @ApiOperation(value = "根据审批记录id过去自动完成记录")
    @PostMapping("/getTaskAutoCompleteLogByTpIds")
    public  BaseResponse getTaskAutoCompleteLogByTpIds(@RequestBody List<String> tpIds,@RequestHeader(value ="token",required = false) String token) {
        log.info("getTaskAutoCompleteLogByTpIds");
        String customerId=redisUtils.getCustomerId(token);
        return sysTaskAutoCompleteLogService.getTaskAutoCompleteLogByTpIds(tpIds,customerId);
    }


    @ApiOperation(value = "根据代办id过去异常审批完成记录")
    @PostMapping("/getTaskAutoCompleteLogByTaskIds")
    public  BaseResponse getTaskAutoCompleteLogByTaskIds(@RequestBody List<String> TaskIds,@RequestHeader(value ="token",required = false) String token) {
        log.info("getTaskAutoCompleteLogByTaskIds");
        String customerId=redisUtils.getCustomerId(token);
        return sysTaskAutoCompleteLogService.getTaskAutoCompleteLogByTaskIds(TaskIds,customerId);
    }

    @ApiOperation(value = "根据taskId删除异常的自动完成日志信息")
    @GetMapping("/deleteTaskAutoCompleteLogByTaskId")
    public   BaseResponse deleteTaskAutoCompleteLogByTaskId(@RequestParam String taskId,@RequestHeader(value ="token",required = false) String token) {
        log.info("deleteTaskAutoCompleteLogByTaskId");
        String customerId=redisUtils.getCustomerId(token);
        return sysTaskAutoCompleteLogService.deleteTaskAutoCompleteLogByTaskId(taskId,customerId);
    }
}
