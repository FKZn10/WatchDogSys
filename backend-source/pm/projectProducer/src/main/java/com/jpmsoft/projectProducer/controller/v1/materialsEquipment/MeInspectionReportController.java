package com.jpmsoft.projectProducer.controller.v1.materialsEquipment;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.MeEnter.MeInspectionReportRequest;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.pojo.request.fund.EngineeringFundApplicationRequest;
import com.jpmsoft.projectProducer.service.materialsEquipment.IMeInspectionReportService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/meInspectionReport")
public class MeInspectionReportController {
    @Resource
    private IMeInspectionReportService meInspectionReportService;
    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createOrUpdateMeInspectionReport")
    public BaseResponse createOrUpdateMeInspectionReport(@RequestBody MeInspectionReportRequest request) {
        log.info("createOrUpdateMeInspectionReport");
        return meInspectionReportService.createOrUpdateMeInspectionReport(request);
    }

    @TenantId
    @PostMapping("/getMeInspectionReportList")
    public BaseResponse getMeInspectionReportList(@RequestBody MeInspectionReportRequest request, @RequestHeader String token) {
        log.info("getMeInspectionReportList");
        return meInspectionReportService.getMeInspectionReportList(request, redisUtils.getUid(token));
    }

    @GetMapping("/getMeInspectionReportByIrId")
    public BaseResponse getMeInspectionReportByIrId(@RequestParam String irId) {
        log.info("getMeInspectionReportByIrId");
        return meInspectionReportService.getMeInspectionReportByIrId(irId);
    }
}
