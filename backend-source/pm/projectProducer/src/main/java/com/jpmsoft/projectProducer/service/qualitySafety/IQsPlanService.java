package com.jpmsoft.projectProducer.service.qualitySafety;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Qs.QsPlanModel;
import com.jpmsoft.projectProducer.pojo.entity.qualitySafety.QsPlan;

public interface IQsPlanService extends IService<QsPlan> {

    BaseResponse createOrUpdateQsPlan(QsPlanModel request);

    BaseResponse getQsPlanList(QsPlanModel request, String userId);

    BaseResponse getQsPlanById(String qspId);

    BaseResponse setQsPlanReplyState(QsPlanModel request);
}
