package com.jpmsoft.projectProducer.service.zdyxy;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.zdyxy.AnnualPlanModel;
import com.jpmsoft.projectProducer.pojo.entity.zdyxy.AnnualPlan;

import java.util.Date;

public interface AnnualPlanService extends IService<AnnualPlan> {
    BaseResponse saveOrUpdateData(AnnualPlanModel request);

    BaseResponse approvalData(AnnualPlanModel request);

    BaseResponse getList(AnnualPlanModel request, String token);

    BaseResponse getDetail(String id);

    BaseResponse getByYear(AnnualPlanModel request);

    BaseResponse getInfo(AnnualPlanModel request);

    /**
     * 查询符合生效日期的年度计划
     *
     * @param time
     * @return
     */
    String getAnnualPlanByTime(Date time);
}