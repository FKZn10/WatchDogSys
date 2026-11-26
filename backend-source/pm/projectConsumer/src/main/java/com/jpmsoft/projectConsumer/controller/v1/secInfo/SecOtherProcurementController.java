package com.jpmsoft.projectConsumer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecOtherProcurementModel;
import com.jpmsoft.projectConsumer.feign.SecFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "其它采购", value = "其它采购")
@RequestMapping("/v1/otherProcurement")
public class SecOtherProcurementController {
    @Autowired
    private SecFeign secFeign;

    @ApiOperation(value = "创建或修改其它采购")
    @PostMapping("/createOrUpdateSecOtherProcurement")
    public BaseResponse createOrUpdateSecOtherProcurement(@RequestBody SecOtherProcurementModel request) {
        log.info("createOrUpdateSecOtherProcurement");
        return secFeign.createOrUpdateSecOtherProcurement(request);
    }

    @ApiOperation(value = "分页查询其它采购")
    @PostMapping("/getSecOtherProcurementList")
    public BaseResponse getSecOtherProcurementList(@RequestBody SecOtherProcurementModel request) {
        log.info("getSecOtherProcurementList");
        return secFeign.getSecOtherProcurementList(request);
    }

    @ApiOperation(value = "ID查询其它采购")
    @GetMapping("/getSecOtherProcurementById")
    public BaseResponse getSecOtherProcurementById(@RequestParam String sopId) {
        log.info("getSecOtherProcurementById");
        return secFeign.getSecOtherProcurementById(sopId);
    }
}
