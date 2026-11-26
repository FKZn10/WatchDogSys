package com.jpmsoft.projectProducer.service.contract;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConContractMoneyDtlRecord;

public interface IConContractMoneyDtlRecordService extends IService<ConContractMoneyDtlRecord> {

    void create(String conId,String recordId);
}
