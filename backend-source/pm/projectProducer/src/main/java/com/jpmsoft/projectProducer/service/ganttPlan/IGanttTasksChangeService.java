package com.jpmsoft.projectProducer.service.ganttPlan;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.request.Plan.GanttTasksChangeDTO;
import com.jpmsoft.common.model.response.Plan.GanttTasksChangeVO;
import com.jpmsoft.projectProducer.pojo.entity.ganttPlan.GanttTasksChange;

import java.util.List;
import java.util.Map;


public interface IGanttTasksChangeService extends IService<GanttTasksChange> {

    /**
     * 新增修改任务
     *
     * @param ganttTasks
     * @param planId
     * @return key:老的taskId value:新的taskId 用于更新关联线
     */
    Map<String, String> createOrUpdateTaskChange(List<GanttTasksChangeDTO> ganttTasks, String planId);

    /**
     * 返回已经填报的想要删除的任务名称集合
     *
     * @param ganttTasks
     * @return
     */
    List<String> canNot2Delete(List<GanttTasksChangeDTO> ganttTasks);

    /**
     * 判断当前任务是否属于变更中且将要删除的任务
     * 是则不允许填报
     *
     * @param id
     * @return
     */
    boolean can2SubmitProcess(String id);

    void deleteByPlanId(String planId);

    List<GanttTasksChangeVO> getGanttTasksChangeByPlanId(String planId);
}
