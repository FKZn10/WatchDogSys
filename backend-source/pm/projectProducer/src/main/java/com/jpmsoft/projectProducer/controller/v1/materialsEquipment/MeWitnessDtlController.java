package com.jpmsoft.projectProducer.controller.v1.materialsEquipment;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.MeEnter.MeWitnessDtlRequest;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.materialsEquipment.IMeWitnessDtlService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/meWitnessDtl")
public class MeWitnessDtlController {
    @Resource
    private IMeWitnessDtlService meWitnessDtlService;

    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/getMeWitnessDtlList")
    public BaseResponse getMeWitnessDtlList(@RequestBody MeWitnessDtlRequest request, @RequestHeader String token) {
        log.info("getMeWitnessDtlList");
        return meWitnessDtlService.getMeWitnessDtlList(request, redisUtils.getUid(token));
    }

    @TenantId
    @PostMapping("/getMeWitnessDtlInspeList")
    public BaseResponse getMeWitnessDtlInspeList(@RequestBody MeWitnessDtlRequest request, @RequestHeader String token) {
        log.info("getMeWitnessDtlInspeList");
        return meWitnessDtlService.getMeWitnessDtlInspeList(request, redisUtils.getUid(token));
    }
}
