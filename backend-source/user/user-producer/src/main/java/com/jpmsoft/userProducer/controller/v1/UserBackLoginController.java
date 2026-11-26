package com.jpmsoft.userProducer.controller.v1;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.UserDto;
import com.jpmsoft.userProducer.pojo.dto.sms.UserSmsLoginDto;
import com.jpmsoft.userProducer.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/6/25 14:34
 */
@Slf4j
@Api(tags = "用户登录(后台管理端)", value = "用户登录（后台管理端）")
@RestController
@RequestMapping("/back/v1/SystemManage")
public class UserBackLoginController {

    @Resource
    private ISysUserService sysUserService;


    @ApiOperation(value = "用户登录")
    @PostMapping("/login")
    public BaseResponse login(@RequestBody UserDto user, HttpServletRequest request) {
        log.info("登录 login");
        String sessionId = request.getHeader("Se-Auth-Code");
        return sysUserService.login(user, sessionId, true, request);
    }


    @ApiOperation(value = "用户登录(后台),验证密码+短信验证码")
    @PostMapping("/backLogin")
    public BaseResponse backLogin(@RequestBody UserDto user, HttpServletRequest request) {
        log.info("登录 backLogin");
        String sessionId = request.getHeader("Se-Auth-Code");
        return sysUserService.backLogin(user, sessionId, true, request);
    }

    @ApiOperation(value = "用户登录,短信验证码方式")
    @PostMapping("/loginSms")
    public BaseResponse loginSms(@RequestBody @Validated UserSmsLoginDto user, HttpServletRequest request) {
        log.info("登录 loginSms");
        return sysUserService.loginSms(user, true, request);
    }
}
