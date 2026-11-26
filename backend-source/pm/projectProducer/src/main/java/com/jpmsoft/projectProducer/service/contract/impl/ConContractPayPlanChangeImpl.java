package com.jpmsoft.projectProducer.service.contract.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.requestAndResponse.Contract.ConContractPayPlanModel;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.projectProducer.mapper.contract.ConContractPayPlanChangeMapper;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConContractPayPlan;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConContractPayPlanChange;
import com.jpmsoft.projectProducer.service.contract.IConContractPayPlanChangeService;
import com.jpmsoft.projectProducer.service.contract.IConContractPayPlanService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConContractPayPlanChangeImpl extends ServiceImpl<ConContractPayPlanChangeMapper, ConContractPayPlanChange> implements IConContractPayPlanChangeService {

    @Resource
    private IConContractPayPlanService conContractPayPlanService;

    @Override
    public void createOrUpdate(List<ConContractPayPlanModel> data, String changeId) {
        deleteByChangeId(changeId);
        if (!CollectionUtils.isEmpty(data)) {
            List<ConContractPayPlanChange> entities = data.stream().map(d -> {
                ConContractPayPlanChange entity = new ConContractPayPlanChange();
                BeanUtils.copyProperties(d, entity);
                entity.setConChangeId(changeId);
                entity.setCppId(GenerateUUID.getUUID());
                return entity;
            }).collect(Collectors.toList());
            saveBatch(entities);
        }
    }

    @Override
    public void deleteByChangeId(String changeId) {
        QueryWrapper<ConContractPayPlanChange> wrapper = new QueryWrapper<>();
        wrapper.eq("Con_Change_Id", changeId);
        baseMapper.delete(wrapper);
    }

    @Override
    public void approve2Sync(String changeId, String conId) {
        conContractPayPlanService.deleteAllConContractPayPlanByConId(conId);
        QueryWrapper<ConContractPayPlanChange> wrapper = new QueryWrapper<>();
        wrapper.eq("Con_Change_Id", changeId);
        List<ConContractPayPlanChange> data = baseMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(data)) {
            List<ConContractPayPlan> entities = data.stream().map(d -> {
                ConContractPayPlan entity = new ConContractPayPlan();
                BeanUtils.copyProperties(d, entity);
                entity.setConId(conId);
                entity.setCppId(GenerateUUID.getUUID());
                return entity;
            }).collect(Collectors.toList());
            conContractPayPlanService.saveBatch(entities);
        }
    }
}
