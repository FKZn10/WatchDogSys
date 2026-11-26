package com.jpmsoft.projectProducer.mapper.investment;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.projectProducer.pojo.dto.investment.FundPlanModifyDto;
import com.jpmsoft.projectProducer.pojo.entity.investment.FundPlanModify;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface FundPlanModifyMapper extends BaseMapper<FundPlanModify> {
    List<FundPlanModifyDto> getFundPlanModifyList(@Param("params") Map<String, Object> params);

    Integer getFundPlanModifyListCount(@Param("params") Map<String, Object> params);

    List<FundPlanModifyDto> getFundPlanModifyByPidsAndYears(@Param("params") Map<String, Object> params);

}
