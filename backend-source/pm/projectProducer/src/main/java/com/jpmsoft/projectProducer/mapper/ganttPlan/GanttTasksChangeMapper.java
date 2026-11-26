package com.jpmsoft.projectProducer.mapper.ganttPlan;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.response.Plan.GanttTasksChangeVO;
import com.jpmsoft.projectProducer.pojo.entity.ganttPlan.GanttTasksChange;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface GanttTasksChangeMapper extends BaseMapper<GanttTasksChange> {

    List<String> canNot2Delete(@Param("ids") List<String> ids);

    Integer can2SubmitProcess(@Param("id") String id);


    List<GanttTasksChangeVO> getGanttTasksChangeByPlanId(@Param("planId") String planId);
}
