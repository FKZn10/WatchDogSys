package com.jpmsoft.projectProducer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecProvisionalEstimateModel;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.secInfo.ISecProvisionalEstimateService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/provisionalEstimate")
public class SecProvisionalEstimateController {

    @Resource
    private ISecProvisionalEstimateService secProvisionalEstimateService;
    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createOrUpdateSecProvisionalEstimate")
    public BaseResponse createOrUpdateSecProvisionalEstimate(@RequestBody SecProvisionalEstimateModel request) {
        log.info("createOrUpdateSecProvisionalEstimate");
        return secProvisionalEstimateService.createOrUpdateSecProvisionalEstimate(request);
    }

    @TenantId
    @PostMapping("/getSecProvisionalEstimateModelList")
    public BaseResponse getSecProvisionalEstimateModelList(@RequestBody SecProvisionalEstimateModel request, @RequestHeader String token) {
        return secProvisionalEstimateService.getSecProvisionalEstimateModelList(request, redisUtils.getUid(token));
    }

    @GetMapping("/getSecProvisionalEstimateByPeId")
    public BaseResponse getSecProvisionalEstimateByPeId(@RequestParam String peId) {
        log.info("getSecProvisionalEstimateByPeId");
        return secProvisionalEstimateService.getSecProvisionalEstimateByPeId(peId);
    }
}
