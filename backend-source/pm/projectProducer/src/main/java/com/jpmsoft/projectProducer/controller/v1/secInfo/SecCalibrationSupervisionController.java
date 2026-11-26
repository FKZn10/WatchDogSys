package com.jpmsoft.projectProducer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecCalibrationSupervisionDTO;
import com.jpmsoft.projectProducer.service.secInfo.ISecCalibrationSupervisionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/5/6 11:49
 */
@Slf4j
@RestController
@RequestMapping("/v1/calibrationSupervision")
public class SecCalibrationSupervisionController {

    @Resource
    private ISecCalibrationSupervisionService secCalibrationSupervisionService;


    @PostMapping("/createOrUpdateSecCalibrationSupervision")
    public BaseResponse createOrUpdateSecCalibrationSupervision(@RequestBody SecCalibrationSupervisionDTO request) {
        log.info("createOrUpdateSecCalibrationSupervision");
        return secCalibrationSupervisionService.createOrUpdateSecCalibrationSupervision(request);
    }

    @GetMapping("/getSecCalibrationSupervisionById")
    public BaseResponse getSecCalibrationSupervisionById(@RequestParam String id) {
        log.info("getSecCalibrationSupervisionById");
        return secCalibrationSupervisionService.getSecCalibrationSupervisionById(id);
    }
}
