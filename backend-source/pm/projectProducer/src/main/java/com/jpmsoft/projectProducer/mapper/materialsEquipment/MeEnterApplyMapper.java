package com.jpmsoft.projectProducer.mapper.materialsEquipment;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.response.MeEnter.MeEnterApplyResponse;
import com.jpmsoft.projectProducer.pojo.entity.materialsEquipment.MeEnterApply;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface MeEnterApplyMapper extends BaseMapper<MeEnterApply> {

    List<MeEnterApplyResponse> getMeEnterApplyList(@Param("page") IPage<MeEnterApplyResponse> page, @Param("params") Map<String, Object> params);


    MeEnterApplyResponse getMeEnterApplyByEaId(@Param("eaId") String eaId);
}
