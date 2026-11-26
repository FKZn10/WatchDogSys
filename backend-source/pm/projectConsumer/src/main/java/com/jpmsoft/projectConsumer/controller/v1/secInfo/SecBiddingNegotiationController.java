package com.jpmsoft.projectConsumer.controller.v1.secInfo;


import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.SecInfo.SecBiddingNegotiationDto;
import com.jpmsoft.projectConsumer.feign.SecFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/v1/biddingNegotiation")
public class SecBiddingNegotiationController {


    @Autowired
    private SecFeign secFeign;

    @PostMapping("/createOrUpdateSecBiddingNegotiation")
    public BaseResponse createOrUpdateSecBiddingNegotiation(@RequestBody SecBiddingNegotiationDto request) {
        log.info("createOrUpdateSecBiddingNegotiation");
        return secFeign.createOrUpdateSecBiddingNegotiation(request);
    }


    @PostMapping("/getSecBiddingNegotiationListClassInfo")
    public BaseResponse getSecBiddingNegotiationListClassInfo(@RequestBody SecBiddingNegotiationDto request,@RequestHeader String token) {
        log.info("getSecBiddingNegotiationListClassInfo");
        return secFeign.getSecBiddingNegotiationListClassInfo(request,token);
    }


    @PostMapping("/getSecBiddingNegotiationListClass")
    public BaseResponse getSecBiddingNegotiationListClass(@RequestBody SecBiddingNegotiationDto request,@RequestHeader String token) {
        log.info("getSecBiddingNegotiationListClass");
        return secFeign.getSecBiddingNegotiationListClass(request,token);
    }

    @GetMapping("/getSecBiddingNegotiationByBnId")
    public BaseResponse getSecBiddingNegotiationByBnId(@RequestParam String bnId) {
        log.info("getSecBiddingNegotiationByBnId");
        return secFeign.getSecBiddingNegotiationByBnId(bnId);
    }

    @GetMapping("/deleteSecBiddingNegotiationByBnId")
    public BaseResponse deleteSecBiddingNegotiationByBnId(@RequestParam String bnId) {
        log.info("deleteSecBiddingNegotiationByBnId");
        return secFeign.deleteSecBiddingNegotiationByBnId(bnId);
    }


}
