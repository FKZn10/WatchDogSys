package com.jpmsoft.fileUpload.controller;

import com.jpmsoft.fileUpload.pojo.dto.SysLoginLogDto;
import com.jpmsoft.fileUpload.service.ISysLoginLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@Api(tags = "登录日志", value = "登录日志")
@RestController
@RequestMapping("/user/login")
public class UserLoginController {

    @Autowired

    private ISysLoginLogService iSysLoginLogService;


    @ApiOperation(value = "登录日志导出")
    @PostMapping("/ExportUserLoginLog")
    public ResponseEntity<byte[]> ExportUserLoginLog(@RequestBody SysLoginLogDto sysLoginLogDto, HttpServletResponse response){
        return  iSysLoginLogService.ExportUserLoginLog(sysLoginLogDto,response);
    }
}
