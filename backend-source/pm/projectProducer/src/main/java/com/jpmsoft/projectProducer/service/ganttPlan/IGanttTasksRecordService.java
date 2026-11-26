package com.jpmsoft.projectProducer.service.ganttPlan;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.request.Plan.GanttTasksChangeDTO;
import com.jpmsoft.common.model.response.Plan.GanttTasksChangeVO;
import com.jpmsoft.projectProducer.pojo.entity.ganttPlan.GanttTasksRecord;

import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/4/25 18:19
 */
public interface IGanttTasksRecordService extends IService<GanttTasksRecord> {

    void createGanttTasksRecord(List<GanttTasksChangeDTO> records, String planId);

    List<GanttTasksChangeVO> getGanttTasksRecordByPlanId(String planId);
}
