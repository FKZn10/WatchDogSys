package com.jpmsoft.userProducer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.userProducer.mapper.SysPortalIndexConfigMapper;
import com.jpmsoft.userProducer.pojo.dto.SysPortalIndexComponentDto;
import com.jpmsoft.userProducer.pojo.dto.SysPortalIndexConfigDto;
import com.jpmsoft.userProducer.pojo.entity.SysPortalIndexComponent;
import com.jpmsoft.userProducer.pojo.entity.SysPortalIndexConfig;
import com.jpmsoft.userProducer.pojo.entity.SysRole;
import com.jpmsoft.userProducer.service.ISysPortalIndexComponentService;
import com.jpmsoft.userProducer.service.ISysPortalIndexConfigService;
import com.jpmsoft.userProducer.utils.RedisUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class SysPortalIndexConfigImpl extends ServiceImpl<SysPortalIndexConfigMapper, SysPortalIndexConfig> implements ISysPortalIndexConfigService {

    private final RedisUtils redisUtils;

    private final ISysPortalIndexComponentService sysPortalIndexComponentService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateSysPortalIndexConfig(SysPortalIndexConfigDto request, String token) {

        SysPortalIndexConfig entity = new SysPortalIndexConfig();
        BeanUtils.copyProperties(request,entity);

        if (ValidateUtil.isEmpty(request.getId())) {
            entity.setId(GenerateUUID.getUUID());
            entity.setCreateTime(new Date());
            entity.setState(0);
            save(entity);
        } else  {
            entity.setId(request.getId());
            entity.setCreateUname(null);
            entity.setCreateUid(null);
            entity.setCreateTime(null);
            entity.setUpdateTime(new Date());
            updateById(entity);
        }

        sysPortalIndexComponentService.createOrUpdateSysPortalIndexComponent(request.getComponents(),entity.getId());

        return BaseResponse.ok(entity.getId());
    }

    @Override
    public BaseResponse updatePortalStateById(SysPortalIndexConfigDto request, String token) {
        if (ValidateUtil.isEmpty(request.getId())) {
            return BaseResponse.fail("没有主键id");
        }
        if (ValidateUtil.isEmpty(request.getPortalState())) {
            return BaseResponse.fail("没有状态");
        }
        SysPortalIndexConfig sysPortalIndexConfig = getById(request.getId());
        if (ValidateUtil.isEmpty(sysPortalIndexConfig)) {
            return BaseResponse.fail("该门户不存在");
        }
        sysPortalIndexConfig.setPortalState(request.getPortalState());
        updateById(sysPortalIndexConfig);
        return BaseResponse.ok(sysPortalIndexConfig.getId());
    }

    @Override
    public BaseResponse getSysPortalIndexConfigById(String id) {

        SysPortalIndexConfig sysPortalIndexConfig = this.getById(id);
        SysPortalIndexConfigDto sysPortalIndexConfigDto = new SysPortalIndexConfigDto();
        BeanUtils.copyProperties(sysPortalIndexConfig,sysPortalIndexConfigDto);

        List<SysPortalIndexComponent> sysPortalIndexComponents = sysPortalIndexComponentService.list(Wrappers.lambdaQuery(SysPortalIndexComponent.class)
                .eq(SysPortalIndexComponent::getIndexConfigId, sysPortalIndexConfigDto.getId()));
        List<SysPortalIndexComponentDto> sysPortalIndexComponentDtos = sysPortalIndexComponents.stream().map(sysPortalIndexComponent -> {
            SysPortalIndexComponentDto sysPortalIndexComponentDto = new SysPortalIndexComponentDto();
            BeanUtils.copyProperties(sysPortalIndexComponent, sysPortalIndexComponentDto);
            return sysPortalIndexComponentDto;
        }).collect(Collectors.toList());

        sysPortalIndexConfigDto.setComponents(sysPortalIndexComponentDtos);

        return BaseResponse.ok(sysPortalIndexConfigDto);
    }

    @Override
    public BaseResponse getSysPortalIndexConfigList(SysPortalIndexConfigDto request) {

        QueryMap<SysPortalIndexConfigDto> queryMap = new QueryMap<>(request);
        queryMap.put("createUname", request.getCreateUname()).put("createUid", request.getCreateUid())
                .put("portalName", request.getPortalName()).put("portalCode", request.getPortalCode())
                .put("portalState", request.getPortalState()).put("state", request.getState())
                .put("systemIdentifier");

        IPage<SysPortalIndexConfigDto> page = new Page<>(request.getPage(), request.getSize());
        IPage<SysPortalIndexConfigDto> configPage = baseMapper.getSysPortalIndexConfigList(page, queryMap.getQuery());

        List<SysPortalIndexConfigDto> configList = configPage.getRecords();

        if (CollectionUtils.isNotEmpty(configList)) {
            List<String> mainIds = configList.stream()
                    .map(SysPortalIndexConfigDto::getId)
                    .collect(Collectors.toList());

            List<SysPortalIndexComponentDto> componentDtos = sysPortalIndexComponentService.getSysPortalIndexComponentByMainIdsList(mainIds);

            if (CollectionUtils.isNotEmpty(componentDtos)) {
                Map<String, List<SysPortalIndexComponentDto>> componentMap = componentDtos.stream()
                        .collect(Collectors.groupingBy(SysPortalIndexComponentDto::getIndexConfigId));

                configList.forEach(config -> {
                    List<SysPortalIndexComponentDto> subList = componentMap.getOrDefault(config.getId(), new ArrayList<>());
                    config.setComponents(subList);
                });
            }
        }
        return BaseResponse.ok(configList, (int) configPage.getTotal());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse deleteSysPortalIndexConfigById(String id) {
        if (ValidateUtil.isEmpty(id)) {
            return BaseResponse.fail("没有主键id");
        }
        SysPortalIndexConfig sysPortalIndexConfig = getById(id);
        if (ValidateUtil.isEmpty(sysPortalIndexConfig)) {
            return BaseResponse.fail("该门户不存在");
        }
        removeById(id);
        sysPortalIndexComponentService.remove(Wrappers.lambdaQuery(SysPortalIndexComponent.class)
                .eq(SysPortalIndexComponent::getIndexConfigId, id));
        return BaseResponse.ok(id);
    }


    @Override
    public BaseResponse queryComponentListByUserInfo(String token) {

        List<SysRole> sysRoles = baseMapper.queryRoleListByUid(redisUtils.getUid(token));
        Set<String> roleIds = sysRoles.stream().map(SysRole::getRoleId).collect(Collectors.toSet());
        List<SysPortalIndexComponent> sysPortalIndexComponents =
                sysPortalIndexComponentService.getSysPortalIndexComponentByRoleIds(roleIds);

        Set<String> indexConfigIds = sysPortalIndexComponents.stream()
                .map(SysPortalIndexComponent::getIndexConfigId).collect(Collectors.toSet());
        if (CollectionUtils.isEmpty(indexConfigIds)) {
            return BaseResponse.ok();
        }
        LambdaQueryWrapper<SysPortalIndexConfig> queryWrapper = Wrappers.lambdaQuery(SysPortalIndexConfig.class)
                .in(SysPortalIndexConfig::getId, indexConfigIds).eq(SysPortalIndexConfig::getPortalState,"1");
        List<SysPortalIndexConfig> sysPortalIndexConfigs = this.list(queryWrapper);

        return BaseResponse.ok(sysPortalIndexConfigs, sysPortalIndexConfigs.size());
    }


}
