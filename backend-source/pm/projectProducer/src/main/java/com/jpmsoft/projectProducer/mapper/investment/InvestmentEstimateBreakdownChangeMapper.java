package com.jpmsoft.projectProducer.mapper.investment;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.InvestmentEstimateBreakdownChangeModel;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvestmentEstimateBreakdownChange;
import com.jpmsoft.projectProducer.pojo.vo.investment.InvestmentEstimateBreakdownChangeVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 *
 */
@Mapper
public interface InvestmentEstimateBreakdownChangeMapper extends BaseMapper<InvestmentEstimateBreakdownChange> {
    InvestmentEstimateBreakdownChange selectByProjectId(@Param("projectId") String projectId);

    List<InvestmentEstimateBreakdownChangeVo> getInvestmentEstimateBreakdownChangeList(@Param("params") Map<String,Object> model, @Param("page") IPage<InvestmentEstimateBreakdownChangeModel> page);

    InvestmentEstimateBreakdownChangeVo getInvestmentEstimateBreakdownChangeById(@Param("id") String id);

}
