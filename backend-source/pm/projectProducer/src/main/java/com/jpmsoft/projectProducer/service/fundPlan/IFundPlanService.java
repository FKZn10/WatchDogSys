package com.jpmsoft.projectProducer.service.fundPlan;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.fundPlan.FundPlanDto;
import com.jpmsoft.projectProducer.pojo.entity.fundPlan.FundPlan;

import java.math.BigDecimal;

/**
 * @author: 李文涛 aaron
 * @date: 2022/8/16 12:05
 */
public interface IFundPlanService extends IService<FundPlan> {

    BaseResponse createOrUpdateFundPlan(FundPlanDto request);

    BaseResponse getFundPlanList(FundPlanDto request, String uId);

    BaseResponse getFundPlanById(String id);

    /**
     * 计算项目下 资金计划（审批通过的） [最终]本期资金需求 累计值
     *
     * @param pId
     * @return
     */
    BaseResponse getProFpDemandFinalSum(String pId);

    /**
     * 更新【最终】本期资金需求，【最终】累计申请比例，【最终】累计申请资金
     * <p>
     * 累计申请比例=累计申请资金/合同总价
     * 本期资金需求=SUM(【资金申请情况】本期资金需求【最终】)
     * 累计申请资金=SUM(【资金申请情况】累计申请金额【最终】)
     *
     * @param id
     */
    void updateFinalInfo(String id);

    /**
     * 获取本期需求资金
     *
     * @param id
     * @param conId
     * @return
     */
    BigDecimal getDemand(String id, String conId);
}
