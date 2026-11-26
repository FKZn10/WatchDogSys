package com.jpmsoft.projectProducer.service.challengePlan;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.challengePlan.ChallengePlanAnnualRequest;
import com.jpmsoft.projectProducer.pojo.entity.challengePlan.ChallengePlanAnnual;

/**
 * @author tianhelin
 * @date 2025/5/30
 */
public interface IChallengePlanAnnualService extends IService<ChallengePlanAnnual> {
    /**
     * 创建或更新年度计划
     *
     * @param request
     * @return
     */
    BaseResponse createOrUpdateChallengePlanAnnual(ChallengePlanAnnualRequest request);

    /**
     * 删除年度计划
     *
     * @param request
     * @return
     */
    BaseResponse deleteChallengePlanAnnualById(ChallengePlanAnnualRequest request);

    /**
     * 获取年度计划列表
     *
     * @param request
     * @return
     */
    BaseResponse getChallengePlanAnnualList(ChallengePlanAnnualRequest request);

    /**
     * 获取年度计划详情
     *
     * @param id
     * @return
     */
    BaseResponse getChallengePlanAnnualById(String id);
}
