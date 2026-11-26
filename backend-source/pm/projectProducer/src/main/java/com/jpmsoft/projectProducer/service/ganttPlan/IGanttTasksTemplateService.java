package com.jpmsoft.projectProducer.service.ganttPlan;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.request.Plan.GanttTasksTemplateDTO;
import com.jpmsoft.projectProducer.pojo.entity.ganttPlan.GanttTasksTemplate;

import java.util.List;


public interface IGanttTasksTemplateService extends IService<GanttTasksTemplate> {

    List<String> selectCurrentCplId(String ptId);

    void createOrUpdateGanttTasks(List<GanttTasksTemplateDTO> ganttTasks, String ptId);


    List<GanttTasksTemplateDTO> getGanttTasksTemplateTree(String ptId);

    /**
     * 获取任务节点最下级列表
     * @param ptId
     * @return
     */
    List<GanttTasksTemplateDTO> getGanttTasksTemplateLast(String ptId);
}
