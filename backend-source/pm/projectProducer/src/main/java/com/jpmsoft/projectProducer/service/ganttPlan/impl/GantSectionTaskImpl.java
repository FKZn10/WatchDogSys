package com.jpmsoft.projectProducer.service.ganttPlan.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.request.Plan.GantSectionTaskDTO;
import com.jpmsoft.common.model.request.Plan.GanttTasksRequest;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.projectProducer.mapper.ganttPlan.GantSectionTaskMapper;
import com.jpmsoft.projectProducer.pojo.entity.ganttPlan.GantSectionTask;
import com.jpmsoft.projectProducer.pojo.entity.ganttPlan.GantSectionTaskChange;
import com.jpmsoft.projectProducer.service.ganttPlan.IGantSectionTaskService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: 李文涛 aaron
 * @date: 2025/4/8 11:24
 */
@Service
public class GantSectionTaskImpl extends ServiceImpl<GantSectionTaskMapper, GantSectionTask>
        implements IGantSectionTaskService {

    @Override
    public void deleteByTaskIds(List<String> taskIds) {
        LambdaQueryWrapper<GantSectionTask> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(GantSectionTask::getTaskId, taskIds);
        baseMapper.delete(wrapper);
    }

    @Override
    public void deleteByPlanId(String planId) {
        LambdaQueryWrapper<GantSectionTask> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(GantSectionTask::getPlanId, planId);
        baseMapper.delete(wrapper);
    }

    @Override
    public List<GantSectionTaskDTO> getGantSectionTaskByPlanId(String planId) {
        LambdaQueryWrapper<GantSectionTask> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(GantSectionTask::getPlanId, planId);
        List<GantSectionTask> entities = baseMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(entities))
            return entities.stream().map(e -> {
                GantSectionTaskDTO d = new GantSectionTaskDTO();
                BeanUtils.copyProperties(e, d);
                return d;
            }).collect(Collectors.toList());
        return new ArrayList<>();
    }

    @Override
    public List<GantSectionTaskDTO> getGantSectionTaskByPlanIds(List<String> planIds) {
        LambdaQueryWrapper<GantSectionTask> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(GantSectionTask::getPlanId, planIds);
        List<GantSectionTask> entities = baseMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(entities))
            return entities.stream().map(e -> {
                GantSectionTaskDTO d = new GantSectionTaskDTO();
                BeanUtils.copyProperties(e, d);
                return d;
            }).collect(Collectors.toList());
        return new ArrayList<>();
    }

    @Override
    public void createOrUpdateSectionTask(List<GanttTasksRequest> tasks, String planId, List<String> deleteTasks) {
        deleteByPlanId(planId);
        if (!CollectionUtils.isEmpty(tasks)) {
            if (!CollectionUtils.isEmpty(deleteTasks))
                tasks = tasks.stream().filter(f -> deleteTasks.contains(f.getTaskId())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(tasks)) {
                List<GantSectionTask> sectionTasks = new ArrayList<>();
                for (GanttTasksRequest task : tasks) {
                    if (!CollectionUtils.isEmpty(task.getGstSections())) {
                        for (String gstSection : task.getGstSections()) {
                            GantSectionTask entity = new GantSectionTask();
                            entity.setId(GenerateUUID.getUUID());
                            entity.setPlanId(planId);
                            entity.setTaskId(task.getTaskId());
                            entity.setGstSection(gstSection);
                            sectionTasks.add(entity);
                        }
                    }
                }
                if (!CollectionUtils.isEmpty(sectionTasks)) saveBatch(sectionTasks, 100);
            }
        }
    }

    @Override
    public void syncSectionTaskChange(List<GantSectionTaskDTO> data, String planId,
                                      Map<String, String> taskIdMapping) {
        deleteByPlanId(planId);
        if (!CollectionUtils.isEmpty(data)) {
            List<GantSectionTask> entities = data.stream().map(d -> {
                GantSectionTask e = new GantSectionTask();
                e.setId(GenerateUUID.getUUID(d.getGstSource()));
                e.setTaskId(taskIdMapping.get(d.getTaskId()));
                e.setGstSection(d.getGstSection());
                e.setPlanId(planId);
                return e;
            }).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(data)) saveBatch(entities, 100);
        }
    }
}
