package com.jpmsoft.projectProducer.mapper.investment;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.InvestmentBudgetBreakdownChangeDetailsModel;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvestmentBudgetBreakdownChangeDetails;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 */
@Mapper
public interface InvestmentBudgetBreakdownChangeDetailsMapper extends BaseMapper<InvestmentBudgetBreakdownChangeDetails> {
    void deleteByIsChangebId(@Param("primaryId")String primaryId);

    List<InvestmentBudgetBreakdownChangeDetailsModel> getDataByIsChangebId(@Param("isChangebId") String isChangebId);

}
