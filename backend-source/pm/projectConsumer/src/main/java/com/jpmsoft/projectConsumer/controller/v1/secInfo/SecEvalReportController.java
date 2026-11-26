package com.jpmsoft.projectConsumer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.SecInfo.SecEvalReportClassInfoRequest;
import com.jpmsoft.common.model.request.SecInfo.SecEvalReportRequest;
import com.jpmsoft.projectConsumer.feign.SecFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "评标报告", value = "评标报告")
@RequestMapping("/v1/evalReport")
public class SecEvalReportController {
    @Autowired
    private SecFeign secFeign;

    @ApiOperation(value = "创建或修改评标报告")
    @PostMapping("/createOrUpdateEvalReport")
    public BaseResponse createOrUpdateEvalReport(@RequestBody SecEvalReportRequest request) {
        log.info("createOrUpdateEvalReport");
        return secFeign.createOrUpdateEvalReport(request);
    }

    @ApiOperation(value = "ID作废评标报告")
    @GetMapping("/cancelSecEvalReportByErId")
    public BaseResponse cancelSecEvalReportByErId(@RequestParam String erId) {
        log.info("cancelSecEvalReportByErId");
        return secFeign.cancelSecEvalReportByErId(erId);
    }

    @ApiOperation(value = "ID查询评标报告")
    @GetMapping("/getSecEvalReportByErId")
    public BaseResponse getSecEvalReportByErId(@RequestParam String erId) {
        log.info("getSecEvalReportByErId");
        return secFeign.getSecEvalReportByErId(erId);
    }

    @GetMapping("/getSecEvalReportBySecId")
    public BaseResponse getSecEvalReportBySecId(@RequestParam String secId) {
        log.info("getSecEvalReportBySrId");
        return secFeign.getSecEvalReportBySecId(secId);
    }

    @ApiOperation("分页查询评标报告")
    @PostMapping("/getSecEvalReportList")
    public BaseResponse getSecEvalReportList(@RequestBody SecEvalReportClassInfoRequest request, @RequestHeader String token) {
        log.info("getSecEvalReportList");
        return secFeign.getSecEvalReportList(request, token);
    }

    @ApiOperation("获取评标报告多条件查询信息")
    @PostMapping("/getSecEvalReportListClassInfo")
    public BaseResponse getSecEvalReportListClassInfo(@RequestBody SecEvalReportClassInfoRequest request, @RequestHeader String token) {
        log.info("getSecEvalReportListClassInfo");
        return secFeign.getSecEvalReportListClassInfo(request, token);
    }

    @ApiOperation("分页查询评标报告（无鉴权）")
    @PostMapping("/getSecEvalReportNoAuthList")
    public BaseResponse getSecEvalReportNoAuthList(@RequestBody SecEvalReportClassInfoRequest request) {
        log.info("getSecEvalReportNoAuthList");
        return secFeign.getSecEvalReportNoAuthList(request);
    }
}
