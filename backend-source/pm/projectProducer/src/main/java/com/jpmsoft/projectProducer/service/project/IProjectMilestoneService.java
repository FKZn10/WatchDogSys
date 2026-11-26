package com.jpmsoft.projectProducer.service.project;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.milestones.ProjectMilestoneRequest;
import com.jpmsoft.projectProducer.pojo.entity.project.ProjectMilestone;

import java.util.List;

public interface IProjectMilestoneService extends IService<ProjectMilestone> {
    List<ProjectMilestone> findAllByPmsId(String pmsId);

    BaseResponse getProjectMilestoneByPmId(String pmId);

    BaseResponse updateProjectMilestone(ProjectMilestoneRequest request);
}
