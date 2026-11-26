package com.jpmsoft.projectProducer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecPriceResultsfModel;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.secInfo.ISecPriceResultsfService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/priceResultsf")
public class SecPriceResultsfController {

    @Resource
    private ISecPriceResultsfService secPriceResultsfService;


    @TenantId
    @PostMapping("/createOrUpdateSecPriceResultsf")
    public BaseResponse createOrUpdateSecPriceResultsf(@RequestBody SecPriceResultsfModel request) {
        log.info("createOrUpdateSecPriceResultsf");
        return secPriceResultsfService.createOrUpdateSecPriceResultsf(request);
    }

    @TenantId
    @PostMapping("/getSecPriceResultsfList")
    public BaseResponse getSecPriceResultsfList(@RequestBody SecPriceResultsfModel request) {
        log.info("getSecPriceResultsfList");
        return secPriceResultsfService.getSecPriceResultsfList(request);
    }

    @GetMapping("/deleteSecPriceResultsfBySprId")
    public BaseResponse deleteSecPriceResultsfBySprId(@RequestParam String sprId) {
        log.info("deleteSecPriceResultsfBySprId");
        return secPriceResultsfService.deleteSecPriceResultsfBySprId(sprId);
    }

    @GetMapping("/getSecPriceResultsfBySprId")
    public BaseResponse getSecPriceResultsfBySprId(@RequestParam String sprId) {
        log.info("getSecPriceResultsfBySprId");
        return secPriceResultsfService.getSecPriceResultsfBySprId(sprId);
    }
}
