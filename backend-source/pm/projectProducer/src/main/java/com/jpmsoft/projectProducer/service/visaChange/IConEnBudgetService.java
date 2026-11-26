package com.jpmsoft.projectProducer.service.visaChange;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.DesignChange.ConEnBudgetRequest;
import com.jpmsoft.projectProducer.pojo.entity.visaChange.ConEnBudget;

public interface IConEnBudgetService extends IService<ConEnBudget> {


    BaseResponse createOrUpdateConEnBudget(ConEnBudgetRequest request);

    BaseResponse getConEnBudgetList(ConEnBudgetRequest request, String uId);

    BaseResponse getConEnBudgetById(String enbId);

    BaseResponse getNotContainAmtByConId(String conId);
}
