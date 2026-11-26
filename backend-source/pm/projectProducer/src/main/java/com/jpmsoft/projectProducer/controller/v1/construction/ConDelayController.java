package com.jpmsoft.projectProducer.controller.v1.construction;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Construction.ConDelayModel;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.construction.IConDelayService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/conDelay")
public class ConDelayController {

    @Resource
    private IConDelayService conDelayService;

    @Resource
    private RedisUtils redisUtils;


    @TenantId
    @PostMapping("/createOrUpdateConDelay")
    public BaseResponse createOrUpdateConDelay(@RequestBody ConDelayModel request) {
        log.info("createOrUpdateConDelay");
        return conDelayService.createOrUpdateConDelay(request);
    }

    @TenantId
    @PostMapping("/getConDelayList")
    public BaseResponse getConDelayList(@RequestBody ConDelayModel request, @RequestHeader String token) {
        log.info("getConDelayList");
        return conDelayService.getConDelayList(request, redisUtils.getUid(token));
    }

    @GetMapping("/getConDelayById")
    public BaseResponse getConDelayById(@RequestParam String deId) {
        log.info("getConDelayById");
        return conDelayService.getConDelayById(deId);
    }
}
