package com.jpmsoft.projectConsumer.feign;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.EngineeringTrainingModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component
@FeignClient(name = "project-producer", contextId = "EngineeringTraining")
public interface EngineeringTrainingFeign {
    @PostMapping("/v1/engineeringTraining/saveOrUpdateEngineeringTraining")
    BaseResponse saveOrUpdateEngineeringTraining(@RequestBody EngineeringTrainingModel request);
    @PostMapping("/v1/engineeringTraining/getEngineeringTrainingList")
    BaseResponse getEngineeringTrainingList(@RequestBody EngineeringTrainingModel request,@RequestHeader String token);
    @GetMapping("/v1/engineeringTraining/getEngineeringTrainingDetail")
    BaseResponse getEngineeringTrainingDetail(@RequestParam String id);
    @PostMapping("/v1/engineeringTraining/approvalData")
    BaseResponse approvalData(@RequestBody EngineeringTrainingModel request);
}
