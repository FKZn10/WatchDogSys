package com.jpmsoft.projectProducer.service.ganttPlan.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.request.Plan.GanttLinksTemplateDTO;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.projectProducer.mapper.ganttPlan.GanttLinksTemplateMapper;
import com.jpmsoft.projectProducer.pojo.entity.ganttPlan.GanttLinksTemplate;
import com.jpmsoft.projectProducer.service.ganttPlan.IGanttLinksTemplateService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/4/17 10:38
 */
@Service
public class GanttLinksTemplateImpl extends ServiceImpl<GanttLinksTemplateMapper, GanttLinksTemplate> implements IGanttLinksTemplateService {


    @Override
    public void createOrUpdateGanttLinks(List<GanttLinksTemplateDTO> links, String ptId) {
        delAllByPtId(ptId);
        if (!CollectionUtils.isEmpty(links)) {
            List<GanttLinksTemplate> tasks = links.stream().map(data -> {
                GanttLinksTemplate task = new GanttLinksTemplate();
                BeanUtils.copyProperties(data, task);
                task.setPtId(ptId);
                return task;
            }).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(tasks)) saveBatch(tasks);
        }
    }

    @Override
    public List<GanttLinksTemplateDTO> getLinksByPtId(String ptId) {
        QueryWrapper<GanttLinksTemplate> wrapper = new QueryWrapper<>();
        wrapper.eq("Pt_Id", ptId);
        List<GanttLinksTemplate> data = baseMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(data)) {
            List<GanttLinksTemplateDTO> links = data.stream().map(link -> {
                GanttLinksTemplateDTO dto = new GanttLinksTemplateDTO();
                BeanUtils.copyProperties(link, dto);
                return dto;
            }).collect(Collectors.toList());
            return links;
        }
        return null;
    }

    private void delAllByPtId(String ptId) {
        QueryWrapper<GanttLinksTemplate> wrapper = new QueryWrapper<>();
        wrapper.eq("Pt_Id", ptId);
        baseMapper.delete(wrapper);
    }
}
