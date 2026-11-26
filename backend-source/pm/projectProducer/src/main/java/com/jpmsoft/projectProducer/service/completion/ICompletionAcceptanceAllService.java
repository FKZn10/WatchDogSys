package com.jpmsoft.projectProducer.service.completion;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Completion.CompletionAcceptanceAllRequest;
import com.jpmsoft.projectProducer.pojo.entity.completion.CompletionAcceptanceAll;

public interface ICompletionAcceptanceAllService extends IService<CompletionAcceptanceAll> {

    BaseResponse createOrUpdateCompletionAcceptanceAll(CompletionAcceptanceAllRequest request);

    BaseResponse getCompletionAcceptanceAllList(CompletionAcceptanceAllRequest request, String uId);

    BaseResponse getCompletionAcceptanceAllListNoAuth(CompletionAcceptanceAllRequest request);

    BaseResponse getCompletionAcceptanceAllById(String caaId);
}
