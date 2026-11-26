package com.jpmsoft.projectProducer.service.secInfo.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.secInfo.SecSupplementRecordDtlMapper;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecSupplementRecordDtlDTO;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecSupplementRecordDtl;
import com.jpmsoft.projectProducer.service.secInfo.ISecSupplementRecordDtlService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/11/27 14:44
 */
@Service
public class SecSupplementRecordDtlImpl extends ServiceImpl<SecSupplementRecordDtlMapper, SecSupplementRecordDtl> implements ISecSupplementRecordDtlService {


    @Override
    public void createOrUpdate(List<SecSupplementRecordDtlDTO> data, String sesId) {
        deleteBySesId(sesId);
        if (!CollectionUtils.isEmpty(data)) {
            List<SecSupplementRecordDtl> entities = data.stream().map(d -> {
                SecSupplementRecordDtl entity = new SecSupplementRecordDtl();
                BeanUtils.copyProperties(d, entity);
                entity.setSrdId(GenerateUUID.getUUID());
                entity.setSesId(sesId);
                entity.setSrdTime(ValidateUtil.isNull(d.getSrdTime()) ? new Date() : d.getSrdTime());
                return entity;
            }).collect(Collectors.toList());
            saveBatch(entities);
        }
    }

    @Override
    public void deleteBySesId(String sesId) {
        QueryWrapper<SecSupplementRecordDtl> wrapper = new QueryWrapper<>();
        wrapper.eq("Ses_Id", sesId);
        baseMapper.delete(wrapper);
    }

    @Override
    public List<SecSupplementRecordDtl> getBySesId(String sesId) {
        QueryWrapper<SecSupplementRecordDtl> wrapper = new QueryWrapper<>();
        wrapper.eq("Ses_Id", sesId);
        wrapper.eq("Srd_Mark", "01");
        return baseMapper.selectList(wrapper);
    }
}
