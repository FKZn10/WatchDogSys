package com.jpmsoft.projectProducer.controller.v1.materialsEquipment;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.MeEnter.MeEnterApplyDtlRequest;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.materialsEquipment.IMeEnterApplyDtlService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/meEnterApplyDtl")
public class MeEnterApplyDtlController {

    @Resource
    private IMeEnterApplyDtlService meEnterApplyDtlService;
    @Resource
    private RedisUtils redisUtils;

    @PostMapping("/createOrUpdateMeEnterApplyDtl")
    public BaseResponse createOrUpdateMeEnterApplyDtl(@RequestBody MeEnterApplyDtlRequest request) {
        log.info("createOrUpdateMeEnterApplyDtl");
        return meEnterApplyDtlService.createOrUpdateMeEnterApplyDtl(request);
    }

    @TenantId
    @PostMapping("/getMeEnterApplyDtlList")
    public BaseResponse getMeEnterApplyDtlList(@RequestBody MeEnterApplyDtlRequest request, @RequestHeader String token) {
        log.info("getMeEnterApplyDtlList");
        return meEnterApplyDtlService.getMeEnterApplyDtlList(request, redisUtils.getUid(token));
    }
}
