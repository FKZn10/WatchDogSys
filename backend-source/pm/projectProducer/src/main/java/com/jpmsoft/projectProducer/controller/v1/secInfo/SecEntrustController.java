package com.jpmsoft.projectProducer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecEntrustDto;
import com.jpmsoft.projectProducer.service.secInfo.ISecEntrustService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author: 李文涛 aaron
 * @date: 2022/8/31 11:22
 */
@Slf4j
@RestController
@RequestMapping("/v1/entrust")
public class SecEntrustController {

    @Resource
    private ISecEntrustService secEntrustService;


    @TenantId
    @PostMapping("/createOrUpdateSecEntrust")
    public BaseResponse createOrUpdateSecEntrust(@RequestBody SecEntrustDto request) {
        log.info("createOrUpdateSecEntrust");
        return secEntrustService.createOrUpdateSecEntrust(request);
    }

    @GetMapping("/getSecEntrustById")
    public BaseResponse getSecEntrustById(@RequestParam String id) {
        log.info("getSecEntrustById");
        return secEntrustService.getSecEntrustById(id);
    }

    @TenantId
    @PostMapping("/getSecEntrustList")
    public BaseResponse getSecEntrustList(@RequestBody SecEntrustDto request) {
        log.info("getSecEntrustList");
        return secEntrustService.getSecEntrustList(request);
    }
}
