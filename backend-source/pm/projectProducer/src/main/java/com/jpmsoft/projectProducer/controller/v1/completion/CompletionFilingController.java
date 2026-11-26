package com.jpmsoft.projectProducer.controller.v1.completion;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Completion.CompletionFilingRequest;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.completion.ICompletionFilingService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/filing")
public class CompletionFilingController {

    @Resource
    private ICompletionFilingService completionFilingService;

    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createOrUpdateCompletionFiling")
    public BaseResponse createOrUpdateCompletionFiling(@RequestBody CompletionFilingRequest request) {
        log.info("createOrUpdateCompletionFiling");
        return completionFilingService.createOrUpdateCompletionFiling(request);
    }

    @TenantId
    @PostMapping("/getCompletionFilingList")
    public BaseResponse getCompletionFilingList(@RequestBody CompletionFilingRequest request, @RequestHeader String token) {
        log.info("getCompletionFilingList");
        return completionFilingService.getCompletionFilingList(request, redisUtils.getUid(token));
    }

    @TenantId
    @PostMapping("/getCompletionFilingListNoAuth")
    public BaseResponse getCompletionFilingListNoAuth(@RequestBody CompletionFilingRequest request) {
        log.info("getCompletionFilingListNoAuth");
        return completionFilingService.getCompletionFilingListNoAuth(request);
    }


    @GetMapping("/getCompletionFilingById")
    public BaseResponse getCompletionFilingById(@RequestParam String cfId) {
        log.info("getCompletionFilingById");
        return completionFilingService.getCompletionFilingById(cfId);
    }
}
