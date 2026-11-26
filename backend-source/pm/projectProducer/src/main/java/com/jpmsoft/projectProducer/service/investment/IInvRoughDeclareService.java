package com.jpmsoft.projectProducer.service.investment;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.InvestmentControl.InvRoughDeclareRequest;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvRoughDeclare;

import java.math.BigDecimal;
import java.util.List;

public interface IInvRoughDeclareService extends IService<InvRoughDeclare> {

    BaseResponse createOrUpdateInvRoughDeclare(InvRoughDeclareRequest request);

    BaseResponse getInvRoughDeclareList(InvRoughDeclareRequest request, String uId);

    BaseResponse getInvRoughDeclareByRdId(String rdId);

    BaseResponse deleteInvRoughDeclareByRdId(String rdId);

    BaseResponse getInvRoughDeclareDtlByPIds(List<String> pIds);

    BaseResponse getInvRoughDeclareByPId(String pId);

    BaseResponse getInvRoughDeclareAndDtlByPid(String pId);
}
