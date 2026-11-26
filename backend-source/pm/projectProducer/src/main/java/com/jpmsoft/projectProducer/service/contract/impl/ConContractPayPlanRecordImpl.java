package com.jpmsoft.projectProducer.service.contract.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.requestAndResponse.Contract.ConContractPayPlanModel;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.projectProducer.mapper.contract.ConContractPayPlanRecordMapper;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConContractPayPlanRecord;
import com.jpmsoft.projectProducer.service.contract.IConContractPayPlanRecordService;
import com.jpmsoft.projectProducer.service.contract.IConContractPayPlanService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConContractPayPlanRecordImpl extends ServiceImpl<ConContractPayPlanRecordMapper, ConContractPayPlanRecord> implements IConContractPayPlanRecordService {

    @Resource
    private IConContractPayPlanService conContractPayPlanService;

    @Override
    public void create(String conId, String recordId) {
        List<ConContractPayPlanModel> data = conContractPayPlanService.getConContractPayPlanByConId(conId);
        if (!CollectionUtils.isEmpty(data)) {
            List<ConContractPayPlanRecord> entities = data.stream().map(d -> {
                ConContractPayPlanRecord entity = new ConContractPayPlanRecord();
                BeanUtils.copyProperties(d, entity);
                entity.setConRecordId(recordId);
                entity.setCppId(GenerateUUID.getUUID());
                return entity;
            }).collect(Collectors.toList());
            saveBatch(entities);
        }
    }
}
