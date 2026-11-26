package com.jpmsoft.projectProducer.controller.v1.audit;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Audit.AuditBudgetModel;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.audit.IAuditBudgetService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/audit/v1/budget")
public class AuditBudgetController {

    @Resource
    private IAuditBudgetService auditBudgetService;

    @Resource
    private RedisUtils redisUtils;


    @TenantId
    @PostMapping("/createOrUpdateAuditBudget")
    public BaseResponse createOrUpdateAuditBudget(@RequestBody AuditBudgetModel request) {
        log.info("createOrUpdateAuditBudget");
        return auditBudgetService.createOrUpdateAuditBudget(request);
    }

    @TenantId
    @PostMapping("/getAuditBudgetList")
    public BaseResponse getAuditBudgetList(@RequestBody AuditBudgetModel request, @RequestHeader String token) {
        log.info("getAuditBudgetList");
        return auditBudgetService.getAuditBudgetList(request, redisUtils.getUid(token));
    }

    @GetMapping("/getAuditBudgetById")
    public BaseResponse getAuditBudgetById(@RequestParam String abId) {
        log.info("getAuditBudgetById");
        return auditBudgetService.getAuditBudgetById(abId);
    }

    @GetMapping("/getAuditBudgetBySecId")
    public BaseResponse getAuditBudgetBySecId(@RequestParam String secId) {
        log.info("getAuditBudgetBySecId");
        return auditBudgetService.getAuditBudgetBySecId(secId);
    }
}
