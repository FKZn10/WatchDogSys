package com.jpmsoft.projectProducer.service.secInfo.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.request.SecInfo.SecDeterminCpyDto;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.secInfo.SecDeterminCpyMapper;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecDeterminCpy;
import com.jpmsoft.projectProducer.service.secInfo.ISecDeterminCpyService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: 李文涛 aaron
 * @date: 2022/7/25 11:02
 */
@Service
public class SecDeterminCpyImpl extends ServiceImpl<SecDeterminCpyMapper, SecDeterminCpy> implements ISecDeterminCpyService {
    @Override
    public void createOrUpdateSecDeterminCpy(List<SecDeterminCpyDto> datas, String prId) {
        delSecDeterminCpyByDrId(prId);
        if (ValidateUtil.isNotEmpty(datas)) {
            Date now = new Date();
            List<SecDeterminCpy> entitys = datas.stream().map(data -> {
                SecDeterminCpy entity = new SecDeterminCpy();
                BeanUtils.copyProperties(data, entity);
                if (ValidateUtil.isEmpty(entity.getId())) entity.setId(GenerateUUID.getUUID());
                if (ValidateUtil.isNull(entity.getCreateTime())) entity.setCreateTime(now);
                entity.setDrId(prId);
                return entity;
            }).collect(Collectors.toList());
            saveBatch(entitys);
        }
    }

    @Override
    public void delSecDeterminCpyByDrId(String drId) {
        QueryWrapper<SecDeterminCpy> wrapper = new QueryWrapper<>();
        wrapper.eq("Dr_Id", drId);
        baseMapper.delete(wrapper);
    }
}
