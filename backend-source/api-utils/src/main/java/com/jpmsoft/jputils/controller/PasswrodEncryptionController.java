package com.jpmsoft.jputils.controller;

import com.jpmsoft.jputils.service.PasswrodEncryptionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@Api(tags = "密码加密处理", value = "密码加密处理")
@RequestMapping("/v1/encryption")
public class PasswrodEncryptionController {

    @Autowired
    private PasswrodEncryptionService passwrodEncryptionService;

    /**
     * 密码盐值获取加密结果
     *
     * @param passWord
     * @param salt
     * @return
     */
    @ApiOperation(value = "密码盐值获取加密结果")
    @GetMapping("/passwrodEncryptionResult")
    public String passwrodEncryptionResult(@RequestParam String passWord, @RequestParam String salt) {
        return passwrodEncryptionService.PasswrodEncryptionResult(passWord, salt);
    }
}
