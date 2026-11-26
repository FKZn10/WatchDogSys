package com.jpmsoft.projectProducer.service.investment;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.InvControlManageModel;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvControlManage;

public interface IInvControlManageService extends IService<InvControlManage> {

    BaseResponse createOrUpdateInvControlManage(InvControlManageModel request);

    BaseResponse getInvControlManageList(InvControlManageModel request, String uId);

    BaseResponse getInvControlManageById(String icmId);
}
