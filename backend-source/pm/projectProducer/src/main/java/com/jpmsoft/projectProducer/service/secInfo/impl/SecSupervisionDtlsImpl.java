package com.jpmsoft.projectProducer.service.secInfo.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.projectProducer.mapper.secInfo.SecSupervisionDtlsMapper;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecSupervisionDtlsDTO;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecSupervisionDtls;
import com.jpmsoft.projectProducer.service.secInfo.ISecSupervisionDtlsService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/5/6 10:51
 */
@Service
public class SecSupervisionDtlsImpl extends ServiceImpl<SecSupervisionDtlsMapper, SecSupervisionDtls> implements ISecSupervisionDtlsService {
    @Override
    public void createOrUpdateSecSupervisionDtls(List<SecSupervisionDtlsDTO> data, String scsId) {
        deleteByScsId(scsId);
        if (!CollectionUtils.isEmpty(data)) {
            List<SecSupervisionDtls> entities = data.stream().map(n -> {
                SecSupervisionDtls entity = new SecSupervisionDtls();
                BeanUtils.copyProperties(n, entity);
                entity.setSsdId(GenerateUUID.getUUID(entity.getSsdId()));
                entity.setScsId(scsId);
                return entity;
            }).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(entities)) saveBatch(entities);
        }
    }

    @Override
    public void deleteByScsId(String scsId) {
        QueryWrapper<SecSupervisionDtls> wrapper = new QueryWrapper<>();
        wrapper.eq("Scs_Id", scsId);
        baseMapper.delete(wrapper);
    }
}
