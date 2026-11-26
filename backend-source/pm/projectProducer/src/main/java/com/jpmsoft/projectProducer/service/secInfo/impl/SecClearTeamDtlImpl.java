package com.jpmsoft.projectProducer.service.secInfo.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.projectProducer.mapper.secInfo.SecClearTeamDtlMapper;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecClearTeamDtlDTO;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecClearTeamDtl;
import com.jpmsoft.projectProducer.service.secInfo.ISecClearTeamDtlService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/5/6 10:51
 */
@Service
public class SecClearTeamDtlImpl extends ServiceImpl<SecClearTeamDtlMapper, SecClearTeamDtl> implements ISecClearTeamDtlService {
    @Override
    public void createOrUpdateSecClearTeamDtl(List<SecClearTeamDtlDTO> data, String sctId) {
        deleteBySctId(sctId);
        if (!CollectionUtils.isEmpty(data))
        {
            List<SecClearTeamDtl> entities=data.stream().map(n->{
                SecClearTeamDtl entity=new SecClearTeamDtl();
                BeanUtils.copyProperties(n,entity);
                entity.setCtdId(GenerateUUID.getUUID(entity.getCtdId()));
                entity.setSctId(sctId);
                return entity;
            }).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(entities)) saveBatch(entities);
        }
    }

    @Override
    public void deleteBySctId(String sctId) {
        QueryWrapper<SecClearTeamDtl> wrapper=new QueryWrapper<>();
        wrapper.eq("Sct_Id",sctId);
        baseMapper.delete(wrapper);
    }
}
