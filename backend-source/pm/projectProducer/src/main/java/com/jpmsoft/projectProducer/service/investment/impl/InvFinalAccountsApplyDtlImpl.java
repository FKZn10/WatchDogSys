package com.jpmsoft.projectProducer.service.investment.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.request.InvestmentControl.InvFinalAccountsApplyDtlDto;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.projectProducer.mapper.investment.InvFinalAccountsApplyDtlMapper;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvFinalAccountsApplyDtl;
import com.jpmsoft.projectProducer.service.investment.IInvFinalAccountsApplyDtlService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/2/23 15:59
 */
@Service
public class InvFinalAccountsApplyDtlImpl extends ServiceImpl<InvFinalAccountsApplyDtlMapper, InvFinalAccountsApplyDtl> implements IInvFinalAccountsApplyDtlService {
    @Override
    public void createOrUpdate(List<InvFinalAccountsApplyDtlDto> datas, String faId) {
        delteByFaId(faId);
        if (!CollectionUtils.isEmpty(datas)) {
            List<InvFinalAccountsApplyDtl> entitys = new ArrayList<>();
            int i = 0;
            for (InvFinalAccountsApplyDtlDto data : datas) {
                InvFinalAccountsApplyDtl entity = new InvFinalAccountsApplyDtl();
                String id = GenerateUUID.getUUID();
                org.springframework.beans.BeanUtils.copyProperties(data, entity);
                entity.setFadId(id);
                entity.setFaId(faId);
                entity.setFadOrder(i += 1);
                entity.setFadParentId("0");
                entitys.add(entity);
                if (data.getChildNodes() != null) {
                    addChildNodes(entitys, data.getChildNodes(), faId, id);
                }
            }
            if (!CollectionUtils.isEmpty(entitys)) saveBatch(entitys);
        }
    }

    @Override
    public void delteByFaId(String faId) {
        QueryWrapper<InvFinalAccountsApplyDtl> wrapper = new QueryWrapper<>();
        wrapper.eq("Fa_Id", faId);
        baseMapper.delete(wrapper);
    }

    @Override
    public List<InvFinalAccountsApplyDtlDto> getListByFaId(String faId) {
        QueryWrapper<InvFinalAccountsApplyDtl> wrapper = new QueryWrapper<>();
        wrapper.eq("Fa_Id", faId);
        List<InvFinalAccountsApplyDtl> models = baseMapper.selectList(wrapper);
        return getInvFinalAccountsApplyDtlTree(models);
    }

    private List<InvFinalAccountsApplyDtlDto> getInvFinalAccountsApplyDtlTree(List<InvFinalAccountsApplyDtl> pojos) {
        if (!CollectionUtils.isEmpty(pojos)) {
            List<InvFinalAccountsApplyDtlDto> datas = new ArrayList<>();
            List<InvFinalAccountsApplyDtl> mainDtl = pojos.stream().filter(n -> n.getFadParentId().equals("0")).collect(Collectors.toList());
            getNodesDtls(datas, pojos, mainDtl);
            return datas;
        }
        return new ArrayList<>();
    }

    private void getNodesDtls(List<InvFinalAccountsApplyDtlDto> datas, List<InvFinalAccountsApplyDtl> pojos, List<InvFinalAccountsApplyDtl> mainDtl) {
        for (InvFinalAccountsApplyDtl dtl : mainDtl) {
            InvFinalAccountsApplyDtlDto data = new InvFinalAccountsApplyDtlDto();
            org.springframework.beans.BeanUtils.copyProperties(dtl, data);
            List<InvFinalAccountsApplyDtl> childs = pojos.stream().filter(n -> n.getFadParentId().equals(data.getFadId())).collect(Collectors.toList());
            if (childs != null && childs.size() > 0) {
                List<InvFinalAccountsApplyDtlDto> childNode = new ArrayList<>();
                getNodesDtls(childNode, pojos, childs);
                data.setChildNodes(childNode);
            }
            datas.add(data);
        }
    }


    private void addChildNodes(List<InvFinalAccountsApplyDtl> entitys, List<InvFinalAccountsApplyDtlDto> datas, String faId, String parentId) {
        if (datas != null && datas.size() > 0) {
            int i = 0;
            for (InvFinalAccountsApplyDtlDto data : datas) {
                InvFinalAccountsApplyDtl entity = new InvFinalAccountsApplyDtl();
                String id = GenerateUUID.getUUID();
                BeanUtils.copyProperties(data, entity);
                entity.setFadId(id);
                entity.setFaId(faId);
                entity.setFadParentId(parentId);
                entity.setFadOrder(i += 1);
                entitys.add(entity);
                if (data.getChildNodes() != null) {
                    addChildNodes(entitys, data.getChildNodes(), faId, id);
                }
            }
        }
    }
}
