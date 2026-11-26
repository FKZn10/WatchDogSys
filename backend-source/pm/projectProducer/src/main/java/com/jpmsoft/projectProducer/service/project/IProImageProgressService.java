package com.jpmsoft.projectProducer.service.project;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Project.ProImageProgressModel;
import com.jpmsoft.projectProducer.pojo.entity.project.ProImageProgress;

public interface IProImageProgressService extends IService<ProImageProgress> {
    BaseResponse createOrUpdateProImageProgress(ProImageProgressModel request);

    BaseResponse getProImageProgressList(ProImageProgressModel request,String userId);

    BaseResponse getProImageProgressById(String id,String userId);

    BaseResponse deleteProImageProgressById(String id);
}
