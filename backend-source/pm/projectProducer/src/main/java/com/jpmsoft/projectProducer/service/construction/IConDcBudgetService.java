package com.jpmsoft.projectProducer.service.construction;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.DesignChange.ConDcBudgetRequest;
import com.jpmsoft.projectProducer.pojo.entity.construction.ConDcBudget;

public interface IConDcBudgetService extends IService<ConDcBudget> {

    BaseResponse createOrUpdateConDcBudget(ConDcBudgetRequest request);

    BaseResponse getConDcBudgetList(ConDcBudgetRequest request, String uId);

    BaseResponse getConDcBudgetByDcbId(String dcbId);

    BaseResponse getNotContainAmtByConId(String conId);
}
