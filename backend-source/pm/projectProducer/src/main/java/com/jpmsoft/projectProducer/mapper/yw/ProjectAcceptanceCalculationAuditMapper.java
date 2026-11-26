package com.jpmsoft.projectProducer.mapper.yw;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.requestAndResponse.yw.ProjectAcceptanceCalculationAuditModel;
import com.jpmsoft.projectProducer.pojo.entity.yw.ProjectAcceptanceCalculationAudit;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ProjectAcceptanceCalculationAuditMapper extends BaseMapper<ProjectAcceptanceCalculationAudit> {
    List<ProjectAcceptanceCalculationAuditModel> getList(@Param("params")Map<String, Object> query, @Param("page")IPage<ProjectAcceptanceCalculationAuditModel> page);

    ProjectAcceptanceCalculationAuditModel getDetail(@Param("id")String id);
}