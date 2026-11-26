package com.jpmsoft.projectProducer.service.investment;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.InvestmentControl.InvSettlementApplyRequest;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvSettlementApply;

public interface IInvSettlementApplyService extends IService<InvSettlementApply> {

    BaseResponse createOrUpdateInvSettlementApply(InvSettlementApplyRequest request);

    BaseResponse updateInvSettlementApplyOnlyInfo(InvSettlementApplyRequest request);

    BaseResponse getInvSettlementApplyList(InvSettlementApplyRequest request, String uId);

    BaseResponse getInvSettlementApplyBysaId(String saId);

    BaseResponse deleteInvSettlementApplyBysaId(String saId);

    /**
     * 合同ID查询最新一条审批通过的结算
     *
     * @param conId
     * @return
     */
    BaseResponse getLastApprovalInvSettlementApplyByConId(String conId);
}
