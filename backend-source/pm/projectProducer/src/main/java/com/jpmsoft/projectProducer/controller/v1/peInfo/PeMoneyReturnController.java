package com.jpmsoft.projectProducer.controller.v1.peInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.pojo.dto.peinfo.PeMoneyReturnDTO;
import com.jpmsoft.projectProducer.service.peInfo.IPeMoneyReturnService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/11/14 10:43
 */
@Slf4j
@RestController
@RequestMapping("/v1/peMoneyReturn")
public class PeMoneyReturnController {

    @Resource
    private IPeMoneyReturnService peMoneyReturnService;
    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createOrUpdatePeMoneyReturn")
    public BaseResponse createOrUpdatePeMoneyReturn(@RequestBody PeMoneyReturnDTO request) {
        log.info("createOrUpdatePeMoneyReturn");
        return peMoneyReturnService.createOrUpdatePeMoneyReturn(request);
    }

    @TenantId
    @PostMapping("/getPeMoneyReturnList")
    public BaseResponse getPeMoneyReturnList(@RequestBody PeMoneyReturnDTO request, @RequestHeader String token) {
        log.info("getPeMoneyReturnList");
        return peMoneyReturnService.getPeMoneyReturnList(request, redisUtils.getUid(token));
    }

    @GetMapping("/getPeMoneyReturnById")
    public BaseResponse getPeMoneyReturnById(@RequestParam String id) {
        log.info("getPeMoneyReturnById");
        return peMoneyReturnService.getPeMoneyReturnById(id);
    }

    @GetMapping("/getPeMoneySumByConId")
    public BaseResponse getPeMoneySumByConId(@RequestParam String conId) {
        log.info("getPeMoneySumByConId");
        return peMoneyReturnService.getPeMoneySumByConId(conId);
    }

}
