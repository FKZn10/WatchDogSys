package com.jpmsoft.projectProducer.service.ganttPlan.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.request.Plan.GanttLinksChangeDTO;
import com.jpmsoft.common.model.response.Plan.GanttLinksChangeVO;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.projectProducer.mapper.ganttPlan.GanttLinksChangeMapper;
import com.jpmsoft.projectProducer.pojo.entity.ganttPlan.GanttLinksChange;
import com.jpmsoft.projectProducer.service.ganttPlan.IGanttLinksChangeService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/4/23 10:05
 */
@Service
public class GanttLinksChangeImpl extends ServiceImpl<GanttLinksChangeMapper, GanttLinksChange> implements IGanttLinksChangeService {
    @Override
    public void createOrUpdateLinksChange(Map<String, String> keys, List<GanttLinksChangeDTO> ganttLinks, String planId) {
        deleteAllByPlanId(planId);
        if (!CollectionUtils.isEmpty(ganttLinks) && keys != null && keys.size() > 0) {
            List<GanttLinksChange> entities = new ArrayList<>();
            for (GanttLinksChangeDTO link : ganttLinks) {
                GanttLinksChange entity = new GanttLinksChange();
                entity.setLinkId(GenerateUUID.getUUID());
                entity.setPlanId(planId);
                entity.setLinkType(link.getLinkType());
                if (keys.containsKey(link.getLinkSource()) && keys.containsKey(link.getLinkTarget())) {
                    entity.setLinkSource(keys.get(link.getLinkSource()));
                    entity.setLinkTarget(keys.get(link.getLinkTarget()));
                    entities.add(entity);
                }
            }
            if (!CollectionUtils.isEmpty(entities)) saveBatch(entities);
        }
    }

    @Override
    public void deleteAllByPlanId(String planId) {
        QueryWrapper<GanttLinksChange> wrapper = new QueryWrapper<>();
        wrapper.eq("Plan_Id", planId);
        baseMapper.delete(wrapper);
    }

    @Override
    public List<GanttLinksChangeVO> getGanttLinksChangeByPlanId(String planId) {
        QueryWrapper<GanttLinksChange> wrapper = new QueryWrapper<>();
        wrapper.eq("Plan_Id", planId);
        List<GanttLinksChange> data = baseMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(data)) {
            return data.stream().map(entity -> {
                GanttLinksChangeVO vo = new GanttLinksChangeVO();
                BeanUtils.copyProperties(entity, vo);
                return vo;
            }).collect(Collectors.toList());
        }
        return null;
    }
}
