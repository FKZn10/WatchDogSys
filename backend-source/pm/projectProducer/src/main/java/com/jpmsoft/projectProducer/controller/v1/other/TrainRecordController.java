package com.jpmsoft.projectProducer.controller.v1.other;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.entity.other.TrainStaff;
import com.jpmsoft.projectProducer.service.other.IApplyRoleService;
import com.jpmsoft.projectProducer.service.other.ITrainRecordService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/v1/trainRecord")
public class TrainRecordController {

    @Resource
    private ITrainRecordService trainRecordService;
    @PostMapping("/getTrainRecord")
    public BaseResponse getTrainRecord(@RequestBody TrainStaff trainStaff) {
        return trainRecordService.getTrainRecord(trainStaff);
    }

}
