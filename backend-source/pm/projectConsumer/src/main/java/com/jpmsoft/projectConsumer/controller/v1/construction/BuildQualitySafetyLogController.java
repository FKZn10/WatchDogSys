package com.jpmsoft.projectConsumer.controller.v1.construction;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.pojo.dto.construction.BuildQualitySafetyLogDto;
import com.jpmsoft.projectConsumer.feign.BaseFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: 李文涛 aaron
 * @date: 2022/7/8 18:08
 */
@Slf4j
@RestController
@Api(tags = "施工质量安全日志", value = "施工质量安全日志")
@RequestMapping("/v1/buildQualitySafetyLog")
public class BuildQualitySafetyLogController {

    @Autowired
    private BaseFeign baseFeign;

    @ApiOperation(value = "新增施工质量安全日志")
    @PostMapping("/createBuildQualitySafetyLog")
    public BaseResponse createBuildQualitySafetyLog(@RequestBody BuildQualitySafetyLogDto request) {
        log.info("createBuildQualitySafetyLog");
        return baseFeign.createBuildQualitySafetyLog(request);
    }

    @ApiOperation(value = "修改施工质量安全日志")
    @PostMapping("/updateBuildQualitySafetyLog")
    public BaseResponse updateBuildQualitySafetyLog(@RequestBody BuildQualitySafetyLogDto request) {
        log.info("updateBuildQualitySafetyLog");
        return baseFeign.updateBuildQualitySafetyLog(request);
    }

    @ApiOperation(value = "分页查询施工质量安全日志")
    @PostMapping("/getBuildQualitySafetyLogList")
    public BaseResponse getBuildQualitySafetyLogList(@RequestBody BuildQualitySafetyLogDto request, @RequestHeader String token) {
        log.info("getBuildQualitySafetyLogList");
        return baseFeign.getBuildQualitySafetyLogList(request, token);
    }

    @ApiOperation(value = "ID查询施工质量安全日志")
    @GetMapping("/getBuildQualitySafetyLogById")
    public BaseResponse getBuildQualitySafetyLogById(@RequestParam String id) {
        log.info("getBuildQualitySafetyLogById");
        return baseFeign.getBuildQualitySafetyLogById(id);
    }

}
