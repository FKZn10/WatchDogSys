package com.jpmsoft.projectProducer.mapper.materialsEquipment;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.response.MeEnter.MeEnterInspectionResponse;
import com.jpmsoft.projectProducer.pojo.entity.materialsEquipment.MeEnterInspection;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface MeEnterInspectionMapper extends BaseMapper<MeEnterInspection> {


    List<MeEnterInspectionResponse> getMeEnterInspectionList(@Param("page") IPage<MeEnterInspectionResponse> page, @Param("params") Map<String, Object> params);

    MeEnterInspectionResponse getMeEnterInspectionByEiId(@Param("eiId") String eiId);
}
