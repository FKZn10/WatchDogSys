package com.jpmsoft.projectProducer.service.zdyxy;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.zdyxy.InvSettlementPayModel;
import com.jpmsoft.projectProducer.pojo.entity.zdyxy.InvSettlementPay;

public interface InvSettlementPayService extends IService<InvSettlementPay> {
    BaseResponse saveOrUpdateData(InvSettlementPayModel model);

    BaseResponse approvalData(InvSettlementPayModel request);

    BaseResponse getList(InvSettlementPayModel request, String token);

    BaseResponse getDetail(String id);
}