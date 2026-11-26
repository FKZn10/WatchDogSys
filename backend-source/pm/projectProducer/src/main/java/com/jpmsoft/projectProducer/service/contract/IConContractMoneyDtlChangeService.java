package com.jpmsoft.projectProducer.service.contract;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.requestAndResponse.Contract.ConContractMoneyDtlModel;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConContractMoneyDtlChange;

import java.util.List;

public interface IConContractMoneyDtlChangeService extends IService<ConContractMoneyDtlChange> {

    void createOrUpdate(List<ConContractMoneyDtlModel> data, String changeId);

    void deleteByChangeId(String changeId);

    void approve2Sync(String changeId, String conId);
}
