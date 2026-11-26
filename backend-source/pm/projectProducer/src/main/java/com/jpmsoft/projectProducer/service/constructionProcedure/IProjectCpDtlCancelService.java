package com.jpmsoft.projectProducer.service.constructionProcedure;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.proCp.ProjectCpDtlCancelModel;
import com.jpmsoft.projectProducer.pojo.entity.constructionProcedure.ProjectCpDtlCancel;

public interface IProjectCpDtlCancelService extends IService<ProjectCpDtlCancel> {

    BaseResponse createOrUpdateProjectCpDtlCancel(ProjectCpDtlCancelModel request);

    BaseResponse getProjectCpDtlCancelList(ProjectCpDtlCancelModel request, String uId);

    BaseResponse getProjectCpDtlCancelById(String cdcId);

    BaseResponse existApproveCpCancel(String cpdId);

    BaseResponse enableProjectCpDtlStatus(String cdcId);

}
