package com.jpmsoft.projectProducer.controller.v1.project;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.pojo.dto.project.ProManagePlanDTO;
import com.jpmsoft.projectProducer.service.project.IProManagePlanService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/10/18 10:33
 */
@Slf4j
@RestController
@RequestMapping("/v1/proManagePlan")
public class ProManagePlanController {

    @Resource
    private IProManagePlanService proManagePlanService;
    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createOrUpdateProManagePlan")
    public BaseResponse createOrUpdateProManagePlan(@RequestBody ProManagePlanDTO request) {
        log.info("createOrUpdateProManagePlan");
        return proManagePlanService.createOrUpdateProManagePlan(request);
    }

    @TenantId
    @PostMapping("/getProManagePlanList")
    public BaseResponse getProManagePlanList(@RequestBody ProManagePlanDTO request, @RequestHeader String token) {
        log.info("getProManagePlanList");
        return proManagePlanService.getProManagePlanList(request, redisUtils.getUid(token));
    }

    @GetMapping("/getProManagePlanById")
    public BaseResponse getProManagePlanById(@RequestParam String id) {
        log.info("getProManagePlanById");
        return proManagePlanService.getProManagePlanById(id);
    }
}
