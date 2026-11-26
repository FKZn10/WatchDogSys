package com.jpmsoft.projectConsumer.controller.v1.contract;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.feign.ConFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 支付变更关联
 */
@Slf4j
@RestController
@RequestMapping("/v1/payChangeAssociation")
public class PayChangeAssociationController {
    @Resource
    private ConFeign conFeign;


    @GetMapping("/existsAssociation")
    public BaseResponse existsAssociation(@RequestParam(required = false) String conId, @RequestParam(required = false) String payId) {
        log.info("existsAssociation");
        return conFeign.existsAssociation(conId, payId);
    }
}
