package com.jpmsoft.projectProducer.service.investment;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.FundSchemeApplyModel;
import com.jpmsoft.projectProducer.pojo.entity.investment.FundSchemeApply;

public interface IFundSchemeApplyService extends IService<FundSchemeApply> {

    BaseResponse createOrUpdateFundSchemeApply(FundSchemeApplyModel request);

    BaseResponse getFundSchemeApplyList(FundSchemeApplyModel request, String uId);

    BaseResponse getFundSchemeApplyById(String fsaId);

    BaseResponse getFundSchemeApplyAmtSumByPid(String pId);

    BaseResponse getFundSchemeApplyDtlAmtSumByConId(String conId);
}
