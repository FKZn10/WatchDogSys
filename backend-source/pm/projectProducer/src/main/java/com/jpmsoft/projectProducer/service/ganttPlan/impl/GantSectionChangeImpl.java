package com.jpmsoft.projectProducer.service.ganttPlan.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.request.Plan.GantSectionDTO;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.ganttPlan.GantSectionChangeMapper;
import com.jpmsoft.projectProducer.pojo.entity.ganttPlan.GantSectionChange;
import com.jpmsoft.projectProducer.service.ganttPlan.IGantSectionChangeService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: 李文涛 aaron
 * @date: 2025/3/28 20:44
 */
@Service
public class GantSectionChangeImpl extends ServiceImpl<GantSectionChangeMapper, GantSectionChange>
        implements IGantSectionChangeService {

    @Override
    public void createOrUpdateGantSection(List<GantSectionDTO> data, String planId) {
        deleteByPlanId(planId);
        if (!CollectionUtils.isEmpty(data)) {
            List<GantSectionChange> entities = new ArrayList<>();
            for (GantSectionDTO d : data) {
                GantSectionChange entity = new GantSectionChange();
                BeanUtils.copyProperties(d, entity);
                entity.setPlanId(planId);
                entity.setId(GenerateUUID.getUUID(d.getId()));
                entities.add(entity);
            }
            this.saveBatch(entities);
        }
    }

    @Override
    public void deleteByPlanId(String planId) {
        if (ValidateUtil.isEmpty(planId)) return;
        LambdaQueryWrapper<GantSectionChange> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(GantSectionChange::getPlanId, planId);
        baseMapper.delete(wrapper);
    }

    @Override
    public List<GantSectionDTO> selectByPlanId(String planId) {
        LambdaQueryWrapper<GantSectionChange> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(GantSectionChange::getPlanId, planId);
        List<GantSectionChange> data = baseMapper.selectList(wrapper);
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
        LambdaQueryWrapper<GantSectionChange> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(GantSectionChange::getPlanId, planIds);
        List<GantSectionChange> data = baseMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(data))
            return data.stream().map(d -> {
                GantSectionDTO r = new GantSectionDTO();
                BeanUtils.copyProperties(d, r);
                return r;
            }).collect(Collectors.toList());
        return new ArrayList<>();
    }
}
