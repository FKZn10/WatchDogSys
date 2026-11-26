package com.jpmsoft.jpbpm.controller.v1;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.SysCoordinationProcessRequest;
import com.jpmsoft.common.model.request.UserCoordinationProcessRequest;
import com.jpmsoft.jpbpm.service.SysCoordinationProcessService;
import com.jpmsoft.jpbpm.util.RedisUtils;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@Slf4j
@RestController
@ApiIgnore
@Api(tags = "流程反馈记录", value = "流程反馈记录")
@RequestMapping("/v1/coordinationProcess")
public class SysCoordinationProcessController {

    @Autowired
    private SysCoordinationProcessService sysCoordinationProcessService;

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 我的事务
     * 流程反馈记录
     *
     * @param token   用户ID
     * @param request 请求参数
     * @return
     */
    @PostMapping("/getUserCoordinationProcessList")
    public BaseResponse getUserCoordinationProcessList(@RequestHeader(value ="token",required = false) String token, @RequestBody UserCoordinationProcessRequest request) {
        Object uId = redisUtils.get(token);
        if (uId == null)
            return new BaseResponse("Token失效！");
        log.info("getUserCoordinationProcessList");
        String customerId=redisUtils.getCustomerId(token);
        return sysCoordinationProcessService.getUserCoordinationProcessList(uId.toString(), request,customerId);
    }

    /**
     * 流程管理
     * 沟通记录
     *
     * @param request
     * @return
     */
    @PostMapping("/getCoordinationProcessList")
    public BaseResponse getCoordinationProcessList(@RequestBody SysCoordinationProcessRequest request,@RequestHeader(value ="token",required = false) String token) {
        log.info("getCoordinationProcessList");
        String customerId=redisUtils.getCustomerId(token);
        return sysCoordinationProcessService.getCoordinationProcessList(request,customerId);
    }

}
