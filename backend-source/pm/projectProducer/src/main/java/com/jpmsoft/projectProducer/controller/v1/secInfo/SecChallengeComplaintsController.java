package com.jpmsoft.projectProducer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.SecInfo.SecChallengeComplaintsRequest;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.secInfo.ISecChallengeComplaintsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/challenge")
public class SecChallengeComplaintsController {

    @Resource
    private ISecChallengeComplaintsService secChallengeComplaintsService;

    @TenantId
    @PostMapping("/createOrUpdateSecChallengeComplaints")
    public BaseResponse createOrUpdateSecChallengeComplaints(@RequestBody SecChallengeComplaintsRequest request) {
        log.info("createOrUpdateSecChallengeComplaints");
        return secChallengeComplaintsService.createOrUpdateSecChallengeComplaints(request);
    }

    @GetMapping("/getSecChallengeComplaintsById")
    public BaseResponse getSecChallengeComplaintsById(@RequestParam String sccId) {
        log.info("getSecChallengeComplaintsById");
        return secChallengeComplaintsService.getSecChallengeComplaintsById(sccId);
    }

    @TenantId
    @PostMapping("/getSecChallengeComplaintsList")
    public BaseResponse getSecChallengeComplaintsList(@RequestBody SecChallengeComplaintsRequest request) {
        log.info("getSecChallengeComplaintsList");
        return secChallengeComplaintsService.getSecChallengeComplaintsList(request);
    }

    @TenantId
    @PostMapping("/getSecChallengeComplaintsListOne2Many")
    public BaseResponse getSecChallengeComplaintsListOne2Many(@RequestBody SecChallengeComplaintsRequest request) {
        log.info("getSecChallengeComplaintsListOne2Many");
        return secChallengeComplaintsService.getSecChallengeComplaintsListOne2Many(request);
    }
}
