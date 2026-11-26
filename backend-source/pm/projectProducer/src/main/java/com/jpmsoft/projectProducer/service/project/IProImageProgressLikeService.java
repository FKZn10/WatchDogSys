package com.jpmsoft.projectProducer.service.project;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Project.ProImageProgressLikeModel;
import com.jpmsoft.projectProducer.pojo.entity.project.ProImageProgressLike;

public interface IProImageProgressLikeService extends IService<ProImageProgressLike> {
    BaseResponse createProImageProgressLike(ProImageProgressLikeModel request);

    BaseResponse deleteProImageProgressLikeById(String id);
}
