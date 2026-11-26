package com.jpmsoft.projectConsumer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.SecInfo.SecCalibrationReportRequest;
import com.jpmsoft.projectConsumer.feign.SecFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 定标情况报告
 */
@Slf4j
@RestController
@Api(tags = "定标情况报告", value = "定标情况报告")
@RequestMapping("/v1/calibrationReport")
public class SecCalibrationReportController {
    @Resource
    private SecFeign secFeign;

    @ApiOperation("分页查询定标情况报告")
    @PostMapping("/getSecCalibrationReportList")
    public BaseResponse getSecCalibrationReportList(@RequestBody SecCalibrationReportRequest request, @RequestHeader String token) {
        log.info("getSecCalibrationReportList");
        return secFeign.getSecCalibrationReportList(request, token);
    }

    @ApiOperation("获取定标情况报告多条件查询信息")
    @PostMapping("/getSecCalibrationReportClassInfo")
    public BaseResponse getSecCalibrationReportClassInfo(@RequestBody SecCalibrationReportRequest request, @RequestHeader String token) {
        log.info("getSecCalibrationReportClassInfo");
        return secFeign.getSecCalibrationReportClassInfo(request, token);
    }
}
