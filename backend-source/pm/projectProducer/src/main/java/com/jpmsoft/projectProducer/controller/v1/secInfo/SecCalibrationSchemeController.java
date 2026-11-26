package com.jpmsoft.projectProducer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecCalibrationSchemeModel;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.secInfo.ISecCalibrationSchemeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/calibrationScheme")
public class SecCalibrationSchemeController {

    @Resource
    private ISecCalibrationSchemeService secCalibrationSchemeService;


    @TenantId
    @PostMapping("/createOrUpdateSecCalibrationScheme")
    public BaseResponse createOrUpdateSecCalibrationScheme(@RequestBody SecCalibrationSchemeModel request) {
        log.info("createOrUpdateSecCalibrationScheme");
        return secCalibrationSchemeService.createOrUpdateSecCalibrationScheme(request);
    }

    @TenantId
    @PostMapping("/getSecCalibrationSchemeList")
    public BaseResponse getSecCalibrationSchemeList(@RequestBody SecCalibrationSchemeModel request) {
        log.info("getSecCalibrationSchemeList");
        return secCalibrationSchemeService.getSecCalibrationSchemeList(request);
    }

    @GetMapping("/getSecCalibrationSchemeById")
    public BaseResponse getSecCalibrationSchemeById(@RequestParam String scsId) {
        log.info("getSecCalibrationSchemeById");
        return secCalibrationSchemeService.getSecCalibrationSchemeById(scsId);
    }
}
