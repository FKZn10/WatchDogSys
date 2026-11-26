package com.jpmsoft.projectProducer.mapper.materialsEquipment;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.response.MeEnter.MeTypeLibraryResponse;
import com.jpmsoft.projectProducer.pojo.entity.materialsEquipment.MeTypeLibrary;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface MeTypeLibraryMapper extends BaseMapper<MeTypeLibrary> {


    List<MeTypeLibraryResponse> getMeTypeLibraryList(@Param("page") IPage<MeTypeLibraryResponse> page, @Param("params") Map<String, Object> params);

}
