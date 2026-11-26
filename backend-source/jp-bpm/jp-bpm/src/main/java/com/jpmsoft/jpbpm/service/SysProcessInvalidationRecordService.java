package com.jpmsoft.jpbpm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.jpbpm.pojo.entity.SysProcessInvalidationRecord;
import com.jpmsoft.jpbpm.pojo.dto.SysProcessInvalidationRecordDto;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface SysProcessInvalidationRecordService extends IService<SysProcessInvalidationRecord> {

    List<SysProcessInvalidationRecord> getSysProcessInvalidationRecordList(SysProcessInvalidationRecordDto request);

    Integer insertSysProcessInvalidationRecord(SysProcessInvalidationRecordDto request);



}
