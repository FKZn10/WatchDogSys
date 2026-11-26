package com.jpmsoft.projectProducer.mapper.investment;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.InvestmentEstimateBreakdownModel;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvestmentEstimateBreakdown;
import com.jpmsoft.projectProducer.pojo.vo.investment.InvestmentEstimateBreakdownVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author yb.Sun
 * @date 2024/10/25 15:47
 */
@Mapper
public interface InvestmentEstimateBreakdownMapper extends BaseMapper<InvestmentEstimateBreakdown> {
    InvestmentEstimateBreakdown selectByProjectId(@Param("projectId") String projectId);

    List<InvestmentEstimateBreakdownVo> getInvestmentEstimateBreakdownList(@Param("params") Map<String, Object> query,
                                                                           @Param("page") IPage<InvestmentEstimateBreakdownModel> page,
                                                                           @Param("authSql") String authSql);

    InvestmentEstimateBreakdownVo getInvestmentEstimateBreakdownById(@Param("id")String id);

    List<InvestmentEstimateBreakdown> getProjectIdList();

    InvestmentEstimateBreakdownVo getInvestmentEstimateBreakdownByProjectId(@Param("projectId")String projectId);

    List<InvestmentEstimateBreakdown> getInvestmentEstimateBreakdownByPId(@Param("projectId") String projectId);

    List<String> getUnusedProjectIdList(@Param("request") InvestmentEstimateBreakdownModel request);
}
