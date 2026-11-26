package com.jpmsoft.userProducer.controller.v1;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.RiskWarn.RiskWarnIndexRequest;
import com.jpmsoft.common.model.requestAndResponse.RiskWarn.RiskWarnIndexModel;
import com.jpmsoft.userProducer.annotations.TenantId;
import com.jpmsoft.userProducer.service.IRiskWarnIndexService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@Api(tags = "风险预警指标", value = "风险预警指标")
@RequestMapping("/v1/warnIndex")
public class RiskWarnIndexController {

    @Resource
    private IRiskWarnIndexService riskWarnIndexService;

    @TenantId
    @ApiOperation(value = "新增修改风险预警指标")
    @PostMapping("/createOrUpdateRiskWarnIndex")
    public BaseResponse createOrUpdateRiskWarnIndex(@RequestBody RiskWarnIndexRequest request) {
        log.info("createOrUpdateRiskWarnIndex");
        return riskWarnIndexService.createOrUpdateRiskWarnIndex(request);
    }

    @TenantId
    @ApiOperation(value = "分页查询风险预警指标")
    @PostMapping("/getRiskWarnIndexList")
    public BaseResponse getRiskWarnIndexList(@RequestBody RiskWarnIndexModel request) {
        log.info("getRiskWarnIndexList");
        return riskWarnIndexService.getRiskWarnIndexList(request);
    }

    @ApiOperation(value = "ID删除风险预警指标")
    @GetMapping("/deleteRiskWarnIndexById")
    public BaseResponse deleteRiskWarnIndexById(@RequestParam String rwiId) {
        log.info("deleteRiskWarnIndexById");
        return riskWarnIndexService.deleteRiskWarnIndexById(rwiId);
    }

    @ApiOperation(value = "ID查询风险预警指标")
    @GetMapping("/getRiskWarnIndexById")
    public BaseResponse getRiskWarnIndexById(@RequestParam String rwiId) {
        log.info("getRiskWarnIndexById");
        return riskWarnIndexService.getRiskWarnIndexById(rwiId);
    }



    @ApiOperation(value = "风险规则文件导入")
    @PostMapping("/importRiskWarnIndex")
    public BaseResponse importRiskWarnIndex(@RequestParam  MultipartFile  request) {
        log.info("importRiskWarnIndex");
        return riskWarnIndexService.importRiskWarnIndex(request);
    }

    @ApiOperation(value = "风险规则批量导出")
    @PostMapping("/exportRiskWarnIndex")
    public BaseResponse exportRiskWarnIndex(@RequestBody List<String> request) {
        log.info("exportRiskWarnIndex");
        return riskWarnIndexService.exportRiskWarnIndex(request);
    }

}
