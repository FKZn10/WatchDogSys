package com.jpmsoft.projectProducer.mapper.project;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.requestAndResponse.Project.ProImageProgressLikeModel;
import com.jpmsoft.projectProducer.pojo.entity.project.ProImageProgressLike;
import org.apache.ibatis.annotations.Param;

public interface ProImageProgressLikeMapper extends BaseMapper<ProImageProgressLike> {
    ProImageProgressLikeModel getProImageProgressLikeByUid(@Param("params") ProImageProgressLikeModel request);
}
