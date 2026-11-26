package com.jpmsoft.projectProducer.controller.v1.jcz;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.request.jzc.PageRequest;
import com.jpmsoft.projectProducer.service.maintenance.impl.MaintenanceRecordImpl;
import com.jpmsoft.projectProducer.service.sporadicSettlement.impl.ISporadicSettlementServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@Slf4j
@RequestMapping("/v1/RepairState")
public class RecordController {

    @Resource
    private MaintenanceRecordImpl maintenanceRecord;

    @Resource
    private ISporadicSettlementServiceImpl sporadicSettlement;

    @PostMapping("/getmrRepairStatePage")
    public BaseResponse getState(@RequestBody PageRequest pageRequest){

        return maintenanceRecord.getRecordsByRepairState(pageRequest.getPage(),pageRequest.getSize());
    }

    @GetMapping("/modifyState")
    public BaseResponse ModifymrRepairState(@RequestParam String id, @RequestParam(required = false) String mrRepairState){

        return sporadicSettlement.setModifymrRepairState(id, mrRepairState);
    }



}










