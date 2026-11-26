package com.jpmsoft.projectProducer.controller.v1.visaChange;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.DesignChange.ConDcoRequest;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.visaChange.IConDcoService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/conDco")
public class ConDcoController {
    @Resource
    private IConDcoService conDcoService;

    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createOrUpdateConDco")
    public BaseResponse createOrUpdateConDco(@RequestBody ConDcoRequest request) {
        log.info("createOrUpdateConDco");
        return conDcoService.createOrUpdateConDco(request);
    }

    @TenantId
    @PostMapping("/getConDcoList")
    public BaseResponse getConDcoList(@RequestBody ConDcoRequest request, @RequestHeader String token) {
        log.info("getConDcoList");
        return conDcoService.getConDcoList(request, redisUtils.getUid(token));
    }

    @TenantId
    @PostMapping("/getConDcoListGm")
    public BaseResponse getConDcoListGm(@RequestBody ConDcoRequest request, @RequestHeader String token) {
        log.info("getConDcoListGm");
        return conDcoService.getConDcoListGm(request, redisUtils.getUid(token));
    }

    @TenantId
    @PostMapping("/getConDcoListNoAuth")
    public BaseResponse getConDcoListNoAuth(@RequestBody ConDcoRequest request) {
        log.info("getConDcoListNoAuth");
        return conDcoService.getConDcoListNoAuth(request);
    }

    @GetMapping("/getConDcoByDcoId")
    public BaseResponse getConDcoByDcoId(@RequestParam String dcoId) {
        log.info("getConDcoByDcoId");
        return conDcoService.getConDcoByDcoId(dcoId);
    }
}
