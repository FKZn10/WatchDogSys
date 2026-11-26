package com.jpmsoft.projectProducer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecQePreTrialDto;
import com.jpmsoft.projectProducer.service.secInfo.ISecQePreTrialService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: 李文涛 aaron
 * @date: 2022/12/13 16:38
 */
@Slf4j
@RestController
@RequestMapping("/v1/qePreTrial")
public class SecQePreTrialController {

    @Resource
    private ISecQePreTrialService secQePreTrialService;

    @PostMapping("/createOrUpdateSecQePreTrial")
    public BaseResponse createOrUpdateSecQePreTrial(@RequestBody SecQePreTrialDto request) {
        log.info("createOrUpdateSecQePreTrial");
        return secQePreTrialService.createOrUpdateSecQePreTrial(request);
    }

    @GetMapping("/getSecQePreTrialById")
    public BaseResponse getSecQePreTrialById(@RequestParam String id) {
        log.info("getSecQePreTrialById");
        return secQePreTrialService.getSecQePreTrialById(id);
    }
}
