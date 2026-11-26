package com.jpmsoft.projectConsumer.controller.v1.maintenance;


import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Maintenance.SporadicSettlementRequest;
import com.jpmsoft.projectConsumer.feign.BaseFeign;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@Api(tags = "零星维修-零星维修申报", value = "零星维修-零星维修申报")
@RequestMapping("/v1/sporadicSettlement")
public class SporadicSettlementController {

    @Autowired
    private BaseFeign baseFeign;

    @PostMapping("/getSporadicSettlementeList")
    public BaseResponse getSporadicSettlementeList(@RequestBody SporadicSettlementRequest sporadicSettlementRequest){
        log.info("getSporadicSettlementeList");
        return baseFeign.getSporadicSettlementeList(sporadicSettlementRequest);
    }

    @GetMapping("/getSporadicSettlementByMrId")
    public BaseResponse getSporadicSettlementByMaintenanceRecordId(@RequestParam String mrId){
        log.info("getSporadicSettlementByMrId");
        return baseFeign.getSporadicSettlementByMrId(mrId);
    }

    @PostMapping("/checkSporadicSettlement")
    public BaseResponse checkSporadicSettlement(@RequestBody SporadicSettlementRequest request){
        log.info("checkSporadicSettlement");
        return baseFeign.checkSporadicSettlement(request);
    }



}
