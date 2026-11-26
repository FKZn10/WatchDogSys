package com.jpmsoft.projectProducer.mapper.project;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.requestAndResponse.Project.ProImageProgressModel;
import com.jpmsoft.projectProducer.pojo.entity.project.ProImageProgressReply;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ProImageProgressReplyMapper extends BaseMapper<ProImageProgressReply> {
    List<ProImageProgressModel> getProImageProgressReplyList(@Param("params") Map<String, Object> params);

    ProImageProgressModel getProImageProgressReplyById(@Param("id") String id);
}
