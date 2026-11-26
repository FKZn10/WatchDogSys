package com.jpmsoft.projectProducer.service.materialsEquipment.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.materialsEquipment.MePriceListDtlMapper;
import com.jpmsoft.projectProducer.pojo.dto.materialsEquipment.MePriceListDtlDto;
import com.jpmsoft.projectProducer.pojo.entity.materialsEquipment.MePriceListDtl;
import com.jpmsoft.projectProducer.service.materialsEquipment.IMePriceListDtlService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: 李文涛 aaron
 * @date: 2022/8/4 14:34
 */
@Service
public class MePriceListDtlImpl extends ServiceImpl<MePriceListDtlMapper, MePriceListDtl> implements IMePriceListDtlService {
    @Override
    public void createOrUpdateMePriceListDtl(List<MePriceListDtlDto> datas, String plId) {
        delMePriceListDtlByPlId(plId);
        if (ValidateUtil.isNotEmpty(datas)) {
            List<MePriceListDtl> entitys = new ArrayList<>();
            entitys = datas.stream().map(n -> {
                MePriceListDtl entity = new MePriceListDtl();
                BeanUtils.copyProperties(n, entity);
                entity.setPlId(plId);
                entity.setId(ValidateUtil.isNotEmpty(n.getId()) ? n.getId() : GenerateUUID.getUUID());
                return entity;
            }).collect(Collectors.toList());
            saveBatch(entitys);
        }
    }

    @Override
    public void delMePriceListDtlByPlId(String plId) {
        QueryWrapper<MePriceListDtl> wrapper = new QueryWrapper<>();
        wrapper.eq("Pl_Id", plId);
        baseMapper.delete(wrapper);
    }
}
