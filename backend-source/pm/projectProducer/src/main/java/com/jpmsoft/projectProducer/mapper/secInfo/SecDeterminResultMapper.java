package com.jpmsoft.projectProducer.mapper.secInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.response.SecInfo.SecDeterminResultResponse;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecDeterminResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface SecDeterminResultMapper extends BaseMapper<SecDeterminResult> {


    List<SecDeterminResultResponse> getSecDeterminResultList(@Param("params") Map<String, Object> params);

    Integer getSecDeterminResultListCount(@Param("params") Map<String, Object> params);

    SecDeterminResultResponse getSecDeterminResultById(@Param("drId") String drId);

    SecDeterminResultResponse getFirstDeterminResultBySecId(@Param("secId") String secId);
}
