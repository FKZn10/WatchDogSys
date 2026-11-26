package com.jpmsoft.projectConsumer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.pojo.dto.secInfo.CalibrationReportDto;
import com.jpmsoft.projectConsumer.feign.SecFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: 李文涛 aaron
 * @date: 2022/7/8 9:56
 */
@Slf4j
@RestController
@Api(tags = "定标报告", value = "定标报告")
@RequestMapping("/v1/calibrationReport")
public class CalibrationReportController {

    @Autowired
    private SecFeign secFeign;


    @ApiOperation(value = "创建或修改定标报告")
    @PostMapping("/createOrUpdateCalibrationReport")
    public BaseResponse createOrUpdateCalibrationReport(@RequestBody CalibrationReportDto request) {
        log.info("createOrUpdateCalibrationReport");
        return secFeign.createOrUpdateCalibrationReport(request);
    }

    @ApiOperation(value = "创建定标报告")
    @PostMapping("/createCalibrationReport")
    public BaseResponse createCalibrationReport(@RequestBody CalibrationReportDto request) {
        log.info("createCalibrationReport");
        return secFeign.createCalibrationReport(request);
    }

    @ApiOperation(value = "修改定标报告")
    @PostMapping("/updateCalibrationReport")
    public BaseResponse updateCalibrationReport(@RequestBody CalibrationReportDto request) {
        log.info("updateCalibrationReport");
        return secFeign.updateCalibrationReport(request);
    }

    @ApiOperation(value = "分页查询定标报告")
    @PostMapping("/getCalibrationReportList")
    public BaseResponse getCalibrationReportList(@RequestBody CalibrationReportDto request) {
        log.info("getCalibrationReportList");
        return secFeign.getCalibrationReportList(request);
    }

    @ApiOperation(value = "ID查询定标报告")
    @GetMapping("/getCalibrationReportById")
    public BaseResponse getCalibrationReportById(@RequestParam String id) {
        log.info("getCalibrationReportById");
        return secFeign.getCalibrationReportById(id);
    }

    @ApiOperation(value = "标段id查询审批通过的最新的一条定标报告信息")
    @GetMapping("/getCalibrationReportBySecId")
    public BaseResponse getCalibrationReportBySecId(@RequestParam String secId) {
        log.info("getCalibrationReportBySecId");
        return secFeign.getCalibrationReportBySecId(secId);
    }

}
