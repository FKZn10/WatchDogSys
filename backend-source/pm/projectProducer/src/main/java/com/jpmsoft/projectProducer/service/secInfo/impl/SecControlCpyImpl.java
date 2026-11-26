package com.jpmsoft.projectProducer.service.secInfo.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.request.SecInfo.SecControlCpyDto;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.secInfo.SecControlCpyMapper;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecControlCpy;
import com.jpmsoft.projectProducer.service.secInfo.ISecControlCpyService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: 李文涛 aaron
 * @date: 2022/7/24 20:34
 */
@Service
public class SecControlCpyImpl extends ServiceImpl<SecControlCpyMapper, SecControlCpy> implements ISecControlCpyService {
    @Override
    public void createOrUpdateControlCpy(List<SecControlCpyDto> datas, String cpId) {
        delControlCpyByCpId(cpId);
        if (ValidateUtil.isNotEmpty(datas)) {
            Date now = new Date();
            List<SecControlCpy> entitys = datas.stream().map(data -> {
                SecControlCpy entity = new SecControlCpy();
                BeanUtils.copyProperties(data, entity);
                if (ValidateUtil.isEmpty(data.getId()))
                    entity.setId(GenerateUUID.getUUID());
                if (ValidateUtil.isNull(data.getCreateTime()))
                    entity.setCreateTime(now);
                entity.setCpId(cpId);
                return entity;
            }).collect(Collectors.toList());
            saveBatch(entitys);
        }
    }

    @Override
    public void delControlCpyByCpId(String cpId) {
        QueryWrapper<SecControlCpy> wrapper = new QueryWrapper<>();
        wrapper.eq("Cp_Id", cpId);
        baseMapper.delete(wrapper);
    }
}
