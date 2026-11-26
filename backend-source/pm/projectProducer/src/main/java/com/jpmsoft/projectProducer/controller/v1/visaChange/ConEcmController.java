package com.jpmsoft.projectProducer.controller.v1.visaChange;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.DesignChange.ConEcmRequest;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.visaChange.IConEcmService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 专家论证会申请
 */
@Slf4j
@RestController
@RequestMapping("/v1/conEcm")
public class ConEcmController {
    @Resource
    private IConEcmService conEcmService;

    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createOrUpdateConEcm")
    public BaseResponse createOrUpdateConEcm(@RequestBody ConEcmRequest request) {
        log.info("createOrUpdateConEcm");
        return conEcmService.createOrUpdateConEcm(request);
    }

    @TenantId
    @PostMapping("/getConEcmList")
    public BaseResponse getConEcmList(@RequestBody ConEcmRequest request, @RequestHeader String token) {
        log.info("getConEcmList");
        return conEcmService.getConEcmList(request, redisUtils.getUid(token));
    }

    @TenantId
    @PostMapping("/getConEcmListNoAuth")
    public BaseResponse getConEcmListNoAuth(@RequestBody ConEcmRequest request) {
        log.info("getConEcmListNoAuth");
        return conEcmService.getConEcmListNoAuth(request);
    }


    @GetMapping("/getConEcmById")
    public BaseResponse getConEcmById(@RequestParam String ecmId) {
        log.info("getConEcmById");
        return conEcmService.getConEcmById(ecmId);
    }
}
