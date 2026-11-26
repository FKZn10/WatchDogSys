package com.jpmsoft.projectProducer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecOtherProcurementModel;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.secInfo.ISecOtherProcurementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/otherProcurement")
public class SecOtherProcurementController {
    @Resource
    private ISecOtherProcurementService secOtherProcurementService;

    @TenantId
    @PostMapping("/createOrUpdateSecOtherProcurement")
    public BaseResponse createOrUpdateSecOtherProcurement(@RequestBody SecOtherProcurementModel request) {
        log.info("createOrUpdateSecOtherProcurement");
        return secOtherProcurementService.createOrUpdateSecOtherProcurement(request);
    }

    @TenantId
    @PostMapping("/getSecOtherProcurementList")
    public BaseResponse getSecOtherProcurementList(@RequestBody SecOtherProcurementModel request) {
        log.info("getSecOtherProcurementList");
        return secOtherProcurementService.getSecOtherProcurementList(request);
    }

    @GetMapping("/getSecOtherProcurementById")
    public BaseResponse getSecOtherProcurementById(@RequestParam String sopId) {
        log.info("getSecOtherProcurementById");
        return secOtherProcurementService.getSecOtherProcurementById(sopId);
    }
}
