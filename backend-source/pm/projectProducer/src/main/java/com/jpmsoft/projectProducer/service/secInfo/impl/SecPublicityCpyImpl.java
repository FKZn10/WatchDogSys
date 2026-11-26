package com.jpmsoft.projectProducer.service.secInfo.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.request.SecInfo.SecPublicityCpyDto;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.secInfo.SecPublicityCpyMapper;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecPublicityCpy;
import com.jpmsoft.projectProducer.service.secInfo.ISecPublicityCpyService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: 李文涛 aaron
 * @date: 2022/7/25 10:46
 */
@Service
public class SecPublicityCpyImpl extends ServiceImpl<SecPublicityCpyMapper, SecPublicityCpy> implements ISecPublicityCpyService {
    @Override
    public void createOrUpdateSecPublicityCpy(List<SecPublicityCpyDto> datas, String prId) {
        delSecPublicityCpyByPrId(prId);
        if (ValidateUtil.isNotEmpty(datas)) {
            Date now = new Date();
            List<SecPublicityCpy> entitys = datas.stream().map(data -> {
                SecPublicityCpy entity = new SecPublicityCpy();
                BeanUtils.copyProperties(data, entity);
                if (ValidateUtil.isEmpty(entity.getId())) entity.setId(GenerateUUID.getUUID());
                if (ValidateUtil.isNull(entity.getCreateTime())) entity.setCreateTime(now);
                entity.setPrId(prId);
                return entity;
            }).collect(Collectors.toList());
            saveBatch(entitys);
        }
    }

    @Override
    public void delSecPublicityCpyByPrId(String prId) {
        QueryWrapper<SecPublicityCpy> wrapper = new QueryWrapper<>();
        wrapper.eq("Pr_Id", prId);
        baseMapper.delete(wrapper);
    }
}
