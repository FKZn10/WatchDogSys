package com.jpmsoft.projectConsumer.controller.v1.other;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.NoticeInfoCloseModel;
import com.jpmsoft.projectConsumer.feign.BaseFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/noticeInfoClose")
@Slf4j
public class NoticeInfoCloseController {
    @Autowired
    private BaseFeign baseFeign;
    @PostMapping("/saveOrUpdateNoticeInfoClose")
    public BaseResponse saveOrUpdateNoticeInfoClose(@RequestBody NoticeInfoCloseModel request) {
        log.info("saveOrUpdateNoticeInfoClose");
        return baseFeign.saveOrUpdateNoticeInfoClose(request);
    }

    @PostMapping("/approvalDataNoticeInfoClose")
    public BaseResponse approvalDataNoticeInfoClose(@RequestBody NoticeInfoCloseModel request) {
        log.info("approvalDataNoticeInfoClose");
        return baseFeign.approvalDataNoticeInfoClose(request);
    }
    @PostMapping("/getNoticeInfoCloseList")
    public BaseResponse getNoticeInfoList(@RequestBody NoticeInfoCloseModel request,@RequestHeader String token) {
        log.info("getNoticeInfoCloseList");
        return baseFeign.getNoticeInfoCloseList(request,token);
    }
    @GetMapping("/getNoticeInfoCloseDetail")
    public BaseResponse getNoticeInfoCloseDetail(@RequestParam String id) {
        log.info("getNoticeInfoCloseDetail");
        return baseFeign.getNoticeInfoCloseDetail(id);
    }
}