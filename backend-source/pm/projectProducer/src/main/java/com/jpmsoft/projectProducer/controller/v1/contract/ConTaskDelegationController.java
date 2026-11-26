package com.jpmsoft.projectProducer.controller.v1.contract;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.pojo.dto.contract.ConTaskDelegationDto;
import com.jpmsoft.projectProducer.service.contract.IConTaskDelegationService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author: 李文涛 aaron
 * @date: 2022/9/5 14:32
 */
@Slf4j
@RestController
@RequestMapping("/v1/conTaskDelegation")
public class ConTaskDelegationController {

    @Resource
    private IConTaskDelegationService conTaskDelegationService;
    @Resource
    private RedisUtils redisUtils;


    @TenantId
    @PostMapping("/createOrUpdateConTaskDelegation")
    public BaseResponse createOrUpdateConTaskDelegation(@RequestBody ConTaskDelegationDto request) {
        log.info("createOrUpdateConTaskDelegation");
        return conTaskDelegationService.createOrUpdateConTaskDelegation(request);
    }

    @TenantId
    @PostMapping("/getConTaskDelegationList")
    public BaseResponse getConTaskDelegationList(@RequestBody ConTaskDelegationDto request, @RequestHeader String token) {
        log.info("getConTaskDelegationList");
        return conTaskDelegationService.getConTaskDelegationList(request, redisUtils.getUid(token));
    }


    @GetMapping("/getConTaskDelegationById")
    public BaseResponse getConTaskDelegationById(@RequestParam String id) {
        log.info("getConTaskDelegationById");
        return conTaskDelegationService.getConTaskDelegationById(id);
    }

    @GetMapping("/getConTaskDelegationByConId")
    public BaseResponse getConTaskDelegationByConId(@RequestParam String conId) {
        log.info("getConTaskDelegationByConId");
        return conTaskDelegationService.getConTaskDelegationByConId(conId);
    }
}
