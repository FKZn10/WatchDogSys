package com.jpmsoft.projectProducer.service.qualitySafety.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.model.requestAndResponse.SafeResponsibility.SafeResponsibilityCopyDTO;
import com.jpmsoft.common.model.requestAndResponse.SafeResponsibility.SafeResponsibilitySystemDTO;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.projectProducer.mapper.qualitySafety.SafeResponsibilitySystemMapper;
import com.jpmsoft.projectProducer.pojo.entity.qualitySafety.SafeResponsibilitySystem;
import com.jpmsoft.projectProducer.service.qualitySafety.ISafeResponsibilityChangeLogService;
import com.jpmsoft.projectProducer.service.qualitySafety.ISafeResponsibilityLevelService;
import com.jpmsoft.projectProducer.service.qualitySafety.ISafeResponsibilitySystemService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 安全责任体系服务实现。
 */
@Service
public class SafeResponsibilitySystemImpl extends ServiceImpl<SafeResponsibilitySystemMapper, SafeResponsibilitySystem> implements ISafeResponsibilitySystemService {
    private static final int STANDARD_SYSTEM = 1;
    private static final int PROJECT_SYSTEM = 2;

    @Resource
    private ISafeResponsibilityLevelService levelService;
    @Resource
    private ISafeResponsibilityChangeLogService changeLogService;

    /**
     * 新增或修改责任体系并记录变更台账。
     *
     * @param request 责任体系数据
     * @param uid     操作人ID
     * @param uname   操作人姓名
     * @return 体系ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateSafeResponsibilitySystem(SafeResponsibilitySystemDTO request, String uid, String uname) {
        validateSystem(request);
        Date now = new Date();
        SafeResponsibilitySystem before = null;
        String operationType = "ADD";
        if (StrUtil.isBlank(request.getId())) {
            request.setId(GenerateUUID.getUUID());
            request.setVersionNo(request.getVersionNo() == null ? 1 : request.getVersionNo());
            request.setStatus(request.getStatus() == null ? Boolean.TRUE : request.getStatus());
            request.setCreateUid(uid);
            request.setCreateUname(uname);
            request.setCreateTime(now);
        } else {
            before = requireSystem(request.getId());
            operationType = "UPDATE";
            request.setCreateUid(before.getCreateUid());
            request.setCreateUname(before.getCreateUname());
            request.setCreateTime(before.getCreateTime());
        }
        request.setUpdateUid(uid);
        request.setUpdateUname(uname);
        request.setUpdateTime(now);
        SafeResponsibilitySystem entity = toEntity(request);
        if (before == null) {
            save(entity);
        } else {
            updateById(entity);
        }
        changeLogService.saveChangeLog(request.getId(), "SYSTEM", request.getId(), request.getSystemName(),
                operationType, before, request, uid, uname, "维护安全责任体系", null);
        return BaseResponse.ok(entity.getId());
    }

    /**
     * 按体系请求字段筛选列表。
     *
     * @param request 体系筛选条件
     * @return 责任体系列表
     */
    @Override
    public BaseResponse getSafeResponsibilitySystemList(SafeResponsibilitySystemDTO request) {
        SafeResponsibilitySystemDTO condition = request == null ? new SafeResponsibilitySystemDTO() : request;
        return BaseResponse.ok(selectSystems(condition).stream().map(this::toDTO).collect(Collectors.toList()));
    }

    /**
     * 筛选责任体系树；下级关键字命中时保留其层级祖先链和所属体系。
     *
     * @param request 体系及树关键字筛选条件
     * @return 责任体系树列表
     */
    @Override
    public BaseResponse getSafeResponsibilitySystemTree(SafeResponsibilitySystemDTO request) {
        SafeResponsibilitySystemDTO condition = request == null ? new SafeResponsibilitySystemDTO() : request;
        List<SafeResponsibilitySystemDTO> result = selectSystems(condition).stream()
                .map(this::toDTO)
                .peek(system -> system.setLevelList(
                        levelService.getLevelTreeBySystemId(system.getId(), condition.getKeyword())))
                .filter(system -> StrUtil.isBlank(condition.getKeyword())
                        || StrUtil.containsIgnoreCase(system.getSystemName(), condition.getKeyword())
                        || StrUtil.containsIgnoreCase(system.getRemark(), condition.getKeyword())
                        || (system.getLevelList() != null && !system.getLevelList().isEmpty()))
                .collect(Collectors.toList());
        return BaseResponse.ok(result);
    }

