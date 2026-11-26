package com.jpmsoft.projectProducer.controller.v1.maintenance;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Maintenance.SporadicPaymentsRequest;
import com.jpmsoft.projectProducer.service.sporadicSettlement.ISporadicPaymentsService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/sporadicPayments")
public class SporadicPaymentsController {

    @Resource
    private RedisUtils redisUtils;

    @Resource
    private ISporadicPaymentsService sporadicPaymentsService;

    @PostMapping("/getSporadicPaymentsList")
    public BaseResponse getSporadicPaymentsList(@RequestBody SporadicPaymentsRequest request, @RequestHeader String token){
        return sporadicPaymentsService.getSporadicPaymentsList(request,redisUtils.getUid(token));
    }

    /**
     * 传递 零星结算 id 的 列表 ，判断 零星维修 的 经费号 是否相同
     */
    @PostMapping("/checkSporadicPaymentsByMfCode")
    public BaseResponse checkSporadicPaymentsByMfCode(@RequestBody List<String> sporadicSettlementIds){
        return sporadicPaymentsService.checkSporadicPaymentsByMfCode(sporadicSettlementIds);
    }

    @GetMapping("/modifyState")
    public BaseResponse ModifymrRepairState(@RequestParam String id, @RequestParam(required = false) String mrRepairState){

        return sporadicPaymentsService.setModifymrRepairState(id, mrRepairState);
    }

    @GetMapping("/getSporadicPaymentsBySeId")
    public BaseResponse getSporadicPaymentsBySeId(@RequestParam String id){
        return sporadicPaymentsService.getSporadicPaymentsBySeId(id);
    }

}
