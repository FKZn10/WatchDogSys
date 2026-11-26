package com.jpmsoft.userProducer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.userProducer.mapper.SysRolePowerMapper;
import com.jpmsoft.userProducer.pojo.dto.RoleDto;
import com.jpmsoft.userProducer.pojo.entity.SysPower;
import com.jpmsoft.userProducer.pojo.entity.SysRolePower;
import com.jpmsoft.userProducer.pojo.vo.RolePowerVo;
import com.jpmsoft.userProducer.service.ISysPowerService;
import com.jpmsoft.userProducer.service.ISysRolePowerService;
import com.jpmsoft.userProducer.utils.CheckNullUtil;
import com.jpmsoft.userProducer.utils.RedisUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysRolePowerImpl extends ServiceImpl<SysRolePowerMapper, SysRolePower> implements ISysRolePowerService {

    @Resource
    private ISysPowerService sysPowerService;

    @Resource
    private RedisUtils redisUtils;

    @Override
    public void CreateRolePower(String roleId, String powerIds, String teId) {
        deleteRolePower(roleId);
        if (!CheckNullUtil.checkString(powerIds)) {
            String[] ids = powerIds.split(",");
            List<SysRolePower> entitys = new ArrayList<>();
            List<SysPower> alls = sysPowerService.allPowerList(teId);
            //无重复值机制
            HashSet<String> hashSet = new HashSet<>();
            for (String id : ids) {
                SysPower power = alls.stream().filter(n -> n.getPowerId().equals(id)).findFirst().get();
                hashSet.add(id);
                if (!power.getPowerParentid().equals("0"))
                    getParentPower(alls, hashSet, power.getPowerParentid());
            }
            hashSet.forEach(powerId -> {
                SysRolePower pojo = new SysRolePower();
                pojo.setRpId(GenerateUUID.getUUID());
                pojo.setPowerId(powerId);
                pojo.setRoleId(roleId);
                entitys.add(pojo);
            });
            if (entitys.size() > 0)
                saveBatch(entitys);
        }
    }

    private void getParentPower(List<SysPower> alls, HashSet<String> hashSet, String parentId) {
        if (hashSet.contains(parentId))
            return;
        SysPower power = alls.stream().filter(n -> n.getPowerId().equals(parentId)).findFirst().get();
        hashSet.add(power.getPowerId());
        if (!power.getPowerParentid().equals("0"))
            getParentPower(alls, hashSet, power.getPowerParentid());
    }

    @Override
    public void deleteRolePower(String roleId) {
        QueryWrapper<SysRolePower> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("Role_ID", roleId);
        baseMapper.delete(queryWrapper);
    }

    @Override
    public BaseResponse getPowerByRole(RoleDto request) {
        QueryWrapper<SysRolePower> wrapper = new QueryWrapper<>();
        wrapper.eq("Role_ID", request.getRoleId());
        List<SysRolePower> list = baseMapper.selectList(wrapper);
        List<String> data = new ArrayList<>();
        List<SysPower> powers = sysPowerService.getAllPowerList(request.getTeId());
        list.forEach(model -> {
            if (powers.stream().filter(n -> n.getPowerParentid().equals(model.getPowerId())).collect(Collectors.toList()).size() < 1)
                data.add(model.getPowerId());
        });
        return new BaseResponse(true, "", data);
    }

    @Override
    public List<RolePowerVo> getRoleNames(String teId) {
        if (ValidateUtil.isNotEmpty(teId))
            return baseMapper.getRoleNameByPowerIdByTeId(teId);
        return baseMapper.getRoleNameByPowerId();
    }

    @Override
    public void deleteRolePowerByRoleId(String roleId) {
        QueryWrapper<SysRolePower> wrapper = new QueryWrapper<>();
        wrapper.eq("Role_ID", roleId);
        baseMapper.delete(wrapper);
    }

    @Override
    public void deleteRolePowerByPowerId(String powerId) {
        QueryWrapper<SysRolePower> wrapper = new QueryWrapper<>();
        wrapper.eq("Power_ID", powerId);
        baseMapper.delete(wrapper);
    }

    @Override
    public void updatePositionRoleBath(List<String> roleIds, String powerId) {
        if (roleIds == null) return;
        deleteRolePowerByPowerId(powerId);
        List<SysRolePower> entitys = new ArrayList<>();
        roleIds.forEach(role -> {
            SysRolePower entity = new SysRolePower();
            entity.setRoleId(role);
            entity.setPowerId(powerId);
            entity.setRpId(GenerateUUID.getUUID());
            entitys.add(entity);
        });
        if (entitys.size() > 0) saveBatch(entitys);
    }
}
