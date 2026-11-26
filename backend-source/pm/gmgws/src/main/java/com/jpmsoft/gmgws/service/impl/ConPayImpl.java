package com.jpmsoft.gmgws.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.gmgws.mapper.ConPayMapper;
import com.jpmsoft.gmgws.mapper.PaymentStatisticsMapper;
import com.jpmsoft.gmgws.pojo.entity.ConPay;
import com.jpmsoft.gmgws.pojo.entity.PaymentStatistics;
import com.jpmsoft.gmgws.service.ConPayService;
import com.jpmsoft.gmgws.service.PaymentStatisticsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConPayImpl extends ServiceImpl<ConPayMapper, ConPay> implements ConPayService {
    @Override
    public List<ConPay> getIdDataList(List<String> list) {
        LambdaQueryWrapper<ConPay> queryWrapper = new LambdaQueryWrapper<>();
       queryWrapper.in(ConPay::getConId, list);
        List<ConPay> conPays = baseMapper.selectList(queryWrapper);
        return conPays;
    }

    @Override
    public List<ConPay> getProjectDataList(List<String> list) {
        LambdaQueryWrapper<ConPay> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(ConPay::getPId, list);
        List<ConPay> conPays = baseMapper.selectList(queryWrapper);
        return conPays;
    }
}