    /**
     * 删除责任体系及其全部层级、岗位和职责，并保留删除快照。
     *
     * @param request 包含体系ID的请求
     * @param uid     操作人ID
     * @param uname   操作人姓名
     * @return 操作结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse deleteSafeResponsibilitySystem(SafeResponsibilitySystemDTO request, String uid, String uname) {
        if (request == null || StrUtil.isBlank(request.getId())) {
            throw new JPMException("安全责任体系ID不能为空");
        }
        SafeResponsibilitySystem entity = requireSystem(request.getId());
        SafeResponsibilitySystemDTO before = toDTO(entity);
        before.setLevelList(levelService.getLevelTreeBySystemId(entity.getId(), null));
        levelService.deleteBySystemId(entity.getId());
        removeById(entity.getId());
        changeLogService.saveChangeLog(entity.getId(), "SYSTEM", entity.getId(), entity.getSystemName(), "DELETE",
                before, null, uid, uname, "删除安全责任体系", null);
        return BaseResponse.ok();
    }

    /**
     * 将来源体系完整复制为项目自定义体系。
     *
     * @param request 复制参数
     * @param uid     操作人ID
     * @param uname   操作人姓名
     * @return 新体系ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse copySafeResponsibilitySystem(SafeResponsibilityCopyDTO request, String uid, String uname) {
        if (request == null || StrUtil.isBlank(request.getSourceSystemId()) || StrUtil.isBlank(request.getPId())) {
            throw new JPMException("来源体系ID和项目ID不能为空");
        }
        SafeResponsibilitySystem source = requireSystem(request.getSourceSystemId());
        Date now = new Date();
        SafeResponsibilitySystem target = new SafeResponsibilitySystem();
        BeanUtils.copyProperties(source, target);
        target.setId(GenerateUUID.getUUID());
        target.setSystemName(StrUtil.isBlank(request.getSystemName()) ? source.getSystemName() : request.getSystemName());
        target.setSystemType(PROJECT_SYSTEM);
        target.setPId(request.getPId());
        target.setConId(request.getConId());
        target.setSourceSystemId(source.getId());
        target.setVersionNo(1);
        target.setCreateUid(uid);
        target.setCreateUname(uname);
        target.setCreateTime(now);
        target.setUpdateUid(uid);
        target.setUpdateUname(uname);
        target.setUpdateTime(now);
        SafeResponsibilitySystemDTO targetDTO = toDTO(target);
        validateSystem(targetDTO);
        save(target);
        levelService.copyBySystem(source.getId(), target.getId(), uid, uname);
        targetDTO.setLevelList(levelService.getLevelTreeBySystemId(target.getId(), null));
        changeLogService.saveChangeLog(target.getId(), "SYSTEM", target.getId(), target.getSystemName(), "COPY",
                source, targetDTO, uid, uname, "复用安全责任体系模板", GenerateUUID.getUUID());
        return BaseResponse.ok(target.getId());
    }

    /**
     * 根据列表请求生成查询条件，并保留标准模板与项目体系的默认查询边界。
     */
    private List<SafeResponsibilitySystem> selectSystems(SafeResponsibilitySystemDTO condition) {
        LambdaQueryWrapper<SafeResponsibilitySystem> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(condition.getId()), SafeResponsibilitySystem::getId, condition.getId())
                .like(StrUtil.isNotBlank(condition.getSystemName()), SafeResponsibilitySystem::getSystemName,
                        condition.getSystemName())
                .eq(StrUtil.isNotBlank(condition.getSourceSystemId()), SafeResponsibilitySystem::getSourceSystemId,
                        condition.getSourceSystemId())
                .eq(condition.getVersionNo() != null, SafeResponsibilitySystem::getVersionNo, condition.getVersionNo())
                .like(StrUtil.isNotBlank(condition.getRemark()), SafeResponsibilitySystem::getRemark,
                        condition.getRemark())
                .eq(condition.getStatus() != null, SafeResponsibilitySystem::getStatus, condition.getStatus());
        if (condition.getSystemType() != null) {
            if (condition.getSystemType() == PROJECT_SYSTEM && StrUtil.isBlank(condition.getPId())) {
                throw new JPMException("查询项目自定义体系时项目ID不能为空");
            }
            wrapper.eq(SafeResponsibilitySystem::getSystemType, condition.getSystemType())
                    .eq(condition.getSystemType() == PROJECT_SYSTEM && StrUtil.isNotBlank(condition.getPId()),
                            SafeResponsibilitySystem::getPId, condition.getPId())
                    .eq(condition.getSystemType() == PROJECT_SYSTEM && StrUtil.isNotBlank(condition.getConId()),
                            SafeResponsibilitySystem::getConId, condition.getConId());
        } else if (StrUtil.isNotBlank(condition.getPId())) {
            wrapper.and(item -> item.eq(SafeResponsibilitySystem::getSystemType, STANDARD_SYSTEM)
                    .or(project -> project.eq(SafeResponsibilitySystem::getSystemType, PROJECT_SYSTEM)
                            .eq(SafeResponsibilitySystem::getPId, condition.getPId())
                            .eq(StrUtil.isNotBlank(condition.getConId()), SafeResponsibilitySystem::getConId,
                                    condition.getConId())));
        } else if (StrUtil.isBlank(condition.getId())) {
            wrapper.eq(SafeResponsibilitySystem::getSystemType, STANDARD_SYSTEM);
        }
        wrapper.orderByAsc(SafeResponsibilitySystem::getSystemType)
                .orderByDesc(SafeResponsibilitySystem::getUpdateTime)
                .orderByDesc(SafeResponsibilitySystem::getCreateTime);
        return list(wrapper);
    }

    /**
     * 校验体系类型、项目归属和名称唯一性。
     */
    private void validateSystem(SafeResponsibilitySystemDTO system) {
        if (system == null || StrUtil.isBlank(system.getSystemName())) {
            throw new JPMException("体系名称不能为空");
        }
        if (system.getSystemType() == null
                || (system.getSystemType() != STANDARD_SYSTEM && system.getSystemType() != PROJECT_SYSTEM)) {
            throw new JPMException("体系类型只能是标准模板或项目自定义");
        }
        if (system.getSystemType() == PROJECT_SYSTEM && StrUtil.isBlank(system.getPId())) {
            throw new JPMException("项目自定义体系必须关联项目");
        }
        long count = count(new LambdaQueryWrapper<SafeResponsibilitySystem>()
                .eq(SafeResponsibilitySystem::getSystemName, system.getSystemName())
                .eq(SafeResponsibilitySystem::getSystemType, system.getSystemType())
                .eq(system.getSystemType() == PROJECT_SYSTEM, SafeResponsibilitySystem::getPId, system.getPId())
                .eq(system.getSystemType() == PROJECT_SYSTEM && StrUtil.isNotBlank(system.getConId()),
                        SafeResponsibilitySystem::getConId, system.getConId())
                .ne(StrUtil.isNotBlank(system.getId()), SafeResponsibilitySystem::getId, system.getId()));
        if (count > 0) {
            throw new JPMException("同一范围内体系名称不能重复");
        }
    }

    /**
     * 查询体系，不存在时抛出业务异常。
     */
    private SafeResponsibilitySystem requireSystem(String systemId) {
        SafeResponsibilitySystem system = getById(systemId);
        if (system == null) {
            throw new JPMException("安全责任体系不存在");
        }
        return system;
    }

    /**
     * 将体系实体转换为DTO。
     */
    private SafeResponsibilitySystemDTO toDTO(SafeResponsibilitySystem entity) {
        SafeResponsibilitySystemDTO dto = new SafeResponsibilitySystemDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    /**
     * 将体系DTO转换为实体。
     */
    private SafeResponsibilitySystem toEntity(SafeResponsibilitySystemDTO dto) {
        SafeResponsibilitySystem entity = new SafeResponsibilitySystem();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }
}
