package com.jpmsoft.projectConsumer.controller.v1.oa;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Oa.OaProjectReportModel;
import com.jpmsoft.projectConsumer.feign.OaFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "工程周报、月报", value = "工程周报、月报")
@RequestMapping("/v1/OA/proReport")
public class OaProjectReportController {
    @Autowired
    private OaFeign oaFeign;

    @ApiOperation(value = "新增或修改工程报告")
    @PostMapping("/createOrUpdateOaProjectReport")
    public BaseResponse createOrUpdateOaProjectReport(@RequestBody OaProjectReportModel request) {
        log.info("createOrUpdateOaProjectReport");
        return oaFeign.createOrUpdateOaProjectReport(request);
    }

    @ApiOperation(value = "分页查询工程报告")
    @PostMapping("/getOaProjectReportList")
    public BaseResponse getOaProjectReportList(@RequestBody OaProjectReportModel request) {
        log.info("getOaProjectReportList");
        return oaFeign.getOaProjectReportList(request);
    }

    @ApiOperation(value = "ID查询工程报告")
    @GetMapping("/getOaProjectReportById")
    public BaseResponse getOaProjectReportById(@RequestParam String prId) {
        log.info("getOaProjectReportById");
        return oaFeign.getOaProjectReportById(prId);
    }

    @ApiOperation(value = "工作报告ID统计工程报告投资总额")
    @GetMapping("/getOaProReportInvSum")
    public BaseResponse getOaProReportInvSum(@RequestParam String wrId) {
        log.info("getOaProReportInvSum");
        return oaFeign.getOaProReportInvSum(wrId);
    }

    @ApiOperation(value = "获取项目最新审批完成的工程报告")
    @GetMapping("/getLastProjectReportByPid")
    public BaseResponse getLastProjectReportByPid(@RequestParam String pId, @RequestParam String prType) {
        log.info("getLastProjectReportByPid");
        return oaFeign.getLastProjectReportByPid(pId, prType);
    }
}
