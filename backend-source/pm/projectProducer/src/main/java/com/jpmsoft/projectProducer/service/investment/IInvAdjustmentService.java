package com.jpmsoft.projectProducer.service.investment;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.InvestmentControl.InvAdjustmentRequest;
import com.jpmsoft.common.model.request.InvestmentControl.InvAdjustmentTemplateRequest;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvAdjustment;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvAdjustmentTemplate;

public interface IInvAdjustmentService extends IService<InvAdjustment> {

    BaseResponse createOrUpdateInvAdjustment(InvAdjustmentRequest request);

    BaseResponse getInvAdjustmentList(InvAdjustmentRequest request, String uId);

    BaseResponse getInvAdjustmentByIaId(String iaId);

    BaseResponse deleteInvAdjustmentByIaId(String iaId);
}
