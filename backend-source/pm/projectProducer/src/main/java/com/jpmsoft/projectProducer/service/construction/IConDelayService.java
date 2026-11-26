package com.jpmsoft.projectProducer.service.construction;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Construction.ConDelayModel;
import com.jpmsoft.projectProducer.pojo.entity.construction.ConDelay;

public interface IConDelayService extends IService<ConDelay> {

    BaseResponse createOrUpdateConDelay(ConDelayModel request);

    BaseResponse getConDelayList(ConDelayModel request, String uId);

    BaseResponse getConDelayById(String deId);
}
