package com.jpmsoft.projectProducer.service.secInfo.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecJGExpertShieldDto;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.projectProducer.mapper.secInfo.SecJGExpertShieldMapper;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecJGExpertShield;
import com.jpmsoft.projectProducer.service.secInfo.ISecJGExpertShieldService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SecJGExpertShieldImpl  extends ServiceImpl<SecJGExpertShieldMapper, SecJGExpertShield> implements ISecJGExpertShieldService {

    @Override
    public void createOrUpdateSecExpertShield(List<SecJGExpertShieldDto> datas, String srId) {
        delSecExpertShieldBySrId(srId);
        if (!CollectionUtils.isEmpty(datas)) {
            List<SecJGExpertShield> entitys = datas.stream().map(data -> {
                SecJGExpertShield entity = new SecJGExpertShield();
                BeanUtils.copyProperties(data, entity);
                entity.setSrId(srId);
                entity.setId(GenerateUUID.getUUID(data.getId()));
                return entity;
            }).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(entitys)) saveBatch(entitys);
        }
    }

    @Override
    public void delSecExpertShieldBySrId(String srId) {
        QueryWrapper<SecJGExpertShield> wrapper = new QueryWrapper<>();
        wrapper.eq("Sr_Id", srId);
        baseMapper.delete(wrapper);
    }



}
