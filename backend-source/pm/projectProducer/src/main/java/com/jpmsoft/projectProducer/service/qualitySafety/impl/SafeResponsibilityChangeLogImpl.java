package com.jpmsoft.projectProducer.service.qualitySafety.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SafeResponsibility.SafeResponsibilityChangeLogDTO;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.projectProducer.mapper.qualitySafety.SafeResponsibilityChangeLogMapper;
import com.jpmsoft.projectProducer.pojo.entity.qualitySafety.SafeResponsibilityChangeLog;
import com.jpmsoft.projectProducer.service.qualitySafety.ISafeResponsibilityChangeLogService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 安全责任体系变更台账服务实现。
 */
@Service
public class SafeResponsibilityChangeLogImpl extends ServiceImpl<SafeResponsibilityChangeLogMapper, SafeResponsibilityChangeLog> implements ISafeResponsibilityChangeLogService {

    /**
     * 按台账请求字段筛选变更记录，并按操作时间倒序返回。
     *
     * @param request 台账筛选条件
     * @return 变更台账列表
     */
    @Override
    public BaseResponse getSafeResponsibilityChangeLogList(SafeResponsibilityChangeLogDTO request) {
        SafeResponsibilityChangeLogDTO condition = request == null ? new SafeResponsibilityChangeLogDTO() : request;
        LambdaQueryWrapper<SafeResponsibilityChangeLog> wrapper = new LambdaQueryWrapper<SafeResponsibilityChangeLog>()
                .eq(StrUtil.isNotBlank(condition.getId()), SafeResponsibilityChangeLog::getId, condition.getId())
                .eq(StrUtil.isNotBlank(condition.getSystemId()), SafeResponsibilityChangeLog::getSystemId, condition.getSystemId())
                .eq(StrUtil.isNotBlank(condition.getTargetType()), SafeResponsibilityChangeLog::getTargetType, condition.getTargetType())
                .eq(StrUtil.isNotBlank(condition.getTargetId()), SafeResponsibilityChangeLog::getTargetId, condition.getTargetId())
                .like(StrUtil.isNotBlank(condition.getTargetName()), SafeResponsibilityChangeLog::getTargetName, condition.getTargetName())
                .eq(StrUtil.isNotBlank(condition.getOperationType()), SafeResponsibilityChangeLog::getOperationType, condition.getOperationType())
                .eq(StrUtil.isNotBlank(condition.getOperatorUid()), SafeResponsibilityChangeLog::getOperatorUid, condition.getOperatorUid())
                .like(StrUtil.isNotBlank(condition.getOperatorUname()), SafeResponsibilityChangeLog::getOperatorUname, condition.getOperatorUname())
                .eq(StrUtil.isNotBlank(condition.getBatchNo()), SafeResponsibilityChangeLog::getBatchNo, condition.getBatchNo())
                .orderByDesc(SafeResponsibilityChangeLog::getOperationTime);
        List<SafeResponsibilityChangeLogDTO> result = list(wrapper).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        return BaseResponse.ok(result);
    }

    /**
     * 保存业务对象变更前后快照。
     *
     * @param systemId      体系ID
     * @param targetType    对象类型
     * @param targetId      对象ID
     * @param targetName    对象名称
     * @param operationType 操作类型
     * @param before        变更前对象
     * @param after         变更后对象
     * @param uid           操作人ID
     * @param uname         操作人姓名
     * @param description   变更摘要
     * @param batchNo       批次号
     */
    @Override
    public void saveChangeLog(String systemId, String targetType, String targetId, String targetName,
                              String operationType, Object before, Object after, String uid, String uname,
                              String description, String batchNo) {
        SafeResponsibilityChangeLog log = new SafeResponsibilityChangeLog();
        log.setId(GenerateUUID.getUUID());
        log.setSystemId(systemId);
        log.setTargetType(targetType);
        log.setTargetId(targetId);
        log.setTargetName(targetName);
        log.setOperationType(operationType);
        log.setBeforeContent(before == null ? null : JacksonUtils.toJSONString(before));
        log.setAfterContent(after == null ? null : JacksonUtils.toJSONString(after));
        log.setChangeDescription(description);
        log.setOperatorUid(uid);
        log.setOperatorUname(uname);
        log.setOperationTime(new Date());
        log.setBatchNo(batchNo);
        save(log);
    }

    /**
     * 将台账实体转换为DTO。
     */
    private SafeResponsibilityChangeLogDTO toDTO(SafeResponsibilityChangeLog entity) {
        SafeResponsibilityChangeLogDTO dto = new SafeResponsibilityChangeLogDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }
}
