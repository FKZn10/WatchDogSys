package com.jpmsoft.projectProducer.service.constructionProcedure;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.proCp.ProjectCpExperienceRequest;
import com.jpmsoft.projectProducer.pojo.entity.constructionProcedure.ProjectCpExperience;

public interface IProjectCpExperienceService extends IService<ProjectCpExperience> {

    BaseResponse createOrUpdateProCpExperience(ProjectCpExperienceRequest request);

    BaseResponse deleteProCpExperienceById(String cpeId);

    BaseResponse getProCpExperienceList(ProjectCpExperienceRequest request);

    Integer getCpExperienceCountByCpdId(String cpdId);

    Integer getCpExperienceCountByCpId(String cpId);
}
