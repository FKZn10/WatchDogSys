package com.jpmsoft.projectProducer.service.other.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.projectProducer.mapper.contract.SystemParameterMapper;
import com.jpmsoft.projectProducer.pojo.entity.other.SystemParameter;
import com.jpmsoft.projectProducer.service.other.SystemParameterService;
import org.springframework.stereotype.Service;

@Service
public class SystemParameterServiceImpl extends ServiceImpl<SystemParameterMapper, SystemParameter> implements SystemParameterService {
    @Override
    public SystemParameter getData() {
        return baseMapper.selectOne(null);
    }
}
