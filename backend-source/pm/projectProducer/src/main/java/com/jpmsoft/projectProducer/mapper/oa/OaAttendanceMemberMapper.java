package com.jpmsoft.projectProducer.mapper.oa;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.requestAndResponse.Oa.OaAttendanceMemberModel;
import com.jpmsoft.projectProducer.pojo.entity.oa.OaAttendanceMember;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface OaAttendanceMemberMapper extends BaseMapper<OaAttendanceMember> {

    OaAttendanceMember getEnabledAttendanceMemberByUid(@Param("amUid") String amUid);

    List<OaAttendanceMemberModel> getAttendanceMemberList(@Param("params") Map<String, Object> params);

    Integer getAttendanceMemberListCount(@Param("params") Map<String, Object> params);
}
