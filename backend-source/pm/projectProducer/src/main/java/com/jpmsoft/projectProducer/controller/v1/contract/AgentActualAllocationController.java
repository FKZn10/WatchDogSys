package com.jpmsoft.projectProducer.controller.v1.contract;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Contract.AgentActualAllocationModel;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.contract.IAgentActualAllocationService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/aaAllocation")
public class AgentActualAllocationController {

    @Resource
    private IAgentActualAllocationService agentActualAllocationService;

    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createOrUpdateAgentActualAllocation")
    public BaseResponse createOrUpdateAgentActualAllocation(@RequestBody AgentActualAllocationModel request) {
        log.info("createOrUpdateAgentActualAllocation");
        return agentActualAllocationService.createOrUpdateAgentActualAllocation(request);
    }

    @TenantId
    @PostMapping("/getAgentActualAllocationList")
    public BaseResponse getAgentActualAllocationList(@RequestBody AgentActualAllocationModel request, @RequestHeader String token) {
        log.info("getAgentActualAllocationList");
        return agentActualAllocationService.getAgentActualAllocationList(request, redisUtils.getUid(token));
    }

    @GetMapping("/getAgentActualAllocationById")
    public BaseResponse getAgentActualAllocationById(@RequestParam String aalId) {
        log.info("getAgentActualAllocationById");
        return agentActualAllocationService.getAgentActualAllocationById(aalId);
    }

    @GetMapping("/getAalThisPayByConId")
    public BaseResponse getAalThisPayByConId(@RequestParam String conId) {
        log.info("getAalThisPayByConId");
        return agentActualAllocationService.getAalThisPayByConId(conId);
    }

    @GetMapping("/getAalThisPayMoneyByAgentConId")
    public BaseResponse getAalThisPayMoneyByAgentConId(@RequestParam String aalAgentConId) {
        log.info("getAalThisPayMoneyByAgentConId");
        return agentActualAllocationService.getAalThisPayMoneyByAgentConId(aalAgentConId);
    }
}
