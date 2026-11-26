package com.jpmsoft.projectProducer.controller.v1.other;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.*;
import com.jpmsoft.projectProducer.service.other.ITodoCenterService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/todoCenter")
public class TodoCenterController {

    @Resource
    private ITodoCenterService todoCenterService;

    @Resource
    private RedisUtils redisUtils;

    /**
     * 获取用户代办信息
     *
     * @param token   用户ID
     * @param request 请求参数
     * @return
     */
    @PostMapping("/getUserTaskList")
    public BaseResponse getUserTaskList(@RequestHeader String token, @RequestBody UserTaskRequest request) {
        log.info("getUserTaskList");
        return todoCenterService.getUserTaskList(token, request);
    }


    /**
     * 获取用户已办信息
     *
     * @param token   用户ID
     * @param request 请求参数
     * @return
     */
    @PostMapping("/getUserTaskProcessList")
    public BaseResponse getUserTaskProcessList(@RequestHeader String token, @RequestBody UserTaskProcessinRequest request) {
        log.info("getUserTaskProcessList");
        return todoCenterService.getUserTaskProcessList(token, request);
    }

    /**
     * 我的事务PM
     * 待反馈流程
     *
     * @param token
     * @param request
     * @return
     */
    @PostMapping("/getUserCoordinationTaskList")
    public BaseResponse getUserCoordinationTaskList(@RequestHeader String token, @RequestBody UserCoordinationTaskRequest request) {
        log.info("getUserCoordinationTaskList");
        return todoCenterService.getUserCoordinationTaskList(token, request);
    }

    /**
     * 用户抄送流程
     *
     * @param token
     * @param request
     * @return
     */
    @PostMapping("/getUserCarbonCopyList")
    public BaseResponse getUserCarbonCopyList(@RequestHeader String token, @RequestBody UserCarbonCopyRequest request) {
        log.info("getUserCoordinationTaskList");
        return todoCenterService.getUserCarbonCopyList(token, request);
    }

    /**
     * 我的事务
     * 流程反馈记录
     *
     * @param token   用户ID
     * @param request 请求参数
     * @return
     */
    @PostMapping("/getUserCoordinationProcessList")
    public BaseResponse getUserCoordinationProcessList(@RequestHeader String token, @RequestBody UserCoordinationProcessRequest request) {
        log.info("getUserCoordinationProcessList");
        return todoCenterService.getUserCoordinationProcessList(token, request);
    }


    /**
     * 获取用户代办数量
     * 待办数量、待阅数量、待反馈任务数量
     *
     * @param token
     * @return
     */
    @GetMapping("/getUserToDoSummary")
    public BaseResponse getUserToDoSummary(@RequestHeader String token) {
        log.info("getUserToDoSummary");
        return todoCenterService.getUserToDoSummary(redisUtils.getUid(token));
    }
}
