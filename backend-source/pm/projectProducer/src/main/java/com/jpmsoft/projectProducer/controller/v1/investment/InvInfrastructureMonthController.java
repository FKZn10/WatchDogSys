package com.jpmsoft.projectProducer.controller.v1.investment;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.pojo.dto.investment.InvInfrastructureMonthDto;
import com.jpmsoft.projectProducer.pojo.dto.investment.InvInfrastructureQueryDto;
import com.jpmsoft.projectProducer.service.investment.IInvInfrastructureMonthDtlService;
import com.jpmsoft.projectProducer.service.investment.IInvInfrastructureMonthService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author: 李文涛 aaron
 * @date: 2022/8/5 11:13
 */
@Slf4j
@RestController
@RequestMapping("/v1/invInfrastructureMonth")
public class InvInfrastructureMonthController {

    @Resource
    private IInvInfrastructureMonthService iInvInfrastructureMonthService;
    @Resource
    private IInvInfrastructureMonthDtlService invInfrastructureMonthDtlService;
    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createOrUpdateInvInfrastructureMonth")
    public BaseResponse createOrUpdateInvInfrastructureMonth(@RequestBody InvInfrastructureMonthDto request) {
        log.info("createOrUpdateInvInfrastructureMonth");
        return iInvInfrastructureMonthService.createOrUpdateInvInfrastructureMonth(request);
    }

    @GetMapping("/getInvInfrastructureMonthById")
    public BaseResponse getInvInfrastructureMonthById(@RequestParam String id) {
        log.info("getInvInfrastructureMonthById");
        return iInvInfrastructureMonthService.getInvInfrastructureMonthById(id);
    }

    @TenantId
    @PostMapping("/getInvInfrastructureMonthDtlList")
    public BaseResponse getInvInfrastructureMonthDtlList(@RequestBody InvInfrastructureQueryDto request, @RequestHeader String token) {
        log.info("getInvInfrastructureMonthDtlList");
        return invInfrastructureMonthDtlService.getInvInfrastructureMonthDtlList(request, redisUtils.getUid(token));
    }
}
