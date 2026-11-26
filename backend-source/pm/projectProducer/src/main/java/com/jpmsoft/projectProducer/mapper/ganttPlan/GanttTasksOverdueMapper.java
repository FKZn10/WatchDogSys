package com.jpmsoft.projectProducer.mapper.ganttPlan;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.projectProducer.pojo.entity.ganttPlan.GanttTasksOverdue;
import com.jpmsoft.projectProducer.pojo.response.OverdueTaskResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author Administrator
* @description 针对表【gantt_tasks_overdue】的数据库操作Mapper
* @createDate 2025-04-19 14:57:41
* @Entity com.whjpsoft.pojo/entity.GanttTasksOverdue
*/
public interface GanttTasksOverdueMapper extends BaseMapper<GanttTasksOverdue> {
    List<OverdueTaskResponse> getGanttTasksOverdueByTaskId(@Param("taskId") String taskId);
    List<OverdueTaskResponse> getGanttTasksOverdueByTaskId(@Param("ipage") IPage<OverdueTaskResponse> ipage,@Param("taskId") String taskId);

    OverdueTaskResponse getGanttTasksOverdueById(@Param("id") String id);
}




