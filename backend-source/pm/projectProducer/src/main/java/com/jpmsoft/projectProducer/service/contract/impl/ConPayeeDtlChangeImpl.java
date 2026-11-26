package com.jpmsoft.projectProducer.service.contract.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.requestAndResponse.Contract.ConPayeeDtlModel;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.projectProducer.mapper.contract.ConPayeeDtlChangeMapper;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConPayeeDtl;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConPayeeDtlChange;
import com.jpmsoft.projectProducer.service.contract.IConPayeeDtlChangeService;
import com.jpmsoft.projectProducer.service.contract.IConPayeeDtlService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConPayeeDtlChangeImpl extends ServiceImpl<ConPayeeDtlChangeMapper, ConPayeeDtlChange> implements IConPayeeDtlChangeService {

    @Resource
    private IConPayeeDtlService conPayeeDtlService;


    @Override
    public void createOrUpdate(List<ConPayeeDtlModel> data, String changeId) {
        deleteByChangeId(changeId);
        if (!CollectionUtils.isEmpty(data)) {
            List<ConPayeeDtlChange> entities = data.stream().map(d -> {
                ConPayeeDtlChange entity = new ConPayeeDtlChange();
                BeanUtils.copyProperties(d, entity);
                entity.setConChangeId(changeId);
                entity.setCpdId(GenerateUUID.getUUID());
                return entity;
            }).collect(Collectors.toList());
            saveBatch(entities);
        }
    }

    @Override
    public void deleteByChangeId(String changeId) {
        QueryWrapper<ConPayeeDtlChange> wrapper = new QueryWrapper<>();
        wrapper.eq("Con_Change_Id", changeId);
        baseMapper.delete(wrapper);
    }

    @Override
    public void approve2Sync(String changeId, String conId) {
        conPayeeDtlService.deleteAllConPayeeDtlByConId(conId);
        QueryWrapper<ConPayeeDtlChange> wrapper = new QueryWrapper<>();
        wrapper.eq("Con_Change_Id", changeId);
        List<ConPayeeDtlChange> data = baseMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(data)) {
            List<ConPayeeDtl> entities = data.stream().map(d -> {
                ConPayeeDtl entity = new ConPayeeDtl();
                BeanUtils.copyProperties(d, entity);
                entity.setConId(conId);
                entity.setCpdId(GenerateUUID.getUUID());
                return entity;
            }).collect(Collectors.toList());
            conPayeeDtlService.saveBatch(entities);
        }
    }
}
