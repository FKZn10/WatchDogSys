package com.jpmsoft.projectProducer.mapper.oa;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.requestAndResponse.Oa.OaAttendanceGroupModel;
import com.jpmsoft.projectProducer.pojo.entity.oa.OaAttendanceGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface OaAttendanceGroupMapper extends BaseMapper<OaAttendanceGroup> {


    List<OaAttendanceGroupModel> getAttendanceGroupList(@Param("page") IPage<OaAttendanceGroupModel> page, @Param("params") Map<String, Object> params);

    OaAttendanceGroupModel getAttendanceGroupById(@Param("agId") String agId);
}
