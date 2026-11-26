package com.jpmsoft.projectProducer.controller.v1.materialsEquipment;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.MeEnter.MeInspectionReportRequest;
import com.jpmsoft.common.model.requestAndResponse.MeEnter.MeDetectionGuideModel;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.materialsEquipment.IMeDetectionGuideService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/meDetectionGuide")
public class MeDetectionGuideController {

    @Resource
    private IMeDetectionGuideService meDetectionGuideService;

    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createOrUpdateMeDetectionGuide")
    public BaseResponse createOrUpdateMeDetectionGuide(@RequestBody MeDetectionGuideModel model) {
        log.info("createOrUpdateMeDetectionGuide");
        return meDetectionGuideService.createOrUpdateMeDetectionGuide(model);
    }

    @TenantId
    @PostMapping("/getMeDetectionGuideList")
    public BaseResponse getMeDetectionGuideList(@RequestBody MeDetectionGuideModel model, @RequestHeader String token) {
        log.info("getMeDetectionGuideList");
        return meDetectionGuideService.getMeDetectionGuideList(model, redisUtils.getUid(token));
    }

    @GetMapping("/getMeDetectionGuideByDgId")
    public BaseResponse getMeDetectionGuideByDgId(@RequestParam String dgId) {
        log.info("getMeDetectionGuideByDgId");
        return meDetectionGuideService.getMeDetectionGuideByDgId(dgId);
    }

    @GetMapping("/approvalData")
    public BaseResponse approvalData(@RequestParam String dgId) {
        log.info("approvalData");
        return meDetectionGuideService.approvalData(dgId);
    }
    @GetMapping("/setMeDetectionGuideData")
    public BaseResponse setMeDetectionGuideData(@RequestParam String dgId) {
        log.info("setMeDetectionGuideData");
        return meDetectionGuideService.setMeDetectionGuideData(dgId);
    }
}
