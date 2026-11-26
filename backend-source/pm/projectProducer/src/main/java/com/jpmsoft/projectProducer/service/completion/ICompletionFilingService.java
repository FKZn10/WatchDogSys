package com.jpmsoft.projectProducer.service.completion;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Completion.CompletionFilingRequest;
import com.jpmsoft.projectProducer.pojo.entity.completion.CompletionFiling;


public interface ICompletionFilingService extends IService<CompletionFiling> {


    BaseResponse createOrUpdateCompletionFiling(CompletionFilingRequest request);

    BaseResponse getCompletionFilingList(CompletionFilingRequest request, String uId);

    BaseResponse getCompletionFilingListNoAuth(CompletionFilingRequest request);

    BaseResponse getCompletionFilingById(String cfId);
}
