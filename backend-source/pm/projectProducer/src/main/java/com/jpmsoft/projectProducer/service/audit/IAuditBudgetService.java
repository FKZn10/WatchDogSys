package com.jpmsoft.projectProducer.service.audit;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Audit.AuditBudgetModel;
import com.jpmsoft.projectProducer.pojo.entity.audit.AuditBudget;

public interface IAuditBudgetService extends IService<AuditBudget> {

    BaseResponse createOrUpdateAuditBudget(AuditBudgetModel request);

    BaseResponse getAuditBudgetList(AuditBudgetModel request, String uId);

    BaseResponse getAuditBudgetById(String abId);

    BaseResponse getAuditBudgetBySecId(String secId);

}
