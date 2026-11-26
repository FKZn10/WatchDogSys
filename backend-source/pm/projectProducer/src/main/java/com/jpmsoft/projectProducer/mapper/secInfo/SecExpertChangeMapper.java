package com.jpmsoft.projectProducer.mapper.secInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecExpertChangeModel;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecExpertChange;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 专家库变更记录
 */
@Mapper
public interface SecExpertChangeMapper extends BaseMapper<SecExpertChange> {
    List<SecExpertChangeModel> getSecExpertChangeList(@Param("page") Page<SecExpertChangeModel> page, @Param("params") Map<String, Object> request);

    SecExpertChangeModel getSecExpertChangeById(String id);
}
