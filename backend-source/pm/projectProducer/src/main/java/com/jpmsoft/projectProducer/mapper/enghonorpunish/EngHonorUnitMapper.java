package com.jpmsoft.projectProducer.mapper.enghonorpunish;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.requestAndResponse.enghonorpunish.EngHonorUnitDto;
import com.jpmsoft.projectProducer.pojo.entity.enghonorpunish.EngHonorUnit;

import java.util.List;

public interface EngHonorUnitMapper extends BaseMapper<EngHonorUnit> {


    List<EngHonorUnitDto> getEngHonorUnitList();

}
