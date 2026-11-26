package com.jpmsoft.userProducer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.userProducer.mapper.SysInterfaceResponseMapper;
import com.jpmsoft.userProducer.pojo.dto.SysInterfaceResponseDTO;
import com.jpmsoft.userProducer.pojo.entity.SysInterfaceResponse;
import com.jpmsoft.userProducer.service.ISysInterfaceResponseService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/11/19 10:47
 */
@Service
public class SysInterfaceResponseImpl extends ServiceImpl<SysInterfaceResponseMapper, SysInterfaceResponse>
        implements ISysInterfaceResponseService {
    @Override
    public void deleteByIfId(String ifId) {
        QueryWrapper<SysInterfaceResponse> wrapper = new QueryWrapper<>();
        wrapper.eq("If_Id", ifId);
        baseMapper.delete(wrapper);
    }

    @Override
    public void createOrUpdate(List<SysInterfaceResponseDTO> data, String ifId) {
        deleteByIfId(ifId);
        if (!CollectionUtils.isEmpty(data)) {
            List<SysInterfaceResponse> entities = data.stream().map(d -> {
                SysInterfaceResponse entity = new SysInterfaceResponse();
                BeanUtils.copyProperties(d, entity);
                entity.setIfId(ifId);
                return entity;
            }).collect(Collectors.toList());
            saveBatch(entities);
        }
    }

    @Override
    public List<SysInterfaceResponseDTO> selectById(String ifId) {
        QueryWrapper<SysInterfaceResponse> wrapper = new QueryWrapper<>();
        wrapper.eq("If_Id", ifId);
        wrapper.orderByAsc("res_sort");
        List<SysInterfaceResponse> entities = baseMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(entities))
            return entities.stream().map(e -> {
                SysInterfaceResponseDTO d = new SysInterfaceResponseDTO();
                BeanUtils.copyProperties(e, d);
                return d;
            }).collect(Collectors.toList());
        return null;
    }
}
