package com.jpmsoft.projectProducer.service.contract;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.requestAndResponse.Contract.ConContractPayPlanModel;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConContractPayPlanChange;

import java.util.List;

public interface IConContractPayPlanChangeService extends IService<ConContractPayPlanChange> {

    void createOrUpdate(List<ConContractPayPlanModel> data, String changeId);

    void deleteByChangeId(String changeId);

    void approve2Sync(String changeId, String conId);
}
