package com.jpmsoft.projectProducer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.SecInfo.SecAnswerQuestionRequest;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.secInfo.ISecAnswerQuestionService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/answerQuestion")
public class SecAnswerQuestionController {
    @Resource
    private ISecAnswerQuestionService secAnswerQuestionService;
    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createOrUpdateSecAnswerQuestion")
    public BaseResponse createOrUpdateSecAnswerQuestion(@RequestBody SecAnswerQuestionRequest request) {
        log.info("createOrUpdateSecAnswerQuestion");
        return secAnswerQuestionService.createOrUpdateSecAnswerQuestion(request);
    }

    @GetMapping("/deleteSecAnswerQuestionByaqId")
    public BaseResponse deleteSecAnswerQuestionByaqId(@RequestParam String aqId) {
        log.info("deleteSecAnswerQuestionByaqId");
        return secAnswerQuestionService.deleteSecAnswerQuestionByaqId(aqId);
    }

    @GetMapping("/getSecAnswerQuestionByaqId")
    public BaseResponse getSecAnswerQuestionByaqId(@RequestParam String aqId) {
        log.info("getSecAnswerQuestionByaqId");
        return secAnswerQuestionService.getSecAnswerQuestionByaqId(aqId);
    }

    @TenantId
    @PostMapping("/getSecAnswerQuestionListClassInfo")
    public BaseResponse getSecAnswerQuestionListClassInfo(@RequestBody SecAnswerQuestionRequest request, @RequestHeader String token) {
        log.info("getSecAnswerQuestionListClassInfo");
        return secAnswerQuestionService.getSecAnswerQuestionListClassInfo(request, redisUtils.getUserInfoByToken(token));
    }

    @TenantId
    @PostMapping("/getSecAnswerQuestionListClass")
    public BaseResponse getSecAnswerQuestionListClass(@RequestBody SecAnswerQuestionRequest request,@RequestHeader String token) {
        log.info("getSecAnswerQuestionListClass");
        return secAnswerQuestionService.getSecAnswerQuestionListClass(request, redisUtils.getUserInfoByToken(token));
    }

    @TenantId
    @PostMapping("/getSecAnswerQuestionList")
    public BaseResponse getSecAnswerQuestionList(@RequestBody SecAnswerQuestionRequest request, @RequestHeader String token) {
        log.info("getSecAnswerQuestionList");
        return secAnswerQuestionService.getSecAnswerQuestionList(request, redisUtils.getUid(token));
    }

    @TenantId
    @PostMapping("/getSecAnswerQuestionListOne2Many")
    public BaseResponse getSecAnswerQuestionListOne2Many(@RequestBody SecAnswerQuestionRequest request, @RequestHeader String token) {
        log.info("getSecAnswerQuestionListOne2Many");
        return secAnswerQuestionService.getSecAnswerQuestionListOne2Many(request, redisUtils.getUid(token));
    }


    @GetMapping("/approveSyncDocumentBidTimeAndLocation")
    public BaseResponse approveSyncDocumentBidTimeAndLocation(@RequestParam String aqId) {
        log.info("approveSyncDocumentBidTimeAndLocation");
        return secAnswerQuestionService.approveSyncDocumentBidTimeAndLocation(aqId);
    }
}
