package com.jpmsoft.projectProducer.service.oa;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.oa.OaAnnualPlanChangeDto;
import com.jpmsoft.projectProducer.pojo.entity.oa.OaAnnualPlanChange;

/**
 * @Author: 李文涛 aaron
 * @date: 2022/12/2 15:05
 */
public interface IOaAnnualPlanChangeService extends IService<OaAnnualPlanChange> {


    BaseResponse createOrUpdateOaAnnualPlanChange(OaAnnualPlanChangeDto request);

    BaseResponse getOaAnnualPlanChangeList(OaAnnualPlanChangeDto request);

    BaseResponse getOaAnnualPlanChangeById(String id);

    BaseResponse approval2SyncOaAnnualPlan(String id);
}
