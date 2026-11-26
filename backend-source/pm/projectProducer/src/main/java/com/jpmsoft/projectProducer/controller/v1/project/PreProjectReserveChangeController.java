package com.jpmsoft.projectProducer.controller.v1.project;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.pojo.dto.project.PreProjectReserveChangeDTO;
import com.jpmsoft.projectProducer.service.project.IPreProjectReserveChangeService;
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
@RequestMapping("/v1/projectReserveChange")
public class PreProjectReserveChangeController {
    @Resource
    private IPreProjectReserveChangeService preProjectReserveChangeService;

    @TenantId
    @PostMapping("/createOrUpdateProjectReserveChange")
    public BaseResponse createOrUpdateProjectReserveChange(@RequestBody PreProjectReserveChangeDTO request) {
        log.info("createOrUpdateProjectReserveChange");
        return preProjectReserveChangeService.createOrUpdateProjectReserveChange(request);
    }

    @TenantId
    @PostMapping("/getProjectReserveChangeList")
    public BaseResponse getProjectReserveChangeList(@RequestBody PreProjectReserveChangeDTO request, @RequestHeader String token) {
        log.info("getProjectReserveChangeList");
        return preProjectReserveChangeService.getProjectReserveChangeList(request);
    }

    @GetMapping("/getProjectReserveChangeById")
    public BaseResponse getProjectReserveChangeById(@RequestParam String id) {
        log.info("getProjectReserveChangeById");
        return preProjectReserveChangeService.getProjectReserveChangeById(id);
    }

    @GetMapping("/syncProjectReserve")
    public BaseResponse syncProjectReserve(@RequestParam String id) {
        log.info("syncProjectReserve");
        return preProjectReserveChangeService.syncProjectReserve(id);
    }

}
