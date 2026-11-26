package com.jpmsoft.projectProducer.service.contract.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.projectProducer.mapper.contract.ConContractRecordMapper;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConContract;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConContractRecord;
import com.jpmsoft.projectProducer.service.contract.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/9/18 13:47
 */
@Service
public class ConContractRecordImpl extends ServiceImpl<ConContractRecordMapper, ConContractRecord> implements IConContractRecordService {

    @Resource
    private IConContractService contractService;
    @Resource
    private IConContractMoneyDtlRecordService conContractMoneyDtlRecordService;
    @Resource
    private IConContractPayPlanRecordService conContractPayPlanRecordService;
    @Resource
    private IConPayeeDtlRecordService conPayeeDtlRecordService;
    @Override
    public void createConContractRecord(String conId, String changeId) {
        ConContract con = contractService.getById(conId);
        ConContractRecord record = new ConContractRecord();
        BeanUtils.copyProperties(con, record);
        record.setConId(conId);
        record.setConChangeId(changeId);
        record.setId(GenerateUUID.getUUID());
        save(record);
        conContractMoneyDtlRecordService.create(conId,record.getId());
        conContractPayPlanRecordService.create(conId,record.getId());
        conPayeeDtlRecordService.create(conId,record.getId());
    }
}
