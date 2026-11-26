package com.jpmsoft.projectProducer.controller.v1.other;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.NoticeInfoCloseModel;
import com.jpmsoft.projectProducer.service.other.INoticeInfoCloseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/noticeInfoClose")
@Slf4j
public class NoticeInfoCloseController{
    @Autowired
    private INoticeInfoCloseService noticeInfoCloseService;
    @PostMapping("/saveOrUpdateNoticeInfoClose")
    public BaseResponse saveOrUpdateNoticeInfoClose(@RequestBody NoticeInfoCloseModel request) {
        log.info("saveOrUpdateNoticeInfoClose");
        return noticeInfoCloseService.saveOrUpdateNoticeInfoClose(request);
    }

    @PostMapping("/approvalData")
    public BaseResponse approvalData(@RequestBody NoticeInfoCloseModel request) {
        log.info("approvalData");
        return noticeInfoCloseService.approvalData(request);
    }
    @PostMapping("/getNoticeInfoCloseList")
    public BaseResponse getNoticeInfoCloseList(@RequestBody NoticeInfoCloseModel request,@RequestHeader String token) {
        log.info("getNoticeInfoCloseList");
        return noticeInfoCloseService.getNoticeInfoCloseList(request,token);
    }
    @GetMapping("/getNoticeInfoCloseDetail")
    public BaseResponse getNoticeInfoDetail(@RequestParam String id) {
        log.info("getNoticeInfoCloseDetail");
        return noticeInfoCloseService.getNoticeInfoCloseDetail(id);
    }
}