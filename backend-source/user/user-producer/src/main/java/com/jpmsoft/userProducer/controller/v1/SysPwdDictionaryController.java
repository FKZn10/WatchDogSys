package com.jpmsoft.userProducer.controller.v1;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.SysPwdDictionaryDTO;
import com.jpmsoft.userProducer.service.ISysPwdDictionaryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/5/26 11:56
 */
@Slf4j
@RestController
@Api(tags = "密码库", value = "密码库")
@RequestMapping("/v1/SystemManage")
public class SysPwdDictionaryController {

    @Resource
    private ISysPwdDictionaryService sysPwdDictionaryService;

    @ApiOperation(value = "新增修改密码库")
    @PostMapping("/createOrUpdatePwdDictionary")
    public BaseResponse createOrUpdatePwdDictionary(@RequestBody @Validated SysPwdDictionaryDTO request) {
        log.info("createOrUpdatePwdDictionary");
        return sysPwdDictionaryService.createOrUpdatePwdDictionary(request);
    }

    @ApiOperation(value = "分页查询密码库")
    @PostMapping("/getPwdDictionaryList")
    public BaseResponse getPwdDictionaryList(@RequestBody SysPwdDictionaryDTO request) {
        log.info("getPwdDictionaryList");
        return sysPwdDictionaryService.getPwdDictionaryList(request);
    }

    @ApiOperation(value = "ID删除")
    @GetMapping("/deletePwdDictionaryById")
    public BaseResponse deletePwdDictionaryById(@RequestParam String id) {
        log.info("deletePwdDictionaryById");
        return sysPwdDictionaryService.deletePwdDictionaryById(id);
    }
}
