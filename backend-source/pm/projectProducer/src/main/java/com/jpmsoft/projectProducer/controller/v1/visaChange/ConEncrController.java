package com.jpmsoft.projectProducer.controller.v1.visaChange;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.DesignChange.ConEncrRequest;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.visaChange.IConEncrService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/conEncr")
public class ConEncrController {
    @Resource
    private IConEncrService conEncrService;

    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createOrUpdateConEncr")
    public BaseResponse createOrUpdateConEncr(@RequestBody ConEncrRequest request) {
        log.info("createOrUpdateConEncr");
        return conEncrService.createOrUpdateConEncr(request);
    }

    @TenantId
    @PostMapping("/getConEncrList")
    public BaseResponse getConEncrList(@RequestBody ConEncrRequest request, @RequestHeader String token) {
        log.info("getConEncrList");
        return conEncrService.getConEncrList(request, redisUtils.getUid(token));
    }

    @TenantId
    @PostMapping("/getConEncrListNoAuth")
    public BaseResponse getConEncrListNoAuth(@RequestBody ConEncrRequest request) {
        log.info("getConEncrListNoAuth");
        return conEncrService.getConEncrListNoAuth(request);
    }


    @GetMapping("/getConEncrById")
    public BaseResponse getConEncrById(@RequestParam String encrId) {
        log.info("getConEncrById");
        return conEncrService.getConEncrById(encrId);
    }
}
