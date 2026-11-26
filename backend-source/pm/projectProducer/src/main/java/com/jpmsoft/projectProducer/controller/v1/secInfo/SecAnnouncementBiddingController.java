package com.jpmsoft.projectProducer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecAnnouncementBiddingModel;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.secInfo.ISecAnnouncementBiddingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@Slf4j
@RestController
@RequestMapping("/v1/announcement")
public class SecAnnouncementBiddingController {

    @Resource
    private ISecAnnouncementBiddingService secAnnouncementBiddingService;


    @TenantId
    @PostMapping("/createOrUpdateSecAnnouncementBidding")
    public BaseResponse createOrUpdateSecAnnouncementBidding(@RequestBody SecAnnouncementBiddingModel request) {
        log.info("createOrUpdateSecAnnouncementBidding");
        return secAnnouncementBiddingService.createOrUpdateSecAnnouncementBidding(request);
    }

    @TenantId
    @PostMapping("/getSecAnnouncementBiddingList")
    public BaseResponse getSecAnnouncementBiddingList(@RequestBody SecAnnouncementBiddingModel request) {
        log.info("getSecAnnouncementBiddingList");
        return secAnnouncementBiddingService.getSecAnnouncementBiddingList(request);
    }

    @GetMapping("/getSecAnnouncementBiddingById")
    public BaseResponse getSecAnnouncementBiddingById(@RequestParam String sabId) {
        log.info("getSecAnnouncementBiddingById");
        return secAnnouncementBiddingService.getSecAnnouncementBiddingById(sabId);
    }
}
