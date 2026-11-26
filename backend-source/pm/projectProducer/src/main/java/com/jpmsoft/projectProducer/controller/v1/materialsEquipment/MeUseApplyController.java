package com.jpmsoft.projectProducer.controller.v1.materialsEquipment;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.MeEnter.MeUseApplyReuqest;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.materialsEquipment.IMeUseApplyService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/meUseApply")
public class MeUseApplyController {
    @Resource
    private IMeUseApplyService meUseApplyService;

    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createOrUpdateMeUseApply")
    public BaseResponse createOrUpdateMeUseApply(@RequestBody MeUseApplyReuqest request) {
        log.info("createOrUpdateMeUseApply");
        return meUseApplyService.createOrUpdateMeUseApply(request);
    }

    @TenantId
    @PostMapping("/getMeUseApplyList")
    public BaseResponse getMeUseApplyList(@RequestBody MeUseApplyReuqest request, @RequestHeader String token) {
        log.info("getMeUseApplyList");
        return meUseApplyService.getMeUseApplyList(request, redisUtils.getUid(token));
    }

    @GetMapping("/getMeUseApplyByUaId")
    public BaseResponse getMeUseApplyByUaId(@RequestParam String uaId) {
        log.info("getMeUseApplyByUaId");
        return meUseApplyService.getMeUseApplyByUaId(uaId);
    }
}
