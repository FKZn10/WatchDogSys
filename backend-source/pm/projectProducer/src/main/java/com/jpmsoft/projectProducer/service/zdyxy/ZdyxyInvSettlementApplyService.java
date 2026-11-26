package com.jpmsoft.projectProducer.service.zdyxy;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.zdyxy.InvSettlementApplyModel;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvSettlementApply;

public interface ZdyxyInvSettlementApplyService extends IService<InvSettlementApply> {
    BaseResponse saveOrUpdateData(InvSettlementApplyModel request);

    BaseResponse approvalData(InvSettlementApplyModel request);

    BaseResponse getList(InvSettlementApplyModel request, String token);

    BaseResponse getDetail(String saId);
}