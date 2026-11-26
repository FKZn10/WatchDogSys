package com.jpmsoft.projectProducer.controller.v1.investment;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.pojo.dto.investment.InvSettlementSecondDto;
import com.jpmsoft.projectProducer.service.investment.IInvSettlementSecondService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author: 李文涛 aaron
 * @date: 2022/10/21 11:30
 */
@Slf4j
@RestController
@RequestMapping("/v1/invSettlementSecond")
public class InvSettlementSecondController {

    @Resource
    private IInvSettlementSecondService invSettlementSecondService;
    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createOrUpdateInvSettlementSecond")
    public BaseResponse createOrUpdateInvSettlementSecond(@RequestBody InvSettlementSecondDto request) {
        log.info("createOrUpdateInvSettlementSecond");
        return invSettlementSecondService.createOrUpdateInvSettlementSecond(request);
    }

    @TenantId
    @PostMapping("/getInvSettlementSecondList")
    public BaseResponse getInvSettlementSecondList(@RequestBody InvSettlementSecondDto request, @RequestHeader String token) {
        log.info("getInvSettlementSecondList");
        return invSettlementSecondService.getInvSettlementSecondList(request, redisUtils.getUid(token));
    }

    @GetMapping("/getInvSettlementSecondById")
    public BaseResponse getInvSettlementSecondById(@RequestParam String id) {
        log.info("getInvSettlementSecondById");
        return invSettlementSecondService.getInvSettlementSecondById(id);
    }
}
