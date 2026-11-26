package com.jpmsoft.projectProducer.mapper.oa;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.requestAndResponse.Oa.OaAttendanceLeaveModel;
import com.jpmsoft.projectProducer.pojo.entity.oa.OaAttendanceLeave;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface OaAttendanceLeaveMapper extends BaseMapper<OaAttendanceLeave> {


    List<OaAttendanceLeaveModel> getAttendanceLeaveList(@Param("params") Map<String, Object> params);

    Integer getAttendanceLeaveListCount(@Param("params") Map<String, Object> params);

    OaAttendanceLeaveModel getAttendanceLeaveById(@Param("alId") String alId);
}
