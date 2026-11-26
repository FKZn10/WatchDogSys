package com.jpmsoft.projectProducer.service.contract.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.requestAndResponse.Contract.ConContractMoneyDtlModel;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.projectProducer.mapper.contract.ConContractMoneyDtlChangeMapper;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConContractMoneyDtl;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConContractMoneyDtlChange;
import com.jpmsoft.projectProducer.service.contract.IConContractMoneyDtlChangeService;
import com.jpmsoft.projectProducer.service.contract.IConContractMoneyDtlService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConContractMoneyDtlChangeImpl extends ServiceImpl<ConContractMoneyDtlChangeMapper, ConContractMoneyDtlChange> implements IConContractMoneyDtlChangeService {

    @Resource
    private IConContractMoneyDtlService conContractMoneyDtlService;


    @Override
    public void createOrUpdate(List<ConContractMoneyDtlModel> data, String changeId) {
        deleteByChangeId(changeId);
        if (!CollectionUtils.isEmpty(data)) {
            List<ConContractMoneyDtlChange> entities = data.stream().map(d -> {
                ConContractMoneyDtlChange entity = new ConContractMoneyDtlChange();
                BeanUtils.copyProperties(d, entity);
                entity.setConChangeId(changeId);
                entity.setCmdId(GenerateUUID.getUUID());
                return entity;
            }).collect(Collectors.toList());
            saveBatch(entities);
        }
    }

    @Override
    public void deleteByChangeId(String changeId) {
        QueryWrapper<ConContractMoneyDtlChange> wrapper = new QueryWrapper<>();
        wrapper.eq("Con_Change_Id", changeId);
        baseMapper.delete(wrapper);
    }

    @Override
    public void approve2Sync(String changeId, String conId) {
        conContractMoneyDtlService.deleteAllConContractMoneyDtlByConId(conId);
        QueryWrapper<ConContractMoneyDtlChange> wrapper=new QueryWrapper<>();
        wrapper.eq("Con_Change_Id",changeId);
        List<ConContractMoneyDtlChange> data=baseMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(data))
        {
            List<ConContractMoneyDtl> entities=data.stream().map(d->{
                ConContractMoneyDtl entity=new ConContractMoneyDtl();
                BeanUtils.copyProperties(d,entity);
                entity.setConId(conId);
                entity.setCmdId(GenerateUUID.getUUID());
                return entity;
            }).collect(Collectors.toList());
            conContractMoneyDtlService.saveBatch(entities);
        }
    }
}
