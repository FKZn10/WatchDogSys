package com.jpmsoft.projectProducer.controller.v1.visaChange;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.DesignChange.ConSvVerifyRequest;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.visaChange.IConSvVerifyService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/conSvVerify")
public class ConSvVerifyController {
    @Resource
    private IConSvVerifyService conSvVerifyService;

    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createOrUpdateConSvVerify")
    public BaseResponse createOrUpdateConSvVerify(@RequestBody ConSvVerifyRequest request) {
        log.info("createOrUpdateConSvVerify");
        return conSvVerifyService.createOrUpdateConSvVerify(request);
    }

    @TenantId
    @PostMapping("/getConSvVerifyList")
    public BaseResponse getConSvVerifyList(@RequestBody ConSvVerifyRequest request, @RequestHeader String token) {
        log.info("getConSvVerifyList");
        return conSvVerifyService.getConSvVerifyList(request, redisUtils.getUid(token));
    }

    @GetMapping("/getConSvVerifyById")
    public BaseResponse getConSvVerifyById(@RequestParam String svvId) {
        log.info("getConSvVerifyById");
        return conSvVerifyService.getConSvVerifyById(svvId);
    }
}
