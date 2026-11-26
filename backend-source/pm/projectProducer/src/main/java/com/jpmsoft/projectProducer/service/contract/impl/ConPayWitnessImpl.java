package com.jpmsoft.projectProducer.service.contract.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Contract.ConPayWitnessDTO;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.projectProducer.mapper.contract.ConPayWitnessMapper;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConPayWitness;
import com.jpmsoft.projectProducer.service.contract.IConPayWitnessService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/10/30 17:31
 */
@Service
public class ConPayWitnessImpl extends ServiceImpl<ConPayWitnessMapper, ConPayWitness> implements IConPayWitnessService {


    @Override
    public void createOrUpdate(List<ConPayWitnessDTO> data, String payId) {
        deleteByPayId(payId);
        if (!CollectionUtils.isEmpty(data)) {
            List<ConPayWitness> entities = data.stream().map(e -> {
                ConPayWitness entity = new ConPayWitness();
                BeanUtils.copyProperties(e, entity);
                entity.setId(GenerateUUID.getUUID());
                entity.setPayId(payId);
                return entity;
            }).collect(Collectors.toList());
            saveBatch(entities);
        }
    }

    @Override
    public void deleteByPayId(String payId) {
        QueryWrapper<ConPayWitness> wrapper = new QueryWrapper<>();
        wrapper.eq("Pay_Id", payId);
        baseMapper.delete(wrapper);
    }

    @Override
    public List<ConPayWitnessDTO> getConPayWitnessByPayId(String payId) {
        return baseMapper.getConPayWitnessByPayId(payId);
    }

    @Override
    public BaseResponse getConPayWitnessByOrderNo(String orderNo) {
        return BaseResponse.ok(baseMapper.getConPayWitnessByOrderNo(orderNo));
    }

    @Override
    public BaseResponse getConPayWitnessByConId(String conId) {
        return BaseResponse.ok(baseMapper.getConPayWitnessByConId(conId));
    }

}
