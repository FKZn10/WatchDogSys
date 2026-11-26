package com.jpmsoft.projectProducer.service.investment;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.InvestmentControl.InvEstimateDeclareRequest;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvEstimateDeclare;

import java.util.List;

public interface IInvEstimateDeclareService extends IService<InvEstimateDeclare> {

    BaseResponse createOrUpdateInvEstimateDeclare(InvEstimateDeclareRequest request);

    BaseResponse getInvEstimateDeclareList(InvEstimateDeclareRequest request, String uId);

    BaseResponse getInvEstimateDeclareByEdId(String edId);

    BaseResponse deleteInvEstimateDeclareByEdId(String edId);

    BaseResponse getInvEstimateDeclareDtlByPIds(List<String> pIds);

    BaseResponse getInvEstimateDeclareByPId(String pId);

    BaseResponse getInvEstimateDeclareAndDtlByPid(String pId);
}
