package com.jpmsoft.projectProducer.service.ganttPlan;


import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Plan.GanttTasksCancelDTO;
import com.jpmsoft.common.model.response.Plan.GanttTasksCancelResponse;
import com.jpmsoft.projectProducer.pojo.entity.ganttPlan.GanttTasksCancel;

import java.util.List;

/**
 * @author Administrator
 * @description 针对表【gantt_tasks_cancel(甘特图任务取消记录表)】的数据库操作Service
 * @createDate 2025-04-19 19:19:55
 */
public interface GanttTasksCancelService extends IService<GanttTasksCancel> {

    BaseResponse createOrUpdateGanttCannel(GanttTasksCancelDTO ganttTasksCancelDTO);

    List<GanttTasksCancelResponse> getGanttTasksCannelByTaskId(String taskId);

    BaseResponse getGanttTasksCannelByTaskIdByPage(int page, int size, String taskId);

    BaseResponse rewriteTask(String taskId);

    BaseResponse getGanttCannelById(String id);

    /**
     * 任务下
     * 是否存在非作废的任务取消数据
     *
     * @param taskId
     * @return
     */
    BaseResponse existApproveGanttCancel(String taskId);
}
