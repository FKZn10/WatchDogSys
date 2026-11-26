package com.jpmsoft.projectProducer.controller.v1.investment;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.service.investment.IOperationRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/operationRecord")
public class OperationRecordController {

    @Resource
    private IOperationRecordService operationRecordService;


    @GetMapping("/getOperationRecordList")
    public BaseResponse getOperationRecordList(@RequestParam String id) {
        log.info("getOperationRecordList");
        return operationRecordService.getOperationRecordList(id);
    }

}
