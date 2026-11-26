package com.jpmsoft.projectProducer.mapper.oa;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.requestAndResponse.Oa.OaAttendanceModel;
import com.jpmsoft.projectProducer.pojo.entity.oa.OaAttendance;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface OaAttendanceMapper extends BaseMapper<OaAttendance> {


    List<OaAttendanceModel> getAttendanceList(@Param("params") Map<String, Object> params);

    Integer getAttendanceListCount(@Param("params") Map<String, Object> params);
}
