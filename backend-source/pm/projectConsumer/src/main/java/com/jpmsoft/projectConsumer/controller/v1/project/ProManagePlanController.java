package com.jpmsoft.projectConsumer.controller.v1.project;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.pojo.dto.project.ProManagePlanDTO;
import com.jpmsoft.projectConsumer.feign.BaseFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/10/18 10:33
 */
@Slf4j
@RestController
@Api(tags = "建设管理方案", value = "建设管理方案")
@RequestMapping("/v1/proManagePlan")
public class ProManagePlanController {

    @Resource
    private BaseFeign baseFeign;

    @ApiOperation(value = "新增修改建设管理方案")
    @PostMapping("/createOrUpdateProManagePlan")
    public BaseResponse createOrUpdateProManagePlan(@RequestBody ProManagePlanDTO request) {
        log.info("createOrUpdateProManagePlan");
        return baseFeign.createOrUpdateProManagePlan(request);
    }

    @ApiOperation(value = "分页查询建设管理方案")
    @PostMapping("/getProManagePlanList")
    public BaseResponse getProManagePlanList(@RequestBody ProManagePlanDTO request, @RequestHeader String token) {
        log.info("getProManagePlanList");
        return baseFeign.getProManagePlanList(request, token);
    }

    @ApiOperation(value = "ID查询建设管理方案")
    @GetMapping("/getProManagePlanById")
    public BaseResponse getProManagePlanById(@RequestParam String id) {
        log.info("getProManagePlanById");
        return baseFeign.getProManagePlanById(id);
    }
}
