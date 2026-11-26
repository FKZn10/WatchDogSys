package com.jpmsoft.projectConsumer.controller.v1.project;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Project.PreConstructionPermitModel;
import com.jpmsoft.projectConsumer.feign.PreFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "开工许可证", value = "开工许可证")
@RequestMapping("/v1/constructionPermit")
public class PreConstructionPermitController {
    @Autowired
    private PreFeign preFeign;

    @ApiOperation(value = "新增修改开工许可证")
    @PostMapping("/createOrUpdatePreConstructionPermit")
    public BaseResponse createOrUpdatePreConstructionPermit(@RequestBody PreConstructionPermitModel request) {
        log.info("createOrUpdatePreConstructionPermit");
        return preFeign.createOrUpdatePreConstructionPermit(request);
    }

    @ApiOperation(value = "获取开工许可证列表")
    @PostMapping("/getPreConstructionPermitList")
    public BaseResponse getPreConstructionPermitList(@RequestBody PreConstructionPermitModel request, @RequestHeader String token) {
        log.info("getPreConstructionPermitList");
        return preFeign.getPreConstructionPermitList(request, token);
    }

    @ApiOperation(value = "获取开工许可证列表（统计+列表）")
    @PostMapping("/getPreConstructionPermitStatisticsList")
    public BaseResponse getPreConstructionPermitStatisticsList(@RequestBody PreConstructionPermitModel request, @RequestHeader String token) {
        log.info("getPreConstructionPermitStatisticsList");
        return preFeign.getPreConstructionPermitStatisticsList(request, token);
    }


    @ApiOperation(value = "ID获取开工许可证")
    @GetMapping("/getPreConstructionPermitById")
    public BaseResponse getPreConstructionPermitById(@RequestParam String cpId) {
        log.info("getPreConstructionPermitById");
        return preFeign.getPreConstructionPermitById(cpId);
    }

    @ApiOperation(value = "项目ID获取参建单位")
    @GetMapping("/getPreConstructionPermitUnitListByPid")
    public BaseResponse getPreConstructionPermitUnitListByPid(String pId) {
        log.info("getPreConstructionPermitById");
        return preFeign.getPreConstructionPermitUnitListByPid(pId);
    }

    @ApiOperation(value = "审批结束刷新项目信息")
    @GetMapping("/approval2RefershProjectInfo")
    public BaseResponse approval2RefershProjectInfo(@RequestParam String cpId) {
        log.info("approval2RefershProjectInfo");
        return preFeign.approval2RefershProjectInfo(cpId);
    }
}
