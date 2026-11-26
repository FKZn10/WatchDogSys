package com.jpmsoft.projectProducer.controller.v1.visaChange;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.pojo.dto.visaChange.ConEnFeeDTO;
import com.jpmsoft.projectProducer.service.visaChange.IConEnFeeService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/conEnFee")
public class ConEnFeeController {
    @Resource
    private IConEnFeeService conEnFeeService;

    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createOrUpdateConEnFee")
    public BaseResponse createOrUpdateConEnFee(@RequestBody ConEnFeeDTO request) {
        log.info("createOrUpdateConEnFee");
        return conEnFeeService.createOrUpdateConEnFee(request);
    }

    @TenantId
    @PostMapping("/getConEnFeeList")
    public BaseResponse getConEnFeeList(@RequestBody ConEnFeeDTO request, @RequestHeader String token) {
        log.info("getConEnFeeList");
        return conEnFeeService.getConEnFeeList(request, redisUtils.getUid(token));
    }

    @GetMapping("/getConEnFeeById")
    public BaseResponse getConEnFeeById(@RequestParam String id) {
        log.info("getConEnFeeById");
        return conEnFeeService.getConEnFeeById(id);
    }
}
