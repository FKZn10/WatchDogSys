package com.jpmsoft.projectProducer.service.project;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.milestones.ProjectMilestoneRequest;
import com.jpmsoft.common.model.request.milestones.ProjectMilestonesRequest;
import com.jpmsoft.projectProducer.pojo.entity.project.ProjectMilestones;

public interface IProjectMilestonesService extends IService<ProjectMilestones> {
    BaseResponse createOrUpdateProjectMilestones(ProjectMilestonesRequest request);

    BaseResponse getProjectMilestonesList(ProjectMilestonesRequest request, String uid);

    BaseResponse getProjectMilestonesByPmsId(String pmsId);

    BaseResponse updateProjectMilestone(ProjectMilestoneRequest request);

    BaseResponse getProjectMilestoneByPmId(String pmId);
}
