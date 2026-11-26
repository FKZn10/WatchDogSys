package com.jpmsoft.projectProducer.service.other.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.other.SysExtendValueMapper;
import com.jpmsoft.projectProducer.pojo.entity.other.SysExtendValue;
import com.jpmsoft.projectProducer.service.other.ISysExtendValueService;
import org.springframework.stereotype.Service;

@Service
public class SysExtendValueServiceImpl extends ServiceImpl<SysExtendValueMapper, SysExtendValue> implements ISysExtendValueService {
    @Override
    public void deleteByFileId(String planId) {
        if (ValidateUtil.isNotEmpty(planId)) {
            remove(Wrappers.<SysExtendValue>lambdaQuery().eq(SysExtendValue::getFileid, planId));
        }
    }
}
