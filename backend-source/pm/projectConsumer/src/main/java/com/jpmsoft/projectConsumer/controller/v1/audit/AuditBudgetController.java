package com.jpmsoft.projectConsumer.controller.v1.audit;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Audit.AuditBudgetModel;
import com.jpmsoft.projectConsumer.feign.BaseFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "审计管理-预算审计", value = "审计管理-预算审计")
@RequestMapping("/audit/v1/budget")
public class AuditBudgetController {
    @Autowired
    private BaseFeign baseFeign;

    @ApiOperation(value = "新增或修改预算审计")
    @PostMapping("/createOrUpdateAuditBudget")
    public BaseResponse createOrUpdateAuditBudget(@RequestBody AuditBudgetModel request) {
        log.info("createOrUpdateAuditBudget");
        return baseFeign.createOrUpdateAuditBudget(request);
    }

    @ApiOperation(value = "分页查询预算审计")
    @PostMapping("/getAuditBudgetList")
    public BaseResponse getAuditBudgetList(@RequestBody AuditBudgetModel request, @RequestHeader String token) {
        log.info("getAuditBudgetList");
        return baseFeign.getAuditBudgetList(request, token);
    }


    @ApiOperation(value = "ID查询预算审计")
    @GetMapping("/getAuditBudgetById")
    public BaseResponse getAuditBudgetById(@RequestParam String abId) {
        log.info("getAuditBudgetById");
        return baseFeign.getAuditBudgetById(abId);
    }

    @ApiOperation(value = "标段ID查询预算审计")
    @GetMapping("/getAuditBudgetBySecId")
    public BaseResponse getAuditBudgetBySecId(@RequestParam String secId) {
        log.info("getAuditBudgetBySecId");
        return baseFeign.getAuditBudgetBySecId(secId);
    }
}
