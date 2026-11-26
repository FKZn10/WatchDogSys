package com.jpmsoft.projectConsumer.controller.v1.project;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.milestones.ProjectMilestoneRequest;
import com.jpmsoft.common.model.request.milestones.ProjectMilestonesRequest;
import com.jpmsoft.projectConsumer.feign.BaseFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@Api(tags = "项目里程碑", value = "项目里程碑")
@RequestMapping("/plan/v1/projectMilestones")
public class ProjectMilestonesController {

    @Autowired
    private BaseFeign baseFeign;

    @ApiOperation(value = "新增或修改项目里程碑")
    @PostMapping("/createOrUpdateProjectMilestones")
    public BaseResponse createOrUpdateProjectMilestones(@RequestBody ProjectMilestonesRequest request) {
        log.info("createOrUpdateProjectMilestones");
        return baseFeign.createOrUpdateProjectMilestones(request);
    }

    @ApiOperation(value = " 分页查询项目里程碑")
    @PostMapping("/getProjectMilestonesList")
    public BaseResponse getProjectMilestonesList(@RequestBody ProjectMilestonesRequest request, @RequestHeader String token) {
        log.info("getProjectMilestonesList");
        return baseFeign.getProjectMilestonesList(request, token);
    }

    @GetMapping("/getProjectMilestonesByPmsId")
    public BaseResponse getProjectMilestonesByPmId(@RequestParam String pmsId) {
        log.info("getProjectMilestonesByPmsId");
        return baseFeign.getProjectMilestonesByPmsId(pmsId);
    }

    @PostMapping("/updateProjectMilestone")
    public BaseResponse updateProjectMilestone(@RequestBody ProjectMilestoneRequest request) {
        log.info("updateProjectMilestone");
        return baseFeign.updateProjectMilestone(request);
    }

    @GetMapping("/getProjectMilestoneByPmId")
    public BaseResponse getProjectMilestoneByPmId(@RequestParam String pmId) {
        log.info("getProjectMilestoneByPmId");
        return baseFeign.getProjectMilestoneByPmId(pmId);
    }
}
