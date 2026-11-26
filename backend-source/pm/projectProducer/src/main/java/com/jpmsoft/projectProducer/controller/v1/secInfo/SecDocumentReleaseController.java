package com.jpmsoft.projectProducer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecDocumentReleaseModel;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.secInfo.ISecDocumentReleaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/documentRelease")
public class SecDocumentReleaseController {

    @Resource
    private ISecDocumentReleaseService secDocumentReleaseService;


    @TenantId
    @PostMapping("/createOrUpdateSecDocumentRelease")
    public BaseResponse createOrUpdateSecDocumentRelease(@RequestBody SecDocumentReleaseModel request) {
        log.info("createOrUpdateSecDocumentRelease");
        return secDocumentReleaseService.createOrUpdateSecDocumentRelease(request);
    }

    @TenantId
    @PostMapping("/getSecDocumentReleaseList")
    public BaseResponse getSecDocumentReleaseList(@RequestBody SecDocumentReleaseModel request) {
        log.info("getSecDocumentReleaseList");
        return secDocumentReleaseService.getSecDocumentReleaseList(request);
    }

    @GetMapping("/getSecDocumentReleaseById")
    public BaseResponse getSecDocumentReleaseById(@RequestParam String sdrId) {
        log.info("getSecDocumentReleaseById");
        return secDocumentReleaseService.getSecDocumentReleaseById(sdrId);
    }

    @GetMapping("/appoval2SyncDocument")
    public BaseResponse appoval2SyncDocument(@RequestParam String sdrId) {
        log.info("appoval2SyncDocument");
        return secDocumentReleaseService.appoval2SyncDocument(sdrId);
    }
}
