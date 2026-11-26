package com.jpmsoft.projectProducer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.SecInfo.SecControlPricePleaseReviewRequest;
import com.jpmsoft.common.model.response.SecInfo.SecControlPriceResponse;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.secInfo.ISecControlPricePleaseReviewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * 控制价请审
 *
 */
@RestController
@RequestMapping("v1/controlPricePleaseReview")
@Slf4j
public class SecControlPricePleaseReviewController {

    @Resource
    private ISecControlPricePleaseReviewService secControlPricePleaseReviewService;


    @TenantId
    @PostMapping("/createOrUpdateControlPriceReview")
    public BaseResponse createOrUpdateControlPriceReview(@RequestBody SecControlPricePleaseReviewRequest request) {
        log.info("createOrUpdateControlPriceReview");
        return secControlPricePleaseReviewService.createOrUpdateControlPriceReview(request);
    }

    @GetMapping("/deleteControlPriceReviewById")
    public BaseResponse deleteControlPriceReviewById(@RequestParam String cpprId) {
        log.info("deleteControlPriceReviewById");
        return secControlPricePleaseReviewService.deleteControlPriceReviewById(cpprId);
    }

    @GetMapping("/getControlPriceReviewById")
    public BaseResponse getControlPriceReviewById(@RequestParam String cpprId) {
        log.info("getControlPriceReviewById");
        return secControlPricePleaseReviewService.getControlPriceReviewById(cpprId);
    }

    @TenantId
    @PostMapping("/getControlPriceReviewList")
    public BaseResponse getControlPriceReviewList(@RequestBody SecControlPricePleaseReviewRequest request) {
        log.info("getControlPriceReviewList");
        return secControlPricePleaseReviewService.getControlPriceReviewList(request);
    }

    /**
     * 根据标段ID获取最新的招标控制价
     */
    @GetMapping("/getControlPriceReviewBysecId")
    public SecControlPriceResponse getControlPriceBysecId(@RequestParam String secId) {
        log.info("getControlPriceReviewBysecId");
        return secControlPricePleaseReviewService.getControlPriceReviewBysecId(secId);
    }

}

