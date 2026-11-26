package com.jpmsoft.projectProducer.mapper.secInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.response.SecInfo.SecPublicityResultsResponse;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecPublicityResults;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface SecPublicityResultsMapper extends BaseMapper<SecPublicityResults> {

    List<SecPublicityResultsResponse> getSecPublicityResultsList(@Param("params") Map<String, Object> params);

    Integer getSecPublicityResultsListCount(@Param("params") Map<String, Object> params);

    SecPublicityResultsResponse getSecPublicityResultsById(@Param("prId") String prId);

    List<SecPublicityResultsResponse> getSecPublicityResultsListOne2Many(@Param("params") Map<String, Object> params);

    Integer getSecPublicityResultsListOne2ManyCount(@Param("params") Map<String, Object> params);

}
