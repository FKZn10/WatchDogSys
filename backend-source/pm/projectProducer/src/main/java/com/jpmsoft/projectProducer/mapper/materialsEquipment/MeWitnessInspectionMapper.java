package com.jpmsoft.projectProducer.mapper.materialsEquipment;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.response.MeEnter.MeWitnessInspectionResponse;
import com.jpmsoft.projectProducer.pojo.entity.materialsEquipment.MeWitnessInspection;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface MeWitnessInspectionMapper extends BaseMapper<MeWitnessInspection> {


    List<MeWitnessInspectionResponse> getMeWitnessInspectionList(@Param("page") IPage<MeWitnessInspectionResponse> page, @Param("params") Map<String, Object> params);

    MeWitnessInspectionResponse getMeWitnessInspectionByWiId(@Param("wiId") String wiId);


    List<MeWitnessInspectionResponse> getMeWitnessInspectionByConId(@Param("conId") String conId);


    List<MeWitnessInspectionResponse> getMeWitnessInspectionByOrderNo(@Param("wiOrderNo") String wiOrderNo);

}
