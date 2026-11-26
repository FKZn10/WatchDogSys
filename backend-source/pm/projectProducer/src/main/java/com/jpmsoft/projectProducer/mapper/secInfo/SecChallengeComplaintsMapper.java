package com.jpmsoft.projectProducer.mapper.secInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.response.SecInfo.SecChallengeComplaintsResponse;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecChallengeComplaints;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface SecChallengeComplaintsMapper extends BaseMapper<SecChallengeComplaints> {

    List<SecChallengeComplaintsResponse> getSecChallengeComplaintsList(@Param("params") Map<String, Object> params);

    Integer getSecChallengeComplaintsListCount(@Param("params") Map<String, Object> params);

    SecChallengeComplaintsResponse getSecChallengeComplaintsById(@Param("sccId") String sccId);

    List<SecChallengeComplaintsResponse> getSecChallengeComplaintsListOne2Many(@Param("params") Map<String, Object> params);

    Integer getSecChallengeComplaintsListOne2ManyCount(@Param("params") Map<String, Object> params);


}
