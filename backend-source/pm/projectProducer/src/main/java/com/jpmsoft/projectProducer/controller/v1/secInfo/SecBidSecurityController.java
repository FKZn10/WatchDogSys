package com.jpmsoft.projectProducer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecBidSecurityModel;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.secInfo.ISecBidSecurityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/bidSecurity")
public class SecBidSecurityController {


    @Resource
    private ISecBidSecurityService secBidSecurityService;


    @TenantId
    @PostMapping("/createOrUpdateSecBidSecurity")
    public BaseResponse createOrUpdateSecBidSecurity(@RequestBody SecBidSecurityModel request) {
        log.info("createOrUpdateSecBidSecurity");
        return secBidSecurityService.createOrUpdateSecBidSecurity(request);
    }

    @TenantId
    @PostMapping("/getSecBidSecurityList")
    public BaseResponse getSecBidSecurityList(@RequestBody SecBidSecurityModel request) {
        log.info("getSecBidSecurityList");
        return secBidSecurityService.getSecBidSecurityList(request);
    }

    @GetMapping("/getSecBidSecurityById")
    public BaseResponse getSecBidSecurityById(@RequestParam String sbsId) {
        log.info("getSecBidSecurityById");
        return secBidSecurityService.getSecBidSecurityById(sbsId);
    }
}
