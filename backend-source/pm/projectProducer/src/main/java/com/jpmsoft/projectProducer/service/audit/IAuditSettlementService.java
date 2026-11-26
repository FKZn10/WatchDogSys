package com.jpmsoft.projectProducer.service.audit;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Audit.AuditSettlementModel;
import com.jpmsoft.projectProducer.pojo.entity.audit.AuditSettlement;

import java.math.BigDecimal;

public interface IAuditSettlementService extends IService<AuditSettlement> {

    BaseResponse createOrUpdateAuditSettlement(AuditSettlementModel request);

    BaseResponse getAuditSettlementList(AuditSettlementModel request, String uId);

    BaseResponse getAuditSettlementById(String asId);

    BaseResponse getApproveAuditSettlementByConId(String conId);

    BigDecimal selectAuditedAmountByCon(String conId);

    AuditSettlement selectSettlementById(String id);
}
