package com.jpmsoft.projectProducer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecBidderEliminationModel;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.secInfo.ISecBidderEliminationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@Slf4j
@RestController
@RequestMapping("/v1/bidderElimination")
public class SecBidderEliminationController {

    @Resource
    private ISecBidderEliminationService secBidderEliminationService;

    @TenantId
    @PostMapping("/createOrUpdateSecBidderElimination")
    public BaseResponse createOrUpdateSecBidderElimination(@RequestBody SecBidderEliminationModel request) {
        log.info("createOrUpdateSecBidderElimination");
        return secBidderEliminationService.createOrUpdateSecBidderElimination(request);
    }

    @TenantId
    @PostMapping("/getSecBidderEliminationList")
    public BaseResponse getSecBidderEliminationList(@RequestBody SecBidderEliminationModel request) {
        log.info("getSecBidderEliminationList");
        return secBidderEliminationService.getSecBidderEliminationList(request);
    }

    @GetMapping("/getSecBidderEliminationById")
    public BaseResponse getSecBidderEliminationById(@RequestParam String sbeId) {
        log.info("getSecBidderEliminationById");
        return secBidderEliminationService.getSecBidderEliminationById(sbeId);
    }
}
