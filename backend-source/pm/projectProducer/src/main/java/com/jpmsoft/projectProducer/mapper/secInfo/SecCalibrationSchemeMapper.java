package com.jpmsoft.projectProducer.mapper.secInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecCalibrationSchemeModel;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecCalibrationScheme;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface SecCalibrationSchemeMapper extends BaseMapper<SecCalibrationScheme> {

    List<SecCalibrationSchemeModel> getSecCalibrationSchemeList(@Param("params") Map<String, Object> params);

    Integer getSecCalibrationSchemeListCount(@Param("params") Map<String, Object> params);

    SecCalibrationSchemeModel getSecCalibrationSchemeById(@Param("scsId") String scsId);
}
