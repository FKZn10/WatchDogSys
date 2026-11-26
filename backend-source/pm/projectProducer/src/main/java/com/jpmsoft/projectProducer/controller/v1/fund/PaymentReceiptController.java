package com.jpmsoft.projectProducer.controller.v1.fund;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.fund.PaymentReceiptDto;
import com.jpmsoft.projectProducer.pojo.request.fund.PaymentReceiptRequest;
import com.jpmsoft.projectProducer.service.fund.IPaymentReceiptService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/paymentReceipt")
public class PaymentReceiptController {
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private IPaymentReceiptService paymentReceiptService;

    @PostMapping("/createOrUpdatePaymentReceipt")
    public BaseResponse createOrUpdatePaymentReceipt(@RequestBody PaymentReceiptDto request) {
        log.info("createOrUpdatePaymentReceipt");
        return paymentReceiptService.createOrUpdatePaymentReceipt(request);
    }
    @PostMapping("/getPaymentReceiptList")
    public BaseResponse getPaymentReceiptList(@RequestBody PaymentReceiptRequest request, @RequestHeader String token) {
        log.info("getPaymentReceiptList");
        return paymentReceiptService.getPaymentReceiptList(request,redisUtils.getUid(token));
    }
    @GetMapping("/getPaymentReceiptById")
    public BaseResponse getPaymentReceiptById(@RequestParam String id) {
        log.info("getPaymentReceiptById");
        return paymentReceiptService.getPaymentReceiptById(id);
    }

    /**
     * 流程处理接口
     * @param request
     * @return
     */
    @PostMapping("/approvePaymentReceipt")
    public BaseResponse approvePaymentReceipt(@RequestBody PaymentReceiptRequest request) {
        log.info("approvePaymentReceipt");
        return paymentReceiptService.approvePaymentReceipt(request);
    }
    /**
     * 核销
     * @param request
     * @return
     */
    @PostMapping("/setPaymentReceiptVerify")
    public BaseResponse setPaymentReceiptVerify(@RequestBody PaymentReceiptRequest request, @RequestHeader String token) {
        log.info("setPaymentReceiptVerify");
        return paymentReceiptService.setPaymentReceiptVerify(request,token);
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
        return paymentReceiptService.setPaymentReceiptVerifys(requests, token);
    }

    /**
     * 开具
     * @param request
     * @return
     */
    @PostMapping("/setPaymentReceiptIssued")
    public BaseResponse setPaymentReceiptIssued(@RequestBody PaymentReceiptRequest request) {
        log.info("setPaymentReceiptIssued");
        return paymentReceiptService.setPaymentReceiptIssued(request);
    }

     /**
     * 回收
     * @param request
     * @return
     */
    @PostMapping("/setPaymentReceiptRecovery")
    public BaseResponse setPaymentReceiptRecovery(@RequestBody PaymentReceiptRequest request) {
        log.info("setPaymentReceiptRecovery");
        return paymentReceiptService.setPaymentReceiptRecovery(request);
    }

    @PostMapping("/getFundPaymentReceiptList")
    public BaseResponse getFundPaymentReceiptList(@RequestBody PaymentReceiptRequest request, @RequestHeader String token) {
        log.info("getFundPaymentReceiptList");
        return paymentReceiptService.getFundPaymentReceiptList(request,redisUtils.getUid(token));
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
        return paymentReceiptService.syncToClaimIncomingFunds(id, token);
    }
}
