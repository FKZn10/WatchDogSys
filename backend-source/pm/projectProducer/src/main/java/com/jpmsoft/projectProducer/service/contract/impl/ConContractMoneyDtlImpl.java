package com.jpmsoft.projectProducer.service.contract.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.requestAndResponse.Contract.ConContractMoneyDtlModel;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.contract.ConContractMoneyDtlMapper;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConContractMoneyDtl;
import com.jpmsoft.projectProducer.service.contract.IConContractMoneyDtlService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConContractMoneyDtlImpl extends ServiceImpl<ConContractMoneyDtlMapper, ConContractMoneyDtl> implements IConContractMoneyDtlService {
    @Override
    public void createOrUpdateConContractMoneyDtl(List<ConContractMoneyDtlModel> datas, String conId) {
        deleteAllConContractMoneyDtlByConId(conId);
        if (ValidateUtil.isNotEmpty(datas)) {
            List<ConContractMoneyDtl> entitys = new ArrayList<>();
            Date now = new Date();
            for (ConContractMoneyDtlModel data : datas) {
                ConContractMoneyDtl entity = new ConContractMoneyDtl();
                BeanUtils.copyProperties(data, entity);
                entity.setConId(conId);
                entity.setCmdCreateTime(data.getCmdCreateTime() == null ? now : data.getCmdCreateTime());
                entity.setCmdId(data.getCmdId() == null ? GenerateUUID.getUUID() : data.getCmdId());
                entity.setCmdParentId("0");
                entitys.add(entity);
                if (ValidateUtil.isNotEmpty(data.getSubMoneyDtlModels()))
                    getChildNodes(entitys, data.getSubMoneyDtlModels(), conId, entity.getCmdId(), now);
            }
            if (ValidateUtil.isNotEmpty(entitys))
                saveBatch(entitys);
        }
    }

    @Override
    public void deleteAllConContractMoneyDtlByConId(String conId) {
        QueryWrapper<ConContractMoneyDtl> wrapper = new QueryWrapper<>();
        wrapper.eq("Con_Id", conId);
        baseMapper.delete(wrapper);
    }

    @Override
    public List<ConContractMoneyDtlModel> getConContractMoneyDtlByConIds(List<String> conIds) {
        QueryWrapper<ConContractMoneyDtl> wrapper = new QueryWrapper<>();
        wrapper.eq("Con_Id", conIds.get(0));
        for (int i = 1; i < conIds.size(); i++) {
            wrapper.or().eq("Con_Id", conIds.get(i));
        }
        wrapper.orderByAsc("Cmd_Sort");
        List<ConContractMoneyDtl> entitys = baseMapper.selectList(wrapper);
        List<ConContractMoneyDtlModel> datas = new ArrayList<>();
        entitys.forEach(entity -> {
            ConContractMoneyDtlModel data = new ConContractMoneyDtlModel();
            BeanUtils.copyProperties(entity, data);
            datas.add(data);
        });
        return datas;
    }

    @Override
    public List<ConContractMoneyDtlModel> getConContractMoneyDtlByConId(String conId) {
        QueryWrapper<ConContractMoneyDtl> wrapper = new QueryWrapper<>();
        wrapper.eq("Con_Id", conId);
        wrapper.orderByAsc("Cmd_Sort");
        List<ConContractMoneyDtl> entitys = baseMapper.selectList(wrapper);
        List<ConContractMoneyDtlModel> datas = new ArrayList<>();
        entitys.forEach(entity -> {
            ConContractMoneyDtlModel data = new ConContractMoneyDtlModel();
            BeanUtils.copyProperties(entity, data);
            datas.add(data);
        });
        return assembleContractMoneyDtlTree(datas);
    }

    @Override
    public List<ConContractMoneyDtlModel> assembleContractMoneyDtlTree(List<ConContractMoneyDtlModel> datas) {
        if (ValidateUtil.isEmpty(datas)) return null;
        List<ConContractMoneyDtlModel> parents = datas.stream().filter(n -> n.getCmdParentId().equals("0")).collect(Collectors.toList());
        if (ValidateUtil.isEmpty(parents)) return null;
        List<ConContractMoneyDtlModel> trees = new ArrayList<>();
        for (ConContractMoneyDtlModel parent : parents) {
            List<ConContractMoneyDtlModel> childs = datas.stream().filter(n -> n.getCmdParentId().equals(parent.getCmdId())).collect(Collectors.toList());
            List<ConContractMoneyDtlModel> childNodes = new ArrayList<>();
            if (ValidateUtil.isNotEmpty(childs))
                setTreeChildValue(childNodes, childs, datas);
            parent.setSubMoneyDtlModels(childs);
            trees.add(parent);
        }
        return trees;
    }

    private void setTreeChildValue(List<ConContractMoneyDtlModel> trees, List<ConContractMoneyDtlModel> parents, List<ConContractMoneyDtlModel> datas) {
        for (ConContractMoneyDtlModel parent : parents) {
            List<ConContractMoneyDtlModel> childs = datas.stream().filter(n -> n.getCmdParentId().equals(parent.getCmdId())).collect(Collectors.toList());
            List<ConContractMoneyDtlModel> childNodes = new ArrayList<>();
            if (ValidateUtil.isNotEmpty(childs))
                setTreeChildValue(childNodes, childs, datas);
            parent.setSubMoneyDtlModels(childs);
            trees.add(parent);
        }
    }

    private void getChildNodes(List<ConContractMoneyDtl> entitys, List<ConContractMoneyDtlModel> datas, String conId, String parentId, Date now) {
        if (ValidateUtil.isNotEmpty(datas)) {
            for (ConContractMoneyDtlModel data : datas) {
                ConContractMoneyDtl entity = new ConContractMoneyDtl();
                BeanUtils.copyProperties(data, entity);
                entity.setConId(conId);
                entity.setCmdCreateTime(data.getCmdCreateTime() == null ? now : data.getCmdCreateTime());
                entity.setCmdId(data.getCmdId() == null ? GenerateUUID.getUUID() : data.getCmdId());
                entity.setCmdParentId(parentId);
                entitys.add(entity);
                getChildNodes(entitys, data.getSubMoneyDtlModels(), conId, entity.getCmdId(), now);
            }
        }
    }

}
