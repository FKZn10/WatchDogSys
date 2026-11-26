package com.jpmsoft.projectConsumer.controller.v1.project;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.feign.PreFeign;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@Api(tags = "项目中标明细表", value = "项目明中标细表")
@RequestMapping("/v1/proWnDetail")
public class ProWnDetailController {

    @Resource
    private PreFeign preFeign;

    @ApiOperation(value = "根据ID查询项目中标明细")
    @GetMapping("/getProWnDetailById")
    public BaseResponse getProWnDetailById(@RequestParam String pwId) {
        log.info("getProWnDetailById");
        return preFeign.getProWnDetailById(pwId);
    }

    @ApiOperation(value = "根据项目ID查询项目中标明细")
    @GetMapping("/getProWnDetailByPid")
    public BaseResponse getProWnDetailByPid(@RequestParam String pwPid) {
        log.info("getProWnDetailByPid");
        return preFeign.getProWnDetailByPid(pwPid);
    }
}