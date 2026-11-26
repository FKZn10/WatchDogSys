package com.jpmsoft.projectProducer.controller.v1.purchase;


import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.purchase.ProPurchaseDto;
import com.jpmsoft.projectProducer.service.purchase.IProPurchaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/purchase")
public class ProPurchaseController {

    @Resource
    private IProPurchaseService proPurchaseService;

    @PostMapping("/createOrUpdatePropurchase")
    public BaseResponse createOrUpdatePropurchase(@RequestBody ProPurchaseDto request) {
        log.info("pp createOrUpdatePropurchase");
        return proPurchaseService.createOrUpdatePropurchase(request);
    }

    @PostMapping("/createPropurchase")
    public BaseResponse createPropurchase(@RequestBody ProPurchaseDto request) {
        log.info("pp createPropurchase");
        return proPurchaseService.createPropurchase(request);
    }

    @PostMapping("/updatePropurchase")
    public BaseResponse updatePropurchase(@RequestBody ProPurchaseDto request) {
        log.info("pp updatePropurchase");
        return proPurchaseService.updatePropurchase(request);
    }

    @GetMapping("/getPropurchaseById")
    public BaseResponse getPropurchaseById(@RequestParam String id) {
        log.info("pp getPropurchaseById");
        return proPurchaseService.getPropurchaseById(id);
    }

    @PostMapping("/getPropurchaseList")
    public BaseResponse getPropurchaseList(@RequestBody ProPurchaseDto request) {
        log.info("pp getPropurchaseList");
        return proPurchaseService.getPropurchaseList(request);
    }

}
