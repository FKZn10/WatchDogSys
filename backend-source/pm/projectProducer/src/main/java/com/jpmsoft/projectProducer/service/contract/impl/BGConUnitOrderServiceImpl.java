package com.jpmsoft.projectProducer.service.contract.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.request.project.ProBGRequest;
import com.jpmsoft.projectProducer.mapper.project.BGConUnitOrderMapper;
import com.jpmsoft.projectProducer.pojo.dto.project.ProBGDTO;
import com.jpmsoft.projectProducer.pojo.entity.contract.BGConUnitOrder;
import com.jpmsoft.projectProducer.service.contract.IBGConUnitOrderService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class BGConUnitOrderServiceImpl extends ServiceImpl<BGConUnitOrderMapper,BGConUnitOrder> implements IBGConUnitOrderService {

    @Override
    public BigDecimal getLJBGMoney(ProBGRequest request) {
        BigDecimal CUOMoney = baseMapper.getBGConUnitOrderMoney(request.getConId());
        return CUOMoney;
    }

    @Override
    public List<ProBGDTO> getBGConUnitOrderListByConId(ProBGRequest request) {
        List<ProBGDTO> PayRankVO = baseMapper.BGConUnitOrderListByConId(new Page(request.getPage(),request.getSize()),request);
        return PayRankVO;
    }

    @Override
    public Page<ProBGDTO> getBGConUnitOrderListByType(ProBGRequest request) {
        Page<ProBGDTO> PayRankVO = baseMapper.BGConUnitOrderListByType(new Page(request.getPage(),request.getSize()),request);
        return PayRankVO;
    }

    @Override
    public String SetConUnitOrderBL(ProBGRequest request) {
        String PayId = request.getPayId();
        if (PayId == null || PayId == "")
            return "OK";

        BGConUnitOrder model = baseMapper.selectById(request.getPayId());
        Integer State = model.getState();
        if (State == 7)
        {
            model.setCuoBL(new BigDecimal(100.00));
            baseMapper.updateById(model);
        }
        return "OK";
    }




}