package com.jpmsoft.projectConsumer.controller.v1.contract;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Contract.ConEcFeeRelationListRequest;
import com.jpmsoft.common.model.request.Contract.ConEcFeeRequest;
import com.jpmsoft.projectConsumer.feign.ConFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "变更费用申报", value = "变更费用申报")
@RequestMapping("/v1/conEcFee")
public class ConEcFeeController {
    @Autowired
    private ConFeign conFeign;


    @ApiOperation(value = "新增或修改变更费用申报")
    @PostMapping("/createOrUpdateConEcFee")
    public BaseResponse createOrUpdateConEcFee(@RequestBody ConEcFeeRequest request) {
        log.info("createOrUpdateConEcFee");
        return conFeign.createOrUpdateConEcFee(request);
    }
    @PostMapping("/createOrUpdateConEcFeeOrRecord")
    public BaseResponse createOrUpdateConEcFeeOrRecord(@RequestBody ConEcFeeRequest request) {
        log.info("createOrUpdateConEcFeeOrRecord");
        return conFeign.createOrUpdateConEcFeeOrRecord(request);
    }

    @ApiOperation(value = "分页查询变更费用申报 需鉴权")
    @PostMapping("/getConEcFeeList")
    public BaseResponse getConEcFeeList(@RequestBody ConEcFeeRequest request, @RequestHeader String token) {
        log.info("getConEcFeeList");
        return conFeign.getConEcFeeList(request, token);
    }

    @ApiOperation(value = "分页查询变更费用申报关联来源列表")
    @PostMapping("/getConEcFeeRelationList")
    public BaseResponse getConEcFeeRelationList(@RequestBody ConEcFeeRelationListRequest request,
                                                @RequestHeader("token") String token) {
        log.info("getConEcFeeRelationList");
        return conFeign.getConEcFeeRelationList(request, token);
    }

    @ApiOperation(value = "分页查询变更费用申报 需鉴权")
    @PostMapping("/getConEcFeeListorRecord")
    public BaseResponse getConEcFeeListorRecord(@RequestBody ConEcFeeRequest request, @RequestHeader String token) {
        log.info("getConEcFeeList");
        return conFeign.getConEcFeeListorRecord(request, token);
    }


    @PostMapping("/getConEcFeeClassInfo")
    public BaseResponse getConEcFeeClassInfo(@RequestBody ConEcFeeRequest request,@RequestHeader String token) {
        log.info("getConEcFeeClassInfo");
        return conFeign.getConEcFeeClassInfo(request,token);
    }


    @ApiOperation(value = "分页查询变更费用申报台账")
    @PostMapping("/getConEcFeeStandingBook")
    public BaseResponse getConEcFeeStandingBook(@RequestBody ConEcFeeRequest request, @RequestHeader String token) {
        log.info("getConEcFeeStandingBook");
        return conFeign.getConEcFeeStandingBook(request, token);
    }

    @ApiOperation(value = "分页查询变更费用申报-不鉴权")
    @PostMapping("/getConEcFeeListNoAuth")
    public BaseResponse getConEcFeeListNoAuth(@RequestBody ConEcFeeRequest request) {
        log.info("getConEcFeeListNoAuth");
        return conFeign.getConEcFeeListNoAuth(request);
    }


    @ApiOperation(value = "ID查询变更费用申报")
    @PostMapping("/getConEcFeeByEcfId")
    public BaseResponse getConEcFeeByEcfId(@RequestBody ConEcFeeRequest request, @RequestHeader String token) {
        log.info("getConEcFeeByEcfId");
        return conFeign.getConEcFeeByEcfId(request, token);
    }

    @PostMapping("/getConEcFeeorRecordByEcfId")
    public BaseResponse getConEcFeeorRecordByEcfId(@RequestBody ConEcFeeRequest request, @RequestHeader String token) {
        log.info("getConEcFeeorRecordByEcfId");
        return conFeign.getConEcFeeorRecordByEcfId(request, token);
    }
    @ApiOperation(value = "合同ID查询合同累计变更金额")
    @GetMapping("/getConEcFeeApplyMoneySum")
    public BaseResponse getConEcFeeApplyMoneySum(@RequestParam String conId) {
        log.info("getConEcFeeApplyMoneySum");
        return conFeign.getConEcFeeApplyMoneySum(conId);
    }

    /********************************************清标********************************************/

    @ApiOperation(value = "新增修改变更费用申报(清标)")
    @PostMapping("/createOrUpdateConEcFeeClear")
    public BaseResponse createOrUpdateConEcFeeClear(@RequestBody ConEcFeeRequest request) {
        log.info("createOrUpdateConEcFeeClear");
        return conFeign.createOrUpdateConEcFeeClear(request);
    }

    @ApiOperation(value = "分页查询变更费用申报(清标)")
    @PostMapping("/getConEcFeeClearList")
    public BaseResponse getConEcFeeClearList(@RequestBody ConEcFeeRequest request, @RequestHeader String token) {
        log.info("getConEcFeeClearList");
        return conFeign.getConEcFeeClearList(request, token);
    }

    @ApiOperation(value = "ID查询变更费用申报(清标)")
    @GetMapping("/getConEcFeeClearById")
    public BaseResponse getConEcFeeClearById(@RequestParam String id) {
        log.info("getConEcFeeClearById");
        return conFeign.getConEcFeeClearById(id);
    }

    @ApiOperation(value = "统计合同的初审金额")
    @GetMapping("/getPreliminaryAmountSumByConId")
    public BaseResponse getPreliminaryAmountSumByConId(@RequestParam("conId") String conId) {
        log.info("getPreliminaryAmountSumByConId");
        return conFeign.getPreliminaryAmountSumByConId(conId);
    }

    @PostMapping("/getConEcFeeChangePayList")
    public BaseResponse getConEcFeeChangePayList(@RequestBody ConEcFeeRequest request, @RequestHeader String token) {
        log.info("getConEcFeeChangePayList");
        return conFeign.getConEcFeeChangePayList(request, token);
    }
}
