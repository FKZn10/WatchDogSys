package com.jpmsoft.projectProducer.service.materialsEquipment.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.projectProducer.mapper.materialsEquipment.MeBrandApprovalDtlMapper;
import com.jpmsoft.projectProducer.pojo.dto.materialsEquipment.MeBrandApprovalDtlDto;
import com.jpmsoft.projectProducer.pojo.entity.materialsEquipment.MeBrandApprovalDtl;
import com.jpmsoft.projectProducer.service.materialsEquipment.IMeBrandApprovalDtlService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/1/3 10:28
 */
@Service
public class MeBrandApprovalDtlImpl extends ServiceImpl<MeBrandApprovalDtlMapper, MeBrandApprovalDtl> implements IMeBrandApprovalDtlService {
    @Override
    public void createOrUpdateMeBrandApprovalDtl(List<MeBrandApprovalDtlDto> datas, String baId) {
        delMeBrandApprovalDtlByBaId(baId);
        if (!CollectionUtils.isEmpty(datas)) {
            List<MeBrandApprovalDtl> entities = datas.stream().map(data -> {
                MeBrandApprovalDtl entity = new MeBrandApprovalDtl();
                BeanUtils.copyProperties(data, entity);
                entity.setBadId(GenerateUUID.getUUID(data.getBadId()));
                entity.setBaId(baId);
                return entity;
            }).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(entities)) saveBatch(entities);
        }
    }

    @Override
    public void delMeBrandApprovalDtlByBaId(String baId) {
        QueryWrapper<MeBrandApprovalDtl> wrapper = new QueryWrapper<>();
        wrapper.eq("Ba_Id", baId);
        baseMapper.delete(wrapper);
    }
}
