package com.jpmsoft.projectProducer.controller.v1.visaChange;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.DesignChange.ConEnVerifyRequest;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.visaChange.IConEnVerifyService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/conEnVerify")
public class ConEnVerifyController {
    @Resource
    private IConEnVerifyService conEnVerifyService;

    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createOrUpdateConEnVerify")
    public BaseResponse createOrUpdateConEnVerify(@RequestBody ConEnVerifyRequest request) {
        log.info("createOrUpdateConEnVerify");
        return conEnVerifyService.createOrUpdateConEnVerify(request);
    }


    @TenantId
    @PostMapping("/getConEnVerifyList")
    public BaseResponse getConEnVerifyList(@RequestBody ConEnVerifyRequest request, @RequestHeader String token) {
        log.info("getConEnVerifyList");
        return conEnVerifyService.getConEnVerifyList(request, redisUtils.getUid(token));
    }


    @GetMapping("/getConEnVerifyById")
    public BaseResponse getConEnVerifyById(@RequestParam String envId) {
        log.info("getConEnVerifyById");
        return conEnVerifyService.getConEnVerifyById(envId);
    }
}
