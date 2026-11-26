package com.jpmsoft.projectProducer.mapper.oa;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.requestAndResponse.Oa.OaAttendanceWeekdayModel;
import com.jpmsoft.projectProducer.pojo.entity.oa.OaAttendanceWeekday;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface OaAttendanceWeekdayMapper extends BaseMapper<OaAttendanceWeekday> {


    List<OaAttendanceWeekdayModel> getAttendanceWeekdayList(@Param("params") Map<String, Object> params);

    Integer getAttendanceWeekdayListCount(@Param("params") Map<String, Object> params);
}
