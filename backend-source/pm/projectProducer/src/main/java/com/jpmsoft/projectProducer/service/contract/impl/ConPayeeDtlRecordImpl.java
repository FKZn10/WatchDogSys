package com.jpmsoft.projectProducer.service.contract.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.requestAndResponse.Contract.ConPayeeDtlModel;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.projectProducer.mapper.contract.ConPayeeDtlRecordMapper;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConPayeeDtlRecord;
import com.jpmsoft.projectProducer.service.contract.IConPayeeDtlRecordService;
import com.jpmsoft.projectProducer.service.contract.IConPayeeDtlService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConPayeeDtlRecordImpl extends ServiceImpl<ConPayeeDtlRecordMapper, ConPayeeDtlRecord> implements IConPayeeDtlRecordService {

    @Resource
    private IConPayeeDtlService conPayeeDtlService;

    @Override
    public void create(String conId, String recordId) {
        List<ConPayeeDtlModel> data = conPayeeDtlService.getConPayeeDtlByConId(conId);
        if (!CollectionUtils.isEmpty(data)) {
            List<ConPayeeDtlRecord> entities = data.stream().map(d -> {
                ConPayeeDtlRecord entity = new ConPayeeDtlRecord();
                BeanUtils.copyProperties(d, entity);
                entity.setConRecordId(recordId);
                entity.setCpdId(GenerateUUID.getUUID());
                return entity;
            }).collect(Collectors.toList());
            saveBatch(entities);
        }
    }
}
