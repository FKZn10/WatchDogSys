package com.jpmsoft.projectProducer.service.secInfo.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.projectProducer.mapper.secInfo.SecSupervisionCommitteeMapper;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecSupervisionCommitteeDTO;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecSupervisionCommittee;
import com.jpmsoft.projectProducer.service.secInfo.ISecSupervisionCommitteeService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/5/6 10:51
 */
@Service
public class SecSupervisionCommitteeImpl extends ServiceImpl<SecSupervisionCommitteeMapper, SecSupervisionCommittee> implements ISecSupervisionCommitteeService {
    @Override
    public void createOrUpdateSecSupervisionCommittee(List<SecSupervisionCommitteeDTO> data, String sccId) {
        deleteBySccId(sccId);
        if (!CollectionUtils.isEmpty(data)) {
            List<SecSupervisionCommittee> entities = data.stream().map(n -> {
                SecSupervisionCommittee entity = new SecSupervisionCommittee();
                BeanUtils.copyProperties(n, entity);
                entity.setSsdId(GenerateUUID.getUUID(entity.getSsdId()));
                entity.setSccId(sccId);
                return entity;
            }).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(entities)) saveBatch(entities);
        }
    }

    @Override
    public void deleteBySccId(String sccId) {
        QueryWrapper<SecSupervisionCommittee> wrapper = new QueryWrapper<>();
        wrapper.eq("Scc_Id", sccId);
        baseMapper.delete(wrapper);
    }
}
