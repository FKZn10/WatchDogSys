package com.jpmsoft.projectProducer.service.secInfo.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.projectProducer.mapper.secInfo.SecDefenseSupervisionMapper;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecDefenseSupervisionDTO;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecDefenseSupervision;
import com.jpmsoft.projectProducer.service.secInfo.ISecDefenseSupervisionService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/5/6 10:51
 */
@Service
public class SecDefenseSupervisionImpl extends ServiceImpl<SecDefenseSupervisionMapper, SecDefenseSupervision> implements ISecDefenseSupervisionService {
    @Override
    public void createOrUpdateSecDefenseSupervision(List<SecDefenseSupervisionDTO> data, String sqId) {
        deleteBySqId(sqId);
        if (!CollectionUtils.isEmpty(data)) {
            List<SecDefenseSupervision> entities = data.stream().map(n -> {
                SecDefenseSupervision entity = new SecDefenseSupervision();
                BeanUtils.copyProperties(n, entity);
                entity.setSdsId(GenerateUUID.getUUID(entity.getSdsId()));
                entity.setSqId(sqId);
                return entity;
            }).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(entities)) saveBatch(entities);
        }
    }

    @Override
    public void deleteBySqId(String sqId) {
        QueryWrapper<SecDefenseSupervision> wrapper = new QueryWrapper<>();
        wrapper.eq("Sq_Id", sqId);
        baseMapper.delete(wrapper);
    }
}
