package com.jpmsoft.projectProducer.mapper.constructionProcedure;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.response.UserProjectCpResponse;
import com.jpmsoft.common.model.response.proCp.ProjectCpChangeVO;
import com.jpmsoft.common.model.response.proCp.ProjectCpResponse;
import com.jpmsoft.projectProducer.pojo.entity.constructionProcedure.ProjectCpChange;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface ProjectCpChangeMapper extends BaseMapper<ProjectCpChange> {


    List<ProjectCpChangeVO> getProCpChangeList(@Param("page") IPage<ProjectCpChangeVO> page, @Param("params") Map<String, Object> params);

    ProjectCpChangeVO getProjectCpChangeById(@Param("id")  String id);
}
