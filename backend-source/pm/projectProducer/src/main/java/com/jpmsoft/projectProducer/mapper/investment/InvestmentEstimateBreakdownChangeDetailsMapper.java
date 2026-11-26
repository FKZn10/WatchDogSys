package com.jpmsoft.projectProducer.mapper.investment;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.InvestmentEstimateBreakdownChangeDetailsModel;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvestmentEstimateBreakdownChangeDetails;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 */
@Mapper
public interface InvestmentEstimateBreakdownChangeDetailsMapper extends BaseMapper<InvestmentEstimateBreakdownChangeDetails> {

    void deleteByIsbChangeId(@Param("primaryId") String primaryId);

    List<InvestmentEstimateBreakdownChangeDetailsModel> getDataByIsbId(@Param("isbId") String isbId);

}
