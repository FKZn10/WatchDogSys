package com.jpmsoft.projectProducer.service.materialsEquipment.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.projectProducer.mapper.materialsEquipment.MeQualityPriceDtlMapper;
import com.jpmsoft.projectProducer.pojo.dto.materialsEquipment.MeQualityPriceDtlDto;
import com.jpmsoft.projectProducer.pojo.entity.materialsEquipment.MeQualityPriceDtl;
import com.jpmsoft.projectProducer.service.materialsEquipment.IMeQualityPriceDtlService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/1/3 13:51
 */
@Service
public class MeQualityPriceDtlImpl extends ServiceImpl<MeQualityPriceDtlMapper, MeQualityPriceDtl>
        implements IMeQualityPriceDtlService {

    @Override
    public void createOrUpdateMeQualityPriceDtl(List<MeQualityPriceDtlDto> datas, String qpId) {
        delMeQualityPriceDtlByQpId(qpId);
        if (!CollectionUtils.isEmpty(datas)) {
            Date now = new Date();
            List<MeQualityPriceDtl> entities = datas.stream().map(data -> {
                MeQualityPriceDtl entity = new MeQualityPriceDtl();
                BeanUtils.copyProperties(data, entity);
                entity.setQpdId(GenerateUUID.getUUID(data.getQpdId()));
                entity.setQpId(qpId);
                entity.setQpdCreateTime(data.getQpdCreateTime() == null ? now : data.getQpdCreateTime());
                return entity;
            }).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(entities)) saveBatch(entities);
        }
    }

    @Override
    public void delMeQualityPriceDtlByQpId(String qpId) {
        QueryWrapper<MeQualityPriceDtl> wrapper = new QueryWrapper<>();
        wrapper.eq("Qp_Id", qpId);
        baseMapper.delete(wrapper);
    }
}
