package com.jpmsoft.projectProducer.service.fundPlan;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.fundPlan.FundAppropriationDto;
import com.jpmsoft.projectProducer.pojo.entity.fundPlan.FundAppropriation;

/**
 * @author: 李文涛 aaron
 * @date: 2022/8/16 17:30
 */
public interface IFundAppropriationService extends IService<FundAppropriation> {

    BaseResponse createOrUpdateFundAppropriation(FundAppropriationDto request);

    BaseResponse getFundAppropriationList(FundAppropriationDto request, String uId);

    BaseResponse getFundAppropriationById(String id);

    /**
     * 获取项目前期已完成投资=本期完成投资合计
     *
     * @param pId
     * @return
     */
    BaseResponse getProCompletedInvSum(String pId);

    /**
     * 查询资金拨付详情指定合同前期已完成投资、前期已付资金
     * 和其对应资金计划详情的本期资金需求
     *
     * @param fpId
     * @param conId
     * @return
     */
    BaseResponse getConAppropriationDtl(String fpId, String conId);

    BaseResponse getAllFundPlanDtlByFpId(String fpId);

    /**
     * 统计项目拨付本期拨付资金合计
     *
     * @param pId
     * @return
     */
    BaseResponse getFaDemandSumByPid(String pId);
}
