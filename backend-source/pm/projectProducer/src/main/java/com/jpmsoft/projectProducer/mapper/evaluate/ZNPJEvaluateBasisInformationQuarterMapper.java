package com.jpmsoft.projectProducer.mapper.evaluate;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.projectProducer.pojo.entity.evaluate.ZNPJEvaluateBasisInformationQuarter;
import org.apache.ibatis.annotations.Param;

public interface ZNPJEvaluateBasisInformationQuarterMapper extends BaseMapper<ZNPJEvaluateBasisInformationQuarter> {

    ZNPJEvaluateBasisInformationQuarter selectBySourceId(@Param("sourceId")String id);
}