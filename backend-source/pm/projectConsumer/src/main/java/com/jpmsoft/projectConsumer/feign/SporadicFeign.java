package com.jpmsoft.projectConsumer.feign;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.request.PageRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 零星结算
 */
@FeignClient(name = "project-producer",contextId = "sporadic")
public interface SporadicFeign {
    /*********************标段信息*********************/
    @PostMapping("/v1/RepairState/getmrRepairStatePage")
    BaseResponse getRepairState(@RequestBody PageRequest pageRequest);

    @GetMapping("/v1/RepairState/modifyState")
    BaseResponse setModifyState(@RequestParam String id, @RequestParam(required = false) String mrRepairState);


}


