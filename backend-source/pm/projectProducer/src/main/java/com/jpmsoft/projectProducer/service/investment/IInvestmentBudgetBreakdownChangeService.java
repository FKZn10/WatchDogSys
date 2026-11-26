package com.jpmsoft.projectProducer.service.investment;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.InvestmentBudgetBreakdownChangeModel;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvestmentBudgetBreakdownChange;

/**
 * @author yb.Sun
 * @date 2024/11/1 15:05
 */
public interface IInvestmentBudgetBreakdownChangeService extends IService<InvestmentBudgetBreakdownChange> {

    BaseResponse createOrUpdateInvestmentBudgetBreakdownChange(InvestmentBudgetBreakdownChangeModel request);

    BaseResponse getInvestmentBudgetBreakdownChangeList(InvestmentBudgetBreakdownChangeModel request);

    BaseResponse getInvestmentBudgetBreakdownChangeById(String id);

}
