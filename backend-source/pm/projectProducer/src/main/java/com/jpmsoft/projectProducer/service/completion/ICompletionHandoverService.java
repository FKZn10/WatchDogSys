package com.jpmsoft.projectProducer.service.completion;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Completion.CompletionHandoverRequest;
import com.jpmsoft.projectProducer.pojo.entity.completion.CompletionHandover;


public interface ICompletionHandoverService extends IService<CompletionHandover> {


    BaseResponse createOrUpdateCompletionHandover(CompletionHandoverRequest request);

    BaseResponse getCompletionHandoverList(CompletionHandoverRequest request, String uId);

    BaseResponse getCompletionHandoverById(String chId);

    BaseResponse updateBuildingInfoSub(String chId);

    BaseResponse retrySyncFailedBuildingInfo();

    BaseResponse checkInsuranceStatus();

}
