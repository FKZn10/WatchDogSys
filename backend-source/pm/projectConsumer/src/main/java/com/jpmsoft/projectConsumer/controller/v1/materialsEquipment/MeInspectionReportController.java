package com.jpmsoft.projectConsumer.controller.v1.materialsEquipment;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.MeEnter.MeInspectionReportRequest;
import com.jpmsoft.projectConsumer.feign.MaterialFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "检测报告", value = "检测报告")
@RequestMapping("/material/v1/meInspectionReport")
public class MeInspectionReportController {
    @Autowired
    private MaterialFeign materialFeign;

    @ApiOperation(value = "新增或修改检测报告")
    @PostMapping("/createOrUpdateMeInspectionReport")
    public BaseResponse createOrUpdateMeInspectionReport(@RequestBody MeInspectionReportRequest request) {
        log.info("createOrUpdateMeInspectionReport");
        log.info("request:{}", request);
        return materialFeign.createOrUpdateMeInspectionReport(request);
    }

    @ApiOperation(value = "分页查询检测报告")
    @PostMapping("/getMeInspectionReportList")
    public BaseResponse getMeInspectionReportList(@RequestBody MeInspectionReportRequest request, @RequestHeader String token) {
        log.info("getMeInspectionReportList");
        return materialFeign.getMeInspectionReportList(request, token);
    }

    @ApiOperation(value = "ID查询检测报告")
    @GetMapping("/getMeInspectionReportByIrId")
    public BaseResponse getMeInspectionReportByIrId(@RequestParam String irId) {
        log.info("getMeInspectionReportByIrId");
        return materialFeign.getMeInspectionReportByIrId(irId);
    }
}
