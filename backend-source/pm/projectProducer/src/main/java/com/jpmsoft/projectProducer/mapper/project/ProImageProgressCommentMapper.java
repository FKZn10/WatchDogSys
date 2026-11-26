package com.jpmsoft.projectProducer.mapper.project;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.requestAndResponse.Project.ProImageProgressCommentModel;
import com.jpmsoft.projectProducer.pojo.entity.project.ProImageProgressComment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProImageProgressCommentMapper extends BaseMapper<ProImageProgressComment> {
    List<ProImageProgressCommentModel> getProImageProgressCommentListByIpId(@Param("ipId") String ipId);

    Integer getProImageProgressCommentListByIpIdCount(@Param("ipId") String ipId);

    ProImageProgressComment getProImageProgressCommentById(@Param("id") String id);
}
