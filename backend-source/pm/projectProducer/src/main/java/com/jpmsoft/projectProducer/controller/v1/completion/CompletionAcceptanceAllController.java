package com.jpmsoft.projectProducer.controller.v1.completion;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Completion.CompletionAcceptanceAllRequest;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.completion.ICompletionAcceptanceAllService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@Slf4j
@RestController
@RequestMapping("/v1/acceptanceAll")
public class CompletionAcceptanceAllController {

    @Resource
    private ICompletionAcceptanceAllService completionAcceptanceAllService;

    @Resource
    private RedisUtils redisUtils;


    @TenantId
    @PostMapping("/createOrUpdateCompletionAcceptanceAll")
    public BaseResponse createOrUpdateCompletionAcceptanceAll(@RequestBody CompletionAcceptanceAllRequest request) {
        log.info("createOrUpdateCompletionAcceptanceAll");
        return completionAcceptanceAllService.createOrUpdateCompletionAcceptanceAll(request);
    }

    @TenantId
    @PostMapping("/getCompletionAcceptanceAllList")
    public BaseResponse getCompletionAcceptanceAllList(@RequestBody CompletionAcceptanceAllRequest request, @RequestHeader String token) {
        log.info("getCompletionAcceptanceAllList");
        return completionAcceptanceAllService.getCompletionAcceptanceAllList(request, redisUtils.getUid(token));
    }


    @TenantId
    @GetMapping("/getCompletionAcceptanceAllListNoAuth")
    public BaseResponse getCompletionAcceptanceAllListNoAuth(@RequestParam CompletionAcceptanceAllRequest request) {
        log.info("getCompletionAcceptanceAllListNoAuth");
        return completionAcceptanceAllService.getCompletionAcceptanceAllListNoAuth(request);
    }

    @GetMapping("/getCompletionAcceptanceAllById")
    public BaseResponse getCompletionAcceptanceAllById(@RequestParam String caaId) {
        log.info("getCompletionAcceptanceAllById");
        return completionAcceptanceAllService.getCompletionAcceptanceAllById(caaId);
    }
}
