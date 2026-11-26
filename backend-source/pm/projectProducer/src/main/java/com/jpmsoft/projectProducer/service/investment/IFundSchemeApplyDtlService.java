package com.jpmsoft.projectProducer.service.investment;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.FundSchemeApplyDtlModel;
import com.jpmsoft.projectProducer.pojo.entity.investment.FundSchemeApplyDtl;

import java.util.List;

public interface IFundSchemeApplyDtlService extends IService<FundSchemeApplyDtl> {

    void createOrUpdateFundSchemeApplyDtl(List<FundSchemeApplyDtlModel> datas, String fsaId);

}
