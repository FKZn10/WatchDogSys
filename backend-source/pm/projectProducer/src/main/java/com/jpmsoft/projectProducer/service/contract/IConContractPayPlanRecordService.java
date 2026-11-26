package com.jpmsoft.projectProducer.service.contract;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConContractPayPlanRecord;

public interface IConContractPayPlanRecordService extends IService<ConContractPayPlanRecord> {

    void create(String conId, String recordId);
}
