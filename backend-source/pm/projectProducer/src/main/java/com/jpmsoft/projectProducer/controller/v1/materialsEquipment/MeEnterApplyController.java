package com.jpmsoft.projectProducer.controller.v1.materialsEquipment;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.MeEnter.MeEnterApplyRequest;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.materialsEquipment.IMeEnterApplyService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/meEnterApply")
public class MeEnterApplyController {
    @Resource
    private IMeEnterApplyService meEnterApplyService;

    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createOrUpdateMeEnterApply")
    public BaseResponse createOrUpdateMeEnterApply(@RequestBody MeEnterApplyRequest request) {
        log.info("createOrUpdateMeEnterApply");
        return meEnterApplyService.createOrUpdateMeEnterApply(request);
    }

    @TenantId
    @PostMapping("/getMeEnterApplyList")
    public BaseResponse getMeEnterApplyList(@RequestBody MeEnterApplyRequest request, @RequestHeader String token) {
        log.info("getMeEnterApplyList");
        return meEnterApplyService.getMeEnterApplyList(request, redisUtils.getUid(token));
    }

    @GetMapping("/getMeEnterApplyByEaId")
    public BaseResponse getMeEnterApplyByEaId(@RequestParam String eaId) {
        log.info("getMeEnterApplyByEaId");
        return meEnterApplyService.getMeEnterApplyByEaId(eaId);
    }
}
