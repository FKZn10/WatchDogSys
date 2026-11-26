package com.jpmsoft.projectProducer.mapper.secInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.response.SecInfo.SecWinUnitMemberResponse;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecWinUnitMember;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface SecWinUnitMemberMapper extends BaseMapper<SecWinUnitMember> {

    List<SecWinUnitMemberResponse> getWinUnitMemberList(@Param("params") Map<String, Object> params);

    Integer getWinUnitMemberListCount(@Param("params") Map<String, Object> params);
}
