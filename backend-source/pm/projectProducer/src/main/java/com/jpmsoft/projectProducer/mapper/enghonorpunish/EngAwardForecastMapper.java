package com.jpmsoft.projectProducer.mapper.enghonorpunish;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.model.requestAndResponse.enghonorpunish.EngAwardForecastDto;
import com.jpmsoft.projectProducer.pojo.entity.enghonorpunish.EngAwardForecast;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 评奖预报(EngAwardForecast)
 *
 * @since 2024-12-03 10:40:07
 */
public interface EngAwardForecastMapper extends BaseMapper<EngAwardForecast> {


    List<EngAwardForecastDto> getEngAwardForecastList(@Param("params") Map<String,Object> params, @Param("page")  IPage<EngAwardForecastDto> page);


}

