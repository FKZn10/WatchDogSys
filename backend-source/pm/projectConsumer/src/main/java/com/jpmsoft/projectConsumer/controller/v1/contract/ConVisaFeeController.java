package com.jpmsoft.projectConsumer.controller.v1.contract;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Contract.ConVisaFeeRequest;
import com.jpmsoft.projectConsumer.feign.ConFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "签证费用申报", value = "签证费用申报")
@RequestMapping("/v1/conVisaFee")
public class ConVisaFeeController {
    @Autowired
    private ConFeign conFeign;


    @ApiOperation(value = "新增或修改签证费用申报")
    @PostMapping("/createOrUpdateConVisaFee")
    public BaseResponse createOrUpdateConVisaFee(@RequestBody ConVisaFeeRequest request) {
        log.info("createOrUpdateConVisaFee");
        return conFeign.createOrUpdateConVisaFee(request);
    }

    @ApiOperation(value = "分页查签证费用申报")
    @PostMapping("/getConVisaFeeList")
    public BaseResponse getConVisaFeeList(@RequestBody ConVisaFeeRequest request, @RequestHeader String token) {
        log.info("getConVisaFeeList");
        return conFeign.getConVisaFeeList(request, token);
    }

    @ApiOperation(value = "ID查签证费用申报 conWhere区分链表查询合同是是查询本表单还是签证计量备案 0:签证备案 1：本表单 默认 0")
    @GetMapping("/getConVisaFeeByVfId")
    public BaseResponse getConVisaFeeByVfId(@RequestParam String vfId, @RequestParam(required = false) Integer conWhere) {
        log.info("getConVisaFeeByVfId");
        return conFeign.getConVisaFeeByVfId(vfId, conWhere);
    }

    @ApiOperation(value = "ID查签证费用申报增加减少金额")
    @GetMapping("/getVisaAmt")
    public BaseResponse getVisaAmt(@RequestParam String conId) {
        log.info("getVisaAmt");
        return conFeign.getVisaAmt(conId);
    }
}
