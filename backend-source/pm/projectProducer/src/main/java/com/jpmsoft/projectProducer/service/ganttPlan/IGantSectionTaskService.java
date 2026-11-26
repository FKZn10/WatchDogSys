package com.jpmsoft.projectProducer.service.ganttPlan;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.request.Plan.GantSectionTaskDTO;
import com.jpmsoft.common.model.request.Plan.GanttTasksRequest;
import com.jpmsoft.projectProducer.pojo.entity.ganttPlan.GantSectionTask;
import com.jpmsoft.projectProducer.pojo.entity.ganttPlan.GantSectionTaskChange;

import java.util.List;
import java.util.Map;

/**
 * @Author: 李文涛 aaron
 * @date: 2025/4/8 11:24
 */
public interface IGantSectionTaskService extends IService<GantSectionTask> {

    void deleteByTaskIds(List<String> taskIds);

    void deleteByPlanId(String planId);

    List<GantSectionTaskDTO> getGantSectionTaskByPlanId(String planId);

    List<GantSectionTaskDTO> getGantSectionTaskByPlanIds(List<String> planIds);

    void createOrUpdateSectionTask(List<GanttTasksRequest> tasks,String planId,List<String> deleteTasks);

    void syncSectionTaskChange(List<GantSectionTaskDTO> data, String planId, Map<String,String> taskIdMapping);
}
