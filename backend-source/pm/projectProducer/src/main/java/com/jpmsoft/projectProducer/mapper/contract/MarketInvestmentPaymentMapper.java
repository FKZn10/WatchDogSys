package com.jpmsoft.projectProducer.mapper.contract;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.projectProducer.pojo.dto.contract.MarketInvestmentPaymentDto;
import com.jpmsoft.projectProducer.pojo.entity.contract.MarketInvestmentPayment;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface MarketInvestmentPaymentMapper extends BaseMapper<MarketInvestmentPayment> {
    List<MarketInvestmentPaymentDto> getMarketInvestmentPaymentList(@Param("params") Map<String, Object> params);

    Integer getMarketInvestmentPaymentCount(@Param("params") Map<String, Object> params);
}
