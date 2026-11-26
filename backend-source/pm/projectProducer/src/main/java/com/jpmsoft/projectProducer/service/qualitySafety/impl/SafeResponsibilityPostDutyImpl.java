package com.jpmsoft.projectProducer.service.qualitySafety.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.model.requestAndResponse.SafeResponsibility.SafeResponsibilityPostDutyDTO;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.projectProducer.mapper.qualitySafety.SafeResponsibilityPostDutyMapper;
import com.jpmsoft.projectProducer.pojo.entity.qualitySafety.SafeResponsibilityPostDuty;
import com.jpmsoft.projectProducer.service.qualitySafety.ISafeResponsibilityChangeLogService;
import com.jpmsoft.projectProducer.service.qualitySafety.ISafeResponsibilityPostDutyService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 安全责任岗位职责明细服务实现。
 */
@Service
public class SafeResponsibilityPostDutyImpl extends ServiceImpl<SafeResponsibilityPostDutyMapper, SafeResponsibilityPostDuty> implements ISafeResponsibilityPostDutyService {
    @Resource
    private ISafeResponsibilityChangeLogService changeLogService;

    /**
     * 新增或修改一条岗位职责并记录变更台账。
     *
     * @param request 岗位职责数据
     * @param uid     操作人ID
     * @param uname   操作人姓名
     * @return 职责ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateSafeResponsibilityPostDuty(SafeResponsibilityPostDutyDTO request,
                                                                 String uid, String uname) {
        if (request == null || StrUtil.isBlank(request.getPostId()) || StrUtil.isBlank(request.getDutyContent())) {
            throw new JPMException("岗位ID和职责内容不能为空");
        }
        Date now = new Date();
        SafeResponsibilityPostDuty before = null;
        String operationType = "ADD";
        if (StrUtil.isBlank(request.getId())) {
            request.setId(GenerateUUID.getUUID());
            request.setCreateUid(uid);
            request.setCreateUname(uname);
            request.setCreateTime(now);
            request.setSortNo(request.getSortNo() == null ? nextSortNo(request.getPostId()) : request.getSortNo());
        } else {
            before = requireDuty(request.getId());
            operationType = "UPDATE";
            request.setPostId(before.getPostId());
            request.setCreateUid(before.getCreateUid());
            request.setCreateUname(before.getCreateUname());
            request.setCreateTime(before.getCreateTime());
        }
        request.setUpdateUid(uid);
        request.setUpdateUname(uname);
        request.setUpdateTime(now);
        SafeResponsibilityPostDuty entity = toEntity(request);
        if (before == null) {
           save(entity);
        } else {
            updateById(entity);
        }
        changeLogService.saveChangeLog(null, "DUTY", request.getId(), request.getDutyContent(), operationType,
                before, request, uid, uname, "维护安全责任岗位职责", null);
        return BaseResponse.ok(entity.getId());
    }

    /**
     * 按请求字段筛选岗位职责列表。
     *
     * @param request 筛选条件
     * @return 岗位职责列表
     */
    @Override
    public BaseResponse getSafeResponsibilityPostDutyList(SafeResponsibilityPostDutyDTO request) {
        SafeResponsibilityPostDutyDTO condition = request == null ? new SafeResponsibilityPostDutyDTO() : request;
        LambdaQueryWrapper<SafeResponsibilityPostDuty> wrapper = new LambdaQueryWrapper<SafeResponsibilityPostDuty>()
                .eq(StrUtil.isNotBlank(condition.getId()), SafeResponsibilityPostDuty::getId, condition.getId())
                .eq(StrUtil.isNotBlank(condition.getPostId()), SafeResponsibilityPostDuty::getPostId, condition.getPostId())
                .like(StrUtil.isNotBlank(condition.getDutyContent()), SafeResponsibilityPostDuty::getDutyContent,
                        condition.getDutyContent())
                .orderByAsc(SafeResponsibilityPostDuty::getSortNo)
                .orderByAsc(SafeResponsibilityPostDuty::getCreateTime);
        return BaseResponse.ok(list(wrapper).stream().map(this::toDTO).collect(Collectors.toList()));
    }

