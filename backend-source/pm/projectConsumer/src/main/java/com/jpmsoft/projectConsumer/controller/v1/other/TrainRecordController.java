package com.jpmsoft.projectConsumer.controller.v1.other;


import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.feign.BaseFeign;
import com.jpmsoft.projectConsumer.pojo.dto.other.TrainStaff;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/v1/trainRecord")
public class TrainRecordController {

    @Resource
    private BaseFeign baseFeign;


    @PostMapping("/getTrainRecord")
    public BaseResponse getTrainRecord(@RequestBody TrainStaff trainStaff) {
        return baseFeign.getTrainRecord(trainStaff);
    }
}
