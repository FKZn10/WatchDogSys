package com.jpmsoft.projectProducer.service.ganttPlan.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.request.Plan.GantSectionDTO;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.ganttPlan.GantSectionMapper;
import com.jpmsoft.projectProducer.pojo.entity.ganttPlan.GantSection;
import com.jpmsoft.projectProducer.service.ganttPlan.IGantSectionService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: 李文涛 aaron
 * @date: 2025/3/28 20:44
 */
@Service
public class GantSectionImpl extends ServiceImpl<GantSectionMapper, GantSection> implements IGantSectionService {


    @Override
    public void createOrUpdateGantSection(List<GantSectionDTO> data, String planId) {
        createOrUpdateGantSection(data, planId, null);
    }

    @Override
    public void createOrUpdateGantSection(List<GantSectionDTO> data, String planId, Map<String, String> gsMapping) {
        deleteByPlanId(planId);
        if (!CollectionUtils.isEmpty(data)) {
            List<GantSection> entities = new ArrayList<>();
            for (GantSectionDTO d : data) {
                GantSection entity = new GantSection();
                BeanUtils.copyProperties(d, entity);
                entity.setPlanId(planId);
                if (ValidateUtil.isEmpty(d.getGsSource())) {
                    if (ValidateUtil.isNotNull(gsMapping))
                        entity.setId(GenerateUUID.getUUID(gsMapping.get(d.getId())));
                    else entity.setId(GenerateUUID.getUUID(d.getId()));
                } else
                    entity.setId(d.getGsSource());
                entities.add(entity);
            }
            this.saveBatch(entities);
        }
    }

    @Override
    public void deleteByPlanId(String planId) {
        if (ValidateUtil.isEmpty(planId)) return;
        LambdaQueryWrapper<GantSection> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(GantSection::getPlanId, planId);
        baseMapper.delete(wrapper);
    }

    @Override
    public List<GantSectionDTO> selectByPlanId(String planId) {
        LambdaQueryWrapper<GantSection> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(GantSection::getPlanId, planId);
        List<GantSection> data = baseMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(data))
            return data.stream().map(d -> {
                GantSectionDTO r = new GantSectionDTO();
                BeanUtils.copyProperties(d, r);
                return r;
            }).collect(Collectors.toList());
        return new ArrayList<>();
    }

    @Override
    public List<GantSectionDTO> selectByPlanIds(List<String> planIds) {
        if (CollectionUtils.isEmpty(planIds)) {
            return new ArrayList<>();
        }
        LambdaQueryWrapper<GantSection> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(GantSection::getPlanId, planIds);
        List<GantSection> data = baseMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(data))
            return data.stream().map(d -> {
                GantSectionDTO r = new GantSectionDTO();
                BeanUtils.copyProperties(d, r);
                return r;
            }).collect(Collectors.toList());
        return new ArrayList<>();
    }
}
