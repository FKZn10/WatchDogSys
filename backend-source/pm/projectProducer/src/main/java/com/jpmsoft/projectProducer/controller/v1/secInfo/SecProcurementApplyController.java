package com.jpmsoft.projectProducer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecProcurementApplyDto;
import com.jpmsoft.projectProducer.service.secInfo.ISecProcurementApplyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: 李文涛 aaron
 * @date: 2022/12/13 16:40
 */
@Slf4j
@RestController
@RequestMapping("/v1/procurementApply")
public class SecProcurementApplyController {
    @Resource
    private ISecProcurementApplyService secProcurementApplyService;


    @PostMapping("/createOrUpdateSecProcurementApply")
    public BaseResponse createOrUpdateSecProcurementApply(@RequestBody SecProcurementApplyDto request) {
        log.info("createOrUpdateSecProcurementApply");
        return secProcurementApplyService.createOrUpdateSecProcurementApply(request);
    }

    @GetMapping("/getSecProcurementApplyById")
    public BaseResponse getSecProcurementApplyById(@RequestParam String id) {
        log.info("getSecProcurementApplyById");
        return secProcurementApplyService.getSecProcurementApplyById(id);
    }

}
