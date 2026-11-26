package com.jpmsoft.projectProducer.service.investment.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.projectProducer.mapper.investment.BalancePaymentMapper;
import com.jpmsoft.projectProducer.pojo.entity.investment.BalancePayment;
import com.jpmsoft.projectProducer.service.investment.BalancePaymentService;
import org.springframework.stereotype.Service;

@Service
public class BalancePaymentServiceImpl extends ServiceImpl<BalancePaymentMapper, BalancePayment> implements BalancePaymentService {
    @Override
    public BalancePayment getBalancePaymentById(String id) {
        LambdaQueryWrapper<BalancePayment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BalancePayment::getId,id);
        return baseMapper.selectOne(queryWrapper);
    }
}
