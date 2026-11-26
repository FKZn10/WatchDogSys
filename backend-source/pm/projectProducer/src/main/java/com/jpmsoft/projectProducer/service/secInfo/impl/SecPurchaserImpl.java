package com.jpmsoft.projectProducer.service.secInfo.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecPurchaserDTO;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.projectProducer.mapper.secInfo.SecPurchaserMapper;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecPurchaser;
import com.jpmsoft.projectProducer.service.secInfo.ISecPurchaserService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/10/8 16:56
 */
@Service
public class SecPurchaserImpl extends ServiceImpl<SecPurchaserMapper, SecPurchaser> implements ISecPurchaserService {


    @Override
    public void createOrUpdateSecPurchaser(List<SecPurchaserDTO> data, String sopId) {
        deleteSecPurchaserBySopId(sopId);
        if (!CollectionUtils.isEmpty(data)) {
            List<SecPurchaser> entities = data.stream().map(d -> {
                SecPurchaser entity = new SecPurchaser();
                BeanUtils.copyProperties(d, entity);
                entity.setSopId(sopId);
                entity.setId(GenerateUUID.getUUID());
                return entity;
            }).collect(Collectors.toList());
            saveBatch(entities);
        }
    }

    @Override
    public void deleteSecPurchaserBySopId(String sopId) {
        QueryWrapper<SecPurchaser> wrapper = new QueryWrapper<>();
        wrapper.eq("Sop_Id", sopId);
        baseMapper.delete(wrapper);
    }

    @Override
    public List<SecPurchaserDTO> selectBySopId(String sopId) {
        QueryWrapper<SecPurchaser> wrapper = new QueryWrapper<>();
        wrapper.eq("Sop_Id", sopId);
        List<SecPurchaser> entities = baseMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(entities))
            return entities.stream().map(e -> {
                SecPurchaserDTO d = new SecPurchaserDTO();
                BeanUtils.copyProperties(e, d);
                return d;
            }).collect(Collectors.toList());
        return null;
    }
}
