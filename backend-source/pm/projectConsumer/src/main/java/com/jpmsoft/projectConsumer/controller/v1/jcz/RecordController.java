package com.jpmsoft.projectConsumer.controller.v1.jcz;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.feign.SporadicFeign;
import com.jpmsoft.projectConsumer.request.PageRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "零星维修", value = "零星维修")
@RequestMapping("/v1/RepairState")
public class RecordController {

    @Autowired
    private SporadicFeign sporadicFeign;

    @ApiOperation(value = "获取mrRepairState字段为空或者等于0")
    @PostMapping("/getmrRepairStatePage")
    public BaseResponse getRepairState(@RequestBody PageRequest pageRequest) {
        log.info("/getmrRepairStatePage");
        return sporadicFeign.getRepairState(pageRequest);
    }

    @ApiOperation(value = "设置mrRepairState")
    @GetMapping("/modifyState")
    public BaseResponse setModifyState(@RequestParam String id, @RequestParam(required = false) String mrRepairState) {
        log.info("/modifyState");
        sporadicFeign.setModifyState(id, mrRepairState);
        return BaseResponse.ok();
    }
}
