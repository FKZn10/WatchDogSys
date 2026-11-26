package com.jpmsoft.projectConsumer.controller.v1.contract;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Contract.PaymentReviewRequest;
import com.jpmsoft.projectConsumer.feign.ConFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@Api(tags = "付款请审", value = "付款请审")
@RequestMapping("/v1/paymentReview")
public class PaymentReviewController {
    @Resource
    private ConFeign conFeign;

    @ApiOperation(value = "新增或修改付款请审")
    @PostMapping("/createOrUpdatePaymentReview")
    public BaseResponse createOrUpdatePaymentReview(@RequestBody PaymentReviewRequest request) {
        log.info("createOrUpdatePaymentReview");
        return conFeign.createOrUpdatePaymentReview(request);
    }

    @ApiOperation(value = "获取付款请审列表")
    @PostMapping("/getPaymentReviewList")
    public BaseResponse getPaymentReviewList(@RequestBody PaymentReviewRequest request) {
        log.info("getPaymentReviewList");
        return conFeign.getPaymentReviewList(request);
    }


    @ApiOperation(value = "ID查询付款请审详情")
    @GetMapping("/getPaymentReviewById")
    public BaseResponse getPaymentReviewById(@RequestParam String id) {
        log.info("getPaymentReviewById");
        return conFeign.getPaymentReviewById(id);
    }

    @ApiOperation(value = "删除付款请审")
    @GetMapping("/deletePaymentReviewById")
    public BaseResponse deletePaymentReviewById(@RequestParam String id) {
        log.info("deletePaymentReviewById");
        return conFeign.deletePaymentReviewById(id);
    }
}
