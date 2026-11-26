package com.jpmsoft.projectProducer.controller.v1.subcontract;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Subcontract.SubcontractApplyRequest;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.subcontract.ISubcontractApplyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/subcontractApply")
public class SubcontractApplyController {
    @Resource
    private ISubcontractApplyService subcontractApplyService;


    @TenantId
    @PostMapping("/createOrUpdateSubcontractApply")
    public BaseResponse createOrUpdateSubcontractApply(@RequestBody SubcontractApplyRequest request) {
        log.info("createOrUpdateSubcontractApply");
        return subcontractApplyService.createOrUpdateSubcontractApply(request);
    }


    @TenantId
    @PostMapping("/getSubcontractApplyList")
    public BaseResponse getSubcontractApplyList(@RequestBody SubcontractApplyRequest request) {
        log.info("getSubcontractApplyList");
        return subcontractApplyService.getSubcontractApplyList(request);
    }


    @GetMapping("/getSubcontractApplyBySaId")
    public BaseResponse getSubcontractApplyBySaId(@RequestParam String saId) {
        log.info("getSubcontractApplyBySaId");
        return subcontractApplyService.getSubcontractApplyBySaId(saId);
    }
}
