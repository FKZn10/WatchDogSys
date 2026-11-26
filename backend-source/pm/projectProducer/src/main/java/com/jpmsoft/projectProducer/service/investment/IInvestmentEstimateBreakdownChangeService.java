package com.jpmsoft.projectProducer.service.investment;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.InvestmentEstimateBreakdownChangeModel;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvestmentEstimateBreakdownChange;

/**
 */
public interface IInvestmentEstimateBreakdownChangeService extends IService<InvestmentEstimateBreakdownChange> {

    /**
     * 保存投资估算分解
     * @param model
     * @return
     */
    BaseResponse createOrUpdateInvestmentEstimateBreakdownChange(InvestmentEstimateBreakdownChangeModel model);

    BaseResponse getInvestmentEstimateBreakdownChangeList(InvestmentEstimateBreakdownChangeModel model);

    /**
     * 详情
     * @param id
     * @return
     */
    BaseResponse getInvestmentEstimateBreakdownChangeById(String id);

}
