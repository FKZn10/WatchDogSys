package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecProvisionalEstimateModel;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecProvisionalEstimate;

public interface ISecProvisionalEstimateService extends IService<SecProvisionalEstimate> {

    BaseResponse createOrUpdateSecProvisionalEstimate(SecProvisionalEstimateModel request);

    BaseResponse getSecProvisionalEstimateModelList(SecProvisionalEstimateModel request, String uId);

    BaseResponse getSecProvisionalEstimateByPeId(String peId);
}
