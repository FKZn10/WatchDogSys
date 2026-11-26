package com.jpmsoft.projectConsumer.controller.v1.contract;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Contract.GuaranteeLetterSignRequest;
import com.jpmsoft.projectConsumer.feign.ConFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "保函签收", value = "保函签收")
@RequestMapping("/guarantee/v1/GuaranteeLetter")
public class GuaranteeLetterSignController {
    @Autowired
    private ConFeign conFeign;

    @ApiOperation(value = "新增或修改保函签收")
    @PostMapping("/createOrUpdateGuaranteeLetterSign")
    public BaseResponse createOrUpdateGuaranteeLetterSign(@RequestBody GuaranteeLetterSignRequest request) {
        log.info("createOrUpdateGuaranteeLetterSign");
        return conFeign.createOrUpdateGuaranteeLetterSign(request);
    }

    @ApiOperation(value = "分页查询保函签收")
    @PostMapping("/getGuaranteeLetterSignList")
    public BaseResponse getGuaranteeLetterSignList(@RequestBody GuaranteeLetterSignRequest request, @RequestHeader String token) {
        log.info("getGuaranteeLetterSignList");
        return conFeign.getGuaranteeLetterSignList(request, token);
    }

    @ApiOperation(value = "ID查询保函签收")
    @GetMapping("/getGuaranteeLetterSignByglsId")
    public BaseResponse getGuaranteeLetterSignByglsId(@RequestParam String glsId) {
        log.info("getGuaranteeLetterSignByglsId");
        return conFeign.getGuaranteeLetterSignByglsId(glsId);
    }


    @ApiOperation(value = "合同ID查询保函签收(最新审批通过的一条)")
    @GetMapping("/getGuaranteeLetterSignByContract")
    public BaseResponse getGuaranteeLetterSignByContract(@RequestParam String conId) {
        log.info("getGuaranteeLetterSignByContract");
        return conFeign.getGuaranteeLetterSignByContract(conId);
    }

    @ApiOperation(value = "查询合同支付时是否存在正常保函信息")
    @GetMapping("/getGuaranteeLetterExistence")
    public BaseResponse getGuaranteeLetterExistence(@RequestParam String conId){
        log.info("getGuaranteeLetterExistence");
        return conFeign.getGuaranteeLetterExistence(conId);
    }
}
