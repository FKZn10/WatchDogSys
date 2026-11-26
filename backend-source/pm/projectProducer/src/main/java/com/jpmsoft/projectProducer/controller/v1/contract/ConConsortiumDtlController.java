package com.jpmsoft.projectProducer.controller.v1.contract;


import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.service.contract.IConConsortiumDtlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/conConsortiumDtl")
public class ConConsortiumDtlController {
    @Resource
    IConConsortiumDtlService conConsortiumDtlService;


    @GetMapping("/slectConConsortiumDtlListByConId")
    public BaseResponse slectConConsortiumDtlListByConId(@RequestParam String conId) {
        log.info("mD");
        return BaseResponse.ok(conConsortiumDtlService.slectConConsortiumDtlListByConId(conId)) ;
    }

}
