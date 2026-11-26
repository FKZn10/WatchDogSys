package com.jpmsoft.projectProducer.service.design;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Design.DesignCompletedModel;
import com.jpmsoft.projectProducer.pojo.entity.design.DesignCompleted;


public interface IDesignCompletedService extends IService<DesignCompleted> {

    BaseResponse createOrUpdateDesignCompleted(DesignCompletedModel request);

    BaseResponse getDesignCompletedList(DesignCompletedModel request, String uId);

    BaseResponse getDesignCompletedListNoAuth(DesignCompletedModel request);

    BaseResponse getDesignCompletedById(String dcId);
}
