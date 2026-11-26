package com.jpmsoft.projectProducer.service.secInfo.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.request.SecInfo.SecInviteDetailDTO;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.projectProducer.mapper.secInfo.SecInviteDetailMapper;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecInviteDetail;
import com.jpmsoft.projectProducer.service.secInfo.ISecInviteDetailService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: 李文涛 aaron
 * @date: 2022/8/23 10:33
 */
@Service
public class SecInviteDetailImpl extends ServiceImpl<SecInviteDetailMapper, SecInviteDetail> implements ISecInviteDetailService {

    @Override
    public void createOrUpdateSecInviteDetail(List<SecInviteDetailDTO> datas, String wsId) {
        delSecInviteDetailByWsId(wsId);
        if (!CollectionUtils.isEmpty(datas)) {
            List<SecInviteDetail> entitys = datas.stream().map(data -> {
                SecInviteDetail entity = new SecInviteDetail();
                BeanUtils.copyProperties(data, entity);
                entity.setWsId(wsId);
                entity.setId(GenerateUUID.getUUID(data.getId()));
                return entity;
            }).collect(Collectors.toList());
            saveBatch(entitys);
        }
    }


    @Override
    public void createOrUpdateSecInviteDetailSir(List<SecInviteDetailDTO> secInviteDetails, String sirId) {
        delSecInviteDetailBySirId(sirId);
        if (!CollectionUtils.isEmpty(secInviteDetails)) {
            List<SecInviteDetail> entitys = secInviteDetails.stream().map(data -> {
                SecInviteDetail entity = new SecInviteDetail();
                BeanUtils.copyProperties(data, entity);
                entity.setSirId(sirId);
                entity.setId(GenerateUUID.getUUID(data.getId()));
                return entity;
            }).collect(Collectors.toList());
            saveBatch(entitys);
        }
    }


    @Override
    public void createOrUpdateSecInviteDetailBath(List<List<SecInviteDetailDTO>> roters, List<String> wsIds) {
        delBathByWsIds(wsIds);
        if (!CollectionUtils.isEmpty(roters)) {
            List<SecInviteDetail> entitys = new ArrayList<>();
            for (int i = 0; i < roters.size(); i++) {
                List<SecInviteDetailDTO> datas = roters.get(i);
                String wsId = wsIds.get(i);
                if (!CollectionUtils.isEmpty(datas)) {
                    for (SecInviteDetailDTO data : datas) {
                        SecInviteDetail entity = new SecInviteDetail();
                        BeanUtils.copyProperties(data, entity);
                        entity.setWsId(wsId);
                        entity.setId(GenerateUUID.getUUID(data.getId()));
                        entitys.add(entity);
                    }
                }
            }
            if (entitys.size() > 0) saveBatch(entitys);
        }
    }

    @Override
    public void delSecInviteDetailByWsId(String wsId) {
        QueryWrapper<SecInviteDetail> wrapper = new QueryWrapper<>();
        wrapper.eq("Ws_Id", wsId);
        baseMapper.delete(wrapper);
    }

    private void delSecInviteDetailBySirId(String sirId) {
        QueryWrapper<SecInviteDetail> wrapper = new QueryWrapper<>();
        wrapper.eq("sir_Id", sirId);
        baseMapper.delete(wrapper);
    }

    @Override
    public void delBathByWsIds(List<String> wsIds) {
        QueryWrapper<SecInviteDetail> wrapper = new QueryWrapper<>();
        wrapper.in("Ws_Id", wsIds);
        baseMapper.delete(wrapper);
    }

    @Override
    public List<SecInviteDetailDTO> getSecInviteDetailByWsIds(List<String> wsIds) {
        if (CollectionUtils.isEmpty(wsIds)) return null;
        return baseMapper.getSecInviteDetailByWsIds(wsIds);
    }


    @Override
    public List<SecInviteDetailDTO> getSecInviteDetailBySirIds(List<String> sirIds) {
        if (CollectionUtils.isEmpty(sirIds)) return null;
        return baseMapper.getSecInviteDetailBySirIds(sirIds);
    }


}
