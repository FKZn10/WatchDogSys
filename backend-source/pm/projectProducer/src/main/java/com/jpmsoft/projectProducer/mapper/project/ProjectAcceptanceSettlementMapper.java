package com.jpmsoft.projectProducer.mapper.project;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.requestAndResponse.Project.ProjectAcceptanceSettlementModel;
import com.jpmsoft.projectProducer.pojo.entity.project.ProjectAcceptanceSettlement;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface ProjectAcceptanceSettlementMapper extends BaseMapper<ProjectAcceptanceSettlement> {
    List<ProjectAcceptanceSettlementModel> getProjectAcceptanceSettlementList(@Param("params")Map<String, Object> query,
                                                                              @Param("page")IPage<ProjectAcceptanceSettlementModel> page);

    ProjectAcceptanceSettlementModel getProjectAcceptanceSettlementById(@Param("id")String id);
}
