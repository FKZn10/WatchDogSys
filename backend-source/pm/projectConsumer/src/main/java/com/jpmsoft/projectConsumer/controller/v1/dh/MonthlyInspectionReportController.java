package com.jpmsoft.projectConsumer.controller.v1.dh;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.dh.MonthlyInspectionReportDTO;
import com.jpmsoft.projectConsumer.feign.BaseFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/monthlyInspectionReport")
public class MonthlyInspectionReportController {
    @Resource
    private BaseFeign baseFeign;

    @PostMapping("/getMonthlyInspectionReportList")
    public BaseResponse getMonthlyInspectionReportList(@RequestBody MonthlyInspectionReportDTO request) {
        log.info("getMonthlyInspectionReportList");
        return baseFeign.getMonthlyInspectionReportList(request);
    }

    @PostMapping("/getMonthlyInspectionReportListClassInfo")
    public BaseResponse getMonthlyInspectionReportListClassInfo(@RequestBody MonthlyInspectionReportDTO request) {
        log.info("getMonthlyInspectionReportListClassInfo");
        return baseFeign.getMonthlyInspectionReportListClassInfo(request);
    }
}
