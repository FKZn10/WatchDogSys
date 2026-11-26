package com.jpmsoft.projectProducer.controller.v1.integrity;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Integrity.FunctionalIntegrityWarningRequest;
import com.jpmsoft.projectProducer.service.integrity.IFunctionalIntegrityWarningService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 功能预警流程记录
 */
@Slf4j
@RestController
@RequestMapping("/v1/integrityWarning")
public class FunctionalIntegrityWarningController {
    @Resource
    private IFunctionalIntegrityWarningService functionalIntegrityWarningService;

    /**
     * 高频风险环节看板
     *
     * @param request
     * @return
     */
    @PostMapping("/getIntegrityWarningLedger")
    public BaseResponse getIntegrityWarningLedger(@RequestBody FunctionalIntegrityWarningRequest request) {
        log.info("getIntegrityWarningLedger");
        return functionalIntegrityWarningService.getIntegrityWarningLedger(request);
    }

    /**
     * 高频风险岗位看板
     *
     * @param request
     * @return
     */
    @PostMapping("/getIntegrityWarningPositionLedger")
    public BaseResponse getIntegrityWarningPositionLedger(@RequestBody FunctionalIntegrityWarningRequest request) {
        log.info("getIntegrityWarningPositionLedger");
        return functionalIntegrityWarningService.getIntegrityWarningPositionLedger(request);
    }
}
