package com.jpmsoft.projectConsumer.controller.v1.project;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.feign.PreFeign;
import com.jpmsoft.projectConsumer.pojo.dto.project.PreProjectReserveChangeDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 城建项目储备
 *
 * @Author: 李文涛 aaron
 * @date: 2025/3/6 14:55
 */
@Slf4j
@RestController
@Api(tags = "项目储备变更", value = "项目储备变更")
@RequestMapping("/v1/projectReserveChange")
public class PreProjectReserveChangeController {
    @Resource
    private PreFeign preFeign;

    @ApiOperation(value = "创建修改项目储备变更")
    @PostMapping("/createOrUpdateProjectReserveChange")
    public BaseResponse createOrUpdateProjectReserveChange(@RequestBody PreProjectReserveChangeDTO request) {
        log.info("createOrUpdateProjectReserveChange");
        return preFeign.createOrUpdateProjectReserveChange(request);
    }

    @ApiOperation(value = "分页查询项目储备变更")
    @PostMapping("/getProjectReserveChangeList")
    public BaseResponse getProjectReserveChangeList(@RequestBody PreProjectReserveChangeDTO request, @RequestHeader String token) {
        log.info("getProjectReserveChangeList");
        return preFeign.getProjectReserveChangeList(request, token);
    }

    @ApiOperation(value = "ID查询项目储备变更")
    @GetMapping("/getProjectReserveChangeById")
    public BaseResponse getProjectReserveChangeById(@RequestParam String id) {
        log.info("getProjectReserveChangeById");
        return preFeign.getProjectReserveChangeById(id);
    }


    @ApiOperation(value = "审批结束同步数据到源目标")
    @GetMapping("/syncProjectReserve")
    public BaseResponse syncProjectReserve(@RequestParam String id) {
        log.info("syncProjectReserve");
        return preFeign.syncProjectReserve(id);
    }

}
