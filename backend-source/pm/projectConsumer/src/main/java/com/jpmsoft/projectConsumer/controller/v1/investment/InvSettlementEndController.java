package com.jpmsoft.projectConsumer.controller.v1.investment;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.InvestmentControl.InvSettlementEndDTO;
import com.jpmsoft.projectConsumer.feign.InvFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@Api(tags = "合同结算申请（终审）", value = "合同结算申请（终审）")
@RequestMapping("/inv/v1/invSettlementEnd")
public class InvSettlementEndController {
    @Resource
    private InvFeign invFeign;


    @ApiOperation(value = "新增或修改合同结算申请（终审）")
    @PostMapping("/createOrUpdateInvSettlementEnd")
    public BaseResponse createOrUpdateInvSettlementEnd(@RequestBody InvSettlementEndDTO request) {
        log.info("createOrUpdateInvSettlementEnd");
        return invFeign.createOrUpdateInvSettlementEnd(request);
    }

    @ApiOperation(value = "修改合同结算申请（终审）(不包含文件信息)")
    @PostMapping("/updateInvSettlementEndOnlyInfo")
    public BaseResponse updateInvSettlementEndOnlyInfo(@RequestBody InvSettlementEndDTO request) {
        log.info("updateInvSettlementEndOnlyInfo");
        return invFeign.updateInvSettlementEndOnlyInfo(request);
    }

    @ApiOperation(value = "分页合同结算申请（终审）")
    @PostMapping("/getInvSettlementEndList")
    public BaseResponse getInvSettlementEndList(@RequestBody InvSettlementEndDTO request, @RequestHeader String token) {
        log.info("getInvSettlementEndList");
        return invFeign.getInvSettlementEndList(request, token);
    }

    @ApiOperation(value = "分页合同结算申请（终审不鉴权）")
    @PostMapping("/getInvSettlementEndNoAuthList")
    public BaseResponse getInvSettlementEndNoAuthList(@RequestBody InvSettlementEndDTO request, @RequestHeader String token) {
        log.info("getInvSettlementEndNoAuthList");
        return invFeign.getInvSettlementEndNoAuthList(request, token);
    }

    @ApiOperation(value = "ID查合同结算申请（终审）")
    @GetMapping("/getInvSettlementEndBySaeId")
    public BaseResponse getInvSettlementEndBySaeId(@RequestParam String saId) {
        log.info("getInvSettlementEndBySaeId");
        return invFeign.getInvSettlementEndBySaeId(saId);
    }

    @ApiOperation(value = "ID删除合同结算申请（终审）")
    @GetMapping("/deleteInvSettlementEndBySaeId")
    public BaseResponse deleteInvSettlementEndBySaeId(@RequestParam String saId) {
        log.info("deleteInvSettlementEndBySaeId");
        return invFeign.deleteInvSettlementEndBySaeId(saId);
    }

    @ApiOperation(value = "合同ID查询最新一条审批通过的结算申请（终审）")
    @GetMapping("/getLastApprovalInvSettlementEndByConId")
    public BaseResponse getLastApprovalInvSettlementEndByConId(@RequestParam String conId) {
        log.info("getLastApprovalInvSettlementEndByConId");
        return invFeign.getLastApprovalInvSettlementEndByConId(conId);
    }

    @GetMapping("/getLastApprovalInvSettlementEndByPayId")
    public BaseResponse getLastApprovalInvSettlementEndByPayId(@RequestParam String id) {
        log.info("getLastApprovalInvSettlementEndByPayId");
        return invFeign.getLastApprovalInvSettlementEndByPayId(id);
    }
}
