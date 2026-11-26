package com.jpmsoft.projectProducer.controller.v1.construction;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.pojo.dto.construction.BuildQualitySafetyLogDto;
import com.jpmsoft.projectProducer.service.construction.IBuildQualitySafetyLogService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author: 李文涛 aaron
 * @date: 2022/7/8 18:08
 */
@Slf4j
@RestController
@RequestMapping("/v1/buildQualitySafetyLog")
public class BuildQualitySafetyLogController {

    @Resource
    private IBuildQualitySafetyLogService buildQualitySafetyLogService;
    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createBuildQualitySafetyLog")
    public BaseResponse createBuildQualitySafetyLog(@RequestBody BuildQualitySafetyLogDto request) {
        log.info("createBuildQualitySafetyLog");
        return buildQualitySafetyLogService.createBuildQualitySafetyLog(request);
    }

    @TenantId
    @PostMapping("/updateBuildQualitySafetyLog")
    public BaseResponse updateBuildQualitySafetyLog(@RequestBody BuildQualitySafetyLogDto request) {
        log.info("updateBuildQualitySafetyLog");
        return buildQualitySafetyLogService.updateBuildQualitySafetyLog(request);
    }

    @TenantId
    @PostMapping("/getBuildQualitySafetyLogList")
    public BaseResponse getBuildQualitySafetyLogList(@RequestBody BuildQualitySafetyLogDto request, @RequestHeader String token) {
        log.info("getBuildQualitySafetyLogList");
        return buildQualitySafetyLogService.getBuildQualitySafetyLogList(request, redisUtils.getUid(token));
    }

    @GetMapping("/getBuildQualitySafetyLogById")
    public BaseResponse getBuildQualitySafetyLogById(@RequestParam String id) {
        log.info("getBuildQualitySafetyLogById");
        return buildQualitySafetyLogService.getBuildQualitySafetyLogById(id);
    }

}
