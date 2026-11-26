package com.jpmsoft.projectProducer.controller.v1.investment;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.InvestmentControl.InvAdjustmentRequest;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.investment.IInvAdjustmentService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/invAdjustment")
public class InvAdjustmentController {

    @Resource
    private IInvAdjustmentService invAdjustmentService;

    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createOrUpdateInvAdjustment")
    public BaseResponse createOrUpdateInvAdjustment(@RequestBody InvAdjustmentRequest request) {
        log.info("createOrUpdateInvAdjustment");
        return invAdjustmentService.createOrUpdateInvAdjustment(request);
    }

    @TenantId
    @PostMapping("/getInvAdjustmentList")
    public BaseResponse getInvAdjustmentList(@RequestBody InvAdjustmentRequest request, @RequestHeader String token) {
        log.info("getInvAdjustmentList");
        return invAdjustmentService.getInvAdjustmentList(request, redisUtils.getUid(token));
    }

    @GetMapping("/getInvAdjustmentByIaId")
    public BaseResponse getInvAdjustmentByIaId(@RequestParam String iaId) {
        log.info("getInvAdjustmentByIaId");
        return invAdjustmentService.getInvAdjustmentByIaId(iaId);
    }

    @GetMapping("/deleteInvAdjustmentByIaId")
    public BaseResponse deleteInvAdjustmentByIaId(@RequestParam String iaId) {
        log.info("deleteInvAdjustmentByIaId");
        return invAdjustmentService.deleteInvAdjustmentByIaId(iaId);
    }
    
}
