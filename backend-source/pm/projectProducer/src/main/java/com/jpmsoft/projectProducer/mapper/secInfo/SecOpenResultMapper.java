package com.jpmsoft.projectProducer.mapper.secInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.response.SecInfo.SecOpenResultResponse;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecOpenResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface SecOpenResultMapper extends BaseMapper<SecOpenResult> {

    List<SecOpenResultResponse> getSecOpenResultList(@Param("params") Map<String, Object> params);

    Integer getSecOpenResultListCount(@Param("params") Map<String, Object> params);

    SecOpenResultResponse getSecOpenResultById(@Param("orId") String orId);


    List<SecOpenResultResponse> getSecOpenResultListOne2Many(@Param("params") Map<String, Object> params);

    Integer getSecOpenResultListOne2ManyCount(@Param("params") Map<String, Object> params);
}
