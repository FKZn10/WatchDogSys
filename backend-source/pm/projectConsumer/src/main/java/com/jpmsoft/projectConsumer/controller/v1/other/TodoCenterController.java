package com.jpmsoft.projectConsumer.controller.v1.other;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.*;
import com.jpmsoft.projectConsumer.feign.BaseFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@Api(tags = "我的事务(PM)", value = "我的事务(PM)")
@RequestMapping("/v1/todoCenter")
public class TodoCenterController {
    @Autowired
    private BaseFeign baseFeign;

    /**
     * 获取用户代办信息
     *
     * @param token   用户ID
     * @param request 请求参数
     * @return
     */
    @ApiOperation(value = "获取用户代办信息", httpMethod = "POST")
    @PostMapping("/getUserTaskList")
    public BaseResponse getUserTaskList(@RequestHeader String token, @RequestBody UserTaskRequest request) {
        log.info("getUserTaskList");
        return baseFeign.getUserTaskList(token, request);
    }


    /**
     * 获取用户已办信息
     *
     * @param token   用户ID
     * @param request 请求参数
     * @return
     */
    @ApiOperation(value = "获取用户已办信息", httpMethod = "POST")
    @PostMapping("/getUserTaskProcessList")
    public BaseResponse getUserTaskProcessList(@RequestHeader String token, @RequestBody UserTaskProcessinRequest request) {
        log.info("getUserTaskProcessList");
        return baseFeign.getUserTaskProcessList(token, request);
    }

    /**
     * 我的事务PM
     * 待反馈流程
     *
     * @param token
     * @param request
     * @return
     */
    @ApiOperation(value = "获取用户待反馈流程", httpMethod = "POST")
    @PostMapping("/getUserCoordinationTaskList")
    public BaseResponse getUserCoordinationTaskList(@RequestHeader String token, @RequestBody UserCoordinationTaskRequest request) {
        log.info("getUserCoordinationTaskList");
        return baseFeign.getUserCoordinationTaskList(token, request);
    }

    /**
     * 用户抄送流程
     *
     * @param token
     * @param request
     * @return
     */
    @ApiOperation(value = "获取用户抄送流程", httpMethod = "POST")
    @PostMapping("/getUserCarbonCopyList")
    public BaseResponse getUserCarbonCopyList(@RequestHeader String token, @RequestBody UserCarbonCopyRequest request) {
        log.info("getUserCoordinationTaskList");
        return baseFeign.getUserCarbonCopyList(token, request);
    }

    /**
     * 我的事务
     * 流程反馈记录
     *
     * @param token   用户ID
     * @param request 请求参数
     * @return
     */
    @ApiOperation(value = "获取用户流程反馈记录", httpMethod = "POST")
    @PostMapping("/getUserCoordinationProcessList")
    public BaseResponse getUserCoordinationProcessList(@RequestHeader String token, @RequestBody UserCoordinationProcessRequest request) {
        log.info("getUserCoordinationProcessList");
        return baseFeign.getUserCoordinationProcessList(token, request);
    }

    /**
     * 获取用户代办数量
     * 待办数量、待阅数量、待反馈任务数量
     *
     * @param token
     * @return
     */
    @ApiOperation(value = "获取用户待办数量（待办数量、待阅数量、待反馈任务数量）")
    @GetMapping("/getUserToDoSummary")
    public BaseResponse getUserToDoSummary(@RequestHeader String token) {
        log.info("getUserToDoSummary");
        return baseFeign.getUserToDoSummary(token);
    }
}
