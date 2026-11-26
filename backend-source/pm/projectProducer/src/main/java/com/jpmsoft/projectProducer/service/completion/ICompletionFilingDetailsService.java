package com.jpmsoft.projectProducer.service.completion;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.request.Completion.CompletionFilingDetailsRequest;
import com.jpmsoft.common.model.response.Completion.CompletionFilingDetailsResponse;
import com.jpmsoft.projectProducer.pojo.entity.completion.CompletionFilingDetails;

import java.util.List;

public interface ICompletionFilingDetailsService   extends IService<CompletionFilingDetails> {
    void createiCompletionFilingDetails(List<CompletionFilingDetailsRequest> completionFilingDetails,String cfId,String type);
    List<CompletionFilingDetailsResponse> getCompletionFilingDetailsByCfid(QueryWrapper<CompletionFilingDetails> queryWrapper);

    void deleteCompletionFilingDetails(String cfId);
}
