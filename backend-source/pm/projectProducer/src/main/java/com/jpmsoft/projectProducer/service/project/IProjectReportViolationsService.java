package com.jpmsoft.projectProducer.service.project;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.project.ProjectReportViolationsDTO;
import com.jpmsoft.projectProducer.pojo.entity.project.ProjectReportViolations;

public interface IProjectReportViolationsService extends IService<ProjectReportViolations> {

    BaseResponse  createOrUpdateProjectReportViolations(ProjectReportViolationsDTO request);

    BaseResponse  createOrUpdateProjectReportViolationsBySms(ProjectReportViolationsDTO request);

    BaseResponse getProjectReportViolationsById(String id);

    BaseResponse getProjectReportViolationsList(ProjectReportViolationsDTO request);
}
