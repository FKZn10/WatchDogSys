package com.jpmsoft.projectProducer.controller.v1.supervision;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Supervisor.ConSupPlanRequest;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.supervision.IConSupPlanService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/conSupPlan")
public class ConSupPlanController {

    @Resource
    private IConSupPlanService conSupPlanService;
    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createOrUpdateConSupPlan")
    public BaseResponse createOrUpdateConSupPlan(@RequestBody ConSupPlanRequest request) {
        log.info("createOrUpdateConSupPlan");
        return conSupPlanService.createOrUpdateConSupPlan(request);
    }

    @TenantId
    @PostMapping("/getConSupPlanList")
    public BaseResponse getConSupPlanList(@RequestBody ConSupPlanRequest request, @RequestHeader String token) {
        log.info("getConSupPlanList");
        return conSupPlanService.getConSupPlanList(request, redisUtils.getUid(token));
    }

    @GetMapping("/getConSupPlanById")
    public BaseResponse getConSupPlanById(@RequestParam String spId) {
        log.info("getConSupPlanById");
        return conSupPlanService.getConSupPlanById(spId);
    }
}
