package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.SecInfo.SecBidEvaluationExpertRequest;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecBidEvaluationExpert;

public interface ISecBidEvaluationExpertService extends IService<SecBidEvaluationExpert> {


    BaseResponse createOrUpdateSecBidEvaluationExpert(SecBidEvaluationExpertRequest request);

    BaseResponse getSecBidEvaluationExpertList(SecBidEvaluationExpertRequest request);

    BaseResponse getSecBidEvaluationExpertById(String beeId);

}
