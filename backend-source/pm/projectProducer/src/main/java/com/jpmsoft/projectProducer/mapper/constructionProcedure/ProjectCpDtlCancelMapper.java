package com.jpmsoft.projectProducer.mapper.constructionProcedure;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.requestAndResponse.proCp.ProjectCpDtlCancelModel;
import com.jpmsoft.projectProducer.pojo.entity.constructionProcedure.ProjectCpDtlCancel;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface ProjectCpDtlCancelMapper extends BaseMapper<ProjectCpDtlCancel> {


    List<ProjectCpDtlCancelModel> getProjectCpDtlCancelList(@Param("page") IPage<ProjectCpDtlCancelModel> page, @Param("params") Map<String, Object> params);


    ProjectCpDtlCancelModel getProjectCpDtlCancelById(@Param("cdcId") String cdcId);
}
