package com.jpmsoft.projectProducer.mapper.constructionProcedure;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.response.proCp.ProjectCpSubtaskResponse;
import com.jpmsoft.projectProducer.pojo.entity.constructionProcedure.ProjectCpSubtask;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ProjectCpSubtaskMapper extends BaseMapper<ProjectCpSubtask> {
    List<ProjectCpSubtaskResponse> getProjectCpSubtaskList(@Param("page") IPage<ProjectCpSubtaskResponse> page, @Param("params") Map<String, Object> params);

    ProjectCpSubtaskResponse getProjectCpSubtaskByCpId(@Param("pcsId") String pcsId);
}
