package com.jpmsoft.projectConsumer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.SecInfo.SecControlPricePleaseReviewRequest;
import com.jpmsoft.projectConsumer.feign.SecFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



/**
 * 控制价请审
 *
 */
@RestController
@RequestMapping("v1/controlPricePleaseReview")
@Slf4j
public class SecControlPricePleaseReviewController {

    @Autowired
    private SecFeign secFeign;


    @PostMapping("/createOrUpdateControlPriceReview")
    public BaseResponse createOrUpdateControlPriceReview(@RequestBody SecControlPricePleaseReviewRequest request) {
        log.info("createOrUpdateControlPriceReview");
        return secFeign.createOrUpdateControlPriceReview(request);
    }

    @GetMapping("/deleteControlPriceReviewById")
    public BaseResponse deleteControlPriceReviewById(@RequestParam String cpprId) {
        log.info("deleteControlPriceReviewById");
        return secFeign.deleteControlPriceReviewById(cpprId);
    }

    @GetMapping("/getControlPriceReviewById")
    public BaseResponse getControlPriceReviewById(@RequestParam String cpprId) {
        log.info("getControlPriceReviewById");
        return secFeign.getControlPriceReviewById(cpprId);
    }

    @PostMapping("/getControlPriceReviewList")
    public BaseResponse getControlPriceReviewList(@RequestBody SecControlPricePleaseReviewRequest request) {
        log.info("getControlPriceReviewList");
        return secFeign.getControlPriceReviewList(request);
    }


}

