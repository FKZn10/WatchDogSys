package com.jpmsoft.projectProducer.mapper.project;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.requestAndResponse.Project.ProImageProgressModel;
import com.jpmsoft.projectProducer.pojo.entity.project.ProImageProgress;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ProImageProgressMapper extends BaseMapper<ProImageProgress> {
    List<ProImageProgressModel> getProImageProgressList(@Param("params") Map<String, Object> params);

    Integer getProImageProgressListCount(@Param("params") Map<String, Object> params);

    ProImageProgressModel getProImageProgressById(@Param("id") String id, @Param("userId") String userId);
}
