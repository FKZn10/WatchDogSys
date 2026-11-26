package com.jpmsoft.projectProducer.controller.v1.investment;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.InvestmentControl.InvProjectPlanRequest;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.investment.IInvProjectPlanService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/invProjectPlan")
public class InvProjectPlanController {

    @Resource
    private IInvProjectPlanService invProjectPlanService;
    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/getInvProjectPlanList")
    public BaseResponse getInvProjectPlanList(@RequestBody InvProjectPlanRequest request, @RequestHeader String token) {
        log.info("getInvProjectPlanList");
        return invProjectPlanService.getInvProjectPlanList(request, redisUtils.getUid(token));
    }

    @GetMapping("/getInvProPlanHistoryAmt")
    public BaseResponse getInvProPlanHistoryAmt(@RequestParam String pId, @RequestParam String year) {
        log.info("getInvProPlanHistoryAmt");
        return invProjectPlanService.getInvProPlanHistoryAmt(pId, year);
    }

    @TenantId
    @PostMapping("/createOrUpdateInvProjectPlan")
    public BaseResponse createOrUpdateInvProjectPlan(@RequestBody InvProjectPlanRequest request) {
        log.info("createOrUpdateInvProjectPlan");
        return invProjectPlanService.createOrUpdateInvProjectPlan(request);
    }

    @GetMapping("/getInvProjectPlanById")
    public BaseResponse getInvProjectPlanById(@RequestParam String ippId) {
        log.info("getInvProjectPlanById");
        return invProjectPlanService.getInvProjectPlanById(ippId);
    }

    @GetMapping("/getInvProjectPlanByPIdAndYear")
    public BaseResponse getInvProjectPlanByPIdAndYear(@RequestParam String pId, @RequestParam Integer year, @RequestParam String fundsType) {
        log.info("getInvProjectPlanByPIdAndYear");
        return invProjectPlanService.getInvProjectPlanByPIdAndYear(pId, year, fundsType);
    }
}
