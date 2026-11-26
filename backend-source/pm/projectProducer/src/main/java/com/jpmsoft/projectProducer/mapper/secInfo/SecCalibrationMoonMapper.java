package com.jpmsoft.projectProducer.mapper.secInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecBrightMoonModel;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecCalibrationMoon;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface SecCalibrationMoonMapper extends BaseMapper<SecCalibrationMoon> {


    List<SecBrightMoonModel> getSecCalibrationMoonList(@Param("params") Map<String, Object> params);

    Integer getSecCalibrationMoonCount(@Param("params") Map<String, Object> params);

    SecBrightMoonModel getSecCalibrationMoonById(@Param("sbmId") String sbmId);
}
