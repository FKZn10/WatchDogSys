package com.jpmsoft.projectConsumer.controller.v1.other;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.ReplyInfoModel;
import com.jpmsoft.projectConsumer.feign.BaseFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/v1/replyInfo")
public class ReplyInfoController {

    @Autowired
    private BaseFeign baseFeign;
    @PostMapping("/createOrUpdateReplyInfo")
    public BaseResponse createOrUpdateReplyInfo(@RequestBody ReplyInfoModel request) {
        log.info("createOrUpdateReplyInfo");
        return baseFeign.createOrUpdateReplyInfo(request);
    }
    @PostMapping("/getReplyInfoList")
    public BaseResponse getReplyInfoList(@RequestBody ReplyInfoModel request,@RequestHeader String token) {
        log.info("getReplyInfoList");
        return baseFeign.getReplyInfoList(request,token);
    }
    @GetMapping("/getReplyInfoById")
    public BaseResponse getReplyInfoById(@RequestParam String id) {
        log.info("getReplyInfoById");
        return baseFeign.getReplyInfoById(id);
    }
}
