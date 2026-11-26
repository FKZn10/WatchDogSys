package com.jpmsoft.projectProducer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecDocControlDto;
import com.jpmsoft.projectProducer.service.secInfo.ISecDocControlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author: 李文涛 aaron
 * @date: 2022/8/4 11:50
 */
@Slf4j
@RestController
@RequestMapping("/v1/docControl")
public class SecDocControlController {
    @Resource
    private ISecDocControlService secDocControlService;

    @TenantId
    @PostMapping("/createOrUpdateSecDocControl")
    public BaseResponse createOrUpdateSecDocControl(@RequestBody SecDocControlDto request) {
        log.info("createOrUpdateSecDocControl");
        return secDocControlService.createOrUpdateSecDocControl(request);
    }

    @TenantId
    @PostMapping("/getSecDocControlList")
    public BaseResponse getSecDocControlList(@RequestBody SecDocControlDto request) {
        log.info("getSecDocControlList");
        return secDocControlService.getSecDocControlList(request);
    }

    @GetMapping("/getSecDocControlById")
    public BaseResponse getSecDocControlById(@RequestParam String id) {
        log.info("getSecDocControlById");
        return secDocControlService.getSecDocControlById(id);
    }
}
