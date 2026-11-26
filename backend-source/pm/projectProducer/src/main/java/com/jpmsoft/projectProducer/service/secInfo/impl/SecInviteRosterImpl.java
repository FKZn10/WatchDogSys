package com.jpmsoft.projectProducer.service.secInfo.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.request.SecInfo.SecInviteRosterDto;
import com.jpmsoft.common.model.response.SecInfo.SecInviteRosterVo;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.projectProducer.mapper.secInfo.SecInviteRosterMapper;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecInviteRoster;
import com.jpmsoft.projectProducer.service.secInfo.ISecInviteRosterService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: 李文涛 aaron
 * @date: 2022/8/23 10:33
 */
@Service
public class SecInviteRosterImpl extends ServiceImpl<SecInviteRosterMapper, SecInviteRoster> implements ISecInviteRosterService {

    @Override
    public void createOrUpdateSecInviteRoster(List<SecInviteRosterDto> datas, String dId) {
        delSecInviteRosterBydId(dId);
        if (!CollectionUtils.isEmpty(datas)) {
            List<SecInviteRoster> entitys = datas.stream().map(data -> {
                SecInviteRoster entity = new SecInviteRoster();
                BeanUtils.copyProperties(data, entity);
                entity.setDId(dId);
                entity.setId(GenerateUUID.getUUID(data.getId()));
                return entity;
            }).collect(Collectors.toList());
            saveBatch(entitys);
        }
    }

    @Override
    public void createOrUpdateSecInviteRosterBath(List<List<SecInviteRosterDto>> roters, List<String> dIds) {
        delBathBydIds(dIds);
        if (!CollectionUtils.isEmpty(roters)) {
            List<SecInviteRoster> entitys = new ArrayList<>();
            for (int i = 0; i < roters.size(); i++) {
                List<SecInviteRosterDto> datas = roters.get(i);
                String dId = dIds.get(i);
                if (!CollectionUtils.isEmpty(datas)) {
                    for (SecInviteRosterDto data : datas) {
                        SecInviteRoster entity = new SecInviteRoster();
                        BeanUtils.copyProperties(data, entity);
                        entity.setDId(dId);
                        entity.setId(GenerateUUID.getUUID(data.getId()));
                        entitys.add(entity);
                    }
                }
            }
            if (entitys.size() > 0) saveBatch(entitys);
        }
    }

    @Override
    public void delSecInviteRosterBydId(String dId) {
        QueryWrapper<SecInviteRoster> wrapper = new QueryWrapper<>();
        wrapper.eq("D_Id", dId);
        baseMapper.delete(wrapper);
    }

    @Override
    public void delBathBydIds(List<String> dIds) {
        QueryWrapper<SecInviteRoster> wrapper = new QueryWrapper<>();
        wrapper.in("D_Id", dIds);
        baseMapper.delete(wrapper);
    }

    @Override
    public List<SecInviteRosterVo> getSecInviteRosterBydIds(List<String> dIds) {
        return baseMapper.getSecInviteRosterBydIds(dIds);
    }
}
