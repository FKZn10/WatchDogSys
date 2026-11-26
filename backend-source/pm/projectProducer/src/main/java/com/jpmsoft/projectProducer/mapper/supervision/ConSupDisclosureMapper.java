package com.jpmsoft.projectProducer.mapper.supervision;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.response.Supervisor.ConSupDisclosureResponse;
import com.jpmsoft.projectProducer.pojo.entity.supervision.ConSupDisclosure;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface ConSupDisclosureMapper extends BaseMapper<ConSupDisclosure> {

    List<ConSupDisclosureResponse> getConSupDisclosureList(@Param("page") IPage<ConSupDisclosureResponse> page, @Param("params") Map<String, Object> params);


    ConSupDisclosureResponse getConSupDisclosureById(@Param("disId") String disId);
}
