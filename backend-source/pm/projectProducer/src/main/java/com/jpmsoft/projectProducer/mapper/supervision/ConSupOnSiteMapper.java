package com.jpmsoft.projectProducer.mapper.supervision;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.response.Supervisor.ConSupOnSiteResponse;
import com.jpmsoft.projectProducer.pojo.entity.supervision.ConSupOnSite;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface ConSupOnSiteMapper extends BaseMapper<ConSupOnSite> {

    List<ConSupOnSiteResponse> getConSupOnSiteList(@Param("page") IPage<ConSupOnSiteResponse> page, @Param("params") Map<String, Object> params);

    ConSupOnSiteResponse getConSupOnSiteById(@Param("osId") String osId);
}
