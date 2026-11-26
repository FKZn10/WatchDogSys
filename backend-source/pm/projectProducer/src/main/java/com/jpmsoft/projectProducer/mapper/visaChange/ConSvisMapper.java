package com.jpmsoft.projectProducer.mapper.visaChange;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.response.DesignChange.ConSvisResponse;
import com.jpmsoft.projectProducer.pojo.entity.visaChange.ConSvis;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface ConSvisMapper extends BaseMapper<ConSvis> {


    List<ConSvisResponse> getConSvisList(@Param("params") Map<String, Object> params);

    Integer getConSvisListCount(@Param("params") Map<String, Object> params);

    ConSvisResponse getConSvisById(@Param("svisId") String svisId);
}
