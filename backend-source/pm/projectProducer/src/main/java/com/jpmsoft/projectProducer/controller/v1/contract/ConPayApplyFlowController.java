package com.jpmsoft.projectProducer.controller.v1.contract;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.pojo.dto.contract.ConPayApplyFlowDto;
import com.jpmsoft.projectProducer.pojo.dto.contract.ConPayApplyFlowSumDto;
import com.jpmsoft.projectProducer.pojo.vo.contract.ConPayApplyFlowVo;
import com.jpmsoft.projectProducer.service.contract.IConPayApplyFlowService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/ConPayApplyFlow")
public class ConPayApplyFlowController {
    @Resource
    private IConPayApplyFlowService conPayApplyFlowService;

    @GetMapping("/getConPayApplyFlowById")
    public BaseResponse getConPayApplyFlowById(@RequestParam String id) {
        log.info("getConPayApplyFlowById");
        return conPayApplyFlowService.getConPayApplyFlowById(id);
    }


    @GetMapping("/getConPayApplyFlowByConId")
    public BaseResponse getConPayApplyFlowByConId(@RequestParam String conId) {
        log.info("getConPayApplyFlowByConId");
        return conPayApplyFlowService.getConPayApplyFlowByConId(conId);
    }

    @TenantId
    @PostMapping("/getConPayApplyFlowList")
    public BaseResponse getConPayApplyFlowList(@RequestBody ConPayApplyFlowDto request) {
        log.info("getConPayApplyFlowList");
        return conPayApplyFlowService.getConPayApplyFlowList(request);
    }

    @TenantId
    @PostMapping("/createOrUpdateConPayApplyFlow")
    public BaseResponse createOrUpdateConPayApplyFlow(@RequestBody ConPayApplyFlowDto request) {
        log.info("createOrUpdateConPayApplyFlow");
        return conPayApplyFlowService.createOrUpdateConPayApplyFlow(request);
    }

    @PostMapping("/getConPayApplyFlowSumById")
    public BaseResponse getConPayApplyFlowSumById(@RequestBody ConPayApplyFlowSumDto request) {
        log.info("getConPayApplyFlowSumById");
        return conPayApplyFlowService.getConPayApplyFlowSumById(request);
    }

    @GetMapping("/getConPayApplyFlowSumByConId")
    public BaseResponse getConPayApplyFlowSumByConId(@RequestParam String conId) {
        log.info("getConPayApplyFlowSumByConId");
        return conPayApplyFlowService.getConPayApplyFlowSumByConId(conId);
    }

    @PostMapping("/getConPayApplyFlowDataList")
    public BaseResponse getConPayApplyFlowDataList(@RequestBody ConPayApplyFlowVo vo){
        return conPayApplyFlowService.getConPayApplyFlowDataList(vo);
    }

    @PostMapping("/getConPayApplyFlowStatsList")
    public BaseResponse getConPayApplyFlowStatsList(@RequestBody ConPayApplyFlowVo vo){
        return conPayApplyFlowService.getConPayApplyFlowStatsList(vo);
    }
}
