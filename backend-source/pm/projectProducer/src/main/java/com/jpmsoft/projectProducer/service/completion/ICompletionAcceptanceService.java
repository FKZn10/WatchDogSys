package com.jpmsoft.projectProducer.service.completion;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Construction.CompletionAcceptanceRequest;
import com.jpmsoft.projectProducer.pojo.entity.completion.CompletionAcceptance;


public interface ICompletionAcceptanceService extends IService<CompletionAcceptance> {


    BaseResponse createOrUpdateCompletionAcceptance(CompletionAcceptanceRequest request);

    BaseResponse getCompletionAcceptanceList(CompletionAcceptanceRequest request, String uId);

    BaseResponse getCompletionAcceptanceStatisticsList(CompletionAcceptanceRequest request, String uId);

    BaseResponse getCompletionAcceptanceListNoAuth(CompletionAcceptanceRequest request);

    BaseResponse getCompletionAcceptanceById(String caId);

    BaseResponse getCompletionAcceptanceLastByPidAndType(CompletionAcceptanceRequest request);

    BaseResponse approve2RefreshProStage(String caId);

    BaseResponse checkCompletionAcceptance(String conId);
}
