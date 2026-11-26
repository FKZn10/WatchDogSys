package com.jpmsoft.projectProducer.mapper.materialsEquipment;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.response.MeEnter.MeUseApplyResponse;
import com.jpmsoft.projectProducer.pojo.entity.materialsEquipment.MeUseApply;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface MeUseApplyMapper extends BaseMapper<MeUseApply> {

    List<MeUseApplyResponse> getMeUseApplyList(@Param("page") IPage<MeUseApplyResponse> page, @Param("params") Map<String, Object> params);

    MeUseApplyResponse getMeUseApplyByUaId(@Param("uaId") String uaId);
}
