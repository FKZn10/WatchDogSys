package com.jpmsoft.projectProducer.service.constructionProcedure;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.ConstructionProcedure.PlanningConditionsRequest;
import com.jpmsoft.projectProducer.pojo.entity.constructionProcedure.PlanningConditions;

public interface IPlanningConditionsService extends IService<PlanningConditions> {

    /**
     * 规划条件
     */
    BaseResponse createPlanningConditions(PlanningConditionsRequest request);

    BaseResponse getPlanningConditionsList(PlanningConditionsRequest request);

    BaseResponse getPlanningConditionsInfoById(String uuId);

    BaseResponse deletePlanningConditions(String uuId);

    BaseResponse updatePlanningConditions(PlanningConditionsRequest request);

    BaseResponse updateApproveStatusPlanningConditions(PlanningConditionsRequest request);

}
