package com.jpmsoft.projectProducer.service.investment;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.InvestmentControl.InvFundReleaseRequest;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvFundRelease;

public interface IInvFundReleaseService extends IService<InvFundRelease> {

    BaseResponse createOrUpdateInvFundRelease(InvFundReleaseRequest request);

    BaseResponse getInvFundReleaseByIfrId(String ifrId);

    BaseResponse getInvFundReleaseList(InvFundReleaseRequest request);
}
