package com.jpmsoft.projectProducer.service.contract;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.entity.contract.ContractPaymentPlanTemplateDl;
import com.jpmsoft.projectProducer.pojo.vo.contract.ContractResultVo;

import java.util.List;

public interface ContractPaymentPlanTemplateDlService extends IService<ContractPaymentPlanTemplateDl> {
    void delContractTemplateDl(String id);

    BaseResponse updateContractTemplateDl(ContractResultVo contractResultVo);

    BaseResponse delContractTemplateDlId(String id);

    BaseResponse saveContractPaymentPlanDlNode(ContractResultVo contractResultVo);

    List<ContractPaymentPlanTemplateDl> getContrActDataDl(List<String> collect);
}
