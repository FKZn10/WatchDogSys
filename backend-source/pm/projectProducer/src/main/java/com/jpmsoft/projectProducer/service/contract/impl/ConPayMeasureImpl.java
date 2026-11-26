package com.jpmsoft.projectProducer.service.contract.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.projectProducer.mapper.contract.ConPayMeasureMapper;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConPayMeasure;
import com.jpmsoft.projectProducer.service.contract.IConPayMeasureService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/10/17 13:50
 */
@Service
public class ConPayMeasureImpl extends ServiceImpl<ConPayMeasureMapper, ConPayMeasure> implements IConPayMeasureService {


    @Override
    public void createOrUpdate(List<String> meaIds, String conId, String payId) {
        if (!CollectionUtils.isEmpty(meaIds))
        {
            QueryWrapper<ConPayMeasure> wrapper=new QueryWrapper<>();
            wrapper.eq("Pay_Id",payId);
            baseMapper.delete(wrapper);
            List<ConPayMeasure> entities=meaIds.stream().map(meaId->{
                ConPayMeasure entity=new ConPayMeasure();
                entity.setId(GenerateUUID.getUUID());
                entity.setMeaId(meaId);
                entity.setConId(conId);
                entity.setPayId(payId);
                return entity;
            }).collect(Collectors.toList());
            saveBatch(entities);
        }
    }

    @Override
    public BaseResponse getApplyAndAmtByPayId(String payId) {
        return BaseResponse.ok(baseMapper.getApplyAndAmtByPayId(payId));
    }

    @Override
    public BaseResponse getUnMeasureApplyAndAmtByConId(String conId) {
        return BaseResponse.ok(baseMapper.getUnMeasureApplyAndAmtByConId(conId));
    }
}
