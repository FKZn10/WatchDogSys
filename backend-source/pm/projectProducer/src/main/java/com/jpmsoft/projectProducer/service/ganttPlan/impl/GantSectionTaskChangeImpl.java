package com.jpmsoft.projectProducer.service.ganttPlan.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.request.Plan.GantSectionTaskDTO;
import com.jpmsoft.common.model.request.Plan.GanttTasksChangeDTO;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.projectProducer.mapper.ganttPlan.GantSectionTaskChangeMapper;
import com.jpmsoft.projectProducer.pojo.entity.ganttPlan.GantSectionTaskChange;
import com.jpmsoft.projectProducer.service.ganttPlan.IGantSectionTaskChangeService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: 李文涛 aaron
 * @date: 2025/4/8 11:24
 */
@Service
public class GantSectionTaskChangeImpl extends ServiceImpl<GantSectionTaskChangeMapper, GantSectionTaskChange>
        implements IGantSectionTaskChangeService {

    @Override
    public void deleteByTaskIds(List<String> taskIds) {
        LambdaQueryWrapper<GantSectionTaskChange> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(GantSectionTaskChange::getTaskId, taskIds);
        baseMapper.delete(wrapper);
    }

    @Override
    public void deleteByPlanId(String planId) {
        LambdaQueryWrapper<GantSectionTaskChange> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(GantSectionTaskChange::getPlanId, planId);
        baseMapper.delete(wrapper);
    }

    @Override
    public List<GantSectionTaskDTO> getGantSectionTaskByPlanId(String planId, List<String> taskIds) {
        LambdaQueryWrapper<GantSectionTaskChange> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(GantSectionTaskChange::getPlanId, planId);
        if (!CollectionUtils.isEmpty(taskIds))
            wrapper.notIn(GantSectionTaskChange::getTaskId, taskIds);
        List<GantSectionTaskChange> entities = baseMapper.selectList(wrapper);
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
        LambdaQueryWrapper<GantSectionTaskChange> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(GantSectionTaskChange::getPlanId, planIds);
        List<GantSectionTaskChange> entities = baseMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(entities))
            return entities.stream().map(e -> {
                GantSectionTaskDTO d = new GantSectionTaskDTO();
                BeanUtils.copyProperties(e, d);
                return d;
            }).collect(Collectors.toList());
        return new ArrayList<>();
    }

    @Override
    public void createOrUpdateSectionTask(List<GanttTasksChangeDTO> tasks, String planId, List<String> deleteTasks) {
        deleteByPlanId(planId);
        if (!CollectionUtils.isEmpty(tasks)) {
            if (!CollectionUtils.isEmpty(deleteTasks))
                tasks = tasks.stream().filter(f -> deleteTasks.contains(f.getTaskId())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(tasks)) {
                List<GantSectionTaskChange> sectionTasks = new ArrayList<>();
                for (GanttTasksChangeDTO task : tasks) {
                    if (!CollectionUtils.isEmpty(task.getGstSections())) {
                        for (String gstSection : task.getGstSections()) {
                            GantSectionTaskChange entity = new GantSectionTaskChange();
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
}
