package com.jpmsoft.userProducer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.userProducer.mapper.SysFlowUserMapper;
import com.jpmsoft.userProducer.pojo.entity.SysFlowUser;
import com.jpmsoft.userProducer.service.ISysFlowUserService;
import org.springframework.stereotype.Service;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/1/15 10:59
 */
@Service
public class SysFlowUserImpl extends ServiceImpl<SysFlowUserMapper, SysFlowUser> implements ISysFlowUserService {
    @Override
    public boolean roleExist(String roleId) {
        if (ValidateUtil.isEmpty(roleId)) return false;
        QueryWrapper<SysFlowUser> wrapper = new QueryWrapper<>();
        wrapper.eq("FlowUserRole", "role," + roleId);
        return baseMapper.exists(wrapper);
    }

    @Override
    public boolean positionExist(String posId) {
        if (ValidateUtil.isEmpty(posId)) return false;
        QueryWrapper<SysFlowUser> wrapper = new QueryWrapper<>();
        wrapper.eq("FlowUserRole", "department_position," + posId);
        return baseMapper.exists(wrapper);
    }
}
