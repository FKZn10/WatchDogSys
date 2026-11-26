package com.jpmsoft.projectConsumer.controller.v1.oa;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Oa.ReportedPersonnelModel;
import com.jpmsoft.common.utils.RequestBean;
import com.jpmsoft.projectConsumer.feign.OaFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@Api(tags = "项目人员报备", value = "项目人员报备")
@RequestMapping("/v1/OA/repPersonnel")
public class ReportedPersonnelController {
    @Autowired
    private OaFeign oaFeign;

    @ApiOperation(value = "新增或修改项目人员报备")
    @PostMapping("/createOrUpdateReportedPersonnel")
    public BaseResponse createOrUpdateReportedPersonnel(@RequestBody ReportedPersonnelModel request) {
        log.info("createOrUpdateReportedPersonnel");
        return oaFeign.createOrUpdateReportedPersonnel(request);
    }

    @ApiOperation(value = "新增或修改项目人员报备（城建定制）")
    @PostMapping("/createOrUpdateReportedPersonnelCj")
    public BaseResponse createOrUpdateReportedPersonnelCj(@RequestBody ReportedPersonnelModel request) {
        log.info("createOrUpdateReportedPersonnelCj");
        return oaFeign.createOrUpdateReportedPersonnelCj(request);
    }

    @ApiOperation(value = "分页项目人员报备")
    @PostMapping("/getReportedPersonnelList")
    public BaseResponse getReportedPersonnelList(@RequestBody ReportedPersonnelModel request, @RequestHeader String token) {
        log.info("getReportedPersonnelList");
        return oaFeign.getReportedPersonnelList(request, token);
    }

    @ApiOperation(value = "ID查项目人员报备")
    @GetMapping("/getReportedPersonnelById")
    public BaseResponse getReportedPersonnelById(@RequestParam String rpId) {
        log.info("getReportedPersonnelById");
        return oaFeign.getReportedPersonnelById(rpId);
    }

    @ApiOperation(value = "审批项目人员报备")
    @GetMapping("/approveReportedPersonnelById")
    public BaseResponse approveReportedPersonnelById(@RequestParam String rpId) {
        log.info("approveReportedPersonnelById");
        return oaFeign.approveReportedPersonnelById(rpId);
    }

    @ApiOperation(value = "审批项目人员报备")
    @GetMapping("/xazhjj/approve2SyncDepartmentP")
    public BaseResponse approve2SyncDepartmentP(@RequestParam String rpId,@RequestHeader String token) {
        log.info("approve2SyncDepartmentP");
        return oaFeign.approve2SyncDepartmentP(rpId,token);
    }

    @PostMapping("/getOaWorkReportStandingbook")
    public BaseResponse getOaWorkReportStandingbook(@RequestBody RequestBean requestBean,@RequestHeader String token) {
        log.info("getOaWorkReportStandingbook");
        return oaFeign.getOaWorkReportStandingbook(requestBean,token);
    }
    @PostMapping("/getOaWorkReportNeverSub")
    public BaseResponse getOaWorkReportNeverSub(@RequestBody RequestBean requestBean) {
        log.info("getOaWorkReportNeverSub");
        return oaFeign.getOaWorkReportNeverSub(requestBean);
    }

    @PostMapping("/countOaWorkReport")
    public BaseResponse countOaWorkReport(@RequestBody List<String> cIds , @RequestParam("type") String type ) {
        log.info("countOaWorkReport");
        return oaFeign.countOaWorkReport(cIds,type);
    }
}
