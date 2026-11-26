package com.jpmsoft.userProducer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.requestAndResponse.RiskWarn.RiskWarnIndexModel;
import com.jpmsoft.userProducer.pojo.entity.RiskWarnIndex;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface RiskWarnIndexMapper extends BaseMapper<RiskWarnIndex> {

    List<RiskWarnIndexModel> getRiskWarnIndexList(@Param("params") Map<String, Object> params);

    Integer getRiskWarnIndexListCount(@Param("params") Map<String, Object> params);
}
