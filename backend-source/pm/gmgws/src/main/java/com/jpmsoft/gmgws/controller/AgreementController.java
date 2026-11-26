package com.jpmsoft.gmgws.controller;


import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.gmgws.service.AgreementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/agreement")
@Api(tags = "协议", value = "协议")
public class AgreementController {

    @Autowired
    private AgreementService agreementService;


    @ApiOperation(value = "协议解除")
    @GetMapping("/relieve")
    public BaseResponse relieve(@RequestParam String id , @RequestParam String aRelieveId){

        return agreementService.relieve(id,aRelieveId);
    }
}
