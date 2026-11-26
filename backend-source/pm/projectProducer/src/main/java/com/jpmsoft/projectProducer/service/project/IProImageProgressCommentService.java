package com.jpmsoft.projectProducer.service.project;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Project.ProImageProgressCommentModel;
import com.jpmsoft.projectProducer.pojo.entity.project.ProImageProgressComment;

public interface IProImageProgressCommentService extends IService<ProImageProgressComment> {
    BaseResponse createProImageProgressComment(ProImageProgressCommentModel request);

    BaseResponse getProImageProgressCommentListByIpId(String ipId);

    BaseResponse getProImageProgressCommentById(String id);

    BaseResponse deleteProImageProgressCommentById(String id);
}
