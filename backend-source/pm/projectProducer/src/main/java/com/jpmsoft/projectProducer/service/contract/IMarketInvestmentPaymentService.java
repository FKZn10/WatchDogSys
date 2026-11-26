package com.jpmsoft.projectProducer.service.contract;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.contract.MarketInvestmentPaymentDto;
import com.jpmsoft.projectProducer.pojo.entity.contract.MarketInvestmentPayment;

public interface IMarketInvestmentPaymentService extends IService<MarketInvestmentPayment> {

    BaseResponse syncMarketInvestmentPayment();

    BaseResponse getMarketInvestmentPaymentList(MarketInvestmentPaymentDto request);

}
