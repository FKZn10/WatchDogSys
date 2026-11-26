package com.jpmsoft.projectProducer.service.contract;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.requestAndResponse.Contract.ConContractMoneyDtlModel;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConContractMoneyDtl;

import java.util.List;

public interface IConContractMoneyDtlService extends IService<ConContractMoneyDtl> {

    void createOrUpdateConContractMoneyDtl(List<ConContractMoneyDtlModel> datas, String conId);

    void deleteAllConContractMoneyDtlByConId(String conId);

    List<ConContractMoneyDtlModel> getConContractMoneyDtlByConIds(List<String> conIds);

    List<ConContractMoneyDtlModel> getConContractMoneyDtlByConId(String conId);

    List<ConContractMoneyDtlModel> assembleContractMoneyDtlTree(List<ConContractMoneyDtlModel> datas);
}
