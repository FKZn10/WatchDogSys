package com.jpmsoft.projectConsumer.controller.v1.completion;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Construction.CompletionAcceptanceRequest;
import com.jpmsoft.projectConsumer.feign.BaseFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@Api(tags = "施工管理-工程验收", value = "施工管理-工程验收")
@RequestMapping("/v1/acceptance")
public class CompletionAcceptanceController {
    @Autowired
    private BaseFeign baseFeign;


    @ApiOperation(value = "新增或修改工程验收")
    @PostMapping("/createOrUpdateCompletionAcceptance")
    public BaseResponse createOrUpdateCompletionAcceptance(@RequestBody CompletionAcceptanceRequest request) {
        log.info("createOrUpdateCompletionAcceptance");
        return baseFeign.createOrUpdateCompletionAcceptance(request);
    }

    @ApiOperation(value = "分页查询工程验收")
    @PostMapping("/getCompletionAcceptanceList")
    public BaseResponse getCompletionAcceptanceList(@RequestBody CompletionAcceptanceRequest request, @RequestHeader String token) {
        log.info("getCompletionAcceptanceList");
        return baseFeign.getCompletionAcceptanceList(request, token);
    }

    @ApiOperation(value = "分页查询工程验收(统计+列表)")
    @PostMapping("/getCompletionAcceptanceStatisticsList")
    public BaseResponse getCompletionAcceptanceStatisticsList(@RequestBody CompletionAcceptanceRequest request, @RequestHeader String token) {
        log.info("getCompletionAcceptanceStatisticsList");
        return baseFeign.getCompletionAcceptanceStatisticsList(request, token);
    }

    @ApiOperation(value = "分页查询工程验收-不鉴权")
    @PostMapping("/getCompletionAcceptanceListNoAuth")
    public BaseResponse getCompletionAcceptanceListNoAuth(@RequestBody CompletionAcceptanceRequest request) {
        log.info("getCompletionAcceptanceListNoAuth");
        return baseFeign.getCompletionAcceptanceListNoAuth(request);
    }

    @ApiOperation(value = "项目ID+类型查询最新审批通过的数据")
    @PostMapping("/getCompletionAcceptanceLastByPidAndType")
    public BaseResponse getCompletionAcceptanceLastByPidAndType(@RequestBody CompletionAcceptanceRequest request) {
        log.info("getCompletionAcceptanceLastByPidAndType");
        return baseFeign.getCompletionAcceptanceLastByPidAndType(request);
    }

    @ApiOperation(value = "ID查询工程验收")
    @GetMapping("/getCompletionAcceptanceById")
    public BaseResponse getCompletionAcceptanceById(@RequestParam String caId) {
        log.info("getCompletionAcceptanceById");
        return baseFeign.getCompletionAcceptanceById(caId);
    }

    @ApiOperation(value = "审批通过更新项目阶段为竣工验收")
    @GetMapping("/approve2RefreshProStage")
    public BaseResponse approve2RefreshProStage(@RequestParam String caId) {
        log.info("approve2RefreshProStage");
        return baseFeign.approve2RefreshProStage(caId);
    }
    @GetMapping("/checkCompletionAcceptance")
    public BaseResponse checkCompletionAcceptance(@RequestParam String conId) {
        log.info("checkCompletionAcceptance");
        return baseFeign.checkCompletionAcceptance(conId);
    }
}
