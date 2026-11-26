package com.jpmsoft.projectProducer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.SecInfo.SecContractSignPleaseReviewRequest;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.secInfo.ISecContractSignPleaseReviewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * 合同签订请审
 *
 */
@RestController
@RequestMapping("v1/contractSignPleaseReview")
@Slf4j
public class SecContractSignPleaseReviewController {

    @Resource
    private ISecContractSignPleaseReviewService secContractSignPleaseReviewService;


    @TenantId
    @PostMapping("/createOrUpdateContractSignPleaseReview")
    public BaseResponse createOrUpdateContractSignPleaseReview(@RequestBody SecContractSignPleaseReviewRequest request) {
        log.info("createOrUpdateContractSignPleaseReview");
        return secContractSignPleaseReviewService.createOrUpdateContractSignPleaseReview(request);
    }

    @GetMapping("/deleteContractSignPleaseReviewById")
    public BaseResponse deleteContractSignPleaseReviewById(@RequestParam String csprId) {
        log.info("deleteContractSignPleaseReviewById");
        return secContractSignPleaseReviewService.deleteContractSignPleaseReviewById(csprId);
    }

    @GetMapping("/getContractSignPleaseReviewById")
    public BaseResponse getContractSignPleaseReviewById(@RequestParam String csprId) {
        log.info("getContractSignPleaseReviewById");
        return secContractSignPleaseReviewService.getContractSignPleaseReviewById(csprId);
    }

    @TenantId
    @PostMapping("/getContractSignPleaseReviewList")
    public BaseResponse getContractSignPleaseReviewList(@RequestBody SecContractSignPleaseReviewRequest request) {
        log.info("getContractSignPleaseReviewList");
        return secContractSignPleaseReviewService.getContractSignPleaseReviewList(request);
    }


}

