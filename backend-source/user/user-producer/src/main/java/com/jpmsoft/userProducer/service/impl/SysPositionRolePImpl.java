package com.jpmsoft.userProducer.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.userProducer.mapper.SysPositionRolePMapper;
import com.jpmsoft.userProducer.pojo.dto.PositionPDto;
import com.jpmsoft.userProducer.pojo.dto.PositionRolePDto;
import com.jpmsoft.userProducer.pojo.entity.SysPositionRoleP;
import com.jpmsoft.userProducer.service.ISysPositionRolePService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SysPositionRolePImpl extends ServiceImpl<SysPositionRolePMapper, SysPositionRoleP> implements ISysPositionRolePService {
    @Override
    public BaseResponse getRoleByPositionP(PositionPDto request) {
        QueryWrapper<SysPositionRoleP> wrapper = new QueryWrapper<>();
        wrapper.eq("PP_ID", request.getPpId());
        List<SysPositionRoleP> models = baseMapper.selectList(wrapper);
        List<String> data = new ArrayList<>();
        models.forEach(model -> {
            data.add(model.getRoleId());
        });
        return  BaseResponse.ok(data);
    }

    @Override
    public void deletePositionRolePByRoleId(String roleId) {
        QueryWrapper<SysPositionRoleP> wrapper = new QueryWrapper<>();
        wrapper.eq("Role_ID", roleId);
        baseMapper.delete(wrapper);
    }

    @Override
    public BaseResponse getPositionRolePByPId(PositionRolePDto request) {
        if(StrUtil.isBlank(request.getpId()))
            throw new JPMException("项目id不能为空");
        List<Map<String, String>> roleList = baseMapper.getPositionRoleP(request.getpId(),request.getuId());
        return BaseResponse.ok(roleList);
    }
}
