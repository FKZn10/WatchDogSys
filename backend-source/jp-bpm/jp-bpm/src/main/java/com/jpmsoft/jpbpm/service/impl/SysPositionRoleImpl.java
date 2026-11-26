package com.jpmsoft.jpbpm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.jpbpm.mapper.SysPositionRoleMapper;
import com.jpmsoft.jpbpm.pojo.entity.SysPositionRole;
import com.jpmsoft.jpbpm.service.SysPositionRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SysPositionRoleImpl extends ServiceImpl<SysPositionRoleMapper, SysPositionRole> implements SysPositionRoleService {

    @Override
    public List<SysPositionRole> getPositionRoleAll(String customerId) {
        QueryWrapper<SysPositionRole> wrapper=new QueryWrapper<>();
        return baseMapper.selectList(wrapper);
    }




}
