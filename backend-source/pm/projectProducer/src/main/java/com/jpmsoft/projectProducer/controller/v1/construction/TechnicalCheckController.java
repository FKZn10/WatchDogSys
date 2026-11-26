package com.jpmsoft.projectProducer.controller.v1.construction;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.pojo.dto.construction.TechnicalCheckDTO;
import com.jpmsoft.projectProducer.service.construction.ITechnicalCheckService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/3/29 15:31
 */
@Slf4j
@RestController
@RequestMapping("/v1/technicalCheck")
public class TechnicalCheckController {

    @Resource
    private ITechnicalCheckService technicalCheckService;

    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createOrUpdateTechnicalCheck")
    public BaseResponse createOrUpdateTechnicalCheck(@RequestBody TechnicalCheckDTO request) {
        log.info("createOrUpdateTechnicalCheck");
        return technicalCheckService.createOrUpdate(request);
    }

    @TenantId
    @PostMapping("/getTechnicalCheckList")
    public BaseResponse getTechnicalCheckList(@RequestBody TechnicalCheckDTO request, @RequestHeader String token) {
        log.info("getTechnicalCheckList");
        return technicalCheckService.getTechnicalCheckList(request, redisUtils.getUid(token));
    }

    @GetMapping("/getTechnicalCheckById")
    public BaseResponse getTechnicalCheckById(@RequestParam String id) {
        log.info("getTechnicalCheckById");
        return technicalCheckService.getTechnicalCheckById(id);
    }

}
