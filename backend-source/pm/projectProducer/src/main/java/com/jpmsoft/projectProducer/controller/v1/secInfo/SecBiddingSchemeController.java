package com.jpmsoft.projectProducer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecBiddingSchemeModel;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.secInfo.ISecBiddingSchemeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@Slf4j
@RestController
@RequestMapping("/v1/biddingScheme")
public class SecBiddingSchemeController {

    @Resource
    private ISecBiddingSchemeService secBiddingSchemeService;

    @TenantId
    @PostMapping("/createOrUpdateSecBiddingScheme")
    public BaseResponse createOrUpdateSecBiddingScheme(@RequestBody SecBiddingSchemeModel request) {
        log.info("createOrUpdateSecBiddingScheme");
        return secBiddingSchemeService.createOrUpdateSecBiddingScheme(request);
    }

    @TenantId
    @PostMapping("/getSecBiddingSchemeList")
    public BaseResponse getSecBiddingSchemeList(@RequestBody SecBiddingSchemeModel request) {
        log.info("getSecBiddingSchemeList");
        return secBiddingSchemeService.getSecBiddingSchemeList(request);
    }

    @GetMapping("/getSecBiddingSchemeById")
    public BaseResponse getSecBiddingSchemeById(@RequestParam String bsId) {
        log.info("getSecBiddingSchemeById");
        return secBiddingSchemeService.getSecBiddingSchemeById(bsId);
    }

    @PostMapping("/updateSecBiddingScheme")
    public BaseResponse updateSecBiddingScheme(@RequestBody SecBiddingSchemeModel request) {
        log.info("updateSecBiddingScheme");
        return secBiddingSchemeService.updateSecBiddingScheme(request);
    }

}
