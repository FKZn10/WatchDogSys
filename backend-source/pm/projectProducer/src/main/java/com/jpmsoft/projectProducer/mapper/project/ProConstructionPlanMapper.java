package com.jpmsoft.projectProducer.mapper.project;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.requestAndResponse.Project.ProConstructionPlanModel;
import com.jpmsoft.projectProducer.pojo.entity.project.ProConstructionPlan;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


public interface ProConstructionPlanMapper extends BaseMapper<ProConstructionPlan> {

    List<ProConstructionPlanModel> getProConstructionPlanList(@Param("page")  IPage<ProConstructionPlanModel> page,@Param("params") Map<String, Object> params);

    BigDecimal getCplPlannedInvSum(@Param("params") Map<String, Object> params);

    ProConstructionPlanModel getProConstructionPlanById(@Param("pcpId") String pcpId);

}
