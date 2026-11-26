package com.jpmsoft.projectProducer.controller.v1.design;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Design.DesignCompletedModel;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.design.IDesignCompletedService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/designCompleted")
public class DesignCompletedController {

    @Resource
    private IDesignCompletedService designCompletedService;

    @Resource
    private RedisUtils redisUtils;


    @TenantId
    @PostMapping("/createOrUpdateDesignCompleted")
    public BaseResponse createOrUpdateDesignCompleted(@RequestBody DesignCompletedModel request) {
        log.info("getDesignCompletedList");
        return designCompletedService.createOrUpdateDesignCompleted(request);
    }

    @TenantId
    @PostMapping("/getDesignCompletedList")
    public BaseResponse getDesignCompletedList(@RequestBody DesignCompletedModel request, @RequestHeader String token) {
        log.info("getDesignCompletedList");
        return designCompletedService.getDesignCompletedList(request, redisUtils.getUid(token));
    }

    @TenantId
    @PostMapping("/getDesignCompletedListNoAuth")
    public BaseResponse getDesignCompletedListNoAuth(@RequestBody DesignCompletedModel request) {
        log.info("getDesignCompletedListNoAuth");
        return designCompletedService.getDesignCompletedListNoAuth(request);
    }

    @GetMapping("/getDesignCompletedById")
    public BaseResponse getDesignCompletedById(@RequestParam String dcId) {
        log.info("getDesignCompletedById");
        return designCompletedService.getDesignCompletedById(dcId);
    }
}
