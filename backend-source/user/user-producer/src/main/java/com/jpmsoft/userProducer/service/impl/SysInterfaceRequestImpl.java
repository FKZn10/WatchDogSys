package com.jpmsoft.userProducer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.userProducer.mapper.SysInterfaceRequestMapper;
import com.jpmsoft.userProducer.pojo.dto.SysInterfaceRequestDTO;
import com.jpmsoft.userProducer.pojo.entity.SysInterfaceRequest;
import com.jpmsoft.userProducer.service.ISysInterfaceRequestService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/11/19 10:48
 */
@Service
public class SysInterfaceRequestImpl extends ServiceImpl<SysInterfaceRequestMapper, SysInterfaceRequest>
        implements ISysInterfaceRequestService {
    @Override
    public void deleteByIfId(String ifId) {
        QueryWrapper<SysInterfaceRequest> wrapper = new QueryWrapper<>();
        wrapper.eq("If_Id", ifId);
        baseMapper.delete(wrapper);
    }

    @Override
    public void createOrUpdate(List<SysInterfaceRequestDTO> data, String ifId) {
        deleteByIfId(ifId);
        if (!CollectionUtils.isEmpty(data)) {
            List<SysInterfaceRequest> entities = data.stream().map(d -> {
                SysInterfaceRequest entity = new SysInterfaceRequest();
                BeanUtils.copyProperties(d, entity);
                entity.setIfId(ifId);
                return entity;
            }).collect(Collectors.toList());
            saveBatch(entities);
        }
    }

    @Override
    public List<SysInterfaceRequestDTO> selectById(String ifId) {
        QueryWrapper<SysInterfaceRequest> wrapper = new QueryWrapper<>();
        wrapper.eq("If_Id", ifId);
        wrapper.orderByAsc("req_sort");
        List<SysInterfaceRequest> entities = baseMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(entities))
            return entities.stream().map(e -> {
                SysInterfaceRequestDTO d = new SysInterfaceRequestDTO();
                BeanUtils.copyProperties(e, d);
                return d;
            }).collect(Collectors.toList());
        return null;
    }
}
