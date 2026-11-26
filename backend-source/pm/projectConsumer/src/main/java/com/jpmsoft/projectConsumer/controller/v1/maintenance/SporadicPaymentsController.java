package com.jpmsoft.projectConsumer.controller.v1.maintenance;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Maintenance.SporadicPaymentsRequest;
import com.jpmsoft.projectConsumer.feign.BaseFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/sporadicPayments")
public class SporadicPaymentsController {

    @Resource
    private BaseFeign baseFeign;

    @PostMapping("/getSporadicPaymentsList")
    public BaseResponse getSporadicPaymentsList(@RequestBody SporadicPaymentsRequest request, @RequestHeader String token){
        return baseFeign.getSporadicPaymentsList(request,token);
    }

    /**
     * 传递 零星结算 id 的 列表 ，判断 零星维修 的 经费号 是否相同
     */
    @PostMapping("/checkSporadicPaymentsByMfCode")
    public BaseResponse checkSporadicPaymentsByMfCode(@RequestBody List<String> sporadicSettlementIds){
        return baseFeign.checkSporadicPaymentsByMfCode(sporadicSettlementIds);
    }

    @GetMapping("/modifyState")
    public BaseResponse modifymrRepairState(@RequestParam String id, @RequestParam(required = false) String mrRepairState){

        return baseFeign.setModifymrRepairState(id, mrRepairState);
    }

    @GetMapping("/getSporadicPaymentsBySeId")
    public BaseResponse getSporadicPaymentsBySeId(@RequestParam String id){
        return baseFeign.getSporadicPaymentsBySeId(id);
    }

}
