package com.jpmsoft.projectProducer.controller.v1.other;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.NoticeInfoModel;
import com.jpmsoft.projectProducer.pojo.dto.blxw.BlxwInfoDto;
import com.jpmsoft.projectProducer.pojo.request.blxw.BlxwInfoRequest;
import com.jpmsoft.projectProducer.service.blxw.IBlxwInfoService;
import com.jpmsoft.projectProducer.service.other.INoticeInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/NoticeInfo")
@Slf4j
public class NoticeInfoController {
    @Autowired
    private INoticeInfoService noticeInfoService;
    @PostMapping("/saveOrUpdateNoticeInfo")
    public BaseResponse saveOrUpdateNoticeInfo(@RequestBody NoticeInfoModel request) {
        log.info("saveOrUpdateNoticeInfo");
        return noticeInfoService.saveOrUpdateNoticeInfo(request);
    }

    @PostMapping("/approvalData")
    public BaseResponse approvalData(@RequestBody NoticeInfoModel request) {
        log.info("approvalData");
        return noticeInfoService.approvalData(request);
    }
    @PostMapping("/getNoticeInfoList")
    public BaseResponse getNoticeInfoList(@RequestBody NoticeInfoModel request,@RequestHeader String token) {
        log.info("getNoticeInfoList");
        return noticeInfoService.getNoticeInfoList(request,token);
    }
    @GetMapping("/getNoticeInfoDetail")
    public BaseResponse getNoticeInfoDetail(@RequestParam String id) {
        log.info("getNoticeInfoDetail");
        return noticeInfoService.getNoticeInfoDetail(id);
    }
}
