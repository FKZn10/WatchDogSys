package com.jpmsoft.projectProducer.controller.v1.contract;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.pojo.dto.contract.ConDurationAdjustDto;
import com.jpmsoft.projectProducer.service.contract.IConDurationAdjustService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author: 李文涛 aaron
 * @date: 2022/11/11 10:53
 */
@Slf4j
@RestController
@RequestMapping("/v1/conDurationAdjust")
public class ConDurationAdjustController {
    @Resource
    private IConDurationAdjustService conDurationAdjustService;
    @Resource
    private RedisUtils redisUtils;


    @TenantId
    @PostMapping("/createOrUpdateConDurationAdjust")
    public BaseResponse createOrUpdateConDurationAdjust(@RequestBody ConDurationAdjustDto request) {
        log.info("createOrUpdateConDurationAdjust");
        return conDurationAdjustService.createOrUpdateConDurationAdjust(request);
    }

    @TenantId
    @PostMapping("/getConDurationAdjustList")
    public BaseResponse getConDurationAdjustList(@RequestBody ConDurationAdjustDto request, @RequestHeader String token) {
        log.info("getConDurationAdjustList");
        return conDurationAdjustService.getConDurationAdjustList(request, redisUtils.getUid(token));
    }

    @GetMapping("/getConDurationAdjustById")
    public BaseResponse getConDurationAdjustById(@RequestParam String id) {
        log.info("getConDurationAdjustById");
        return conDurationAdjustService.getConDurationAdjustById(id);
    }
}
