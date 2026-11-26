package com.jpmsoft.projectProducer.mapper.secInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecExpertDeleteModel;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecExpertDelete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 专家删除申请
 */
@Mapper
public interface SecExpertDeleteMapper extends BaseMapper<SecExpertDelete> {
    List<SecExpertDeleteModel> getSecExpertDeleteList(@Param("page") Page<SecExpertDeleteModel> page, @Param("params") Map<String, Object> query);

    SecExpertDeleteModel getSecExpertDeleteById(String id);
}
