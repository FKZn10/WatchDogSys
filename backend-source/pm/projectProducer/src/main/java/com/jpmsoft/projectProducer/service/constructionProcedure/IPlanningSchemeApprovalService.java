package com.jpmsoft.projectProducer.service.constructionProcedure;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.ConstructionProcedure.PlanningSchemeApprovalRequest;
import com.jpmsoft.projectProducer.pojo.entity.constructionProcedure.PlanningSchemeApproval;

public interface IPlanningSchemeApprovalService extends IService<PlanningSchemeApproval> {

    /**
     * .规划方案批复
     */
    BaseResponse createPlanningSchemeApproval(PlanningSchemeApprovalRequest request);

    BaseResponse getPlanningSchemeApprovalList(PlanningSchemeApprovalRequest request);

    BaseResponse getPlanningSchemeApprovalListInfoById(String uuId);

    BaseResponse deletePlanningSchemeApproval(String uuId);

    BaseResponse updatePlanningSchemeApproval(PlanningSchemeApprovalRequest request);

    BaseResponse updateApproveStatusPlanningSchemeApproval(PlanningSchemeApprovalRequest request);

}
