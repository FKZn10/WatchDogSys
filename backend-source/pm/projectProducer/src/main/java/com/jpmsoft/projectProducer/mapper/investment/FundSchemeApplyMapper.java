package com.jpmsoft.projectProducer.mapper.investment;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.FundSchemeApplyModel;
import com.jpmsoft.projectProducer.pojo.entity.investment.FundSchemeApply;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


public interface FundSchemeApplyMapper extends BaseMapper<FundSchemeApply> {


    List<FundSchemeApplyModel> getFundSchemeApplyList(@Param("page") IPage<FundSchemeApplyModel> page, @Param("params") Map<String, Object> params);

    FundSchemeApplyModel getFundSchemeApplyById(@Param("fsaId") String fsaId);

    Map<String, BigDecimal> getFundSchemeApplyAmtSumByPid(@Param("pId") String pId);

    BigDecimal getFundSchemeApplyDtlAmtSumByConId(@Param("conId") String conId);
}
