package com.jpmsoft.projectConsumer.controller.v1.contract;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.pojo.dto.contract.ConPayApplyFlowDto;
import com.jpmsoft.projectConsumer.pojo.dto.contract.ConPayApplyFlowSumDto;
import com.jpmsoft.projectConsumer.pojo.dto.contract.ConPayApplyFlowVo;
import com.jpmsoft.projectConsumer.feign.ConFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "资金支付申请详情", value = "资金支付申请详情")
@RequestMapping("/v1/ConPayApplyFlow")
public class ConPayApplyFlowController {
    @Autowired
    private ConFeign conFeign;

    @GetMapping("/getConPayApplyFlowById")
    public BaseResponse getConPayApplyFlowById(@RequestParam String id) {
        log.info("getConPayApplyFlowById");
        return conFeign.getConPayApplyFlowById(id);
    }

    @ApiOperation(value = "合同ID查询审批通过的数据集合")
    @GetMapping("/getConPayApplyFlowByConId")
    public BaseResponse getConPayApplyFlowByConId(@RequestParam String conId) {
        log.info("getConPayApplyFlowByConId");
        return conFeign.getConPayApplyFlowByConId(conId);
    }


    @PostMapping("/getConPayApplyFlowList")
    public BaseResponse getConPayApplyFlowList(@RequestBody ConPayApplyFlowDto request) {
        log.info("getConPayApplyFlowList");
        return conFeign.getConPayApplyFlowList(request);
    }


    @PostMapping("/createOrUpdateConPayApplyFlow")
    public BaseResponse createOrUpdateConPayApplyFlow(@RequestBody ConPayApplyFlowDto request) {
        log.info("createOrUpdateConPayApplyFlow");
        return conFeign.createOrUpdateConPayApplyFlow(request);
    }
    @PostMapping("/getConPayApplyFlowSumById")
    public BaseResponse getConPayApplyFlowSumById(@RequestBody ConPayApplyFlowSumDto request) {
        log.info("getConPayApplyFlowSumById");
        return conFeign.getConPayApplyFlowSumById(request);
    }

    @GetMapping("/getConPayApplyFlowSumByConId")
    public BaseResponse getConPayApplyFlowSumByConId(@RequestParam String conId) {
        log.info("getConPayApplyFlowSumByConId");
        return conFeign.getConPayApplyFlowSumByConId(conId);
    }

    @PostMapping("/getConPayApplyFlowDataList")
    public BaseResponse getConPayApplyFlowDataList(@RequestBody ConPayApplyFlowVo vo){
        log.info("getConPayApplyFlowDataList");
        return conFeign.getConPayApplyFlowDataList(vo);
    }

    @PostMapping("/getConPayApplyFlowStatsList")
    public BaseResponse getConPayApplyFlowStatsList(@RequestBody ConPayApplyFlowVo vo){
        log.info("getConPayApplyFlowStatsList");
        return conFeign.getConPayApplyFlowStatsList(vo);
    }

}
