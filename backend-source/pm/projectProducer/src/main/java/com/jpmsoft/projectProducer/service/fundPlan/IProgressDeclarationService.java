package com.jpmsoft.projectProducer.service.fundPlan;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.fundPlan.ProgressDeclarationDTO;
import com.jpmsoft.projectProducer.pojo.dto.fundPlan.ProgressDeclarationMatchDTO;
import com.jpmsoft.projectProducer.pojo.entity.fundPlan.ProgressDeclaration;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/9/24 20:01
 */
public interface IProgressDeclarationService extends IService<ProgressDeclaration> {

    BaseResponse createOrUpdateProgressDeclaration(ProgressDeclarationDTO request);

    BaseResponse getProgressDeclarationById(String id);

    BaseResponse getProgressDeclarationList(ProgressDeclarationDTO request);

    BaseResponse getPlanNodes(String conId);

    BaseResponse progressDeclarationMatch(ProgressDeclarationMatchDTO dto);

    BaseResponse getContractDeclarationList(ProgressDeclarationMatchDTO dto);

    BaseResponse progerGeneratePayment(ProgressDeclarationMatchDTO dto);

    BaseResponse updateContrCompletionData(ProgressDeclarationMatchDTO dto);

    BaseResponse delBalancePaymentById(String id,String conId);

    BaseResponse delInvFinalAccountsApplyById(String id,String conId);

    BaseResponse delPreliminaryAuditById(String id,String conId);

    BaseResponse delAuditSettlementById(String id,String conId);

    BaseResponse getAccordingThePlan(ProgressDeclarationMatchDTO dto);
}
