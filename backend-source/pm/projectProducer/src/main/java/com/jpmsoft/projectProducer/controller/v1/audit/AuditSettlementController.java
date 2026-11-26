package com.jpmsoft.projectProducer.controller.v1.audit;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Audit.AuditSettlementModel;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.audit.IAuditSettlementService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@Slf4j
@RestController
@RequestMapping("/audit/v1/settlement")
public class AuditSettlementController {

    @Resource
    private IAuditSettlementService auditSettlementService;

    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createOrUpdateAuditSettlement")
    public BaseResponse createOrUpdateAuditSettlement(@RequestBody AuditSettlementModel request) {
        log.info("createOrUpdateAuditSettlement");
        return auditSettlementService.createOrUpdateAuditSettlement(request);
    }

    @TenantId
    @PostMapping("/getAuditSettlementList")
    public BaseResponse getAuditSettlementList(@RequestBody AuditSettlementModel request, @RequestHeader String token) {
        log.info("getAuditSettlementList");
        return auditSettlementService.getAuditSettlementList(request, redisUtils.getUid(token));
    }

    @GetMapping("/getAuditSettlementById")
    public BaseResponse getAuditSettlementById(@RequestParam String asId) {
        log.info("getAuditSettlementById");
        return auditSettlementService.getAuditSettlementById(asId);
    }

    @GetMapping("/getApproveAuditSettlementByConId")
    public BaseResponse getApproveAuditSettlementByConId(@RequestParam String conId) {
        log.info("getApproveAuditSettlementByConId");
        return auditSettlementService.getApproveAuditSettlementByConId(conId);
    }

}
