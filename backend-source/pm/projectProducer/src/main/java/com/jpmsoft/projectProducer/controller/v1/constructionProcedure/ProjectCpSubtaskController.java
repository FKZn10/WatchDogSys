package com.jpmsoft.projectProducer.controller.v1.constructionProcedure;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.proCp.ProjectCpSubtaskRequest;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.constructionProcedure.IProjectCpSubtaskService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/ConstructionProcedure/ProjectCpSubtask")
public class ProjectCpSubtaskController {
    @Resource
    private IProjectCpSubtaskService projectCpSubtaskService;
    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createOrUpdateProjectCpSubtask")
    public BaseResponse createOrUpdateProjectCpSubtask(@RequestBody ProjectCpSubtaskRequest request) {
        log.info("createOrUpdateProjectCpSubtask");
        return projectCpSubtaskService.createOrUpdateProjectCpSubtask(request);
    }

    @GetMapping("/deleteProjectCpSubtaskById")
    public BaseResponse deleteProjectCpSubtaskById(@RequestParam String pcsId) {
        log.info("deleteProjectCpSubtaskById");
        return projectCpSubtaskService.deleteProjectCpSubtaskById(pcsId);
    }

    @GetMapping("/getProjectCpSubtaskById")
    public BaseResponse getProjectCpSubtaskById(@RequestParam String pcsId) {
        log.info("getProjectCpSubtaskById");
        return projectCpSubtaskService.getProjectCpSubtaskById(pcsId);
    }

    @TenantId
    @PostMapping("/getProjectCpSubtaskList")
    public BaseResponse getProjectCpSubtaskList(@RequestBody ProjectCpSubtaskRequest request) {
        if (request.getPcsHandler() != null && !request.getPcsHandler().equals("")) {
            request.setPcsHandler(redisUtils.getUid(request.getPcsHandler()));
        } else {
            request.setPcsHandler(null);
        }
        log.info("getProjectCpSubtaskList");
        return projectCpSubtaskService.getProjectCpSubtaskList(request);
    }
}
