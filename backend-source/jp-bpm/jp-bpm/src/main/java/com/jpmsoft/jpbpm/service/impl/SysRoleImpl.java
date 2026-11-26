package com.jpmsoft.jpbpm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.jpbpm.mapper.SysRoleMapper;
import com.jpmsoft.jpbpm.pojo.entity.SysRole;
import com.jpmsoft.jpbpm.service.SysRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SysRoleImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {


    @Override
    public List<SysRole> getRoleAll(String customerId) {
        QueryWrapper<SysRole> wrapper = new QueryWrapper<>();
        return baseMapper.selectList(wrapper);
    }
}
