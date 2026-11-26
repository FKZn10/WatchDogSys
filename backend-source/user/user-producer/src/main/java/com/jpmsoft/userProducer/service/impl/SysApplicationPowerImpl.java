package com.jpmsoft.userProducer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.userProducer.mapper.SysApplicationPowerMapper;
import com.jpmsoft.userProducer.pojo.dto.SysApplicationPowerDTO;
import com.jpmsoft.userProducer.pojo.entity.SysApplicationPower;
import com.jpmsoft.userProducer.service.ISysApplicationPowerService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/3/7 9:24
 */
@Service
public class SysApplicationPowerImpl extends ServiceImpl<SysApplicationPowerMapper, SysApplicationPower>
        implements ISysApplicationPowerService {

    @Override
    public void createOrUpdateSysAppPower(List<SysApplicationPowerDTO> powers, String appId) {
        delSysAppPowerByAppId(appId);
        if (!CollectionUtils.isEmpty(powers)) {
            List<SysApplicationPower> entitys = powers.stream().map(data -> {
                SysApplicationPower p = new SysApplicationPower();
                BeanUtils.copyProperties(data, p);
                p.setApId(GenerateUUID.getUUID(data.getApId()));
                p.setAppId(appId);
                return p;
            }).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(entitys)) saveBatch(entitys);
        }
    }

    @Override
    public void delSysAppPowerByAppId(String appId) {
        QueryWrapper<SysApplicationPower> wrapper = new QueryWrapper<>();
        wrapper.eq("App_Id", appId);
        baseMapper.delete(wrapper);
    }

    @Override
    public List<SysApplicationPowerDTO> getSysAppPowerByAppId(String appId) {
        QueryWrapper<SysApplicationPower> wrapper = new QueryWrapper<>();
        wrapper.eq("App_Id", appId);
        List<SysApplicationPower> data = baseMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(data)) {
            List<SysApplicationPowerDTO> models = data.stream().map(entity -> {
                SysApplicationPowerDTO dto = new SysApplicationPowerDTO();
                BeanUtils.copyProperties(entity, dto);
                return dto;
            }).collect(Collectors.toList());
            return models;
        }
        return new ArrayList<>();
    }
}
