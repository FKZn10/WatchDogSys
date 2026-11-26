package com.jpmsoft.projectProducer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.SecInfo.SecCalibrationReportRequest;
import com.jpmsoft.projectProducer.service.secInfo.ISecCalibrationReportService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 定标情况报告
 */
@Slf4j
@RestController
@RequestMapping("/v1/calibrationReport")
public class SecCalibrationReportController {
    @Resource
    private ISecCalibrationReportService secCalibrationReportService;
    @Resource
    private RedisUtils redisUtils;

    @PostMapping("/getSecCalibrationReportList")
    public BaseResponse getSecCalibrationReportList(@RequestBody SecCalibrationReportRequest request, @RequestHeader String token) {
        log.info("getSecCalibrationReportList");
        return secCalibrationReportService.getSecCalibrationReportList(request, redisUtils.getUserInfoByToken(token));
    }

    @PostMapping("/getSecCalibrationReportClassInfo")
    public BaseResponse getSecCalibrationReportClassInfo(@RequestBody SecCalibrationReportRequest request, @RequestHeader String token) {
        log.info("getSecCalibrationReportClassInfo");
        return secCalibrationReportService.getSecCalibrationReportClassInfo(request, redisUtils.getUserInfoByToken(token));
    }
}
