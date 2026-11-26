package com.jpmsoft.projectProducer.controller.v1.contract;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Contract.AgentFundAdvanceModel;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.contract.IAgentFundAdvanceListService;
import com.jpmsoft.projectProducer.service.contract.IAgentFundAdvanceService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@Slf4j
@RestController
@RequestMapping("/v1/afAdvance")
public class AgentFundAdvanceController {
    @Resource
    private IAgentFundAdvanceService agentFundAdvanceService;
    @Resource
    private IAgentFundAdvanceListService agentFundAdvanceListService;

    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createOrUpdateAgentFundAdvance")
    public BaseResponse createOrUpdateAgentFundAdvance(@RequestBody AgentFundAdvanceModel request) {
        log.info("createOrUpdateAgentFundAdvance");
        return agentFundAdvanceService.createOrUpdateAgentFundAdvance(request);
    }

    @TenantId
    @PostMapping("/getAgentFundAdvanceList")
    public BaseResponse getAgentFundAdvanceList(@RequestBody AgentFundAdvanceModel request, @RequestHeader String token) {
        log.info("getAgentFundAdvanceList");
        return agentFundAdvanceService.getAgentFundAdvanceList(request, redisUtils.getUid(token));
    }

    @GetMapping("/getAgentFundAdvanceById")
    public BaseResponse getAgentFundAdvanceById(@RequestParam String afaId) {
        log.info("getAgentFundAdvanceById");
        return agentFundAdvanceService.getAgentFundAdvanceById(afaId);
    }

    @GetMapping("/getAfaThisMoneyByConId")
    public BaseResponse getAfaThisMoneyByConId(@RequestParam String conId) {
        log.info("getAfaThisMoneyByConId");
        return agentFundAdvanceService.getAfaThisMoneyByConId(conId);
    }


    @GetMapping("/getAfalThisMoneyByAfalConId")
    public BaseResponse getAfalThisMoneyByAfalConId(@RequestParam String afalConId) {
        log.info("getAfalThisMoneyByAfalConId");
        return agentFundAdvanceListService.getAfalThisMoneyByAfalConId(afalConId);
    }
}
