package com.jpmsoft.projectProducer.service.investment;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.InvestmentControl.InvestmentControlRequest;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.InvestmentBudgetBreakdownModel;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvestmentBudgetBreakdown;

/**
 * @author yb.Sun
 * @date 2024/11/1 15:05
 */
public interface IInvestmentBudgetBreakdownService extends IService<InvestmentBudgetBreakdown> {

    BaseResponse createOrUpdateInvestmentBudgetBreakdown(InvestmentBudgetBreakdownModel request);

    BaseResponse getInvestmentBudgetBreakdownList(InvestmentBudgetBreakdownModel request,String uId);

    BaseResponse getInvestmentBudgetBreakdownById(String id);

    BaseResponse deleteInvestmentBudgetBreakdownById(String id);

    BaseResponse removeInvestmentBudgetBreakdownById(String id);

    /**
     * 通过项目获取最新的概算分解
     * @param pId
     * @return
     */
    BaseResponse getInvestmentBudgetBreakdownByPId(String pId);

    /**
     * 将概算、估算数据组装成标段信息
     * @param pId
     * @return
     */
    BaseResponse getInvestmentSecInfoByPId(String pId);

    BaseResponse getLatestInvestmentBudgetBreakdownById(String id);

    InvestmentBudgetBreakdown getInvestmentBudgetBreakdownByPIdData(String pId);

    BaseResponse getInvestmentControlList(InvestmentControlRequest request);

    BaseResponse getInvestmentControlByPId(String pId);

    BaseResponse getInvestmentControlClassInfo(InvestmentControlRequest request);

    BaseResponse getInvestmentBudgetBreakdownTreeByPId(String pId);
}
