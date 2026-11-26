package com.jpmsoft.projectProducer.controller.v1.project;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Project.ProConstructionPlanModel;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.project.IProConstructionPlanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/proConstPlan")
public class ProConstructionPlanController {

    @Resource
    private IProConstructionPlanService proConstructionPlanService;

    @TenantId
    @PostMapping("/createOrUpdateProConstructionPlan")
    public BaseResponse createOrUpdateProConstructionPlan(@RequestBody ProConstructionPlanModel request) {
        log.info("createOrUpdateProConstructionPlan");
        return proConstructionPlanService.createOrUpdateProConstructionPlan(request);
    }

    @TenantId
    @PostMapping("/getProConstructionPlanList")
    public BaseResponse getProConstructionPlanList(@RequestBody ProConstructionPlanModel request) {
        log.info("getProConstructionPlanList");
        return proConstructionPlanService.getProConstructionPlanList(request);
    }

    @GetMapping("/getProConstructionPlanById")
    public BaseResponse getProConstructionPlanById(@RequestParam String pcpId) {
        log.info("getProConstructionPlanById");
        return proConstructionPlanService.getProConstructionPlanById(pcpId);
    }
}
