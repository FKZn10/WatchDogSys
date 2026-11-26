package com.jpmsoft.projectProducer.service.contract;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.requestAndResponse.Contract.conConsortiumModel;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConConsortiumDtl;

import java.util.List;

public interface IConConsortiumDtlService extends IService<ConConsortiumDtl> {
    //新增修改合同乙方单位合同价分配信息
    void createOrUpdateConConsortiumDtl(List<conConsortiumModel> data, String conId);

    List<conConsortiumModel> slectConConsortiumDtlListByConId(String conId);
}
