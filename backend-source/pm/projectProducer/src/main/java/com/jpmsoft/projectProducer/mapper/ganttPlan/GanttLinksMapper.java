package com.jpmsoft.projectProducer.mapper.ganttPlan;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.response.Plan.GanttLinksResponse;
import com.jpmsoft.projectProducer.pojo.entity.ganttPlan.GanttLinks;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface GanttLinksMapper extends BaseMapper<GanttLinks> {

    List<GanttLinksResponse> getLinksByPlanId(@Param("planId") String planId);
}
