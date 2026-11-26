package com.jpmsoft.projectProducer.service.ganttPlan;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.request.Plan.GantSectionTaskDTO;
import com.jpmsoft.common.model.request.Plan.GanttTasksChangeDTO;
import com.jpmsoft.common.model.request.Plan.GanttTasksRequest;
import com.jpmsoft.projectProducer.pojo.entity.ganttPlan.GantSectionTaskChange;

import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2025/4/8 11:24
 */
public interface IGantSectionTaskChangeService extends IService<GantSectionTaskChange> {

    void deleteByTaskIds(List<String> taskIds);

    void deleteByPlanId(String planId);

    List<GantSectionTaskDTO> getGantSectionTaskByPlanId(String planId,List<String> taskIds);

    List<GantSectionTaskDTO> getGantSectionTaskByPlanIds(List<String> planIds);

    void createOrUpdateSectionTask(List<GanttTasksChangeDTO> tasks, String planId, List<String> deleteTasks);
}
