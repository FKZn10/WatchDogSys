package com.jpmsoft.projectProducer.service.ganttPlan.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.model.request.Plan.GanttTasksTemplateDTO;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.ganttPlan.GanttTasksTemplateMapper;
import com.jpmsoft.projectProducer.pojo.entity.ganttPlan.GanttTasksTemplate;
import com.jpmsoft.projectProducer.service.ganttPlan.IGanttTasksTemplateService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/4/17 10:39
 */
@Service
public class GanttTasksTemplateImpl extends ServiceImpl<GanttTasksTemplateMapper, GanttTasksTemplate> implements IGanttTasksTemplateService {

    @Override
    public List<String> selectCurrentCplId(String ptId) {
        LambdaQueryWrapper<GanttTasksTemplate> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(GanttTasksTemplate::getPtId, ptId);
        wrapper.isNotNull(GanttTasksTemplate::getCplId);
        List<GanttTasksTemplate> data = baseMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(data)) {
            List<GanttTasksTemplate> trims = data.stream().filter(f -> ValidateUtil.isNotEmpty(f.getCplId())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(trims))
                return trims.stream().map(GanttTasksTemplate::getCplId).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    @Override
    public void createOrUpdateGanttTasks(List<GanttTasksTemplateDTO> ganttTasks, String ptId) {
        delAllByPtId(ptId);
        if (!CollectionUtils.isEmpty(ganttTasks)) {
            List<String> cplIds = new ArrayList<>();
            List<GanttTasksTemplate> tasks = ganttTasks.stream().map(data -> {
                GanttTasksTemplate task = new GanttTasksTemplate();
                BeanUtils.copyProperties(data, task);
                task.setPtId(ptId);
                if (ValidateUtil.isNotEmpty(data.getCplId())) {
                    if (cplIds.contains(data.getCplId()))
                        throw new JPMException(String.format(ResMsg.taskNodeProjectCpRe, data.getTaskText()));
                    else
                        cplIds.add(data.getCplId());
                }
                return task;
            }).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(tasks)) saveBatch(tasks);
        }
    }

    @Override
    public List<GanttTasksTemplateDTO> getGanttTasksTemplateTree(String ptId) {
        QueryWrapper<GanttTasksTemplate> wrapper = new QueryWrapper<>();
        wrapper.eq("Pt_Id", ptId);
        List<GanttTasksTemplate> ganttTasks = baseMapper.selectList(wrapper);
        List<GanttTasksTemplateDTO> data = new ArrayList<>();
        if (!CollectionUtils.isEmpty(ganttTasks)) {
            List<GanttTasksTemplateDTO> datas = ganttTasks.stream().map(g -> {
                GanttTasksTemplateDTO t = new GanttTasksTemplateDTO();
                BeanUtils.copyProperties(g, t);
                return t;
            }).collect(Collectors.toList());

            List<GanttTasksTemplateDTO> roots = datas.stream().filter(n -> n.getTaskParent().equals("0")).sorted(Comparator.comparing(GanttTasksTemplateDTO::getTaskSort)).collect(Collectors.toList());

            if (!CollectionUtils.isEmpty(roots)) {
                roots.forEach(root -> {
                    List<GanttTasksTemplateDTO> child = datas.stream().filter(n -> n.getTaskParent().equals(root.getTaskId())).sorted(Comparator.comparing(GanttTasksTemplateDTO::getTaskSort)).collect(Collectors.toList());
                    setChildNodes(child, datas);
                    root.setChildNodes(child);
                    data.add(root);
                });
            }
        }
        return data;
    }

    @Override
    public List<GanttTasksTemplateDTO> getGanttTasksTemplateLast(String ptId) {
        List<GanttTasksTemplateDTO> data = getGanttTasksTemplateTree(ptId);
        List<GanttTasksTemplateDTO> res = new ArrayList<>();
        if (!CollectionUtils.isEmpty(data))
            getLastNode(data, res);
        return res;
    }

    private void getLastNode(List<GanttTasksTemplateDTO> data, List<GanttTasksTemplateDTO> res) {
        for (GanttTasksTemplateDTO d : data) {
            if (CollectionUtils.isEmpty(d.getChildNodes()))
                res.add(d);
            else getLastNode(d.getChildNodes(), res);
        }
    }


    private void setChildNodes(List<GanttTasksTemplateDTO> roots, List<GanttTasksTemplateDTO> data) {
        if (!CollectionUtils.isEmpty(roots)) {
            roots.forEach(root -> {
                List<GanttTasksTemplateDTO> child = data.stream().filter(n -> n.getTaskParent().equals(root.getTaskId())).sorted(Comparator.comparing(GanttTasksTemplateDTO::getTaskSort)).collect(Collectors.toList());
                setChildNodes(child, data);
                root.setChildNodes(child);
            });
        }
    }


    private void delAllByPtId(String ptId) {
        QueryWrapper<GanttTasksTemplate> wrapper = new QueryWrapper<>();
        wrapper.eq("Pt_Id", ptId);
        baseMapper.delete(wrapper);
    }
}
