package com.jpmsoft.projectProducer.controller.v1.contract;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.service.contract.IConPayWitnessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/10/30 17:58
 */
@Slf4j
@RestController
@RequestMapping("/v1/conPayWitness")
public class ConPayWitnessController {

    @Resource
    private IConPayWitnessService conPayWitnessService;

    @GetMapping("/getConPayWitnessByPayId")
    public BaseResponse getConPayWitnessByPayId(@RequestParam String payId) {
        log.info("getConPayWitnessByPayId");
        return BaseResponse.ok(conPayWitnessService.getConPayWitnessByPayId(payId));
    }


    @GetMapping("/getConPayWitnessByOrderNo")
    public BaseResponse getConPayWitnessByOrderNo(@RequestParam String orderNo) {
        log.info("getConPayWitnessByOrderNo");
        return conPayWitnessService.getConPayWitnessByOrderNo(orderNo);
    }

    @GetMapping("/getConPayWitnessByConId")
    public BaseResponse getConPayWitnessByConId(@RequestParam String conId) {
        log.info("getConPayWitnessByConId");
        return conPayWitnessService.getConPayWitnessByConId(conId);
    }
}
