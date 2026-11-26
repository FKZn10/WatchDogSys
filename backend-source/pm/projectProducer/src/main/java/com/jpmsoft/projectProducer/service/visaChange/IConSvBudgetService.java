package com.jpmsoft.projectProducer.service.visaChange;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.visaChange.ConSvBudgetDTO;
import com.jpmsoft.projectProducer.pojo.entity.visaChange.ConSvBudget;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/12/19 10:35
 */
public interface IConSvBudgetService extends IService<ConSvBudget> {

    BaseResponse createOrUpdateConSvBudget(ConSvBudgetDTO request);

    BaseResponse getConSvBudgetList(ConSvBudgetDTO request, String uId);

    BaseResponse getConSvBudgetById(String id);

    BaseResponse getNotContainAmtByConId(String conId);
}