    /**
     * 删除一条岗位职责并记录变更台账。
     *
     * @param request 包含职责ID的请求
     * @param uid     操作人ID
     * @param uname   操作人姓名
     * @return 操作结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse deleteSafeResponsibilityPostDuty(SafeResponsibilityPostDutyDTO request,
                                                         String uid, String uname) {
        if (request == null || StrUtil.isBlank(request.getId())) {
            throw new JPMException("岗位职责ID不能为空");
        }
        SafeResponsibilityPostDuty before = requireDuty(request.getId());
        removeById(before.getId());
        changeLogService.saveChangeLog(null, "DUTY", before.getId(), before.getDutyContent(), "DELETE",
                before, null, uid, uname, "删除安全责任岗位职责", null);
        return BaseResponse.ok();
    }

    /**
     * 查询指定岗位的全部职责明细。
     *
     * @param postId 岗位ID
     * @return 职责列表
     */
    @Override
    public List<SafeResponsibilityPostDutyDTO> listByPostId(String postId) {
        return list(new LambdaQueryWrapper<SafeResponsibilityPostDuty>()
                        .eq(SafeResponsibilityPostDuty::getPostId, postId)
                        .orderByAsc(SafeResponsibilityPostDuty::getSortNo)
                        .orderByAsc(SafeResponsibilityPostDuty::getCreateTime)).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * 用请求中的职责集合整体替换岗位现有职责。
     *
     * @param systemId 体系ID
     * @param postId   岗位ID
     * @param duties   新职责集合
     * @param uid      操作人ID
     * @param uname    操作人姓名
     * @param now      操作时间
     */
    @Override
    public void replacePostDuties(String systemId, String postId, List<SafeResponsibilityPostDutyDTO> duties,
                                  String uid, String uname, Date now) {
        deleteByPostId(postId);
        if (CollectionUtil.isEmpty(duties)) {
            return;
        }
        int sortNo = 1;
        for (SafeResponsibilityPostDutyDTO duty : duties) {
            if (duty == null || StrUtil.isBlank(duty.getDutyContent())) {
                continue;
            }
            duty.setId(GenerateUUID.getUUID());
            duty.setPostId(postId);
            duty.setSortNo(duty.getSortNo() == null ? sortNo : duty.getSortNo());
            duty.setCreateUid(uid);
            duty.setCreateUname(uname);
            duty.setCreateTime(now);
            duty.setUpdateUid(uid);
            duty.setUpdateUname(uname);
            duty.setUpdateTime(now);
            save(toEntity(duty));
            sortNo++;
        }
    }

    /**
     * 删除指定岗位的全部职责。
     *
     * @param postId 岗位ID
     */
    @Override
    public void deleteByPostId(String postId) {
        remove(new LambdaQueryWrapper<SafeResponsibilityPostDuty>()
                .eq(SafeResponsibilityPostDuty::getPostId, postId));
    }

    /**
     * 查询职责，不存在时抛出业务异常。
     */
    private SafeResponsibilityPostDuty requireDuty(String id) {
        SafeResponsibilityPostDuty duty = getById(id);
        if (duty == null) {
            throw new JPMException("安全责任岗位职责不存在");
        }
        return duty;
    }

    /**
     * 计算岗位下一个排序号。
     */
    private int nextSortNo(String postId) {
        return list(new LambdaQueryWrapper<SafeResponsibilityPostDuty>()
                        .eq(SafeResponsibilityPostDuty::getPostId, postId)).stream()
                .map(SafeResponsibilityPostDuty::getSortNo)
                .filter(Objects::nonNull)
                .max(Integer::compareTo)
                .orElse(0) + 1;
    }

    /**
     * 将职责实体转换为DTO。
     */
    private SafeResponsibilityPostDutyDTO toDTO(SafeResponsibilityPostDuty entity) {
        SafeResponsibilityPostDutyDTO dto = new SafeResponsibilityPostDutyDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    /**
     * 将职责DTO转换为实体。
     */
    private SafeResponsibilityPostDuty toEntity(SafeResponsibilityPostDutyDTO dto) {
        SafeResponsibilityPostDuty entity = new SafeResponsibilityPostDuty();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }
}
