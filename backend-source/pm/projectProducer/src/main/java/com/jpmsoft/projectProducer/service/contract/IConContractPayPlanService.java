package com.jpmsoft.projectProducer.service.contract;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Contract.ConContractPayPlanModel;
import com.jpmsoft.projectProducer.pojo.dto.contract.ConContractPayPlanDto;
import com.jpmsoft.projectProducer.pojo.dto.fundPlan.ProgressDeclarationDtlDTO;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConContractPayPlan;

import java.util.List;

public interface IConContractPayPlanService extends IService<ConContractPayPlan> {


    void createOrUpdateConContractPayPlan(List<ConContractPayPlanModel> datas, String conId);

    void deleteAllConContractPayPlanByConId(String conId);

    List<ConContractPayPlanModel> getConContractPayPlanByConIds(List<String> conIds);

    List<ConContractPayPlanModel> getConContractPayPlanByConId(String conId);

    /**
     * 进度申报本次申报金额汇算
     *
     * @param request
     * @return
     */
    BaseResponse  ProgressDeclarationCompute(ProgressDeclarationDtlDTO request);


    List<ConContractPayPlan> getConPaymentPlan(String conId);

    List<ConContractPayPlan> getConPaymentPlanList(List<String> ids);

    List<ConContractPayPlan> getConPaymentPlanConIds(List<String> conIds);

    ConContractPayPlan getConPaymentPlanConIdNode(String conId, Integer pddThisNode);

    // 新增更新：更新合同支付计划
    BaseResponse updateConPayPlan(ConContractPayPlanDto request);
}
