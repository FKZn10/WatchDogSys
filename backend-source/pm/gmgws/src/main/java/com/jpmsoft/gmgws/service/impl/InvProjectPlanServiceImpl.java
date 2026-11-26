package com.jpmsoft.gmgws.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.gmgws.mapper.InvProjectPlanMapper;
import com.jpmsoft.gmgws.pojo.entity.InvProjectPlan;
import com.jpmsoft.gmgws.service.InvProjectPlanService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvProjectPlanServiceImpl extends ServiceImpl<InvProjectPlanMapper, InvProjectPlan> implements InvProjectPlanService {


    @Override
    public List<InvProjectPlan> getDataIdS(List<String> ids) {
        LambdaQueryWrapper<InvProjectPlan> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(InvProjectPlan::getPId,ids);


        return baseMapper.selectList(wrapper);
    }
}
