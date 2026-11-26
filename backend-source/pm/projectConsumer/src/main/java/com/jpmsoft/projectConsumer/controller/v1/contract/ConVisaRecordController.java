package com.jpmsoft.projectConsumer.controller.v1.contract;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Contract.ConVisaRecordRequest;
import com.jpmsoft.projectConsumer.feign.ConFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "签证计量备案", value = "签证计量备案")
@RequestMapping("/v1/conVisaRecord")
public class ConVisaRecordController {
    @Autowired
    private ConFeign conFeign;


    @ApiOperation(value = "新增或修改签证计量备案")
    @PostMapping("/createOrUpdateConVisaRecord")
    public BaseResponse createOrUpdateConVisaRecord(@RequestBody ConVisaRecordRequest request) {
        log.info("createOrUpdateConVisaRecord");
        return conFeign.createOrUpdateConVisaRecord(request);
    }

    @ApiOperation(value = "分页查签证计量备案")
    @PostMapping("/getConVisaRecordList")
    public BaseResponse getConVisaRecordList(@RequestBody ConVisaRecordRequest request, @RequestHeader String token) {
        log.info("getConVisaRecordList");
        return conFeign.getConVisaRecordList(request, token);
    }

    @ApiOperation(value = "ID查签证计量备案")
    @GetMapping("/getConVisaRecordByVrId")
    public BaseResponse getConVisaRecordByVrId(@RequestParam String vrId) {
        log.info("getConVisaRecordByVrId");
        return conFeign.getConVisaRecordByVrId(vrId);
    }
}
