package com.jpmsoft.projectProducer.controller.v1.contract;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.pojo.dto.contract.ConMeasureRecordCheckDto;
import com.jpmsoft.projectProducer.service.contract.IConMeasureRecordCheckService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author: 李文涛 aaron
 * @date: 2022/7/14 8:58
 */
@Slf4j
@RestController
@RequestMapping("/v1/conMeasureRecordCheck")
public class ConMeasureRecordCheckController {

    @Resource
    private IConMeasureRecordCheckService conMeasureRecordCheckService;

    @TenantId
    @PostMapping("/createConMeasureRecordCheck")
    public BaseResponse createConMeasureRecordCheck(@RequestBody ConMeasureRecordCheckDto request) {
        log.info("createConMeasureRecordCheck");
        return conMeasureRecordCheckService.createConMeasureRecordCheck(request);
    }

    @PostMapping("/updateConMeasureRecordCheck")
    public BaseResponse updateConMeasureRecordCheck(@RequestBody ConMeasureRecordCheckDto request) {
        log.info("updateConMeasureRecordCheck");
        return conMeasureRecordCheckService.updateConMeasureRecordCheck(request);
    }

    @GetMapping("/getConMeasureRecordCheckById")
    public BaseResponse getConMeasureRecordCheckById(@RequestParam String id) {
        log.info("getConMeasureRecordCheckById");
        return conMeasureRecordCheckService.getConMeasureRecordCheckById(id);
    }

    @TenantId
    @PostMapping("/getConMeasureRecordCheckList")
    public BaseResponse getConMeasureRecordCheckList(@RequestBody ConMeasureRecordCheckDto request) {
        log.info("getConMeasureRecordCheckList");
        return conMeasureRecordCheckService.getConMeasureRecordCheckList(request);
    }

}
