package com.jpmsoft.projectProducer.mapper.investment;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.InvestmentBudgetBreakdownChangeModel;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvestmentBudgetBreakdownChange;
import com.jpmsoft.projectProducer.pojo.vo.investment.InvestmentBudgetBreakdownChangeVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**

 */
@Mapper
public interface InvestmentBudgetBreakdownChangeMapper extends BaseMapper<InvestmentBudgetBreakdownChange> {
    InvestmentBudgetBreakdownChange selectByProjectId(@Param("projectId")String projectId);

    List<InvestmentBudgetBreakdownChangeVo> getInvestmentBudgetBreakdownChangeList(@Param("params")Map<String, Object> query, @Param("page")IPage<InvestmentBudgetBreakdownChangeModel> page);

    InvestmentBudgetBreakdownChangeVo getInvestmentBudgetBreakdownChangeById(@Param("id")String id);

}
