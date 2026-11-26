package com.jpmsoft.projectProducer.service.ganttPlan;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Plan.GanttTasksOverdueDTO;
import com.jpmsoft.projectProducer.pojo.entity.ganttPlan.GanttTasksOverdue;
import com.jpmsoft.projectProducer.pojo.response.OverdueTaskResponse;

import java.util.List;

/**
* @author Administrator
* @description 针对表【gantt_tasks_overdue】的数据库操作Service
* @createDate 2025-04-19 14:57:41
*/
public interface GanttTasksOverdueService extends IService<GanttTasksOverdue> {

    BaseResponse createOrUpdateGanttPlan(GanttTasksOverdueDTO ganttTasksOverdue);
    List<OverdueTaskResponse> getGanttTasksOverdueByTaskId(String taskId);
    BaseResponse getGanttTasksOverdueByTaskIdByPage(int page,int size,String  taskId);

    BaseResponse getGanttTaskOverdueById(String id);

    BaseResponse existInApprovalTaskOverdue(String taskId);
}
