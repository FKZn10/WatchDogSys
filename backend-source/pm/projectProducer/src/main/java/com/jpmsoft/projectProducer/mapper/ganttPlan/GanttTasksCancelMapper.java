package com.jpmsoft.projectProducer.mapper.ganttPlan;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.request.Plan.GanttTasksCancelDTO;
import com.jpmsoft.common.model.response.Plan.GanttTasksCancelResponse;
import com.jpmsoft.projectProducer.pojo.entity.ganttPlan.GanttTasksCancel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author Administrator
* @description 针对表【gantt_tasks_cancel(甘特图任务取消记录表)】的数据库操作Mapper
* @createDate 2025-04-19 19:19:55
* @Entity com.whjpsoft.pojo/entity.GanttTasksCancel
*/
public interface GanttTasksCancelMapper extends BaseMapper<GanttTasksCancel> {


    List<GanttTasksCancelResponse> getGanttTasksCancelResponseByTaskId(@Param("taskId") String taskId);
    List<GanttTasksCancelResponse> getGanttTasksCancelResponseByTaskId(@Param("iPage") IPage<GanttTasksCancelResponse> page, @Param("taskId") String taskId);

    GanttTasksCancelResponse getGanttCannelById(@Param("id") String id);
}




