package com.jpmsoft.jpbpm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.jpbpm.mapper.GanttTasksProcessMapper;
import com.jpmsoft.jpbpm.pojo.entity.GanttTasksProcess;
import com.jpmsoft.jpbpm.service.GanttTasksProcessService;
import com.jpmsoft.jpbpm.service.GanttTasksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GanttTasksProcessImpl extends ServiceImpl<GanttTasksProcessMapper, GanttTasksProcess> implements GanttTasksProcessService {

    @Autowired
    private GanttTasksService ganttTasksService;

    @Override
    public void ganttTasksProcessToUpdateGanttTasks(String id) {
        GanttTasksProcess pojo = baseMapper.selectById(id);
        Float taskRate = baseMapper.getGtpRateSum(pojo.getTaskId());
        if (taskRate.compareTo(100F) == 0)
            baseMapper.updateGanttTasksProgressAndFinishTime(pojo.getTaskId(), taskRate, pojo.getGtpDate());
        else
            baseMapper.updateGanttTasksProgress(pojo.getTaskId(), taskRate);
        //递归更新任务
        ganttTasksService.RecursionUpdateGanttTasksParent(pojo.getTaskId(), pojo.getGtpDate());
    }
}
