package com.jpmsoft.projectProducer.controller.v1.investment;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.service.investment.IMeasurementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author: shanzhi
 * @date: 2024/09/02 10:45
 */
@Slf4j
@RestController
@RequestMapping("/v1/Measurement")
public class MeasurementController {
    @Resource
    private IMeasurementService measurementService;

    @GetMapping("/getCalculationTenderPriceBySecId")
    public BaseResponse getCalculationTenderPriceBySecId(@RequestParam String pId, @RequestParam String secId) {
        log.info("getCalculationTenderPriceBySecId");
        return measurementService.getCalculationTenderPriceBySecId(pId,secId);
    }

}
