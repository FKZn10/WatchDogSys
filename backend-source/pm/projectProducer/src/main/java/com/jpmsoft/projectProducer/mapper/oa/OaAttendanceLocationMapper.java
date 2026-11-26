package com.jpmsoft.projectProducer.mapper.oa;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.requestAndResponse.Oa.OaAttendanceLocationModel;
import com.jpmsoft.projectProducer.pojo.entity.oa.OaAttendanceLocation;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface OaAttendanceLocationMapper extends BaseMapper<OaAttendanceLocation> {


    List<OaAttendanceLocationModel> getOaAttendanceLocationList(@Param("params") Map<String, Object> params);

    Integer getOaAttendanceLocationListCount(@Param("params") Map<String, Object> params);
}
