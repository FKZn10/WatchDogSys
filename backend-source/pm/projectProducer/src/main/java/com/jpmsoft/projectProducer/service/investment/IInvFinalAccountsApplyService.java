package com.jpmsoft.projectProducer.service.investment;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.InvestmentControl.InvFinalAccountsApplyRequest;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvFinalAccountsApply;

import java.math.BigDecimal;

public interface IInvFinalAccountsApplyService extends IService<InvFinalAccountsApply> {

    BaseResponse createOrUpdateInvFinalAccountsApply(InvFinalAccountsApplyRequest request);

    BaseResponse getInvFinalAccountsApplyList(InvFinalAccountsApplyRequest request, String uId);

    BaseResponse getInvFinalAccountsApplyByfaId(String faId);

    BaseResponse getInvFinalAccountsApplyAndDtlByPid(String pId);

    BaseResponse deleteInvFinalAccountsApplyByfaId(String faId);


    /**
     * 校验项目ID是否已有决算
     *
     * @param pId
     * @return
     */
    boolean havingFinalApplyWithPid(String pId);


    BigDecimal  selectContractApprovedAmt(String conId);
}
