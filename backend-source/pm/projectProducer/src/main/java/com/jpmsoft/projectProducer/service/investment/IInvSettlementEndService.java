package com.jpmsoft.projectProducer.service.investment;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.InvestmentControl.InvSettlementEndDTO;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvSettlementEnd;

import java.util.List;

public interface IInvSettlementEndService extends IService<InvSettlementEnd> {

    BaseResponse createOrUpdateInvSettlementEnd(InvSettlementEndDTO request);

    BaseResponse updateInvSettlementEndOnlyInfo(InvSettlementEndDTO request);

    BaseResponse getInvSettlementEndList(InvSettlementEndDTO request, String uId);

    BaseResponse getInvSettlementEndNoAuthList(InvSettlementEndDTO request, String uId);

    BaseResponse refreshSettlementEndOtherTableInfo();

    BaseResponse getInvSettlementEndBySaeId(String saeId);

    BaseResponse deleteInvSettlementEndBySaeId(String saeId);

    /**
     * 合同ID查询最新一条审批通过的结算
     *
     * @param conId
     * @return
     */
    BaseResponse getLastApprovalInvSettlementEndByConId(String conId);

    BaseResponse getLastApprovalInvSettlementEndByPayId(String id);

    List<InvSettlementEnd> getInvConIdList(List<String> conIds);
}
