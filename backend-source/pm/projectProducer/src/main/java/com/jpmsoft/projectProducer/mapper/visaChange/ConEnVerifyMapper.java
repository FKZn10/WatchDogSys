package com.jpmsoft.projectProducer.mapper.visaChange;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.response.DesignChange.ConEnVerifyResponse;
import com.jpmsoft.projectProducer.pojo.entity.visaChange.ConEnVerify;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface ConEnVerifyMapper extends BaseMapper<ConEnVerify> {


    List<ConEnVerifyResponse> getConEnVerifyList(@Param("page") IPage<ConEnVerifyResponse> page, @Param("params") Map<String, Object> params);


    ConEnVerifyResponse getConEnVerifyById(@Param("envId") String envId);
}
