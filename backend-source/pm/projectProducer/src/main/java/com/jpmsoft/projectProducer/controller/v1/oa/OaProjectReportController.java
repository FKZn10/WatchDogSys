package com.jpmsoft.projectProducer.controller.v1.oa;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Oa.OaProjectReportModel;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.oa.IOaProjectReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/OA/proReport")
public class OaProjectReportController {

    @Resource
    private IOaProjectReportService oaProjectReportService;

    @TenantId
    @PostMapping("/createOrUpdateOaProjectReport")
    public BaseResponse createOrUpdateOaProjectReport(@RequestBody OaProjectReportModel request) {
        log.info("createOrUpdateOaProjectReport");
        return oaProjectReportService.createOrUpdateOaProjectReport(request);
    }

    @TenantId
    @PostMapping("/getOaProjectReportList")
    public BaseResponse getOaProjectReportList(@RequestBody OaProjectReportModel request) {
        log.info("getOaProjectReportList");
        return oaProjectReportService.getOaProjectReportList(request);
    }

    @GetMapping("/getOaProjectReportById")
    public BaseResponse getOaProjectReportById(@RequestParam String prId) {
        log.info("getOaProjectReportById");
        return oaProjectReportService.getOaProjectReportById(prId);
    }

    @GetMapping("/getOaProReportInvSum")
    public BaseResponse getOaProReportInvSum(@RequestParam String wrId) {
        log.info("getOaProReportInvSum");
        return oaProjectReportService.getOaProReportInvSum(wrId);
    }

    @GetMapping("/getLastProjectReportByPid")
    public BaseResponse getLastProjectReportByPid(@RequestParam String pId, @RequestParam String prType) {
        log.info("getLastProjectReportByPid");
        return oaProjectReportService.getLastProjectReportByPid(pId, prType);
    }
}
