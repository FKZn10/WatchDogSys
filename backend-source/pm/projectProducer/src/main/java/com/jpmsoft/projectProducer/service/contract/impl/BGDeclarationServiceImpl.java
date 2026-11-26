package com.jpmsoft.projectProducer.service.contract.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.request.project.ProBGRequest;
import com.jpmsoft.projectProducer.mapper.project.BGDeclarationMapper;
import com.jpmsoft.projectProducer.pojo.dto.project.ProBGDTO;
import com.jpmsoft.projectProducer.pojo.entity.contract.BGDeclaration;
import com.jpmsoft.projectProducer.service.contract.IBGDeclarationService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class BGDeclarationServiceImpl extends ServiceImpl<BGDeclarationMapper, BGDeclaration> implements IBGDeclarationService {

    @Override
    public BigDecimal getLJBGMoney(ProBGRequest request) {
        BigDecimal CUOMoney = baseMapper.getBGDeclarationMoney(request.getConId());
        return CUOMoney;
    }

    @Override
    public List<ProBGDTO> getBGDeclarationListByConId(ProBGRequest request) {
        List<ProBGDTO> PayRankVO = baseMapper.BGDeclarationListByConId(new Page(request.getPage(),request.getSize()),request);
        return PayRankVO;
    }


    @Override
    public Page<ProBGDTO> getBGDeclarationListByType(ProBGRequest request) {
        Page<ProBGDTO> PayRankVO = baseMapper.BGDeclarationListByType(new Page(request.getPage(),request.getSize()),request);
        return PayRankVO;
    }


    @Override
    public String SetDeclarationBL(ProBGRequest request) {
        String PayId = request.getPayId();
        if (PayId == null || PayId == "")
            return "OK";

        BGDeclaration model = baseMapper.selectById(request.getPayId());
        Integer State = model.getState();
        if (State == 7)
        {
            model.setDBL(new BigDecimal(100.00));
            baseMapper.updateById(model);
        }
        return "OK";
    }
}