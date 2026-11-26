package com.jpmsoft.custom.controller;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.custom.pojo.dto.TableActionDTO;
import com.jpmsoft.custom.service.ISysTableService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/1/29 9:19
 */
@Slf4j
@RestController
@Api(tags = "自定义表单通用接口", value = "自定义表单通用接口")
@RequestMapping("/v1/tb")
public class SysTableController {
    @Resource
    private ISysTableService sysTableService;

    @ApiOperation(value = "表创建")
    @PostMapping("/create")
    public BaseResponse create(@RequestBody TableActionDTO request) {
        return sysTableService.create(request);
    }

    @ApiOperation(value = "新增列")
    @PostMapping("/add")
    public BaseResponse add(@RequestBody TableActionDTO request) {
        return sysTableService.addColumn(request);
    }

    @ApiOperation(value = "修改列")
    @PostMapping("/modify")
    public BaseResponse modify(@RequestBody TableActionDTO request) {
        return sysTableService.modifyColumn(request);
    }

    @ApiOperation(value = "删除列")
    @PostMapping("/dropColumn")
    public BaseResponse dropColumn(@RequestBody TableActionDTO request) {
        return sysTableService.dropColumn(request);
    }

}
