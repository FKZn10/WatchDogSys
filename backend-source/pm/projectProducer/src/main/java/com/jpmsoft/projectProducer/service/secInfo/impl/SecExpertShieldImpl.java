package com.jpmsoft.projectProducer.service.secInfo.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecExpertShieldDto;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.projectProducer.mapper.secInfo.SecExpertShieldMapper;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecExpertShield;
import com.jpmsoft.projectProducer.service.secInfo.ISecExpertShieldService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: 李文涛 aaron
 * @date: 2022/10/10 14:02
 */
@Service
public class SecExpertShieldImpl extends ServiceImpl<SecExpertShieldMapper, SecExpertShield> implements ISecExpertShieldService {
    @Override
    public void createOrUpdateSecExpertShield(List<SecExpertShieldDto> datas, String srId) {
        delSecExpertShieldBySrId(srId);
        if (!CollectionUtils.isEmpty(datas)) {
            List<SecExpertShield> entitys = datas.stream().map(data -> {
                SecExpertShield entity = new SecExpertShield();
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
        QueryWrapper<SecExpertShield> wrapper = new QueryWrapper<>();
        wrapper.eq("Sr_Id", srId);
        baseMapper.delete(wrapper);
    }
}
