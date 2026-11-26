package com.jpmsoft.projectProducer.service.investment;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.investment.InvBudgetDeclareAddDTO;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvBudgetDeclareAdd;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/3/29 15:14
 */
public interface IInvBudgetDeclareAddService extends IService<InvBudgetDeclareAdd> {

    BaseResponse createOrUpdate(InvBudgetDeclareAddDTO request);

    BaseResponse getInvBudgetDeclareAddList(InvBudgetDeclareAddDTO request,String uid);

    BaseResponse getInvBudgetDeclareAddById(String id);

    BaseResponse approve2AddProBudget(String id);
}
