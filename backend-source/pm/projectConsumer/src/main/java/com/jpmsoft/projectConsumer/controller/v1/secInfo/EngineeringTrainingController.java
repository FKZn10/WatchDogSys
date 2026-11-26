package com.jpmsoft.projectConsumer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.EngineeringTrainingModel;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.projectConsumer.feign.EngineeringTrainingFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/v1/engineeringTraining")
public class EngineeringTrainingController {
    @Autowired
    private EngineeringTrainingFeign engineeringTrainingFeign;
    @PostMapping("/saveOrUpdateEngineeringTraining")
    public BaseResponse saveOrUpdateEngineeringTraining(@RequestBody EngineeringTrainingModel request) {
        log.info("saveOrUpdateEngineeringTraining");
        return engineeringTrainingFeign.saveOrUpdateEngineeringTraining(request);
    }
    @PostMapping("/getEngineeringTrainingList")
    public BaseResponse getEngineeringTrainingList(@RequestBody EngineeringTrainingModel request, @RequestHeader String token) {
        log.info("request:{}", JacksonUtils.toJSONString(request));
        log.info("getEngineeringTrainingList");
        return engineeringTrainingFeign.getEngineeringTrainingList(request,token);
    }
    @GetMapping("/getEngineeringTrainingDetail")
    public BaseResponse getEngineeringTrainingDetail(@RequestParam String id) {
        log.info("getEngineeringTrainingDetail");
        return engineeringTrainingFeign.getEngineeringTrainingDetail(id);
    }
    @PostMapping("/approvalData")
    public BaseResponse approvalData(@RequestBody EngineeringTrainingModel request) {
        log.info("approvalData");
        return engineeringTrainingFeign.approvalData(request);
    }
}
