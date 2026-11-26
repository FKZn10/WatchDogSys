package com.jpmsoft.fileUpload.controller;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.fileUpload.pojo.dto.SysUserManualDTO;
import com.jpmsoft.fileUpload.service.ISysUserManualService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/12/26 9:26
 */
@Slf4j
@RestController
@Api(tags = "用户使用手册", value = "用户使用手册")
@RequestMapping("/questionSum")
public class SysUserManualController {

    @Resource
    private ISysUserManualService sysUserManualService;


    @ApiOperation(value = "新增修改用户手册")
    @PostMapping("/createOrUpdateManual")
    public BaseResponse createOrUpdateManual(@RequestBody SysUserManualDTO request, @RequestHeader String token) {
        return sysUserManualService.createOrUpdateManual(request, token);
    }

    @ApiOperation(value = "查询用户手册目录（不查询具体内容仅返回目录结构）")
    @PostMapping("/getUserManualTree")
    public BaseResponse getUserManualTree(@RequestBody SysUserManualDTO request) {
        return sysUserManualService.getUserManualTree(request);
    }


    @ApiOperation(value = "ID删除")
    @PostMapping("/deleteUserManualById")
    public BaseResponse deleteUserManualById(@RequestBody SysUserManualDTO request) {
        return sysUserManualService.deleteUserManualById(request);
    }

    @ApiOperation(value = "ID查询用户手册具体信息")
    @GetMapping("/getUserManualById")
    public BaseResponse getUserManualById(@RequestParam String id) {
        return sysUserManualService.getUserManualById(id);
    }


    @ApiOperation(value = "关键词查询用户手册具体信息")
    @GetMapping("/getUserManualByKey")
    public BaseResponse getUserManualByKey(@RequestParam String key) {
        return sysUserManualService.getUserManualByKey(key);
    }

    @ApiOperation(value = "编码查询用户手册具体信息")
    @GetMapping("/getUserManualByCode")
    public BaseResponse getUserManualByCode(@RequestParam String code) {
        return sysUserManualService.getUserManualByCode(code);
    }
}
