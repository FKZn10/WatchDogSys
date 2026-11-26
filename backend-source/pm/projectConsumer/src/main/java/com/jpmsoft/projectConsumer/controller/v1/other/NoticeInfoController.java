package com.jpmsoft.projectConsumer.controller.v1.other;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.NoticeInfoModel;
import com.jpmsoft.projectConsumer.feign.BaseFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/NoticeInfo")
@Slf4j
public class NoticeInfoController {
    @Autowired
    private BaseFeign baseFeign;
    @PostMapping("/saveOrUpdateNoticeInfo")
    public BaseResponse saveOrUpdateNoticeInfo(@RequestBody NoticeInfoModel request) {
        log.info("saveOrUpdateNoticeInfo");
        return baseFeign.saveOrUpdateNoticeInfo(request);
    }

    @PostMapping("/approvalData")
    public BaseResponse approvalData(@RequestBody NoticeInfoModel request) {
        log.info("approvalData");
        return baseFeign.approvalData(request);
    }
    @PostMapping("/getNoticeInfoList")
    public BaseResponse getNoticeInfoList(@RequestBody NoticeInfoModel request,@RequestHeader String token) {
        log.info("getNoticeInfoList");
        return baseFeign.getNoticeInfoList(request,token);
    }
    @GetMapping("/getNoticeInfoDetail")
    public BaseResponse getNoticeInfoDetail(@RequestParam String id) {
        log.info("getNoticeInfoDetail");
        return baseFeign.getNoticeInfoDetail(id);
    }
}
