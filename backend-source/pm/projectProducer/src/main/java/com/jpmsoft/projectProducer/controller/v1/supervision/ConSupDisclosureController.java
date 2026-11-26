package com.jpmsoft.projectProducer.controller.v1.supervision;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Supervisor.ConSupDisclosureRequest;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.supervision.IConSupDisclosureService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/conSupDisclosure")
public class ConSupDisclosureController {
    @Resource
    private IConSupDisclosureService conSupDisclosureService;
    @Resource
    private RedisUtils redisUtils;


    @TenantId
    @PostMapping("/createOrUpdateConSupDisclosure")
    public BaseResponse createOrUpdateConSupDisclosure(@RequestBody ConSupDisclosureRequest request) {
        log.info("createOrUpdateConSupDisclosure");
        return conSupDisclosureService.createOrUpdateConSupDisclosure(request);
    }

    @TenantId
    @PostMapping("/getConSupDisclosureList")
    public BaseResponse getConSupDisclosureList(@RequestBody ConSupDisclosureRequest request, @RequestHeader String token) {
        log.info("getConSupDisclosureList");
        return conSupDisclosureService.getConSupDisclosureList(request, redisUtils.getUid(token));
    }

    @GetMapping("/getConSupDisclosureById")
    public BaseResponse getConSupDisclosureById(@RequestParam String disId) {
        log.info("getConSupDisclosureById");
        return conSupDisclosureService.getConSupDisclosureById(disId);
    }
}
