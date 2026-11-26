package com.jpmsoft.projectProducer.mapper.investment;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.InvestmentBudgetBreakdownDetailsModel;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvestmentBudgetBreakdownDetails;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author yb.Sun
 * @date 2024/11/1 15:04
 */
@Mapper
public interface InvestmentBudgetBreakdownDetailsMapper extends BaseMapper<InvestmentBudgetBreakdownDetails> {
    void deleteByIsbId(@Param("primaryId")String primaryId);

    List<InvestmentBudgetBreakdownDetailsModel> getDataByIsbId(@Param("isbId") String isbId);

    void insertBatch(@Param("list") List<InvestmentBudgetBreakdownDetails> list);
}
