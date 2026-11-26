package com.jpmsoft.projectProducer.service.secInfo.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.projectProducer.mapper.secInfo.SecDefenseReviewMapper;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecDefenseReviewDTO;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecDefenseReview;
import com.jpmsoft.projectProducer.service.secInfo.ISecDefenseReviewService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/5/6 10:51
 */
@Service
public class SecDefenseReviewImpl extends ServiceImpl<SecDefenseReviewMapper, SecDefenseReview> implements ISecDefenseReviewService {
    @Override
    public void createOrUpdateSecDefenseReview(List<SecDefenseReviewDTO> data, String sqId) {
        deleteBySqId(sqId);
        if (!CollectionUtils.isEmpty(data)) {
            List<SecDefenseReview> entities = data.stream().map(n -> {
                SecDefenseReview entity = new SecDefenseReview();
                BeanUtils.copyProperties(n, entity);
                entity.setSdrId(GenerateUUID.getUUID(entity.getSdrId()));
                entity.setSqId(sqId);
                return entity;
            }).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(entities)) saveBatch(entities);
        }
    }

    @Override
    public void deleteBySqId(String sqId) {
        QueryWrapper<SecDefenseReview> wrapper = new QueryWrapper<>();
        wrapper.eq("Sq_Id", sqId);
        baseMapper.delete(wrapper);
    }
}
