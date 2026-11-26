package com.jpmsoft.projectProducer.service.contract;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.requestAndResponse.Contract.ConPayeeDtlModel;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConPayeeDtl;

import java.util.List;


public interface IConPayeeDtlService extends IService<ConPayeeDtl> {

    void createOrUpdateConPayeeDtl(List<ConPayeeDtlModel> datas, String conId, String createUid, String createUname);

    List<ConPayeeDtlModel> getConPayeeDtlByConId(String conId);

    void deleteAllConPayeeDtlByConId(String conId);
}
