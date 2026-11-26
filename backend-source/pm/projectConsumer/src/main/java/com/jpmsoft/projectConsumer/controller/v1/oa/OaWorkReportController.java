package com.jpmsoft.projectConsumer.controller.v1.oa;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Oa.OaWorkReportRequest;
import com.jpmsoft.projectConsumer.feign.OaFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "工作报告（日报、周报、月报、季报、年报）", value = "工作报告（日报、周报、月报、季报、年报）")
@RequestMapping("/OA/WorkReport")
public class OaWorkReportController {
    @Autowired
    private OaFeign oaFeign;

    @ApiOperation(value = "新增或修改工作报告")
    @PostMapping("/createOrUpdateOaWorkReport")
    public BaseResponse createOrUpdateOaWorkReport(@RequestBody OaWorkReportRequest request) {
        log.info("createOrUpdateOaWorkReport");
        return oaFeign.createOrUpdateOaWorkReport(request);
    }

    @ApiOperation(value = "分页查询工作报告(项目鉴权)")
    @PostMapping("/getOaWorkReportList")
    public BaseResponse getOaWorkReportList(@RequestBody OaWorkReportRequest request, @RequestHeader String token) {
        log.info("getOaWorkReportList");
        return oaFeign.getOaWorkReportList(request, token);
    }

    @ApiOperation(value = "分页查询工作报告(含子表明细数量合计)")
    @PostMapping("/getOaWorkReportSumList")
    public BaseResponse getOaWorkReportSumList(@RequestBody OaWorkReportRequest request, @RequestHeader String token) {
        log.info("getOaWorkReportSumList");
        return oaFeign.getOaWorkReportSumList(request, token);
    }

    @ApiOperation(value = "分页查询工作报告（组织机构鉴权）")
    @PostMapping("/getOaWorkReportListOnlyDep")
    public BaseResponse getOaWorkReportListOnlyDep(@RequestBody OaWorkReportRequest request, @RequestHeader String token) {
        log.info("getOaWorkReportListOnlyDep");
        return oaFeign.getOaWorkReportListOnlyDep(request, token);
    }

    @ApiOperation(value = "分页查询工作报告(不鉴权)")
    @PostMapping("/getOaWorkReportListNoAuth")
    public BaseResponse getOaWorkReportListNoAuth(@RequestBody OaWorkReportRequest request) {
        log.info("getOaWorkReportListNoAuth");
        return oaFeign.getOaWorkReportListNoAuth(request);
    }


    @ApiOperation(value = "ID查询工作报告")
    @GetMapping("/getOaWorkReportById")
    public BaseResponse getOaWorkReportById(@RequestParam String wrId) {
        log.info("getOaWorkReportById");
        return oaFeign.getOaWorkReportById(wrId);
    }
}
