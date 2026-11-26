package com.jpmsoft.projectConsumer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.SecInfo.SecQeReportRequest;
import com.jpmsoft.projectConsumer.feign.SecFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "资格审查报告", value = "资格审查报告")
@RequestMapping("/v1/qeReport")
public class SecQeReportController {
    @Autowired
    private SecFeign secFeign;
    ;

    @ApiOperation(value = "创建或修改资格审查报告")
    @PostMapping("/createOrUpdateQeReport")
    public BaseResponse createOrUpdateQeReport(@RequestBody SecQeReportRequest request) {
        log.info("createOrUpdateQeReport");
        return secFeign.createOrUpdateQeReport(request);
    }

    @ApiOperation(value = "分页查询资格审查报告")
    @PostMapping("/getQeReportList")
    public BaseResponse getQeReportList(@RequestBody SecQeReportRequest request) {
        log.info("getQeReportList");
        return secFeign.getQeReportList(request);
    }

    @ApiOperation(value = "ID删除资格审查报告")
    @GetMapping("/deleteQeReportByQerId")
    public BaseResponse deleteQeReportByQerId(@RequestParam String qerId) {
        log.info("deleteQeReportByQerId");
        return secFeign.deleteQeReportByQerId(qerId);
    }

    @ApiOperation(value = "ID查询资格审查报告")
    @GetMapping("/getQeReportByQerId")
    public BaseResponse getQeReportByQerId(@RequestParam String qerId) {
        log.info("getQeReportByQerId");
        return secFeign.getQeReportByQerId(qerId);
    }
}
