package com.jpmsoft.projectProducer.controller.v1.supervision;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Supervisor.ConSupOnSiteRequest;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.supervision.IConSupOnSiteService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/conSupOnSite")
public class ConSupOnSiteController {
    @Resource
    private IConSupOnSiteService conSupOnSiteService;
    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createOrUpdateConSupOnSite")
    public BaseResponse createOrUpdateConSupOnSite(@RequestBody ConSupOnSiteRequest request) {
        log.info("createOrUpdateConSupOnSite");
        return conSupOnSiteService.createOrUpdateConSupOnSite(request);
    }

    @TenantId
    @PostMapping("/getConSupOnSiteList")
    public BaseResponse getConSupOnSiteList(@RequestBody ConSupOnSiteRequest request, @RequestHeader String token) {
        log.info("getConSupOnSiteList");
        return conSupOnSiteService.getConSupOnSiteList(request, redisUtils.getUid(token));
    }

    @GetMapping("/getConSupOnSiteById")
    public BaseResponse getConSupOnSiteById(@RequestParam String osId) {
        log.info("getConSupOnSiteById");
        return conSupOnSiteService.getConSupOnSiteById(osId);
    }
}
