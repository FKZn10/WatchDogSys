package com.jpmsoft.projectProducer.service.contract;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConPayeeDtlRecord;


public interface IConPayeeDtlRecordService extends IService<ConPayeeDtlRecord> {
    void create(String conId, String recordId);
}
