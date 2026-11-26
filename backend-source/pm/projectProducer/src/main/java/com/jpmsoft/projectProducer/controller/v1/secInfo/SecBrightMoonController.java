package com.jpmsoft.projectProducer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecBrightMoonModel;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.secInfo.ISecBrightMoonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@Slf4j
@RestController
@RequestMapping("/v1/brightMoon")
public class SecBrightMoonController {


    @Resource
    private ISecBrightMoonService secBrightMoonService;


    @TenantId
    @PostMapping("/createOrUpdateSecBrightMoon")
    public BaseResponse createOrUpdateSecBrightMoon(@RequestBody SecBrightMoonModel request) {
        log.info("createOrUpdateSecBrightMoon");
        return secBrightMoonService.createOrUpdateSecBrightMoon(request);
    }

    @TenantId
    @PostMapping("/getSecBrightMoonList")
    public BaseResponse getSecBrightMoonList(@RequestBody SecBrightMoonModel request) {
        log.info("getSecBrightMoonList");
        return secBrightMoonService.getSecBrightMoonList(request);
    }

    @GetMapping("/getSecBrightMoonById")
    public BaseResponse getSecBrightMoonById(@RequestParam String sbmId) {
        log.info("getSecBrightMoonById");
        return secBrightMoonService.getSecBrightMoonById(sbmId);
    }
}
