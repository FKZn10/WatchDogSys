package com.jpmsoft.projectProducer.mapper.project;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.response.Project.PreProjectOrgStructResponse;
import com.jpmsoft.projectProducer.pojo.dto.project.ProjectReportViolationsDTO;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProjectOrgStruct;
import com.jpmsoft.projectProducer.pojo.entity.project.ProjectReportViolations;
import feign.Param;

import java.util.List;
import java.util.Map;

public interface ProjectReportViolationsMapper extends BaseMapper<ProjectReportViolations> {
    ProjectReportViolationsDTO getProjectReportViolationsById(@Param("id")String id);


    List<ProjectReportViolationsDTO> getProjectReportViolationsList(Map<String, Object> query,
                                                                    IPage<ProjectReportViolationsDTO> page);
}
