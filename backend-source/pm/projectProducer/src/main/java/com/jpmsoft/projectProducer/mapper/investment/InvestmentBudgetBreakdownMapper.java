package com.jpmsoft.projectProducer.mapper.investment;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.model.request.InvestmentControl.InvestmentControlRequest;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.InvestmentBudgetBreakdownModel;
import com.jpmsoft.common.model.response.InvestmentControl.InvestmentControlResponse;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvestmentBudgetBreakdown;
import com.jpmsoft.projectProducer.pojo.vo.investment.InvestmentBudgetBreakdownVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author yb.Sun
 * @date 2024/11/1 15:03
 */
@Mapper
public interface InvestmentBudgetBreakdownMapper extends BaseMapper<InvestmentBudgetBreakdown> {
    InvestmentBudgetBreakdown selectByProjectId(@Param("projectId")String projectId);

    List<InvestmentBudgetBreakdownVo> getInvestmentBudgetBreakdownList(@Param("params")Map<String, Object> query,
                                                                       @Param("page")IPage<InvestmentBudgetBreakdownModel> page,
                                                                       @Param("authSql") String authSql);

    InvestmentBudgetBreakdownVo getInvestmentBudgetBreakdownById(@Param("id")String id);

    InvestmentBudgetBreakdownVo getInvestmentBudgetBreakdownByPid(@Param("pId") String pId);

    List<InvestmentBudgetBreakdown> getInvestmentBudgetBreakdownByPId(@Param("projectId")String projectId);

    Page<InvestmentControlResponse> getInvestmentControlList(@Param("page") Page<InvestmentControlResponse> page,@Param("params") InvestmentControlRequest request);

    List<Object> getValueByCode(@Param("value") String dbValue);
}
