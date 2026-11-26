package com.jpmsoft.jpbpm.controller.v1;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.SysCoordinationTaskRequest;
import com.jpmsoft.common.model.request.UserCoordinationTaskRequest;
import com.jpmsoft.jpbpm.service.SysCoordinationTaskService;
import com.jpmsoft.jpbpm.util.RedisUtils;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@Slf4j
@RestController
@ApiIgnore
@Api(tags = "待反馈流程", value = "待反馈流程")
@RequestMapping("/v1/coordinationTask")
public class SysCoordinationTaskController {

    @Autowired
    private SysCoordinationTaskService sysCoordinationTaskService;

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 待反馈流程
     *
     * @param token   用户ID
     * @param request 请求参数
     * @return
     */
    @PostMapping("/getUserCoordinationTaskList")
    public BaseResponse getUserCoordinationTaskList(@RequestHeader(value ="token",required = false) String token, @RequestBody UserCoordinationTaskRequest request) {
        Object uId = redisUtils.get(token);
        if (uId == null)
            return new BaseResponse("Token失效！");
        log.info("getUserCoordinationTaskList");
        String customerId=redisUtils.getCustomerId(token);
        return sysCoordinationTaskService.getUserCoordinationTaskList(uId.toString(), request,customerId);
    }


    /**
     * 流程管理
     * 沟通任务
     *
     * @param request
     * @return
     */
    @PostMapping("/getCoordinationTaskList")
    public BaseResponse getCoordinationTaskList(@RequestBody SysCoordinationTaskRequest request,@RequestHeader(value ="token",required = false) String token) {
        log.info("getCoordinationTaskList");
        String customerId=redisUtils.getCustomerId(token);
        return sysCoordinationTaskService.getCoordinationTaskList(request,customerId);
    }

    @GetMapping("/getUserToDoSummary")
    public BaseResponse getUserToDoSummary(@RequestParam String userId,@RequestHeader(value ="token",required = false) String token) {
        log.info("getUserToDoSummary");
        String customerId=redisUtils.getCustomerId(token);
        return sysCoordinationTaskService.getUserToDoSummary(userId,customerId);
    }


    @GetMapping("/deleteById")
    public BaseResponse deleteById(@RequestParam String id,@RequestHeader(value ="token",required = false) String token) {
        log.info("deleteById");
        String customerId=redisUtils.getCustomerId(token);
        return sysCoordinationTaskService.deleteById(id,customerId);
    }
}
