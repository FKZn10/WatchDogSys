package com.jpmsoft.projectProducer.mapper.oa;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.requestAndResponse.Oa.OaAttendanceSupplementModel;
import com.jpmsoft.projectProducer.pojo.entity.oa.OaAttendanceSupplement;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface OaAttendanceSupplementMapper extends BaseMapper<OaAttendanceSupplement> {

    List<OaAttendanceSupplementModel> getAttendanceSupplementList(@Param("params") Map<String, Object> params);

    Integer getAttendanceSupplementListCount(@Param("params") Map<String, Object> params);

    OaAttendanceSupplementModel getAttendanceSupplementById(@Param("asId") String asId);
}
