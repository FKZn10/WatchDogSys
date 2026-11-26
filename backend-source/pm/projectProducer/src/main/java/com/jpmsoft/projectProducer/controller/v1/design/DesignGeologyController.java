package com.jpmsoft.projectProducer.controller.v1.design;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Design.DesignGeologyModel;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.design.IDesignGeologyService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/designGeology")
public class DesignGeologyController {

    @Resource
    private IDesignGeologyService designGeologyService;

    @Resource
    private RedisUtils redisUtils;


    @TenantId
    @PostMapping("/createOrUpdateDesignGeology")
    public BaseResponse createOrUpdateDesignGeology(@RequestBody DesignGeologyModel request) {
        log.info("getDesignGeologyList");
        return designGeologyService.createOrUpdateDesignGeology(request);
    }

    @TenantId
    @PostMapping("/getDesignGeologyList")
    public BaseResponse getDesignGeologyList(@RequestBody DesignGeologyModel request, @RequestHeader String token) {
        log.info("getDesignGeologyList");
        return designGeologyService.getDesignGeologyList(request, redisUtils.getUid(token));
    }

    @TenantId
    @PostMapping("/getDesignGeologyListNoAuth")
    public BaseResponse getDesignGeologyListNoAuth(@RequestBody DesignGeologyModel request) {
        log.info("getDesignGeologyListNoAuth");
        return designGeologyService.getDesignGeologyListNoAuth(request);
    }

    @GetMapping("/getDesignGeologyById")
    public BaseResponse getDesignGeologyById(@RequestParam String dgId) {
        log.info("getDesignGeologyById");
        return designGeologyService.getDesignGeologyById(dgId);
    }
}
