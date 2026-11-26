package com.jpmsoft.projectProducer.service.secInfo.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.projectProducer.mapper.secInfo.SecCalibrationCommitteeMapper;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecCalibrationCommitteeDTO;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecCalibrationCommittee;
import com.jpmsoft.projectProducer.service.secInfo.ISecCalibrationCommitteeService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/5/6 10:51
 */
@Service
public class SecCalibrationCommitteeImpl extends ServiceImpl<SecCalibrationCommitteeMapper, SecCalibrationCommittee> implements ISecCalibrationCommitteeService {
    @Override
    public void createOrUpdateSecCalibrationCommittee(List<SecCalibrationCommitteeDTO> data, String sccId) {
        deleteBySccId(sccId);
        if (!CollectionUtils.isEmpty(data)) {
            List<SecCalibrationCommittee> entities = data.stream().map(n -> {
                SecCalibrationCommittee entity = new SecCalibrationCommittee();
                BeanUtils.copyProperties(n, entity);
                entity.setScdId(GenerateUUID.getUUID(entity.getScdId()));
                entity.setSccId(sccId);
                return entity;
            }).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(entities)) saveBatch(entities);
        }
    }

    @Override
    public void deleteBySccId(String sccId) {
        QueryWrapper<SecCalibrationCommittee> wrapper = new QueryWrapper<>();
        wrapper.eq("Scc_Id", sccId);
        baseMapper.delete(wrapper);
    }
}
