package com.jpmsoft.projectProducer.service.contract;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.requestAndResponse.Contract.ConPayeeDtlModel;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConPayeeDtlChange;

import java.util.List;


public interface IConPayeeDtlChangeService extends IService<ConPayeeDtlChange> {

    void createOrUpdate(List<ConPayeeDtlModel> data, String changeId);

    void deleteByChangeId(String changeId);

    void approve2Sync(String changeId, String conId);
}
