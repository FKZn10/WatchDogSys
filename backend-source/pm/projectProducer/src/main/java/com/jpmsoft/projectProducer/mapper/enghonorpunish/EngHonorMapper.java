package com.jpmsoft.projectProducer.mapper.enghonorpunish;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.requestAndResponse.enghonorpunish.EngHonorDto;
import com.jpmsoft.projectProducer.pojo.entity.enghonorpunish.EngHonor;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 工程荣誉	(EngHonor)
 *
 * @author c
 * @since 2024-12-03 10:40:10
 */
public interface EngHonorMapper extends BaseMapper<EngHonor> {


    List<EngHonorDto> getEngHonorList(@Param("params") Map<String,Object> params, @Param("page") IPage<EngHonorDto> page);

}

