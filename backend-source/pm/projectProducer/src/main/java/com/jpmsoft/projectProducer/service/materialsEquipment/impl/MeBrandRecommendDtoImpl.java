package com.jpmsoft.projectProducer.service.materialsEquipment.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.projectProducer.mapper.materialsEquipment.MeBrandRecommendDtlMapper;
import com.jpmsoft.projectProducer.pojo.dto.materialsEquipment.MeBrandRecommendDtlDto;
import com.jpmsoft.projectProducer.pojo.entity.materialsEquipment.MeBrandRecommendDtl;
import com.jpmsoft.projectProducer.service.materialsEquipment.IMeBrandRecommendDtlService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/1/3 9:47
 */
@Service
public class MeBrandRecommendDtoImpl extends ServiceImpl<MeBrandRecommendDtlMapper, MeBrandRecommendDtl> implements IMeBrandRecommendDtlService {
    @Override
    public void createOrUpdateMeBrandRecommendDtl(List<MeBrandRecommendDtlDto> datas, String brId) {
        delMeBrandRecommendDtlByBrId(brId);
        if (!CollectionUtils.isEmpty(datas)) {
            List<MeBrandRecommendDtl> entities = datas.stream().map(data -> {
                MeBrandRecommendDtl entity = new MeBrandRecommendDtl();
                BeanUtils.copyProperties(data, entity);
                entity.setBrdId(GenerateUUID.getUUID(data.getBrdId()));
                entity.setBrId(brId);
                return entity;
            }).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(entities)) saveBatch(entities);
        }
    }

    @Override
    public void delMeBrandRecommendDtlByBrId(String brId) {
        QueryWrapper<MeBrandRecommendDtl> wrapper = new QueryWrapper<>();
        wrapper.eq("Br_Id", brId);
        baseMapper.delete(wrapper);
    }
}
