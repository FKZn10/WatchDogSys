package com.jpmsoft.projectProducer.service.investment;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.SettlementFirstAuditChangeLogModel;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.SettlementFirstAuditModel;
import com.jpmsoft.projectProducer.pojo.entity.investment.SettlementFirstAudit;
import com.jpmsoft.projectProducer.pojo.request.investment.SettlementFirstAuditRequest;

import java.util.Map;

public interface ISettlementFirstAuditService extends IService<SettlementFirstAudit> {
    BaseResponse saveOrUpdateSettlementFirstAudit(SettlementFirstAuditModel request, String token);

    BaseResponse getSettlementFirstAuditList(SettlementFirstAuditRequest request);

    BaseResponse getSettlementFirstAuditId(String id);

    BaseResponse getSettlementFirstAuditClassInfo(SettlementFirstAuditRequest request);

    BaseResponse saveOrUpdateSettlementFirstAuditChangeLog(SettlementFirstAuditChangeLogModel request, String uid);

    BaseResponse getSettlementFirstAuditChangeLogList(SettlementFirstAuditModel request);

    BaseResponse approveChangeLogData(SettlementFirstAuditChangeLogModel request);

    BaseResponse loadContractInfo(SettlementFirstAuditModel request);

    BaseResponse getSettlementFirstAuditChangeLogId(String id);

    BaseResponse deleteSettlementFirstAudit(SettlementFirstAuditModel request);

    Map<String,Object> getConReviewAmount(String conId);
}
