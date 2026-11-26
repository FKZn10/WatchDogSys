package com.jpmsoft.projectProducer.service.challengePlan;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.challengePlan.ChallengePlanAnnualRequest;
import com.jpmsoft.common.model.request.challengePlan.ChallengePlanMonthlyPageRequest;
import com.jpmsoft.common.model.request.challengePlan.ChallengePlanMonthlyRequest;
import com.jpmsoft.projectProducer.pojo.entity.challengePlan.ChallengePlanMonthly;

/**
 * @author tianhelin
 * @date 2025/5/30
 */
public interface IChallengePlanMonthlyService extends IService<ChallengePlanMonthly> {
    /**
     * 创建当前年份的月度计划
     *
     * @param request
     * @return
     */
    BaseResponse createChallengePlanMonthly(ChallengePlanAnnualRequest request);

    /**
     * 更新月度计划
     *
     * @param request
     * @return
     */
    BaseResponse updateChallengePlanMonthly(ChallengePlanMonthlyRequest request);

    /**
     * 获取月度计划列表
     *
     * @param request
     * @param uId
     * @return
     */
    BaseResponse getChallengePlanMonthlyList(ChallengePlanMonthlyPageRequest request, String uId);

    /**
     * 获取月度计划信息
     *
     * @param request
     * @param uId
     * @return
     */
    BaseResponse getChallengePlanMonthlyClassInfo(ChallengePlanMonthlyPageRequest request, String uId);

    /**
     * 获取月度计划信息
     *
     * @param id
     * @param uId
     * @return
     */
    BaseResponse getChallengePlanMonthlyById(String id, String uId);

    /**
     * 批量更新状态
     *
     * @param request
     * @return
     */
    BaseResponse batchUpdateStatus(ChallengePlanMonthlyRequest request, String token);
}
