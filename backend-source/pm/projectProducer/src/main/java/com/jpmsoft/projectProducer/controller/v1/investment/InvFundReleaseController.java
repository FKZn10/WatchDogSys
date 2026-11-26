package com.jpmsoft.projectProducer.controller.v1.investment;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.InvestmentControl.InvFundReleaseRequest;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.investment.IInvFundReleaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/invFundRelease")
public class InvFundReleaseController {

    @Resource
    private IInvFundReleaseService invFundReleaseService;

    @TenantId
    @PostMapping("/createOrUpdateInvFundRelease")
    public BaseResponse createOrUpdateInvFundRelease(@RequestBody InvFundReleaseRequest request) {
        log.info("createOrUpdateInvFundRelease");
        return invFundReleaseService.createOrUpdateInvFundRelease(request);
    }

    @GetMapping("/getInvFundReleaseByIfrId")
    public BaseResponse getInvFundReleaseByIfrId(@RequestParam String ifrId) {
        log.info("getInvFundReleaseByIfrId");
        return invFundReleaseService.getInvFundReleaseByIfrId(ifrId);
    }

    @TenantId
    @PostMapping("/getInvFundReleaseList")
    public BaseResponse getInvFundReleaseList(@RequestBody InvFundReleaseRequest request) {
        log.info("getInvFundReleaseList");
        return invFundReleaseService.getInvFundReleaseList(request);
    }
}
