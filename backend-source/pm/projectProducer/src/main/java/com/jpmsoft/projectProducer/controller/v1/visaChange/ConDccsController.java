package com.jpmsoft.projectProducer.controller.v1.visaChange;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.pojo.dto.visaChange.ConDccsDTO;
import com.jpmsoft.projectProducer.service.visaChange.IConDccsService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 设计变更联系单
 *
 * @Author: 李文涛 aaron
 * @date: 2023/4/6 18:16
 */
@Slf4j
@RestController
@RequestMapping("/v1/conDccs")
public class ConDccsController {

    @Resource
    private IConDccsService conDccsService;
    @Resource
    private RedisUtils redisUtils;


    @TenantId
    @PostMapping("/createOrUpdateConDccs")
    public BaseResponse createOrUpdateConDccs(@RequestBody ConDccsDTO request) {
        log.info("createOrUpdateConDccs");
        return conDccsService.createOrUpdateConDccs(request);
    }

    @TenantId
    @PostMapping("/getConDccsList")
    public BaseResponse getConDccsList(@RequestBody ConDccsDTO request, @RequestHeader String token) {
        log.info("getConDccsList");
        return conDccsService.getConDccsList(request, redisUtils.getUid(token));
    }

    @GetMapping("/getConDccsById")
    public BaseResponse getConDccsById(@RequestParam String id) {
        log.info("getConDccsById");
        return conDccsService.getConDccsById(id);
    }
}
