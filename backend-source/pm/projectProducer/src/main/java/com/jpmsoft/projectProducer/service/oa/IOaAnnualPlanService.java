package com.jpmsoft.projectProducer.service.oa;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.oa.OaAnnualPlanDto;
import com.jpmsoft.projectProducer.pojo.entity.oa.OaAnnualPlan;

/**
 * @Author: 李文涛 aaron
 * @date: 2022/12/2 15:05
 */
public interface IOaAnnualPlanService extends IService<OaAnnualPlan> {

    BaseResponse createOrUpdateOaAnnualPlan(OaAnnualPlanDto request);

    BaseResponse getOaAnnualPlanList(OaAnnualPlanDto request);

    BaseResponse getOaAnnualPlanById(String id);
}
