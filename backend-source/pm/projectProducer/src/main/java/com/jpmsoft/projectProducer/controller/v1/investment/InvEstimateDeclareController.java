package com.jpmsoft.projectProducer.controller.v1.investment;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.InvestmentControl.InvEstimateDeclareRequest;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.investment.IInvEstimateDeclareService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/invEstimateDeclare")
public class InvEstimateDeclareController {
    @Resource
    private IInvEstimateDeclareService invEstimateDeclareService;

    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createOrUpdateInvEstimateDeclare")
    public BaseResponse createOrUpdateInvEstimateDeclare(@RequestBody InvEstimateDeclareRequest request) {
        log.info("createOrUpdateInvEstimateDeclare");
        return invEstimateDeclareService.createOrUpdateInvEstimateDeclare(request);
    }

    @TenantId
    @PostMapping("/getInvEstimateDeclareList")
    public BaseResponse getInvEstimateDeclareList(@RequestBody InvEstimateDeclareRequest request, @RequestHeader String token) {
        log.info("getInvEstimateDeclareList");
        return invEstimateDeclareService.getInvEstimateDeclareList(request, redisUtils.getUid(token));
    }

    @GetMapping("/getInvEstimateDeclareByBdId")
    public BaseResponse getInvEstimateDeclareByBdId(@RequestParam String edId) {
        log.info("getInvEstimateDeclareByBdId");
        return invEstimateDeclareService.getInvEstimateDeclareByEdId(edId);
    }

    @GetMapping("/deleteInvEstimateDeclareByBdId")
    public BaseResponse deleteInvEstimateDeclareByBdId(@RequestParam String edId) {
        log.info("deleteInvEstimateDeclareByBdId");
        return invEstimateDeclareService.deleteInvEstimateDeclareByEdId(edId);
    }

    @PostMapping("/getInvEstimateDeclareDtlByPIds")
    public BaseResponse getInvEstimateDeclareDtlByPIds(@RequestBody List<String> pIds) {
        log.info("getInvEstimateDeclareDtlByPIds");
        return invEstimateDeclareService.getInvEstimateDeclareDtlByPIds(pIds);
    }

    @GetMapping("/getInvEstimateDeclareByPId")
    public BaseResponse getInvEstimateDeclareByPId(@RequestParam String pId) {
        log.info("getInvEstimateDeclareDtlByPIds");
        return invEstimateDeclareService.getInvEstimateDeclareByPId(pId);
    }

    @GetMapping("/getInvEstimateDeclareAndDtlByPid")
    public BaseResponse getInvEstimateDeclareAndDtlByPid(@RequestParam String pId) {
        log.info("getInvEstimateDeclareAndDtlByPid");
        return invEstimateDeclareService.getInvEstimateDeclareAndDtlByPid(pId);
    }
}
