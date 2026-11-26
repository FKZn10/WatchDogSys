package com.jpmsoft.projectProducer.service.contract.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.request.project.ProBGRequest;
import com.jpmsoft.projectProducer.mapper.project.BGVisaOrderMapper;
import com.jpmsoft.projectProducer.pojo.entity.contract.BGVisaOrder;
import com.jpmsoft.projectProducer.service.contract.IBGVisaOrderService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class BGVisaOrderServiceImpl extends ServiceImpl<BGVisaOrderMapper, BGVisaOrder> implements IBGVisaOrderService {

    @Override
    public BigDecimal getLJBGMoney(ProBGRequest request) {
        BigDecimal CUOMoney = baseMapper.getBGVisaOrderMoney(request.getConId());
        return CUOMoney;
    }

}