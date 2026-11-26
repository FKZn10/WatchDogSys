package com.jpmsoft.userProducer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.userProducer.mapper.SysApplicationPriceMapper;
import com.jpmsoft.userProducer.pojo.dto.SysApplicationPriceDTO;
import com.jpmsoft.userProducer.pojo.entity.SysApplicationPrice;
import com.jpmsoft.userProducer.service.ISysApplicationPriceService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/3/7 17:45
 */
@Service
public class SysApplicationPriceImpl extends ServiceImpl<SysApplicationPriceMapper, SysApplicationPrice>
        implements ISysApplicationPriceService {


    @Override
    public void createOrUpdateSysAppPrice(List<SysApplicationPriceDTO> powers, String appId) {
        delSysAppPriceByAppId(appId);
        if (!CollectionUtils.isEmpty(powers)) {
            List<SysApplicationPrice> entitys = powers.stream().map(data -> {
                SysApplicationPrice p = new SysApplicationPrice();
                BeanUtils.copyProperties(data, p);
                p.setApId(GenerateUUID.getUUID(data.getApId()));
                p.setAppId(appId);
                return p;
            }).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(entitys)) saveBatch(entitys);
        }
    }

    @Override
    public void delSysAppPriceByAppId(String appId) {
        QueryWrapper<SysApplicationPrice> wrapper = new QueryWrapper<>();
        wrapper.eq("App_Id", appId);
        baseMapper.delete(wrapper);
    }

    @Override
    public List<SysApplicationPriceDTO> getSysAppPriceByAppId(String appId) {
        QueryWrapper<SysApplicationPrice> wrapper = new QueryWrapper<>();
        wrapper.eq("App_Id", appId);
        List<SysApplicationPrice> data = baseMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(data)) {
            List<SysApplicationPriceDTO> models = data.stream().map(entity -> {
                SysApplicationPriceDTO dto = new SysApplicationPriceDTO();
                BeanUtils.copyProperties(entity, dto);
                return dto;
            }).collect(Collectors.toList());
            return models;
        }
        return new ArrayList<>();
    }
}
