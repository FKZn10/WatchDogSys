package com.jpmsoft.projectProducer.service.supervision;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Supervisor.ConSupPlanRequest;
import com.jpmsoft.projectProducer.pojo.entity.supervision.ConSupPlan;

public interface IConSupPlanService extends IService<ConSupPlan> {

    BaseResponse createOrUpdateConSupPlan(ConSupPlanRequest request);

    BaseResponse getConSupPlanList(ConSupPlanRequest request, String uId);

    BaseResponse getConSupPlanById(String spId);
}
