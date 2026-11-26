package com.jpmsoft.projectProducer.mapper.project;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.requestAndResponse.Project.ProAssignmentModel;
import com.jpmsoft.projectProducer.pojo.entity.project.ProAssignment;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface ProAssignmentMapper extends BaseMapper<ProAssignment> {


    List<ProAssignmentModel> getProAssignmentList(@Param("page") IPage<ProAssignmentModel> page, @Param("params") Map<String, Object> params);


    ProAssignmentModel getProAssignmentById(@Param("paId") String paId);
}
