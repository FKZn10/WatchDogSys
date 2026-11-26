package com.jpmsoft.projectProducer.controller.v1.other;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.ReplyInfoModel;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.projectProducer.service.other.IReplyInfoService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/v1/replyInfo")
public class ReplyInfoController {

    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private IReplyInfoService fundsService;

    @PostMapping("/createOrUpdateReplyInfo")
    public BaseResponse createOrUpdateReplyInfo(@RequestBody ReplyInfoModel request) {
        log.info("ReplyInfoModel:{}",JacksonUtils.toJSONString(request));
        log.info("createOrUpdateReplyInfo");
        return fundsService.createOrUpdateReplyInfo(request);
    }
    @PostMapping("/getReplyInfoList")
    public BaseResponse getReplyInfoList(@RequestBody ReplyInfoModel request,@RequestHeader String token) {
        log.info("getReplyInfoList");
        return fundsService.getReplyInfoList(request,redisUtils.getUid(token));
    }
    @GetMapping("/getReplyInfoById")
    public BaseResponse getReplyInfoById(@RequestParam String id) {
        log.info("getReplyInfoById");
        return fundsService.getReplyInfoById(id);
    }
}
