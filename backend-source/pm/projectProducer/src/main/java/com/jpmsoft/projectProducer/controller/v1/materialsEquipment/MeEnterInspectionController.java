package com.jpmsoft.projectProducer.controller.v1.materialsEquipment;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.MeEnter.MeEnterInspectionRequest;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.materialsEquipment.IMeEnterInspectionService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/meEnterInspection")
public class MeEnterInspectionController {
    @Resource
    private IMeEnterInspectionService meEnterInspectionService;

    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createOrUpdateMeEnterInspection")
    public BaseResponse createOrUpdateMeEnterInspection(@RequestBody MeEnterInspectionRequest request) {
        log.info("createOrUpdateMeEnterInspection");
        return meEnterInspectionService.createOrUpdateMeEnterInspection(request);
    }

    @TenantId
    @PostMapping("/getMeEnterInspectionList")
    public BaseResponse getMeEnterInspectionList(@RequestBody MeEnterInspectionRequest request, @RequestHeader String token) {
        log.info("getMeEnterInspectionList");
        return meEnterInspectionService.getMeEnterInspectionList(request, redisUtils.getUid(token));
    }

    @GetMapping("/getMeEnterInspectionByEiId")
    public BaseResponse getMeEnterInspectionByEiId(@RequestParam String eiId) {
        log.info("getMeEnterInspectionByEiId");
        return meEnterInspectionService.getMeEnterInspectionByEiId(eiId);
    }
}
