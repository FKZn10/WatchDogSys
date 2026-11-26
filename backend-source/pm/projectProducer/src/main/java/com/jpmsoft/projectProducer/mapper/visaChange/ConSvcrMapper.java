package com.jpmsoft.projectProducer.mapper.visaChange;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.response.DesignChange.ConSvcrResponse;
import com.jpmsoft.projectProducer.pojo.entity.visaChange.ConSvcr;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface ConSvcrMapper extends BaseMapper<ConSvcr> {


    List<ConSvcrResponse> getConSvcrList(@Param("page") IPage<ConSvcrResponse> page, @Param("params") Map<String, Object> params);

    ConSvcrResponse getConSvcrById(@Param("svcrId") String svcrId);
}
