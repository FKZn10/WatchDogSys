package com.jpmsoft.projectProducer.service.fund;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.fund.PaymentReceiptDto;
import com.jpmsoft.projectProducer.pojo.entity.fund.PaymentReceipt;
import com.jpmsoft.projectProducer.pojo.request.fund.PaymentReceiptRequest;

import java.util.List;

public interface IPaymentReceiptService extends IService<PaymentReceipt> {
    BaseResponse createOrUpdatePaymentReceipt(PaymentReceiptDto request);
    BaseResponse getPaymentReceiptById(String id);
    BaseResponse getPaymentReceiptList(PaymentReceiptRequest request,String uId);

    BaseResponse approvePaymentReceipt(PaymentReceiptRequest request);
    /**
     * 核销
     * @param request
     * @return
     */
    BaseResponse setPaymentReceiptVerify(PaymentReceiptRequest request,String token);

    BaseResponse setPaymentReceiptVerifys(List<PaymentReceiptRequest> requests, String token);

    /**
     * 开具
     * @param request
     * @return
     */
    BaseResponse setPaymentReceiptIssued(PaymentReceiptRequest request);
    /**
     * 回收
     * @param request
     * @return
     */
    BaseResponse setPaymentReceiptRecovery(PaymentReceiptRequest request);

    BaseResponse getFundPaymentReceiptList(PaymentReceiptRequest request,String uid);

    /**
     * 同步到来款认领
     *
     * @param id
     * @param token
     * @return
     */
    BaseResponse syncToClaimIncomingFunds(String id, String token);
}
