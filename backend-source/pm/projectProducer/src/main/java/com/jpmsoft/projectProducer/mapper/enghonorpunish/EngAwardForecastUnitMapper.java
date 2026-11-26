package com.jpmsoft.projectProducer.mapper.enghonorpunish;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.requestAndResponse.enghonorpunish.EngAwardForecastUnitDto;
import com.jpmsoft.projectProducer.pojo.entity.enghonorpunish.EngAwardForecastUnit;

import java.util.List;

public interface EngAwardForecastUnitMapper extends BaseMapper<EngAwardForecastUnit> {

    List<EngAwardForecastUnitDto> getEngAwardForecastUnitList();

}
