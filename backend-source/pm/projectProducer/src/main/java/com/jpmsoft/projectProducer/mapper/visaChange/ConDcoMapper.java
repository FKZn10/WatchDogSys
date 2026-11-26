package com.jpmsoft.projectProducer.mapper.visaChange;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.response.DesignChange.ConDcoResponse;
import com.jpmsoft.projectProducer.pojo.entity.visaChange.ConDco;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface ConDcoMapper extends BaseMapper<ConDco> {


    List<ConDcoResponse> getConDcoList(@Param("page") IPage<ConDcoResponse> page, @Param("params") Map<String, Object> params);

    ConDcoResponse getConDcoByDcoId(@Param("dcoId") String dcoId);
}
