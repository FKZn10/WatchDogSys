package com.jpmsoft.projectProducer.service.ganttPlan.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.request.Plan.GanttLinksRequest;
import com.jpmsoft.common.model.response.Plan.GanttLinksResponse;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.ganttPlan.GanttLinksMapper;
import com.jpmsoft.projectProducer.pojo.entity.ganttPlan.GanttLinks;
import com.jpmsoft.projectProducer.service.ganttPlan.IGanttLinksService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GanttLinksImpl extends ServiceImpl<GanttLinksMapper, GanttLinks> implements IGanttLinksService {


    @Override
    public void createOrUpdateGanttLinks(List<GanttLinksRequest> ganttLinks, String planId, List<String> relDel) {
        if (relDel == null) {
            deleteGanttLinksByPlanId(planId);
            if (ganttLinks != null && ganttLinks.size() > 0) {
                List<GanttLinks> entitys = new ArrayList<>();
                ganttLinks.forEach(model -> {
                    GanttLinks entity = new GanttLinks();
                    BeanUtils.copyProperties(model, entity);
                    entity.setPlanId(planId);
                    entitys.add(entity);
                });
                saveBatch(entitys);
            }
        } else {
            QueryWrapper<GanttLinks> wrapper = new QueryWrapper<>();
            wrapper.eq("Plan_Id", planId);
            List<GanttLinks> oldDatas = baseMapper.selectList(wrapper);
            //  oldDatas有 而 ganttLinks 内没有的 想要删除的
            List<GanttLinks> delList = oldDatas.stream()
                    .filter(item -> !ganttLinks.stream()
                            .map(GanttLinksRequest::getLinkId)
                            .collect(Collectors.toList())
                            .contains(item.getLinkId()))
                    .collect(Collectors.toList());

            // ganttLinks 有 而 oldDatas 内没有的 想要新增的
            List<GanttLinksRequest> addList = ganttLinks.stream()
                    .filter(item -> !oldDatas.stream()
                            .map(GanttLinks::getLinkId)
                            .collect(Collectors.toList())
                            .contains(item.getLinkId()))
                    .collect(Collectors.toList());
            if (delList != null && relDel != null && relDel.size() > 0 && delList.size() > 0) {
                List<String> canDel = new ArrayList<>();
                for (GanttLinks link : delList) {
                    //源或者目标是已删除的任务
                    if (relDel.contains(link.getLinkTarget()) || relDel.contains(link.getLinkSource()))
                        canDel.add(link.getLinkId());
                }
                if (ValidateUtil.isNotEmpty(canDel))
                    baseMapper.deleteBatchIds(canDel);
            }
            if (addList != null && addList.size() > 0) {
                List<GanttLinks> entitys = new ArrayList<>();
                addList.forEach(model -> {
                    GanttLinks entity = new GanttLinks();
                    BeanUtils.copyProperties(model, entity);
                    entity.setPlanId(planId);
                    entitys.add(entity);
                });
                if (entitys.size() > 0)
                    saveBatch(entitys);
            }
        }
    }

    @Override
    public List<GanttLinksResponse> getLinksByPlanId(String planId) {
        if (ValidateUtil.isEmpty(planId)) return new ArrayList<>();
        return baseMapper.getLinksByPlanId(planId);
    }

    @Override
    public void deleteGanttLinksByPlanId(String planId) {
        QueryWrapper<GanttLinks> wrapper = new QueryWrapper<>();
        wrapper.eq("Plan_Id", planId);
        baseMapper.delete(wrapper);
    }
}
