package com.jpmsoft.projectProducer.controller.v1.project;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.service.project.IProWnDetailService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/proWnDetail")
public class ProWnDetailController {

    @Resource
    private IProWnDetailService proWnDetailService;


    @GetMapping("/getProWnDetailById")
    public BaseResponse getProWnDetailById(@RequestParam String pwId) {
        log.info("getProWnDetailById");
        return proWnDetailService.getProWnDetailById(pwId);
    }

    @GetMapping("/getProWnDetailByPid")
    public BaseResponse getProWnDetailByPid(@RequestParam String pwPid) {
        log.info("getProWnDetailByPid");
        return proWnDetailService.getProWnDetailByPid(pwPid);
    }
}