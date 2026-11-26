package com.jpmsoft.userProducer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.userProducer.mapper.SysPositionPRoleMapper;
import com.jpmsoft.userProducer.pojo.entity.SysPositionPRole;
import com.jpmsoft.userProducer.service.ISysPositionPRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SysPositionPRoleImpl extends ServiceImpl<SysPositionPRoleMapper, SysPositionPRole> implements ISysPositionPRoleService {

    @Override
    public void CreatePositionPRole(String ppId, String roleIds) {
        QueryWrapper<SysPositionPRole> wrapper = new QueryWrapper<>();
        wrapper.eq("PP_ID", ppId);
        baseMapper.delete(wrapper);
        if (!ValidateUtil.isEmpty(roleIds)) {
            String[] ids = roleIds.split(",");
            for (String id : ids) {
                SysPositionPRole entity = new SysPositionPRole();
                entity.setPpId(ppId);
                entity.setRoleId(id);
                entity.setPpRId(GenerateUUID.getUUID());
                baseMapper.insert(entity);
            }
        }
    }

    @Override
    public void insert(SysPositionPRole sysPositionPRole) {
        baseMapper.insert(sysPositionPRole);
    }

    @Override
    public void deletePositionPRoleByRoleId(String roleId) {
        QueryWrapper<SysPositionPRole> wrapper = new QueryWrapper<>();
        wrapper.eq("Role_ID", roleId);
        baseMapper.delete(wrapper);
    }

    @Override
    public List<SysPositionPRole> getPositionPRoleByPpId(String ppId) {
        QueryWrapper<SysPositionPRole> wrapper = new QueryWrapper<>();
        wrapper.eq("PP_ID", ppId);
        return baseMapper.selectList(wrapper);
    }
}
