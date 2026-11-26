package com.jpmsoft.projectProducer.controller.v1.contract;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.pojo.dto.contract.ConPowerAttorneyDTO;
import com.jpmsoft.projectProducer.service.contract.IConPowerAttorneyService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/3/7 10:04
 */
@Slf4j
@RestController
@RequestMapping("/v1/conPowerAttorney")
public class ConPowerAttorneyController {

    @Resource
    private IConPowerAttorneyService conPowerAttorneyService;


    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createOrUpdateConPowerAttorney")
    public BaseResponse createOrUpdateConPowerAttorney(@RequestBody ConPowerAttorneyDTO request) {
        log.info("createOrUpdateConPowerAttorney");
        return conPowerAttorneyService.createOrUpdateConPowerAttorney(request);
    }

    @TenantId
    @PostMapping("/getConPowerAttorneyList")
    public BaseResponse getConPowerAttorneyList(@RequestBody ConPowerAttorneyDTO request, @RequestHeader String token) {
        log.info("getConPowerAttorneyList");
        return conPowerAttorneyService.getConPowerAttorneyList(request, redisUtils.getUid(token));
    }

    @GetMapping("/getConPowerAttorneyById")
    public BaseResponse getConPowerAttorneyById(@RequestParam String id) {
        log.info("getConPowerAttorneyById");
        return conPowerAttorneyService.getConPowerAttorneyById(id);
    }


    @GetMapping("/contractApprove2Create")
    public BaseResponse contractApprove2Create(@RequestParam String conId) {
        log.info("contractApprove2Create");
        return conPowerAttorneyService.contractApprove2Create(conId);
    }
}
