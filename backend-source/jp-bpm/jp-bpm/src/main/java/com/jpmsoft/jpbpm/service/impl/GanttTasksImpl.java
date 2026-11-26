package com.jpmsoft.jpbpm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.jpbpm.mapper.GanttTasksMapper;
import com.jpmsoft.jpbpm.pojo.entity.GanttTasks;
import com.jpmsoft.jpbpm.service.GanttTasksService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GanttTasksImpl extends ServiceImpl<GanttTasksMapper, GanttTasks> implements GanttTasksService {

    @Override
    public void RecursionUpdateGanttTasksParent(String taskId, Date finishTime) {
        GanttTasks tasks = baseMapper.selectById(taskId);
        if (tasks != null) {
            QueryWrapper<GanttTasks> wrapper = new QueryWrapper<>();
            wrapper.eq("plan_Id", tasks.getPlanId());
            wrapper.eq("Task_State", 1);
            List<GanttTasks> allTasks = baseMapper.selectList(wrapper);
            List<GanttTasks> toUpdate = new ArrayList<>();
            updateGanttTasksParent(toUpdate, allTasks, tasks.getTaskParent(), finishTime);
            if (toUpdate.size() > 0)
                updateBatchById(toUpdate);
        }
    }

    private void updateGanttTasksParent(List<GanttTasks> toUpdate, List<GanttTasks> allTasks, String taskParent, Date finishTime) {
        List<GanttTasks> datas = allTasks.stream().filter(n -> n.getTaskParent().equals(taskParent) && n.getTaskProgress().compareTo(new Float(100)) != 0).collect(Collectors.toList());
        if (datas == null || datas.size() < 1) {
            List<GanttTasks> parents = allTasks.stream().filter(n -> n.getTaskId().equals(taskParent)).collect(Collectors.toList());
            if (parents != null && parents.size() > 0) {
                GanttTasks parent = parents.stream().findFirst().get();
                parent.setTaskProgress(100F);
                parent.setTaskFinishTime(finishTime);
                toUpdate.add(parent);
                updateGanttTasksParent(toUpdate, allTasks, parent.getTaskParent(), finishTime);
            }
        }
    }
}
