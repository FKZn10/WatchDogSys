package com.jpmsoft.projectProducer.controller.v1.visaChange;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.pojo.dto.visaChange.ConDclsDTO;
import com.jpmsoft.projectProducer.service.visaChange.IConDclsService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 设计变更记录单
 *
 * @Author: 李文涛 aaron
 * @date: 2023/4/6 18:16
 */
@Slf4j
@RestController
@RequestMapping("/v1/conDcls")
public class ConDclsController {

    @Resource
    private IConDclsService conDclsService;
    @Resource
    private RedisUtils redisUtils;


    @TenantId
    @PostMapping("/createOrUpdateConDcls")
    public BaseResponse createOrUpdateConDcls(@RequestBody ConDclsDTO request) {
        log.info("createOrUpdateConDcls");
        return conDclsService.createOrUpdateConDcls(request);
    }

    @TenantId
    @PostMapping("/getConDclsList")
    public BaseResponse getConDclsList(@RequestBody ConDclsDTO request, @RequestHeader String token) {
        log.info("getConDclsList");
        return conDclsService.getConDclsList(request, redisUtils.getUid(token));
    }

    @GetMapping("/getConDclsById")
    public BaseResponse getConDclsById(@RequestParam String id) {
        log.info("getConDclsById");
        return conDclsService.getConDclsById(id);
    }
}
