package com.jpmsoft.projectConsumer.controller.v1.project;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.pojo.dto.project.ProYearConsPlanDto;
import com.jpmsoft.projectConsumer.feign.PreFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author: 李文涛 aaron
 * @date: 2022/11/9 15:42
 */
@Slf4j
@RestController
@Api(tags = "年度计划申报管理", value = "年度计划申报管理")
@RequestMapping("/v1/proYearConsPlan")
public class ProYearConsPlanController {

    @Resource
    private PreFeign preFeign;

    @ApiOperation(value = "新增修改年度计划申报管理")
    @PostMapping("/createOrUpdateProYearConsPlan")
    public BaseResponse createOrUpdateProYearConsPlan(@RequestBody ProYearConsPlanDto request) {
        log.info("createOrUpdateProYearConsPlan");
        return preFeign.createOrUpdateProYearConsPlan(request);
    }

    @ApiOperation(value = "分页查询年度计划申报管理")
    @PostMapping("/getProYearConsPlanList")
    public BaseResponse getProYearConsPlanList(@RequestBody ProYearConsPlanDto request) {
        log.info("getProYearConsPlanList");
        return preFeign.getProYearConsPlanList(request);
    }

    @ApiOperation(value = "ID查询年度计划申报管理")
    @GetMapping("/getProYearConsPlanById")
    public BaseResponse getProYearConsPlanById(@RequestParam String id) {
        log.info("getProYearConsPlanById");
        return preFeign.getProYearConsPlanById(id);
    }
}
