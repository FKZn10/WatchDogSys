package com.jpmsoft.projectProducer.mapper.materialsEquipment;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.response.MeEnter.MeEnterApplyDtlResponse;
import com.jpmsoft.projectProducer.pojo.entity.materialsEquipment.MeEnterApplyDtl;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface MeEnterApplyDtlMapper extends BaseMapper<MeEnterApplyDtl> {


    List<MeEnterApplyDtlResponse> getMeEnterApplyDtlList(@Param("page") IPage<MeEnterApplyDtlResponse> page, @Param("params") Map<String, Object> params);

}
