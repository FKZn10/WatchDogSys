package com.jpmsoft.projectProducer.mapper.constructionProcedure;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.response.proCp.ProCpExperienceResponse;
import com.jpmsoft.projectProducer.pojo.entity.constructionProcedure.ProjectCpExperience;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface ProjectCpExperienceMapper extends BaseMapper<ProjectCpExperience> {

    List<ProCpExperienceResponse> getProCpExperienceList(@Param("page") IPage<ProCpExperienceResponse> page, @Param("params") Map<String, Object> params);

    Integer getCpExperienceCountByCpId(@Param("cpId") String cpId);
}
