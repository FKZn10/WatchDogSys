package com.jpmsoft.projectProducer.mapper.secInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.response.SecInfo.SecBidEvaluationExpertResponse;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecBidEvaluationExpert;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface SecBidEvaluationExpertMapper extends BaseMapper<SecBidEvaluationExpert> {

    List<SecBidEvaluationExpertResponse> getSecBidEvaluationExpertList(@Param("params") Map<String, Object> params);

    Integer getSecBidEvaluationExpertListCount(@Param("params") Map<String, Object> params);

    SecBidEvaluationExpertResponse getSecBidEvaluationExpertById(@Param("beeId") String beeId);
}
