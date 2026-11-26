package com.jpmsoft.jpbpm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.jpbpm.mapper.SysPositionMapper;
import com.jpmsoft.jpbpm.pojo.entity.SysPosition;
import com.jpmsoft.jpbpm.service.SysPositionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class  SysPositionImpl extends ServiceImpl<SysPositionMapper, SysPosition> implements SysPositionService {

    @Override
    public List<SysPosition> getPositionAll(String customerId) {
        QueryWrapper<SysPosition> wrapper=new QueryWrapper<>();
        return baseMapper.selectList(wrapper);
    }





}
