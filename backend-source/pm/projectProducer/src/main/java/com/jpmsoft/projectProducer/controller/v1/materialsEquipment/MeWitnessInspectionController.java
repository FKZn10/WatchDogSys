package com.jpmsoft.projectProducer.controller.v1.materialsEquipment;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.MeEnter.MeWitnessInspectionRequest;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.materialsEquipment.IMeWitnessInspectionService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/meWitnessInspection")
public class MeWitnessInspectionController {
    @Resource
    private IMeWitnessInspectionService meWitnessInspectionService;

    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createOrUpdateMeWitnessInspection")
    public BaseResponse createOrUpdateMeWitnessInspection(@RequestBody MeWitnessInspectionRequest request) {
        log.info("createOrUpdateMeWitnessInspection");
        return meWitnessInspectionService.createOrUpdateMeWitnessInspection(request);
    }

    @PostMapping("/updateMeWitnessInspectionDtl")
    public BaseResponse updateMeWitnessInspectionDtl(@RequestBody MeWitnessInspectionRequest request) {
        log.info("updateMeWitnessInspectionDtl");
        return meWitnessInspectionService.updateMeWitnessInspectionDtl(request);
    }

    @GetMapping("/return2InitWitnessInspectionDtl")
    public BaseResponse return2InitWitnessInspectionDtl(@RequestParam String wiId) {
        log.info("return2InitWitnessInspectionDtl");
        return meWitnessInspectionService.return2InitWitnessInspectionDtl(wiId);
    }

    @TenantId
    @PostMapping("/getMeWitnessInspectionList")
    public BaseResponse getMeWitnessInspectionList(@RequestBody MeWitnessInspectionRequest request, @RequestHeader String token) {
        log.info("getMeWitnessInspectionList");
        return meWitnessInspectionService.getMeWitnessInspectionList(request, redisUtils.getUid(token));
    }

    @GetMapping("/getMeWitnessInspectionByWiId")
    public BaseResponse getMeWitnessInspectionByWiId(@RequestParam String wiId) {
        log.info("getMeWitnessInspectionByWiId");
        return meWitnessInspectionService.getMeWitnessInspectionByWiId(wiId);
    }

    @GetMapping("/getMeWitnessInspectionByConId")
    public BaseResponse getMeWitnessInspectionByConId(@RequestParam String conId) {
        log.info("getMeWitnessInspectionByConId");
        return meWitnessInspectionService.getMeWitnessInspectionByConId(conId);
    }


    @GetMapping("/getMeWitnessInspectionByOrderNo")
    public BaseResponse getMeWitnessInspectionByOrderNo(@RequestParam String wiOrderNo) {
        log.info("getMeWitnessInspectionByOrderNo");
        return meWitnessInspectionService.getMeWitnessInspectionByOrderNo(wiOrderNo);
    }
}
