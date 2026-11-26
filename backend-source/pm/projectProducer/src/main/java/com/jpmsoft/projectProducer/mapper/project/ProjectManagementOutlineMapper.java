package com.jpmsoft.projectProducer.mapper.project;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.projectProducer.pojo.entity.project.ProjectManagementOutline;
import com.jpmsoft.projectProducer.pojo.vo.project.ProjectManagementOutlineVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface ProjectManagementOutlineMapper extends BaseMapper<ProjectManagementOutline> {
    List<ProjectManagementOutlineVo> getProjectManagementOutlineList(@Param("page") IPage<ProjectManagementOutlineVo> page,
                                                                     @Param("params") Map<String, Object> query);

    ProjectManagementOutlineVo getProjectManagementOutlineById(@Param("id") String id);

    List<Object> getValueByCode(@Param("value") String dbValue, @Param("params") Map<String, Object> query);
}
