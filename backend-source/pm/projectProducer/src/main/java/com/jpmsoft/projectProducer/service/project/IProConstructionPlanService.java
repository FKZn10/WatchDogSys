package com.jpmsoft.projectProducer.service.project;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Project.ProConstructionPlanModel;
import com.jpmsoft.projectProducer.pojo.entity.project.ProConstructionPlan;

public interface IProConstructionPlanService extends IService<ProConstructionPlan> {

    BaseResponse createOrUpdateProConstructionPlan(ProConstructionPlanModel request);

    BaseResponse getProConstructionPlanList(ProConstructionPlanModel request);

    BaseResponse getProConstructionPlanById(String pcpId);

}
