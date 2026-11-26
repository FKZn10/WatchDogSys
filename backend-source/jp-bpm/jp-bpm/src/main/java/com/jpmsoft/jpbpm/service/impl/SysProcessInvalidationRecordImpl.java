package com.jpmsoft.jpbpm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.jpbpm.mapper.SysProcessInvalidationRecordMapper;
import com.jpmsoft.jpbpm.pojo.dto.SysProcessInvalidationRecordDto;
import com.jpmsoft.jpbpm.pojo.entity.SysProcessInvalidationRecord;
import com.jpmsoft.jpbpm.service.SysProcessInvalidationRecordService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class SysProcessInvalidationRecordImpl extends ServiceImpl<SysProcessInvalidationRecordMapper, SysProcessInvalidationRecord> implements SysProcessInvalidationRecordService {
    @Override
    public List<SysProcessInvalidationRecord> getSysProcessInvalidationRecordList(SysProcessInvalidationRecordDto request) {
        QueryWrapper<SysProcessInvalidationRecord> queryWrapper = new QueryWrapper<>();
        if (ValidateUtil.isNotEmpty(request.getFileId())) {
            queryWrapper.eq("file_id", request.getFileId());
        }
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional
    public Integer insertSysProcessInvalidationRecord(SysProcessInvalidationRecordDto request) {
        SysProcessInvalidationRecord obj = new SysProcessInvalidationRecord();
        obj.setId(GenerateUUID.getUUID());
        obj.setFileId(request.getFileId());
        obj.setInvalidExplanation(request.getInvalidExplanation());
        obj.setOperator(request.getOperator());
        obj.setOperatorName(request.getOperatorName());
        obj.setOperatorTime(new Date());
        return baseMapper.insert(obj);
    }

}
