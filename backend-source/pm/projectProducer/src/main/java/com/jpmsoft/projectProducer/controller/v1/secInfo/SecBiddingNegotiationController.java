package com.jpmsoft.projectProducer.controller.v1.secInfo;


import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.SecInfo.SecBiddingNegotiationDto;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.secInfo.ISecBiddingNegotiationService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/biddingNegotiation")
public class SecBiddingNegotiationController {

    @Resource
    private ISecBiddingNegotiationService secBiddingNegotiationService;

    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createOrUpdateSecBiddingNegotiation")
    public BaseResponse createOrUpdateSecBiddingNegotiation(@RequestBody SecBiddingNegotiationDto request) {
        log.info("createOrUpdateSecBiddingNegotiation");
        return secBiddingNegotiationService.createOrUpdateSecBiddingNegotiation(request);
    }

    @TenantId
    @PostMapping("/getSecBiddingNegotiationListClassInfo")
    public BaseResponse getSecBiddingNegotiationListClassInfo(@RequestBody SecBiddingNegotiationDto request, @RequestHeader String token) {
        log.info("getSecBiddingNegotiationListClassInfo");
        return secBiddingNegotiationService.getSecBiddingNegotiationListClassInfo(request, redisUtils.getUserInfoByToken(token));
    }

    @TenantId
    @PostMapping("/getSecBiddingNegotiationListClass")
    public BaseResponse getSecBiddingNegotiationListClass(@RequestBody SecBiddingNegotiationDto request, @RequestHeader String token) {
        log.info("getSecBiddingNegotiationListClass");
        return secBiddingNegotiationService.getSecBiddingNegotiationListClass(request, redisUtils.getUserInfoByToken(token));
    }

    @GetMapping("/getSecBiddingNegotiationByBnId")
    public BaseResponse getSecBiddingNegotiationByBnId(@RequestParam String bnId) {
        log.info("getSecBiddingNegotiationByBnId");
        return secBiddingNegotiationService.getSecBiddingNegotiationByBnId(bnId);
    }

    @GetMapping("/deleteSecBiddingNegotiationByBnId")
    public BaseResponse deleteSecBiddingNegotiationByBnId(@RequestParam String bnId) {
        log.info("deleteSecBiddingNegotiationByBnId");
        return secBiddingNegotiationService.deleteSecBiddingNegotiationByBnId(bnId);
    }


}
