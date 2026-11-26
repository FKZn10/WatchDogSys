package com.jpmsoft.userProducer.controller.v2;


import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.UserDto;
import com.jpmsoft.userProducer.service.v2.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


@Slf4j
@Api(tags = "用户登录", value = "用户登录")
@RestController("UserLog2")
@Deprecated
@RequestMapping("/v2/SystemManage")
public class SysUserLoginController {

    @Resource
    private ISysUserService sysUserService;


    @ApiOperation(value = "用户登录")
    @PostMapping("/login")
    public BaseResponse login(@RequestBody UserDto user, HttpServletRequest request) {
        log.info("登录 login");
        String sessionId = request.getHeader("Se-Auth-Code");
        return sysUserService.login(user, sessionId, request);
    }


    @ApiOperation(value = "用户登录(不校验验证码)")
    @PostMapping("/loginNotCode")
    BaseResponse loginNotCode(@RequestBody UserDto user, HttpServletRequest request) {
        log.info("不校验验证码登录 loginNotCode");
        return sysUserService.loginType(user, "app", request);
    }

}
