package com.jpmsoft.projectProducer.mapper.visaChange;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.response.DesignChange.ConEcmResponse;
import com.jpmsoft.projectProducer.pojo.entity.visaChange.ConEcm;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface ConEcmMapper extends BaseMapper<ConEcm> {


    List<ConEcmResponse> getConEcmList(@Param("page") IPage<ConEcmResponse> page, @Param("params") Map<String, Object> params);

    ConEcmResponse getConEcmById(@Param("ecmId") String ecmId);
}
