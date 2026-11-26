package com.jpmsoft.projectProducer.mapper.visaChange;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.response.DesignChange.ConEnisResponse;
import com.jpmsoft.projectProducer.pojo.entity.visaChange.ConEnis;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface ConEnisMapper extends BaseMapper<ConEnis> {

    List<ConEnisResponse> getConEnisList(@Param("page") IPage<ConEnisResponse> page, @Param("params") Map<String, Object> params);


    ConEnisResponse getConEnisById(@Param("enisId") String enisId);
}
