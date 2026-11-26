package com.jpmsoft.projectProducer.controller.v1.oa;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Oa.SysPersonalBpmAuthorizeModel;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.oa.ISysPersonalBpmAuthorizeService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/OA/abAuthorize")
public class SysPersonalBpmAuthorizeController {

    @Resource
    private ISysPersonalBpmAuthorizeService sysPersonalBpmAuthorizeService;
    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createOrUpdatePersonalBpmAuthorize")
    public BaseResponse createOrUpdatePersonalBpmAuthorize(@RequestBody SysPersonalBpmAuthorizeModel request,@RequestHeader String token) {
        log.info("createOrUpdatePersonalBpmAuthorize");
        return sysPersonalBpmAuthorizeService.createOrUpdatePersonalBpmAuthorize(request,redisUtils.getUid(token));
    }

    @TenantId
    @PostMapping("/getSelfPersonalBpmAuthorizeList")
    public BaseResponse getSelfPersonalBpmAuthorizeList(@RequestBody SysPersonalBpmAuthorizeModel request, @RequestHeader String token) {
        log.info("getSelfPersonalBpmAuthorizeList");
        return sysPersonalBpmAuthorizeService.getSelfPersonalBpmAuthorizeList(request, redisUtils.getUid(token));
    }

    @TenantId
    @PostMapping("/getPersonalBpmAuthorizeList")
    public BaseResponse getPersonalBpmAuthorizeList(@RequestBody SysPersonalBpmAuthorizeModel request) {
        log.info("getPersonalBpmAuthorizeList");
        return sysPersonalBpmAuthorizeService.getPersonalBpmAuthorizeList(request);
    }

    @GetMapping("/getPersonalBpmAuthorizeById")
    public BaseResponse getPersonalBpmAuthorizeById(@RequestParam String baId) {
        log.info("getPersonalBpmAuthorizeById");
        return sysPersonalBpmAuthorizeService.getPersonalBpmAuthorizeById(baId);
    }
}
