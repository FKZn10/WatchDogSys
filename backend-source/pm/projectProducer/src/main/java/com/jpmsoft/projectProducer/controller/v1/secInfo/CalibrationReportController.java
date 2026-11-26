package com.jpmsoft.projectProducer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.CalibrationReportDto;
import com.jpmsoft.projectProducer.service.secInfo.ICalibrationReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author: 李文涛 aaron
 * @date: 2022/7/8 9:56
 */
@Slf4j
@RestController
@RequestMapping("/v1/calibrationReport")
public class CalibrationReportController {

    @Resource
    private ICalibrationReportService calibrationReportService;

    @TenantId
    @PostMapping("/createOrUpdateCalibrationReport")
    public BaseResponse createOrUpdateCalibrationReport(@RequestBody CalibrationReportDto request) {
        log.info("createOrUpdateCalibrationReport");
        return calibrationReportService.createOrUpdateCalibrationReport(request);
    }

    @TenantId
    @PostMapping("/createCalibrationReport")
    public BaseResponse createCalibrationReport(@RequestBody CalibrationReportDto request) {
        log.info("createCalibrationReport");
        return calibrationReportService.createCalibrationReport(request);
    }

    @PostMapping("/updateCalibrationReport")
    public BaseResponse updateCalibrationReport(@RequestBody CalibrationReportDto request) {
        log.info("updateCalibrationReport");
        return calibrationReportService.updateCalibrationReport(request);
    }

    @TenantId
    @PostMapping("/getCalibrationReportList")
    public BaseResponse getCalibrationReportList(@RequestBody CalibrationReportDto request) {
        log.info("getCalibrationReportList");
        return calibrationReportService.getCalibrationReportList(request);
    }

    @GetMapping("/getCalibrationReportById")
    public BaseResponse getCalibrationReportById(@RequestParam String id) {
        log.info("getCalibrationReportById");
        return calibrationReportService.getCalibrationReportById(id);
    }

    @GetMapping("/getCalibrationReportBySecId")
    public BaseResponse getCalibrationReportBySecId(@RequestParam String secId) {
        log.info("getCalibrationReportBySecId");
        return calibrationReportService.getCalibrationReportBySecId(secId);
    }

}
