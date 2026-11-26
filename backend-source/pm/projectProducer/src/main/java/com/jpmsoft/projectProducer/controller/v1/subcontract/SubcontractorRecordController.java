package com.jpmsoft.projectProducer.controller.v1.subcontract;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Subcontract.SubcontractorRecordRequest;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.subcontract.ISubcontractorRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/subcontractorRecord")
public class SubcontractorRecordController {
    @Resource
    private ISubcontractorRecordService subcontractorRecordService;

    @TenantId
    @PostMapping("/createOrUpdateSubcontractorRecord")
    public BaseResponse createOrUpdateSubcontractorRecord(@RequestBody SubcontractorRecordRequest request) {
        log.info("createOrUpdateSubcontractorRecord");
        return subcontractorRecordService.createOrUpdateSubcontractorRecord(request);
    }

    @TenantId
    @PostMapping("/getSubcontractorRecordList")
    public BaseResponse getSubcontractorRecordList(@RequestBody SubcontractorRecordRequest request) {
        log.info("getSubcontractorRecordList");
        return subcontractorRecordService.getSubcontractorRecordList(request);
    }


    @GetMapping("/getSubcontractorRecordBySrId")
    public BaseResponse getSubcontractorRecordBySrId(@RequestParam String srId) {
        log.info("getSubcontractorRecordBySrId");
        return subcontractorRecordService.getSubcontractorRecordBySrId(srId);
    }

    @GetMapping("/checkCreditCodeIsExist")
    public BaseResponse checkCreditCodeIsExist(@RequestParam String crditCode) {
        log.info("checkCreditCodeIsExist");
        return subcontractorRecordService.checkCreditCodeIsExist(crditCode);
    }


    @GetMapping("/syncSubcontractorRecordComapny")
    public BaseResponse syncSubcontractorRecordComapny(@RequestParam String srId) {
        log.info("syncSubcontractorRecordComapny");
        return subcontractorRecordService.syncSubcontractorRecordComapny(srId);
    }
}
