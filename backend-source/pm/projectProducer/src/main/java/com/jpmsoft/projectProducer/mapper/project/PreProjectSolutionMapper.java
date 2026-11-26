package com.jpmsoft.projectProducer.mapper.project;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.requestAndResponse.Project.PreProjectSolutionModel;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProjectSolution;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface PreProjectSolutionMapper extends BaseMapper<PreProjectSolution> {
    List<PreProjectSolutionModel> getPreProjectSolutionList(@Param("params") Map<String, Object> query, @Param("page") IPage<PreProjectSolutionModel> page);

    PreProjectSolutionModel getPreProjectSolutionById(@Param("id") String id);

    List<PreProjectSolutionModel> getPreProjectSolutionListModifyInfo(@Param("params")Map<String, Object> query,  @Param("page") IPage<PreProjectSolutionModel> page);
}
