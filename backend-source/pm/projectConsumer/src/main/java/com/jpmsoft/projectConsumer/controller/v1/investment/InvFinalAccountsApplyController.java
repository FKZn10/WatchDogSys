package com.jpmsoft.projectConsumer.controller.v1.investment;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.InvestmentControl.InvFinalAccountsApplyRequest;
import com.jpmsoft.projectConsumer.feign.InvFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "决算申请", value = "决算申请")
@RequestMapping("/inv/v1/invFinal")
public class InvFinalAccountsApplyController {
    @Autowired
    private InvFeign invFeign;

    @ApiOperation(value = "新增或修改决算申请")
    @PostMapping("/createOrUpdateInvFinalAccountsApply")
    public BaseResponse createOrUpdateInvFinalAccountsApply(@RequestBody InvFinalAccountsApplyRequest request) {
        log.info("createOrUpdateInvFinalAccountsApply");
        return invFeign.createOrUpdateInvFinalAccountsApply(request);
    }

    @ApiOperation(value = "分页决算申请")
    @PostMapping("/getInvFinalAccountsApplyList")
    public BaseResponse getInvFinalAccountsApplyList(@RequestBody InvFinalAccountsApplyRequest request, @RequestHeader String token) {
        log.info("getInvFinalAccountsApplyList");
        return invFeign.getInvFinalAccountsApplyList(request, token);
    }

    @ApiOperation(value = "ID查决算申请")
    @GetMapping("/getInvFinalAccountsApplyByfaId")
    public BaseResponse getInvFinalAccountsApplyByfaId(@RequestParam String faId) {
        log.info("getInvFinalAccountsApplyByfaId");
        return invFeign.getInvFinalAccountsApplyByfaId(faId);
    }

    @ApiOperation(value = "项目ID查决算申请及其决算清单")
    @GetMapping("/getInvFinalAccountsApplyAndDtlByPid")
    public BaseResponse getInvFinalAccountsApplyAndDtlByPid(@RequestParam String pId) {
        log.info("getInvFinalAccountsApplyAndDtlByPid");
        return invFeign.getInvFinalAccountsApplyAndDtlByPid(pId);
    }

    @ApiOperation(value = "检测项目是否存在非作废的决算")
    @GetMapping("/havingFinalApplyWithPid")
    public BaseResponse havingFinalApplyWithPid(@RequestParam String pId) {
        log.info("havingFinalApplyWithPid");
        return invFeign.havingFinalApplyWithPid(pId);
    }


    @ApiOperation(value = "删除决算申请")
    @GetMapping("/deleteInvFinalAccountsApplyByfaId")
    public BaseResponse deleteInvFinalAccountsApplyByfaId(@RequestParam String faId) {
        log.info("deleteInvFinalAccountsApplyByfaId");
        return invFeign.deleteInvFinalAccountsApplyByfaId(faId);
    }
}
