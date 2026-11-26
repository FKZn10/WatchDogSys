package com.jpmsoft.projectProducer.mapper.project;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.response.milestones.ProjectMilestonesResponse;
import com.jpmsoft.projectProducer.pojo.entity.project.ProjectMilestones;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface ProjectMilestonesMapper extends BaseMapper<ProjectMilestones> {
    Integer findCountByPId(String pid);

    List<ProjectMilestonesResponse> getProjectMilestonesList(@Param("params") Map<String, Object> params);

    Integer getProjectMilestonesListCount(@Param("params") Map<String, Object> params);

    ProjectMilestonesResponse getProjectMilestonesByPmsId(String pmsId);
}
