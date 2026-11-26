package com.jpmsoft.projectProducer.mapper.project;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.response.milestones.ProjectMilestoneResponse;
import com.jpmsoft.projectProducer.pojo.entity.project.ProjectMilestone;

import java.util.List;


public interface ProjectMilestoneMapper extends BaseMapper<ProjectMilestone> {
    List<ProjectMilestone> findAllByPmsId(String pmsId);

    ProjectMilestoneResponse getProjectMilestoneByPmId(String pmId);
}
