package com.jpmsoft.projectProducer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.SecInfo.SecQeReportRequest;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.secInfo.ISecQeReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/qeReport")
public class SecQeReportController {
    @Resource
    private ISecQeReportService secQeReportService;

    @TenantId
    @PostMapping("/createOrUpdateQeReport")
    public BaseResponse createOrUpdateQeReport(@RequestBody SecQeReportRequest request) {
        log.info("createOrUpdateQeReport");
        return secQeReportService.createOrUpdateQeReport(request);
    }

    @TenantId
    @PostMapping("/getQeReportList")
    public BaseResponse getQeReportList(@RequestBody SecQeReportRequest request) {
        log.info("getQeReportList");
        return secQeReportService.getQeReportList(request);
    }

    @GetMapping("/deleteQeReportByQerId")
    public BaseResponse deleteQeReportByQerId(@RequestParam String qerId) {
        log.info("deleteQeReportByQerId");
        return secQeReportService.deleteQeReportByQerId(qerId);
    }

    @GetMapping("/getQeReportByQerId")
    public BaseResponse getQeReportByQerId(@RequestParam String qerId) {
        log.info("getQeReportByQerId");
        return secQeReportService.getQeReportByQerId(qerId);
    }
}
