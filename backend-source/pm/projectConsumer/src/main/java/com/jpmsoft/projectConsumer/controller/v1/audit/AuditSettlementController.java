package com.jpmsoft.projectConsumer.controller.v1.audit;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Audit.AuditSettlementModel;
import com.jpmsoft.projectConsumer.feign.BaseFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@Api(tags = "审计管理-结算审计", value = "审计管理-结算审计")
@RequestMapping("/audit/v1/settlement")
public class AuditSettlementController {
    @Autowired
    private BaseFeign baseFeign;


    @ApiOperation(value = "新增或修改结算审计")
    @PostMapping("/createOrUpdateAuditSettlement")
    public BaseResponse createOrUpdateAuditSettlement(@RequestBody AuditSettlementModel request) {
        log.info("createOrUpdateAuditSettlement");
        return baseFeign.createOrUpdateAuditSettlement(request);
    }

    @ApiOperation(value = "分页查询结算审计")
    @PostMapping("/getAuditSettlementList")
    public BaseResponse getAuditSettlementList(@RequestBody AuditSettlementModel request, @RequestHeader String token) {
        log.info("getAuditSettlementList");
        return baseFeign.getAuditSettlementList(request, token);
    }


    @ApiOperation(value = "ID查询结算审计")
    @GetMapping("/getAuditSettlementById")
    public BaseResponse getAuditSettlementById(@RequestParam String asId) {
        log.info("getAuditSettlementById");
        return baseFeign.getAuditSettlementById(asId);
    }

    @ApiOperation(value = "合同ID查询最新一条审批通过的结算审计")
    @GetMapping("/getApproveAuditSettlementByConId")
    public BaseResponse getApproveAuditSettlementByConId(@RequestParam String conId) {
        log.info("getApproveAuditSettlementByConId");
        return baseFeign.getApproveAuditSettlementByConId(conId);
    }
}
