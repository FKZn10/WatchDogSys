package com.jpmsoft.projectProducer.service.ganttPlan.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.request.Plan.GanttTasksChangeDTO;
import com.jpmsoft.common.model.response.Plan.GanttTasksChangeVO;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.ganttPlan.GanttTasksChangeMapper;
import com.jpmsoft.projectProducer.pojo.entity.ganttPlan.GanttTasksChange;
import com.jpmsoft.projectProducer.service.ganttPlan.IGanttTasksChangeService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/4/23 10:05
 */
@Service
public class GanttTasksChangeImpl extends ServiceImpl<GanttTasksChangeMapper, GanttTasksChange> implements IGanttTasksChangeService {


    @Override
    public Map<String, String> createOrUpdateTaskChange(List<GanttTasksChangeDTO> ganttTasks, String planId) {
        deleteByPlanId(planId);
        Map<String, String> map = new HashMap<>();
        if (!CollectionUtils.isEmpty(ganttTasks)) {
            List<GanttTasksChange> data = ganttTasks.stream().map(task -> {
                GanttTasksChange t = new GanttTasksChange();
                BeanUtils.copyProperties(task, t);
                t.setPlanId(planId);
                String oldId = t.getTaskId();
                if (ValidateUtil.isEmpty(t.getTaskId())) t.setTaskId(GenerateUUID.getUUID());
                else {
                    if (ValidateUtil.isNotEmpty(t.getTaskSource()) && t.getTaskSource().equals(t.getTaskId()))
                        //源ID和当前ID相同 则重新赋值ID（创建表单时查询GanttTasks taskId未重新赋值则与sourceId一致）
                        t.setTaskId(GenerateUUID.getUUID());
                    if (ValidateUtil.isNotEmpty(t.getTaskChangeType()) && t.getTaskChangeType().equals("新增"))
                        t.setTaskId(GenerateUUID.getUUID());
                }
                if (ValidateUtil.isEmpty(t.getTaskParent())) t.setTaskParent("0");
                map.put(oldId, t.getTaskId());
                return t;
            }).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(data)) {
                List<GanttTasksChange> entities = data.stream().map(task -> {
                    if (!task.getTaskParent().equals("0"))
                        task.setTaskParent(map.get(task.getTaskParent()));
                    return task;
                }).collect(Collectors.toList());
                saveBatch(entities);
            }
        }
        return map;
    }

    @Override
    public List<String> canNot2Delete(List<GanttTasksChangeDTO> ganttTasks) {
        if (!CollectionUtils.isEmpty(ganttTasks)) return null;
        List<String> ids = ganttTasks.stream().filter(n -> ValidateUtil.isNotEmpty(n.getTaskChangeType())
                && ValidateUtil.isNotEmpty(n.getTaskSource()) && n.getTaskChangeType().equals("删除")).map(GanttTasksChangeDTO::getTaskSource).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(ids)) return null;
        return baseMapper.canNot2Delete(ids);
    }

    @Override
    public boolean can2SubmitProcess(String id) {
        Integer num = baseMapper.can2SubmitProcess(id);
        return num == null || num <= 0;
    }

    @Override
    public void deleteByPlanId(String planId) {
        QueryWrapper<GanttTasksChange> wrapper = new QueryWrapper<>();
        wrapper.eq("Plan_Id", planId);
        baseMapper.delete(wrapper);
    }

    @Override
    public List<GanttTasksChangeVO> getGanttTasksChangeByPlanId(String planId) {
        if (ValidateUtil.isEmpty(planId)) return null;
        return baseMapper.getGanttTasksChangeByPlanId(planId);
    }


}
