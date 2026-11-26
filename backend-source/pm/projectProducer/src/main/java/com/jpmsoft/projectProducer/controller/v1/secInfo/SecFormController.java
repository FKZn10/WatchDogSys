package com.jpmsoft.projectProducer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecFormDTO;
import com.jpmsoft.projectProducer.service.secInfo.ISecFormService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/8/7 17:16
 */
@Slf4j
@RestController
@RequestMapping("/v1/expert/secForm")
public class SecFormController {
    @Resource
    private ISecFormService secFormService;
    @Resource
    private RedisUtils redisUtils;


    @TenantId
    @PostMapping("/createOrUpdateSecForm")
    public BaseResponse createOrUpdate(@RequestBody SecFormDTO request) {
        log.info("createOrUpdate");
        secFormService.createOrUpdate(request);
        return BaseResponse.ok();
    }

    @TenantId
    @PostMapping("/createOrUpdateSecFormAndDetail")
    public BaseResponse createOrUpdateSecFormAndDetail(@RequestBody SecFormDTO request) {
        log.info("createOrUpdateSecForm");
        return secFormService.createOrUpdateSecForm(request);
    }

    @TenantId
    @PostMapping("/getSecFormList")
    public BaseResponse getSecFormList(@RequestBody SecFormDTO request) {
        log.info("getSecFormList");
        return secFormService.getSecFormList(request);
    }

    @GetMapping("/getSecFormLatest")
    public BaseResponse getSecFormLatest(@RequestHeader String token) {
        log.info("getSecFormLatest");
        return secFormService.getSecFormLatest(redisUtils.getTeId(token));
    }

    @GetMapping("/getSecFormById")
    public BaseResponse getSecFormById(@RequestParam String id) {
        log.info("getSecFormById");
        return secFormService.getSecFormById(id);
    }


    @GetMapping("/deleteSecFormById")
    public BaseResponse deleteSecFormById(@RequestParam String id) {
        log.info("deleteSecFormById");
        return secFormService.deleteSecFormById(id);
    }
}
