package com.jpmsoft.projectProducer.mapper.materialsEquipment;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.response.MeEnter.MeWitnessDtlResponse;
import com.jpmsoft.projectProducer.pojo.entity.materialsEquipment.MeWitnessDtl;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface MeWitnessDtlMapper extends BaseMapper<MeWitnessDtl> {

    List<MeWitnessDtlResponse> getMeWitnessDtlList(@Param("page") IPage<MeWitnessDtlResponse> page,@Param("params") Map<String, Object> params);


    List<MeWitnessDtlResponse> getMeWitnessDtlInspeList(@Param("page") IPage<MeWitnessDtlResponse> page, @Param("params") Map<String, Object> params);

    Integer  updateValueNull(@Param("wiId") String wiId);
}
