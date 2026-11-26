package com.jpmsoft.projectProducer.service.investment.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.projectProducer.mapper.investment.OperationRecordMapper;
import com.jpmsoft.projectProducer.pojo.entity.investment.OperationRecord;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import com.jpmsoft.projectProducer.service.investment.IOperationRecordService;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class OperationRecordImpl extends ServiceImpl<OperationRecordMapper, OperationRecord> implements IOperationRecordService {

    @Override
    public void createRecord(String entityId, LogUserInfoVo user, String orType, String oldStatus, String newStatus) {
        OperationRecord entity = new OperationRecord();
        entity.setId(GenerateUUID.getUUID());
        entity.setOrOperator(user.getId());
        entity.setOrOperatorName(user.getName());
        entity.setRecordId(entityId);
        entity.setOrType(orType);
        entity.setOrStatusOld(oldStatus);
        entity.setOrStatusNow(newStatus);
        entity.setOrTime(new Date());
        baseMapper.insert(entity);
    }

    @Override
    public OperationRecord entity(String entityId,  LogUserInfoVo user, String orType, String oldStatus, String newStatus,String orRemarks) {
        OperationRecord entity = new OperationRecord();
        entity.setId(GenerateUUID.getUUID());
        entity.setOrOperator(user.getId());
        entity.setOrOperatorName(user.getName());
        entity.setRecordId(entityId);
        entity.setOrType(orType);
        entity.setOrStatusOld(oldStatus);
        entity.setOrStatusNow(newStatus);
        entity.setOrRemarks(orRemarks);
        entity.setOrTime(new Date());
        return entity;
    }

    @Override
    public BaseResponse getOperationRecordList(String entityId) {
        return BaseResponse.ok(baseMapper.getOperationRecordList(entityId));
    }
}
