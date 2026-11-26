package com.jpmsoft.projectProducer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.SecInfo.SecPublicityResultsRequest;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.secInfo.ISecPublicityResultsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@Slf4j
@RestController
@RequestMapping("/v1/publicityResults")
public class SecPublicityResultsController {

    @Resource
    private ISecPublicityResultsService secPublicityResultsService;

    @TenantId
    @PostMapping("/createOrUpdateSecPublicityResults")
    public BaseResponse createOrUpdateSecPublicityResults(@RequestBody SecPublicityResultsRequest request) {
        log.info("createOrUpdateSecPublicityResults");
        return secPublicityResultsService.createOrUpdateSecPublicityResults(request);
    }

    @GetMapping("/getSecPublicityResultsById")
    public BaseResponse getSecPublicityResultsById(@RequestParam String prId) {
        log.info("getSecPublicityResultsById");
        return secPublicityResultsService.getSecPublicityResultsById(prId);
    }

    @TenantId
    @PostMapping("/getSecPublicityResultsList")
    public BaseResponse getSecPublicityResultsList(@RequestBody SecPublicityResultsRequest request) {
        log.info("getSecPublicityResultsList");
        return secPublicityResultsService.getSecPublicityResultsList(request);
    }

    @TenantId
    @PostMapping("/getSecPublicityResultsListOne2Many")
    public BaseResponse getSecPublicityResultsListOne2Many(@RequestBody SecPublicityResultsRequest request) {
        log.info("getSecPublicityResultsListOne2Many");
        return secPublicityResultsService.getSecPublicityResultsListOne2Many(request);
    }
}
