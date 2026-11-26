package com.jpmsoft.projectProducer.controller.v1.contract;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.pojo.dto.contract.ConContractChangeDTO;
import com.jpmsoft.projectProducer.service.contract.IConContractChangeService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/9/18 14:48
 */
@Slf4j
@RestController
@RequestMapping("/v1/contractChange")
public class ConContractChangeController {
    @Resource
    private IConContractChangeService conContractChangeService;
    @Resource
    private RedisUtils redisUtils;


    @TenantId
    @PostMapping("/createOrUpdateConContractChange")
    public BaseResponse createOrUpdateConContractChange(@RequestBody ConContractChangeDTO request) {
        log.info("createOrUpdateConContractChange");
        return conContractChangeService.createOrUpdateConContractChange(request);
    }

    @TenantId
    @PostMapping("/getContractChangeList")
    public BaseResponse getContractChangeList(@RequestBody ConContractChangeDTO request, @RequestHeader String token) {
        log.info("getContractChangeList");
        return conContractChangeService.getContractChangeList(request, redisUtils.getUid(token));
    }

    @GetMapping("/getContractChangeById")
    public BaseResponse getContractChangeById(@RequestParam String id) {
        log.info("getContractChangeById");
        return conContractChangeService.getContractChangeById(id);
    }

    @GetMapping("/approve2RefreshContract")
    public BaseResponse approve2RefreshContract(@RequestParam String id) {
        log.info("approve2RefreshContract");
        return conContractChangeService.approve2RefreshContract(id);
    }
}
