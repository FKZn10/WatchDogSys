package com.jpmsoft.projectProducer.controller.v1.contract;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.service.contract.IPayChangeAssociationService;
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
    private IPayChangeAssociationService payChangeAssociationService;

    @GetMapping("/existsAssociation")
    public BaseResponse existsAssociation(@RequestParam(required = false) String conId, @RequestParam(required = false) String payId) {
        log.info("existsAssociation");
        return payChangeAssociationService.existsAssociation(conId, payId);
    }
}
