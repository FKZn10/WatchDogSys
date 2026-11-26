package com.jpmsoft.projectProducer.mapper.visaChange;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.response.DesignChange.ConEncrResponse;
import com.jpmsoft.projectProducer.pojo.entity.visaChange.ConEncr;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface ConEncrMapper extends BaseMapper<ConEncr> {

    List<ConEncrResponse> getConEncrList(@Param("page") IPage<ConEncrResponse> page, @Param("params") Map<String, Object> params);


    ConEncrResponse getConEncrById(@Param("encrId") String encrId);
}
