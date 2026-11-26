package com.jpmsoft.projectProducer.service.sporadicSettlement;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Maintenance.SporadicPaymentsRequest;
import com.jpmsoft.projectProducer.pojo.entity.SporadicSetttlement.SporadicPayments;

import java.util.List;

public interface ISporadicPaymentsService extends IService<SporadicPayments> {

    BaseResponse getSporadicPaymentsList(SporadicPaymentsRequest request,String uId);

    BaseResponse checkSporadicPaymentsByMfCode(List<String> sporadicSettlementIds);

    BaseResponse setModifymrRepairState(String id, String mrRepairState);

    BaseResponse getSporadicPaymentsBySeId(String seId);
}
