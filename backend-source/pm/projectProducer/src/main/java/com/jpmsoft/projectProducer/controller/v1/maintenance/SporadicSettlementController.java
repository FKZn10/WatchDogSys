package com.jpmsoft.projectProducer.controller.v1.maintenance;


import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Maintenance.SporadicSettlementRequest;
import com.jpmsoft.projectProducer.pojo.request.jzc.PageRequest;
import com.jpmsoft.projectProducer.service.sporadicSettlement.ISporadicSettlementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/sporadicSettlement")
public class SporadicSettlementController {

    @Resource
    private ISporadicSettlementService ISporadicSettlementService;

    @PostMapping("/getSporadicSettlementeList")
    public BaseResponse getSporadicSettlementeList(@RequestBody SporadicSettlementRequest sporadicSettlementRequest){
        log.info("getSporadicSettlementeList");
        return ISporadicSettlementService.getSporadicSettlementeList(sporadicSettlementRequest);
    }


    @GetMapping("/getSporadicSettlementByMrId")
    public BaseResponse getSporadicSettlementByMaintenanceRecordId(@RequestParam String mrId){
        log.info("getSporadicSettlementByMrId");
        return ISporadicSettlementService.getSporadicSettlementByMrId(mrId);
    }



    /**
     * 校验此零星结算是否符合要求
     * 此保修单不能被除状态为3的结算单选中，不能重复选
     * @param request
     * @return
     */
    @PostMapping("/checkSporadicSettlement")
    public BaseResponse checkSporadicSettlement(@RequestBody SporadicSettlementRequest request){
        log.info("checkSporadicSettlement");
        return ISporadicSettlementService.checkSporadicSettlement(request);
    }

}
