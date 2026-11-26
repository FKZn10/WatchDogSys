package com.jpmsoft.projectProducer.controller.v1.project;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Project.ProAssignmentModel;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.project.IProAssignmentService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/proAssignment")
public class ProAssignmentController {

    @Resource
    private IProAssignmentService proAssignmentService;

    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createOrUpdateProAssignment")
    public BaseResponse createOrUpdateProAssignment(@RequestBody ProAssignmentModel request) {
        log.info("createOrUpdateProAssignment");
        return proAssignmentService.createOrUpdateProAssignment(request);
    }

    @TenantId
    @PostMapping("/getProAssignmentList")
    public BaseResponse getProAssignmentList(@RequestBody ProAssignmentModel request, @RequestHeader String token) {
        log.info("getProAssignmentList");
        return proAssignmentService.getProAssignmentList(request, redisUtils.getUid(token));
    }

    @GetMapping("/getProAssignmentById")
    public BaseResponse getProAssignmentById(@RequestParam String paId) {
        log.info("getProAssignmentById");
        return proAssignmentService.getProAssignmentById(paId);
    }

    @GetMapping("/syncPCompetentDepartment")
    public BaseResponse syncPCompetentDepartment(@RequestParam String paId) {
        log.info("syncPCompetentDepartment");
        return proAssignmentService.syncPCompetentDepartment(paId);
    }
}
