package com.jpmsoft.projectProducer.mapper.investment;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.InvestmentEstimateBreakdownDetailsModel;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvestmentEstimateBreakdownDetails;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author yb.Sun
 * @date 2024/10/25 15:48
 */
@Mapper
public interface InvestmentEstimateBreakdownDetailsMapper extends BaseMapper<InvestmentEstimateBreakdownDetails> {
    void deleteByIsbId(@Param("primaryId") String primaryId);

    List<InvestmentEstimateBreakdownDetailsModel> getDataByIsbId(@Param("isbId") String isbId);

    void insertBatch(List<InvestmentEstimateBreakdownDetails> flatList);
}
