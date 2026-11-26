package com.jpmsoft.projectConsumer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecAnnouncementBiddingModel;
import com.jpmsoft.projectConsumer.feign.SecFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "招标公告发布", value = "招标公告发布")
@RequestMapping("/v1/announcement")
public class SecAnnouncementBiddingController {
    @Autowired
    private SecFeign secFeign;

    @ApiOperation(value = "创建或修改招标公告发布")
    @PostMapping("/createOrUpdateSecAnnouncementBidding")
    public BaseResponse createOrUpdateSecAnnouncementBidding(@RequestBody SecAnnouncementBiddingModel request) {
        log.info("createOrUpdateSecAnnouncementBidding");
        return secFeign.createOrUpdateSecAnnouncementBidding(request);
    }

    @ApiOperation(value = "分页查询招标公告发布")
    @PostMapping("/getSecAnnouncementBiddingList")
    public BaseResponse getSecAnnouncementBiddingList(@RequestBody SecAnnouncementBiddingModel request) {
        log.info("getSecAnnouncementBiddingList");
        return secFeign.getSecAnnouncementBiddingList(request);
    }

    @ApiOperation(value = "ID查询招标公告发布")
    @GetMapping("/getSecAnnouncementBiddingById")
    public BaseResponse getSecAnnouncementBiddingById(@RequestParam String sabId) {
        log.info("getSecAnnouncementBiddingById");
        return secFeign.getSecAnnouncementBiddingById(sabId);
    }
}
