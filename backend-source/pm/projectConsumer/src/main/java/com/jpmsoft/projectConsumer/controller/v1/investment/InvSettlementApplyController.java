package com.jpmsoft.projectConsumer.controller.v1.investment;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.InvestmentControl.InvSettlementApplyRequest;
import com.jpmsoft.projectConsumer.feign.InvFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "合同结算申请", value = "合同结算申请")
@RequestMapping("/inv/v1/invSettlementApply")
public class InvSettlementApplyController {
    @Autowired
    private InvFeign invFeign;


    @ApiOperation(value = "新增或修改合同结算申请")
    @PostMapping("/createOrUpdateInvSettlementApply")
    public BaseResponse createOrUpdateInvSettlementApply(@RequestBody InvSettlementApplyRequest request) {
        log.info("createOrUpdateInvSettlementApply");
        return invFeign.createOrUpdateInvSettlementApply(request);
    }

    @ApiOperation(value = "修改合同结算申请(不包含文件信息)")
    @PostMapping("/updateInvSettlementApplyOnlyInfo")
    public BaseResponse updateInvSettlementApplyOnlyInfo(@RequestBody InvSettlementApplyRequest request) {
        log.info("updateInvSettlementApplyOnlyInfo");
        return invFeign.updateInvSettlementApplyOnlyInfo(request);
    }

    @ApiOperation(value = "分页合同结算申请")
    @PostMapping("/getInvSettlementApplyList")
    public BaseResponse getInvSettlementApplyList(@RequestBody InvSettlementApplyRequest request, @RequestHeader String token) {
        log.info("getInvSettlementApplyList");
        return invFeign.getInvSettlementApplyList(request, token);
    }

    @ApiOperation(value = "ID查合同结算申请")
    @GetMapping("/getInvSettlementApplyBysaId")
    public BaseResponse getInvSettlementApplyBysaId(@RequestParam String saId, @RequestHeader String token) {
        log.info("getInvSettlementApplyBysaId");
        return invFeign.getInvSettlementApplyBysaId(saId, token);
    }

    @ApiOperation(value = "ID删除合同结算申请")
    @GetMapping("/deleteInvSettlementApplyBysaId")
    public BaseResponse deleteInvSettlementApplyBysaId(@RequestParam String saId) {
        log.info("deleteInvSettlementApplyBysaId");
        return invFeign.deleteInvSettlementApplyBysaId(saId);
    }

    @ApiOperation(value = "合同ID查询最新一条审批通过的结算申请")
    @GetMapping("/getLastApprovalInvSettlementApplyByConId")
    public BaseResponse getLastApprovalInvSettlementApplyByConId(@RequestParam String conId) {
        log.info("getLastApprovalInvSettlementApplyByConId");
        return invFeign.getLastApprovalInvSettlementApplyByConId(conId);
    }
}
