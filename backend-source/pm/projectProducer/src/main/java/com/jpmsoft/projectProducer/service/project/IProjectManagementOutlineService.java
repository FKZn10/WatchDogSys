package com.jpmsoft.projectProducer.service.project;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.project.ProjectManagementOutlineDto;
import com.jpmsoft.projectProducer.pojo.entity.project.ProjectManagementOutline;


public interface IProjectManagementOutlineService extends IService<ProjectManagementOutline> {

    BaseResponse createOrUpdateProjectManagementOutline(ProjectManagementOutlineDto request);

    BaseResponse getProjectManagementOutlineById(String id);

    BaseResponse deleteProjectManagementOutlineById(String id);

    BaseResponse getProjectManagementOutlineList(ProjectManagementOutlineDto request, String uId);

    BaseResponse getProjectManagementOutlineClassInfo(ProjectManagementOutlineDto request,String uId);
}
