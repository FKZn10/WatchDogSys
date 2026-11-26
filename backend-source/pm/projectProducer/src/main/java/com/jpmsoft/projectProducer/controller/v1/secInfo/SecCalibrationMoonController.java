package com.jpmsoft.projectProducer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecBrightMoonModel;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.secInfo.ISecCalibrationMoonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@Slf4j
@RestController
@RequestMapping("/v1/calibrationMoon")
public class SecCalibrationMoonController {


    @Resource
    private ISecCalibrationMoonService SecCalibrationMoonService;


    @TenantId
    @PostMapping("/createOrUpdateSecCalibrationMoon")
    public BaseResponse createOrUpdateSecCalibrationMoon(@RequestBody SecBrightMoonModel request) {
        log.info("createOrUpdateSecCalibrationMoon");
        return SecCalibrationMoonService.createOrUpdateSecCalibrationMoon(request);
    }

    @TenantId
    @PostMapping("/getSecCalibrationMoonList")
    public BaseResponse getSecCalibrationMoonList(@RequestBody SecBrightMoonModel request) {
        log.info("getSecCalibrationMoonList");
        return SecCalibrationMoonService.getSecCalibrationMoonList(request);
    }

    @GetMapping("/getSecCalibrationMoonById")
    public BaseResponse getSecCalibrationMoonById(@RequestParam String sbmId) {
        log.info("getSecCalibrationMoonById");
        return SecCalibrationMoonService.getSecCalibrationMoonById(sbmId);
    }
}
