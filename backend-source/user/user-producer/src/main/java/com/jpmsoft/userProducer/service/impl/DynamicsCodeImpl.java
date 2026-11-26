package com.jpmsoft.userProducer.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.userProducer.mapper.DynamicsCodeMapper;
import com.jpmsoft.userProducer.pojo.entity.DynamicsCode;
import com.jpmsoft.userProducer.service.IDynamicsCodeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DynamicsCodeImpl extends ServiceImpl<DynamicsCodeMapper, DynamicsCode> implements IDynamicsCodeService {
    @Override
    public List<Map> selectList(String sql) {
        return baseMapper.selectList(sql);
    }

    @Override
    public Map selectOne(String sql) {
        return baseMapper.selectOne(sql);
    }

    @Override
    public Integer selectCount(String sql) {
        return baseMapper.selectCount(sql);
    }


}
