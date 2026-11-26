package com.jpmsoft.projectProducer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.SecInfo.SecDeterminResultRequest;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.secInfo.ISecDeterminResultService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/determinResult")
public class SecDeterminResultController {

    @Resource
    private ISecDeterminResultService secDeterminResultService;

    @TenantId
    @PostMapping("/createOrUpdateSecDeterminResult")
    public BaseResponse createOrUpdateSecDeterminResult(@RequestBody SecDeterminResultRequest request) {
        log.info("createOrUpdateSecDeterminResult");
        return secDeterminResultService.createOrUpdateSecDeterminResult(request);
    }

    @GetMapping("/getSecDeterminResultById")
    public BaseResponse getSecDeterminResultById(@RequestParam String drId) {
        log.info("getSecDeterminResultById");
        return secDeterminResultService.getSecDeterminResultById(drId);
    }

    @GetMapping("/getFirstDeterminResultBySecId")
    public BaseResponse getFirstDeterminResultBySecId(@RequestParam String secId) {
        log.info("getFirstDeterminResultBySecId");
        return secDeterminResultService.getFirstDeterminResultBySecId(secId);
    }

    @TenantId
    @PostMapping("/getSecDeterminResultList")
    public BaseResponse getSecDeterminResultList(@RequestBody SecDeterminResultRequest request) {
        log.info("getSecDeterminResultList");
        return secDeterminResultService.getSecDeterminResultList(request);
    }
}
