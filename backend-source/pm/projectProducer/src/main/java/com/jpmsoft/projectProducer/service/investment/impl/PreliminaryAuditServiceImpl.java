package com.jpmsoft.projectProducer.service.investment.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.projectProducer.mapper.investment.PreliminaryAuditMapper;
import com.jpmsoft.projectProducer.pojo.entity.investment.PreliminaryAudit;
import com.jpmsoft.projectProducer.service.investment.PreliminaryAuditService;
import org.springframework.stereotype.Service;

@Service
public class PreliminaryAuditServiceImpl  extends ServiceImpl<PreliminaryAuditMapper, PreliminaryAudit>
        implements PreliminaryAuditService {

    @Override
    public PreliminaryAudit getpreliminaryById(String id) {
        LambdaQueryWrapper<PreliminaryAudit> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PreliminaryAudit::getId,id);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public PreliminaryAudit getpreliminaryConId(String conId) {
        LambdaQueryWrapper<PreliminaryAudit> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PreliminaryAudit::getConId,conId);
        queryWrapper.orderByDesc(PreliminaryAudit::getCreateTime);
        queryWrapper.last(" limit 1 ");
        return baseMapper.selectOne(queryWrapper);
    }
}
