package com.jpmsoft.projectProducer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecExpertSupplementDTO;
import com.jpmsoft.projectProducer.service.secInfo.ISecExpertSupplementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/11/27 15:17
 */
@Slf4j
@RestController
@RequestMapping("/v1/secExpertSupplement")
public class SecExpertSupplementController {

    @Resource
    private ISecExpertSupplementService secExpertSupplementService;

    @TenantId
    @PostMapping("/createOrUpdateExpertSupplement")
    public BaseResponse createOrUpdateExpertSupplement(@RequestBody SecExpertSupplementDTO request) {
        log.info("createOrUpdateExpertSupplement");
        return secExpertSupplementService.createOrUpdate(request);
    }

    @TenantId
    @PostMapping("/getExpertSupplementList")
    public BaseResponse getExpertSupplementList(@RequestBody SecExpertSupplementDTO request) {
        log.info("createOrUpdateSecExpertSelect");
        return secExpertSupplementService.selectList(request);
    }

    @GetMapping("/getExpertSupplementById")
    public BaseResponse getExpertSupplementById(@RequestParam String id) {
        log.info("createOrUpdateSecExpertSelect");
        return secExpertSupplementService.selectById(id);
    }

    @GetMapping("/approveAppendSelection")
    public BaseResponse approveAppendSelection(@RequestParam String id) {
        log.info("approveAppendSelection");
        return secExpertSupplementService.approveAppend(id);
    }

}
