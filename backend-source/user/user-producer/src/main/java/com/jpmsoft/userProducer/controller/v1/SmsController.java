package com.jpmsoft.userProducer.controller.v1;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.service.ISmsServerService;
import com.jpmsoft.userProducer.utils.RedisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;

/**
 * @author: 李文涛 aaron
 * @date: 2022/9/1 16:54
 */
@Slf4j
@RestController
@Api(tags = "短信发送", value = "短信发送")
@RequestMapping("/sms/v1/smsSend")
public class SmsController {

    @Resource
    private ISmsServerService smsServerService;
    @Resource
    private RedisUtils redisUtils;

    @ApiIgnore
    @GetMapping("/sendCodeSms")
    public BaseResponse sendCodeSms(@RequestParam String phoneNumber, @RequestParam String type) {
        return smsServerService.sendCodeSms(phoneNumber, type);
    }

    @ApiOperation(value = "登录短信验证码(token识别用户)")
    @GetMapping("/sendCheckSms")
    public BaseResponse sendCheckSms(@RequestHeader String token) {
        return smsServerService.sendCodeSmsByToken(redisUtils.getUserInfoByToken(token), "login");
    }

    @ApiOperation(value = "登录验证码")
    @GetMapping("/sendLoginSms")
    public BaseResponse sendCodeSms(@RequestParam String phoneNumber) {
        return smsServerService.sendCodeSms(phoneNumber, "login");
    }

    @ApiOperation(value = "忘记密码验证码")
    @GetMapping("/sendSecretSms")
    public BaseResponse sendSecretSms(@RequestParam String phoneNumber) {
        return smsServerService.sendCodeSms(phoneNumber, "secret");
    }


    @ApiOperation(value = "外部表单提交验证码")
    @GetMapping("/sendSubmitSms")
    public BaseResponse sendSubmitSms(@RequestParam String phoneNumber, @RequestParam(required = false) String tempCode) {
        return smsServerService.sendCodeSms(phoneNumber, "submit", tempCode, false);
    }

    @ApiOperation(value = "验证表单提交短信")
    @GetMapping("/checkSubmitSms")
    public BaseResponse checkSubmitSms(@RequestParam String phoneNumber, @RequestParam String code) {
        return BaseResponse.ok(smsServerService.checkSmsCode(phoneNumber, "submit", code));
    }

}
