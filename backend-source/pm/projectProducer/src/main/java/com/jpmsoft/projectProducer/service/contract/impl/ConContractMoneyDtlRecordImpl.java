package com.jpmsoft.projectProducer.service.contract.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.requestAndResponse.Contract.ConContractMoneyDtlModel;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.projectProducer.mapper.contract.ConContractMoneyDtlRecordMapper;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConContractMoneyDtlRecord;
import com.jpmsoft.projectProducer.service.contract.IConContractMoneyDtlRecordService;
import com.jpmsoft.projectProducer.service.contract.IConContractMoneyDtlService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConContractMoneyDtlRecordImpl extends ServiceImpl<ConContractMoneyDtlRecordMapper, ConContractMoneyDtlRecord> implements IConContractMoneyDtlRecordService {

    @Resource
    private IConContractMoneyDtlService conContractMoneyDtlService;

    @Override
    public void create(String conId, String recordId) {
        List<ConContractMoneyDtlModel> data = conContractMoneyDtlService.getConContractMoneyDtlByConId(conId);
        if (!CollectionUtils.isEmpty(data)) {
            List<ConContractMoneyDtlRecord> entities = data.stream().map(d -> {
                ConContractMoneyDtlRecord entity = new ConContractMoneyDtlRecord();
                BeanUtils.copyProperties(d, entity);
                entity.setConRecordId(recordId);
                entity.setCmdId(GenerateUUID.getUUID());
                return entity;
            }).collect(Collectors.toList());
            saveBatch(entities);
        }
    }
}
