package com.jpmsoft.projectProducer.mapper.ganttPlan;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.response.Plan.GanttPlanChangeVO;
import com.jpmsoft.projectProducer.pojo.entity.ganttPlan.GanttPlanChange;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface GanttPlanChangeMapper extends BaseMapper<GanttPlanChange> {

    List<GanttPlanChangeVO> getGanttPlanChangeList(@Param("page") IPage<GanttPlanChangeVO> page, @Param("params") Map<String, Object> params);

    GanttPlanChangeVO selectGanttPlanChangeById(@Param("id")  String id);
}
