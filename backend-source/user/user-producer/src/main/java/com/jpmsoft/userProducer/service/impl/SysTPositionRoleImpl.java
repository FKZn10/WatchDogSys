package com.jpmsoft.userProducer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.userProducer.mapper.SysTPositionRoleMapper;
import com.jpmsoft.userProducer.pojo.dto.TPositionDto;
import com.jpmsoft.userProducer.pojo.entity.SysTPositionRole;
import com.jpmsoft.userProducer.service.ISysTPositionRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class SysTPositionRoleImpl extends ServiceImpl<SysTPositionRoleMapper, SysTPositionRole> implements ISysTPositionRoleService {

    @Override
    public BaseResponse getRoleByT_Position(TPositionDto request) {
        QueryWrapper<SysTPositionRole> wrapper = new QueryWrapper<>();
        wrapper.eq("TP_ID", request.getTpId());
        List<SysTPositionRole> models = baseMapper.selectList(wrapper);
        List<String> data = new ArrayList<>();
        for (SysTPositionRole model : models) {
            data.add(model.getRoleId());
        }
        return  BaseResponse.ok(data);
    }

    @Override
    public void createTPositionRole(String tpId, String roleIds) {
        deleteTPositionRole(tpId);
        if (!ValidateUtil.isEmpty(roleIds)) {
            String[] ids = roleIds.split(",");
            for (String id : ids) {
                SysTPositionRole entity = new SysTPositionRole();
                entity.setTpId(tpId);
                entity.setRoleId(id);
                entity.setTprId(GenerateUUID.getUUID());
                baseMapper.insert(entity);
            }
        }
    }

    @Override
    public void deleteTPositionRole(String tpId) {
        QueryWrapper<SysTPositionRole> wrapper = new QueryWrapper<>();
        wrapper.eq("TP_ID", tpId);
        baseMapper.delete(wrapper);
    }

    @Override
    public List<SysTPositionRole> getTPositionRoleByTpId(String tpId) {
        QueryWrapper<SysTPositionRole> wrapper = new QueryWrapper<>();
        wrapper.eq("TP_ID", tpId);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public void deleteTPositionRoleByRole(String roleId) {
        QueryWrapper<SysTPositionRole> wrapper = new QueryWrapper<>();
        wrapper.eq("Role_ID", roleId);
        baseMapper.delete(wrapper);
    }
}
