package com.jpmsoft.projectProducer.mapper.materialsEquipment;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.requestAndResponse.MeEnter.MeDetectionGuideModel;
import com.jpmsoft.projectProducer.pojo.entity.materialsEquipment.MeDetectionGuide;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface MeDetectionGuideMapper extends BaseMapper<MeDetectionGuide> {

    List<MeDetectionGuideModel> getMeDetectionGuideList(@Param("params") Map<String, Object> params);

    Integer getMeDetectionGuideListCount(@Param("params") Map<String, Object> params);

    MeDetectionGuideModel getMeDetectionGuideModelByDgId(@Param("dgId") String dgId);
}
