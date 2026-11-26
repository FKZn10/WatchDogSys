package com.jpmsoft.projectProducer.service.contract;


import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.entity.contract.ContractPaymentPlanTemplate;
import com.jpmsoft.projectProducer.pojo.vo.contract.*;

import java.util.List;

public interface ContractPaymentPlanTemplateService extends IService<ContractPaymentPlanTemplate> {
    BaseResponse getContractTemplate(ContractResultVo contractResultVo);

    BaseResponse saveContractPaymentPlan(ContractResultVo contractResultVo);

    BaseResponse delContractTemplate(String id);

    BaseResponse updateContractTemplate(ContractResultVo contractResultVo);

    BaseResponse getContrcatDataList();

    BaseResponse getPaymentConditionType();

    BaseResponse getPaymentMethod();

    BaseResponse getPaymentBasis();

    BaseResponse getContrActDataList(ContractResultVo contractResultVo);

    BaseResponse saveContractPaymentPlanBatch(List<ContractResultVo> list);

    BaseResponse getPayableConDataList(ContractManageVo contractManageVo);

    BaseResponse updatePaymentState(ContractManageVo contractManageVo);

    BaseResponse getPaymentStateContr(String cId);

    BaseResponse getCompanyPaySum(ContractManageVo contractManageVo);

    BaseResponse getCapitalSituation(ContractManageVo contractManageVo);

    BaseResponse getProgressPlan(ProgressDeclarationVo progressDeclarationVo);

    BaseResponse contrBrowse(ContractBrowseVo contractBrowseVo);

    BaseResponse moneyDisbursement(ConPayDisbursementVo vo);

    BaseResponse saveMoneyDisbursement(ConPayDisbursementVo vo);
}
