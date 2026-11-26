package com.jpmsoft.jputils.controller;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.jputils.dto.*;
import com.jpmsoft.jputils.sms.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author: 李文涛 aaron
 * @date: 2022/8/11 9:55
 */
@Slf4j
@RestController
@Api(tags = "短信服务", value = "短信服务")
@RequestMapping("/v1/sms")
public class SmsController {

    @Resource
    private GmSms gmSms;

    /**
     * 发送阿里云短信
     *
     * @param sms
     */
    @ApiOperation(value = "发送阿里云短信（单条）")
    @PostMapping("/sendAliyunSms")
    public BaseResponse sendAliyunSms(@RequestBody AliyunSmsDto sms) {
        return AliyunSms.sendSms(sms);
    }


    @ApiOperation(value = "苏相短信")
    @PostMapping("/sendSxSms")
    public BaseResponse sendSxSms(@RequestBody SxSmsDto sms) {
        return SxSms.sendSms(sms);
    }

    @ApiOperation(value = "光明短信发送")
    @PostMapping("/sendGmSms")
    public BaseResponse sendGmSms(@RequestBody GmSmsWebServiceDto sms) {
        return BaseResponse.ok(gmSms.doPostSoap(sms));
    }

    @ApiOperation(value = "华为短信发送")
    @PostMapping("/sendHwSms")
    public BaseResponse sendHwSms(@RequestBody HwSmsDto sms) {
        return HwSms.send(sms);
    }

    @ApiOperation(value = "华为IOT短信发送")
    @PostMapping("/sendHwIotSms")
    public BaseResponse sendHwIotSms(@RequestBody HwSmsDto sms) throws Exception {
        return HwIotSms.send(sms);
    }

    @ApiOperation(value = "华润短信发送")
    @PostMapping("/sendCrcSms")
    public BaseResponse sendCrcSms(@RequestBody CrcSmsDto sms) {
        return CrcSms.send(sms);
    }

    @ApiIgnore
    @GetMapping("/sendBath")
    public void sendBath() {
        AliyunBathSmsDto bathSms = new AliyunBathSmsDto();
        bathSms.setPhoneNumbers(Arrays.asList("18617114587", "13264621236"));
        bathSms.setSignNames(Arrays.asList("阿里云短信测试", "阿里云短信测试"));
        bathSms.setTemplateCode("SMS_248485252");
        Map<String, String> code = new HashMap<>();
        code.put("code", "95588");
        List<Object> params = new ArrayList<>();
        params.add(code);
        code.put("code", "85588");
        params.add(code);
        bathSms.setTemplateParams(params);
        AliyunSms.sendBatchSms(bathSms);
    }


    /**
     * wudz
     *
     * @param sms
     * @return
     */
    @ApiOperation(value = "发送移动短信（单条）")
    @PostMapping("/sendYdSms")
    public BaseResponse sendYdMasSms(@RequestBody ChinaYdSmsDTO sms) {
        return YdSms.send(sms);
    }

    /**
     * submail 短信发送
     *
     * @param sms
     * @return
     */
    @ApiOperation(value = "发送移动短信（单条）")
    @PostMapping("/sendSubSms")
    public BaseResponse sendSubSms(@RequestBody HwSmsDto sms) {
        return SubMailSms.send(sms);
    }

}
