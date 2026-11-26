package com.jpmsoft.projectProducer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.SecInfo.SecBidEvaluationExpertRequest;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.secInfo.ISecBidEvaluationExpertService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/bee")
public class SecBidEvaluationExpertController {
    @Resource
    private ISecBidEvaluationExpertService secBidEvaluationExpertService;

    @TenantId
    @PostMapping("/createOrUpdateSecBidEvaluationExpert")
    public BaseResponse createOrUpdateSecBidEvaluationExpert(@RequestBody SecBidEvaluationExpertRequest request) {
        log.info("createOrUpdateSecBidEvaluationExpert");
        return secBidEvaluationExpertService.createOrUpdateSecBidEvaluationExpert(request);
    }

    @GetMapping("/getSecBidEvaluationExpertById")
    public BaseResponse getSecBidEvaluationExpertById(@RequestParam String beeId) {
        log.info("getSecBidEvaluationExpertById");
        return secBidEvaluationExpertService.getSecBidEvaluationExpertById(beeId);
    }

    @TenantId
    @PostMapping("/getSecBidEvaluationExpertList")
    public BaseResponse getSecBidEvaluationExpertList(@RequestBody SecBidEvaluationExpertRequest request) {
        log.info("getSecBidEvaluationExpertList");
        return secBidEvaluationExpertService.getSecBidEvaluationExpertList(request);
    }
}
