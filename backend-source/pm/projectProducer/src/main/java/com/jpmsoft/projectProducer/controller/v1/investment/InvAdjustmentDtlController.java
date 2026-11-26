package com.jpmsoft.projectProducer.controller.v1.investment;

import com.jpmsoft.projectProducer.service.investment.IInvAdjustmentTemplateDtlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 调整事项清单
 */
@Slf4j
@RestController
@RequestMapping("/v1/invAdjustmentDtl")
public class InvAdjustmentDtlController {

    @Resource
    private IInvAdjustmentTemplateDtlService invAdjustmentDtlService;
}
