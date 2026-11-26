package com.jpmsoft.projectProducer.service.contract;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConContractRecord;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/9/18 13:44
 */
public interface IConContractRecordService extends IService<ConContractRecord> {

    void createConContractRecord(String conId, String changeId);
}
