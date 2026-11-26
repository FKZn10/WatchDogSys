package com.jpmsoft.projectProducer.controller.v1.performanceClaim;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Claim.PcClaimRequest;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.performanceClaim.IPcClaimService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/pcClaim")
public class PcClaimController {

    @Resource
    private IPcClaimService pcClaimService;

    @Resource
    private RedisUtils redisUtils;


    @TenantId
    @PostMapping("/createOrUpdatePcClaim")
    public BaseResponse createOrUpdatePcClaim(@RequestBody PcClaimRequest request) {
        log.info("createOrUpdatePcClaim");
        return pcClaimService.createOrUpdatePcClaim(request);
    }

    @TenantId
    @PostMapping("/getPcClaimList")
    public BaseResponse getPcClaimList(@RequestBody PcClaimRequest request, @RequestHeader String token) {
        log.info("getPcClaimList");
        return pcClaimService.getPcClaimList(request, redisUtils.getUid(token));
    }

    @GetMapping("/getPcClaimById")
    public BaseResponse getPcClaimById(@RequestParam String claimId) {
        log.info("getPcClaimById");
        return pcClaimService.getPcClaimById(claimId);
    }
}
