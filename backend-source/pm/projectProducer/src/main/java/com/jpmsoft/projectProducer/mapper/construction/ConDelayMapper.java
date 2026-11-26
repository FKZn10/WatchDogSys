package com.jpmsoft.projectProducer.mapper.construction;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.requestAndResponse.Construction.ConDelayModel;
import com.jpmsoft.projectProducer.pojo.entity.construction.ConDelay;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface ConDelayMapper extends BaseMapper<ConDelay> {

    List<ConDelayModel> getConDelayList(@Param("page") IPage<ConDelayModel> page, @Param("params") Map<String, Object> params);


    ConDelayModel getConDelayById(@Param("deId") String deId);
}
