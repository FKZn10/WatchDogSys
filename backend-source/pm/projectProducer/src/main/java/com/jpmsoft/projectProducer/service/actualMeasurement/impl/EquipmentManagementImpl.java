package com.jpmsoft.projectProducer.service.actualMeasurement.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.model.request.ActualMeasurement.EquipmentManagementRequest;
import com.jpmsoft.projectProducer.mapper.actualMeasurement.EquipmentManagementMapper;
import com.jpmsoft.projectProducer.pojo.entity.actualMeasurement.EquipmentManagement;
import com.jpmsoft.projectProducer.service.actualMeasurement.IEquipmentManagementService;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


@Service
public class EquipmentManagementImpl extends ServiceImpl<EquipmentManagementMapper, EquipmentManagement> implements IEquipmentManagementService {


    @Override
    public BaseResponse getEquipmentManagementList(EquipmentManagementRequest request, String token) {
        Page<EquipmentManagement> page = new Page<>(request.getPage(), request.getSize());
        LambdaQueryWrapper<EquipmentManagement> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(request.getEmName()), EquipmentManagement::getEmName, request.getEmName())
                .like(StringUtils.isNotBlank(request.getEmBlueName()), EquipmentManagement::getEmBlueName, request.getEmBlueName());
        page = baseMapper.selectPage(page, queryWrapper);
        return BaseResponse.ok(page.getRecords(),(int)page.getTotal());
    }
}
