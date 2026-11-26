package com.jpmsoft.projectProducer.controller.v1.project;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.milestones.ProjectMilestoneRequest;
import com.jpmsoft.common.model.request.milestones.ProjectMilestonesRequest;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.project.IProjectMilestonesService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@Slf4j
@RestController
@RequestMapping("/v1/projectMilestones")
public class ProjectMilestonesController {

    @Resource
    private IProjectMilestonesService milestonesService;
    @Resource
    private RedisUtils redisUtils;

    @PostMapping("/createOrUpdateProjectMilestones")
    public BaseResponse createOrUpdateConPay(@RequestBody ProjectMilestonesRequest request) {
        log.info("createOrUpdateProjectMilestones");
        return milestonesService.createOrUpdateProjectMilestones(request);
    }

    @TenantId
    @PostMapping("/getProjectMilestonesList")
    public BaseResponse getProjectMilestonesList(@RequestBody ProjectMilestonesRequest request, @RequestHeader String token) {
        log.info("getProjectMilestonesList");
        return milestonesService.getProjectMilestonesList(request, redisUtils.getUid(token));
    }

    @GetMapping("/getProjectMilestonesByPmsId")
    public BaseResponse getProjectMilestonesByPmsId(@RequestParam String pmsId) {
        log.info("getProjectMilestonesByPmsId");
        return milestonesService.getProjectMilestonesByPmsId(pmsId);
    }

    @PostMapping("/updateProjectMilestone")
    public BaseResponse updateProjectMilestone(@RequestBody ProjectMilestoneRequest request) {
        log.info("updateProjectMilestone");
        return milestonesService.updateProjectMilestone(request);
    }

    @GetMapping("/getProjectMilestoneByPmId")
    public BaseResponse getProjectMilestoneByPmId(@RequestParam String pmId) {
        log.info("getProjectMilestoneByPmId");
        return milestonesService.getProjectMilestoneByPmId(pmId);
    }
}
