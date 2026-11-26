package com.jpmsoft.projectProducer.controller.v1.constructionProcedure;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.proCp.ProjectCpExperienceRequest;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.constructionProcedure.IProjectCpExperienceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/ConstructionProcedure/proCpExperience")
public class ProjectCpExperienceController {
    @Resource
    private IProjectCpExperienceService projectCpExperienceService;

    @TenantId
    @PostMapping("/createOrUpdateProCpExperience")
    public BaseResponse createOrUpdateProCpExperience(@RequestBody ProjectCpExperienceRequest request) {
        log.info("createOrUpdateProCpExperience");
        return projectCpExperienceService.createOrUpdateProCpExperience(request);
    }

    @GetMapping("/deleteProCpExperienceById")
    public BaseResponse deleteProCpExperienceById(@RequestParam String cpeId) {
        log.info("deleteProCpExperienceById");
        return projectCpExperienceService.deleteProCpExperienceById(cpeId);
    }

    @TenantId
    @PostMapping("/getProCpExperienceList")
    public BaseResponse getProCpExperienceList(@RequestBody ProjectCpExperienceRequest request) {
        log.info("getProCpExperienceList");
        return projectCpExperienceService.getProCpExperienceList(request);
    }
}
