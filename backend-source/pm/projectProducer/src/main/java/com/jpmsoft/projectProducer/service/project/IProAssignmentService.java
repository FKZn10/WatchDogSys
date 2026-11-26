package com.jpmsoft.projectProducer.service.project;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Project.ProAssignmentModel;
import com.jpmsoft.projectProducer.pojo.entity.project.ProAssignment;

public interface IProAssignmentService extends IService<ProAssignment> {

    BaseResponse createOrUpdateProAssignment(ProAssignmentModel request);

    BaseResponse getProAssignmentList(ProAssignmentModel request, String uId);

    BaseResponse getProAssignmentById(String paId);

    BaseResponse syncPCompetentDepartment(String paId);
}
