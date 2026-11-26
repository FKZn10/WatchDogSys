package com.jpmsoft.projectProducer.controller.v1.visaChange;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.DesignChange.ConEnisRequest;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.visaChange.IConEnisService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/conEnis")
public class ConEnisController {
    @Resource
    private IConEnisService conEnisService;

    @Resource
    private RedisUtils redisUtils;


    @TenantId
    @PostMapping("/createOrUpdateConEnis")
    public BaseResponse createOrUpdateConEnis(@RequestBody ConEnisRequest request) {
        log.info("createOrUpdateConEnis");
        return conEnisService.createOrUpdateConEnis(request);
    }

    @TenantId
    @PostMapping("/getConEnisList")
    public BaseResponse getConEnisList(@RequestBody ConEnisRequest request, @RequestHeader String token) {
        log.info("getConEnisList");
        return conEnisService.getConEnisList(request, redisUtils.getUid(token));
    }

    @TenantId
    @PostMapping("/getConEnisListGm")
    public BaseResponse getConEnisListGm(@RequestBody ConEnisRequest request, @RequestHeader String token) {
        log.info("getConEnisListGm");
        return conEnisService.getConEnisListGm(request, redisUtils.getUid(token));
    }

    @TenantId
    @PostMapping("/getConEnisListNoAuth")
    public BaseResponse getConEnisListNoAuth(@RequestBody ConEnisRequest request) {
        log.info("getConEnisListNoAuth");
        return conEnisService.getConEnisListNoAuth(request);
    }

    
    @GetMapping("/getConEnisById")
    public BaseResponse getConEnisById(@RequestParam String enisId) {
        log.info("getConEnisById");
        return conEnisService.getConEnisById(enisId);
    }
}
