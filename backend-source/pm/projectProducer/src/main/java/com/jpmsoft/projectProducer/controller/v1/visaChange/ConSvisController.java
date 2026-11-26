package com.jpmsoft.projectProducer.controller.v1.visaChange;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.DesignChange.ConSvisRequest;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.visaChange.IConSvisService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/conSvis")
public class ConSvisController {

    @Resource
    private IConSvisService conSvisService;

    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createOrUpdateConSvis")
    public BaseResponse createOrUpdateConSvis(@RequestBody ConSvisRequest request) {
        log.info("createOrUpdateConSvis");
        return conSvisService.createOrUpdateConSvis(request);
    }

    @TenantId
    @PostMapping("/getConSvisList")
    public BaseResponse getConSvisList(@RequestBody ConSvisRequest request, @RequestHeader String token) {
        log.info("getConSvisList");
        return conSvisService.getConSvisList(request, redisUtils.getUid(token));
    }

    @GetMapping("/getConSvisById")
    public BaseResponse getConSvisById(@RequestParam String svisId) {
        log.info("getConSvisById");
        return conSvisService.getConSvisById(svisId);
    }
}
