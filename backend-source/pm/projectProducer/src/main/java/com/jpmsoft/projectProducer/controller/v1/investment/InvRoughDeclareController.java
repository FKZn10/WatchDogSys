package com.jpmsoft.projectProducer.controller.v1.investment;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.InvestmentControl.InvRoughDeclareRequest;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.investment.IInvRoughDeclareService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/invRoughDeclare")
public class InvRoughDeclareController {
    @Resource
    private IInvRoughDeclareService invRoughDeclareService;

    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createOrUpdateInvRoughDeclare")
    public BaseResponse createOrUpdateInvRoughDeclare(@RequestBody InvRoughDeclareRequest request) {
        log.info("createOrUpdateInvRoughDeclare");
        return invRoughDeclareService.createOrUpdateInvRoughDeclare(request);
    }

    @TenantId
    @PostMapping("/getInvRoughDeclareList")
    public BaseResponse getInvRoughDeclareList(@RequestBody InvRoughDeclareRequest request, @RequestHeader String token) {
        log.info("getInvRoughDeclareList");
        return invRoughDeclareService.getInvRoughDeclareList(request, redisUtils.getUid(token));
    }

    @GetMapping("/getInvRoughDeclareByBdId")
    public BaseResponse getInvRoughDeclareByBdId(@RequestParam String rdId) {
        log.info("getInvRoughDeclareByBdId");
        return invRoughDeclareService.getInvRoughDeclareByRdId(rdId);
    }

    @GetMapping("/deleteInvRoughDeclareByBdId")
    public BaseResponse deleteInvRoughDeclareByBdId(@RequestParam String rdId) {
        log.info("deleteInvRoughDeclareByBdId");
        return invRoughDeclareService.deleteInvRoughDeclareByRdId(rdId);
    }

    @PostMapping("/getInvRoughDeclareDtlByPIds")
    public BaseResponse getInvRoughDeclareDtlByPIds(@RequestBody List<String> pIds) {
        log.info("getInvRoughDeclareDtlByPIds");
        return invRoughDeclareService.getInvRoughDeclareDtlByPIds(pIds);
    }

    @GetMapping("/getInvRoughDeclareByPId")
    public BaseResponse getInvRoughDeclareByPId(@RequestParam String pId) {
        log.info("getInvRoughDeclareDtlByPIds");
        return invRoughDeclareService.getInvRoughDeclareByPId(pId);
    }

    @GetMapping("/getInvRoughDeclareAndDtlByPid")
    public BaseResponse getInvRoughDeclareAndDtlByPid(@RequestParam String pId) {
        log.info("getInvRoughDeclareAndDtlByPid");
        return invRoughDeclareService.getInvRoughDeclareAndDtlByPid(pId);
    }
}
