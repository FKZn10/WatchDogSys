package com.jpmsoft.userProducer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.userProducer.mapper.SysPortalIndexComponentMapper;
import com.jpmsoft.userProducer.pojo.dto.SysPortalIndexComponentDto;
import com.jpmsoft.userProducer.pojo.entity.SysPortalIndexComponent;
import com.jpmsoft.userProducer.service.ISysPortalIndexComponentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ISysPortalIndexComponentImpl extends ServiceImpl<SysPortalIndexComponentMapper,SysPortalIndexComponent> implements ISysPortalIndexComponentService {


    @Override
    public void createOrUpdateSysPortalIndexComponent(List<SysPortalIndexComponentDto> components, String id) {

        if (CollectionUtils.isEmpty(components)) {
            return;
        }

        this.remove(new LambdaQueryWrapper<SysPortalIndexComponent>()
                .eq(SysPortalIndexComponent::getIndexConfigId, id));

        List<SysPortalIndexComponent> entityList = components.stream().map(dto -> {
            SysPortalIndexComponent entity = new SysPortalIndexComponent();
            BeanUtils.copyProperties(dto, entity);
            entity.setIndexConfigId(id);
            return entity;
        }).collect(Collectors.toList());

        this.saveBatch(entityList);
    }

    @Override
    public List<SysPortalIndexComponentDto> getSysPortalIndexComponentByMainIdsList(List<String> mainIds) {
        if (CollectionUtils.isEmpty(mainIds)) {
            return Collections.emptyList();
        }
        return baseMapper.getSysPortalIndexComponentByMainIdsList(mainIds);
    }

    @Override
    public List<SysPortalIndexComponent> getSysPortalIndexComponentByRoleIds(Set<String> roleIds) {
        if (CollectionUtils.isEmpty(roleIds)) {
            return Collections.emptyList();
        }
        return baseMapper.getSysPortalIndexComponentByRoleIds(roleIds);
    }

}
