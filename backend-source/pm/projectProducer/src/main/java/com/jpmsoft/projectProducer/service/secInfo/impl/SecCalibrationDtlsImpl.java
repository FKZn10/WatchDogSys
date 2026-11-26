package com.jpmsoft.projectProducer.service.secInfo.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.projectProducer.mapper.secInfo.SecCalibrationDtlsMapper;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecCalibrationDtlsDTO;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecCalibrationDtls;
import com.jpmsoft.projectProducer.service.secInfo.ISecCalibrationDtlsService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/5/6 10:51
 */
@Service
public class SecCalibrationDtlsImpl extends ServiceImpl<SecCalibrationDtlsMapper, SecCalibrationDtls> implements ISecCalibrationDtlsService {
    @Override
    public void createOrUpdateSecCalibrationDtls(List<SecCalibrationDtlsDTO> data, String scsId) {
        deleteByScsId(scsId);
        if (!CollectionUtils.isEmpty(data)) {
            List<SecCalibrationDtls> entities = data.stream().map(n -> {
                SecCalibrationDtls entity = new SecCalibrationDtls();
                BeanUtils.copyProperties(n, entity);
                entity.setScdId(GenerateUUID.getUUID(entity.getScdId()));
                entity.setScsId(scsId);
                return entity;
            }).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(entities)) saveBatch(entities);
        }
    }

    @Override
    public void deleteByScsId(String scsId) {
        QueryWrapper<SecCalibrationDtls> wrapper = new QueryWrapper<>();
        wrapper.eq("Scs_Id", scsId);
        baseMapper.delete(wrapper);
    }
}
