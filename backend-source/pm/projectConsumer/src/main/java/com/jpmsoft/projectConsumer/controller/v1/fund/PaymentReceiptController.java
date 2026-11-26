package com.jpmsoft.projectConsumer.controller.v1.fund;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.projectConsumer.feign.FundFeign;
import com.jpmsoft.projectConsumer.pojo.dto.fund.PaymentReceiptDto;
import com.jpmsoft.projectConsumer.request.fund.PaymentReceiptRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/paymentReceipt")
public class PaymentReceiptController {
    @Autowired
    private FundFeign fundFeign;

    @PostMapping("/createOrUpdatePaymentReceipt")
    public BaseResponse createOrUpdatePaymentReceipt(@RequestBody PaymentReceiptDto request) {
        log.info("createOrUpdatePaymentReceipt");
        return fundFeign.createOrUpdatePaymentReceipt(request);
    }
    @PostMapping("/getPaymentReceiptList")
    public BaseResponse getPaymentReceiptList(@RequestBody PaymentReceiptRequest request) {
        log.info("getPaymentReceiptList");
        return fundFeign.getPaymentReceiptList(request);
    }


    @GetMapping("/getPaymentReceiptById")
    public BaseResponse getPaymentReceiptById(@RequestParam String id) {
        log.info("getPaymentReceiptById");
        return fundFeign.getPaymentReceiptById(id);
    }

    @PostMapping("/approvePaymentReceipt")
    public BaseResponse approvePaymentReceipt(@RequestBody PaymentReceiptRequest request) {
        log.info("approvePaymentReceipt");
        return fundFeign.approvePaymentReceipt(request);
    }
    /**
     * 核销
     * @param request
     * @return
     */
    @PostMapping("/setPaymentReceiptVerify")
    public BaseResponse setPaymentReceiptVerify(@RequestBody PaymentReceiptRequest request) {
        log.info("setPaymentReceiptVerify");
        return fundFeign.setPaymentReceiptVerify(request);
    }

    /**
     * 批量核销
     *
     * @param requests
     * @return
     */
    @PostMapping("/setPaymentReceiptVerifys")
    public BaseResponse setPaymentReceiptVerifys(@RequestBody List<PaymentReceiptRequest> requests, @RequestHeader String token) {
        log.info("setPaymentReceiptVerifys");
        return fundFeign.setPaymentReceiptVerifys(requests, token);
    }

    /**
     * 开具
     * @param request
     * @return
     */
    @PostMapping("/setPaymentReceiptIssued")
    public BaseResponse setPaymentReceiptIssued(@RequestBody PaymentReceiptRequest request) {
        log.info("setPaymentReceiptIssued");
        return fundFeign.setPaymentReceiptIssued(request);
    }

     /**
     * 回收
     * @param request
     * @return
     */
    @PostMapping("/setPaymentReceiptRecovery")
    public BaseResponse setPaymentReceiptRecovery(@RequestBody PaymentReceiptRequest request) {
        log.info("setPaymentReceiptRecovery");
        return fundFeign.setPaymentReceiptRecovery(request);
    }

    @PostMapping("/getFundPaymentReceiptList")
    public BaseResponse getFundPaymentReceiptList(@RequestBody PaymentReceiptRequest request) {
        log.info("getFundPaymentReceiptList");
        log.info("request:"+ JacksonUtils.toJSONString(request));
        return fundFeign.getFundPaymentReceiptList(request);
    }

    /**
     * 同步到来款认领
     *
     * @param id
     * @param token
     * @return
     */
    @GetMapping("/syncToClaimIncomingFunds")
    public BaseResponse syncToClaimIncomingFunds(@RequestParam String id, @RequestHeader String token) {
        log.info("syncToClaimIncomingFunds");
        return fundFeign.syncToClaimIncomingFunds(id, token);
    }
}
