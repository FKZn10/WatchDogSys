package com.jpmsoft.projectProducer.mapper.visaChange;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.response.DesignChange.ConSvVerifyResponse;
import com.jpmsoft.projectProducer.pojo.entity.visaChange.ConSvVerify;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface ConSvVerifyMapper extends BaseMapper<ConSvVerify> {


    List<ConSvVerifyResponse> getConSvVerifyList(@Param("params") Map<String, Object> params);

    Integer getConSvVerifyListCount(@Param("params") Map<String, Object> params);

    ConSvVerifyResponse getConSvVerifyById(@Param("svvId") String svvId);
}
