package com.jpmsoft.projectProducer.service.project;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Project.ProImageProgressReplyModel;
import com.jpmsoft.projectProducer.pojo.entity.project.ProImageProgressReply;

public interface IProImageProgressReplyService extends IService<ProImageProgressReply> {
    BaseResponse createProImageProgressReply(ProImageProgressReplyModel request);

    BaseResponse deleteProImageProgressReplyById(String id);
}
