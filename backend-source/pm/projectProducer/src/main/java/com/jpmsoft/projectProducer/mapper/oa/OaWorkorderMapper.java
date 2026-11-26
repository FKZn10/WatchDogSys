package com.jpmsoft.projectProducer.mapper.oa;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.response.Oa.OaWorkorderResponse;
import com.jpmsoft.projectProducer.pojo.entity.oa.OaWorkorder;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface OaWorkorderMapper extends BaseMapper<OaWorkorder> {


    List<OaWorkorderResponse> getOaWorkorderList(@Param("page") IPage<OaWorkorderResponse> page, @Param("params") Map<String, Object> params);


    OaWorkorderResponse getOaWorkorderById(@Param("woId") String woId);
}
