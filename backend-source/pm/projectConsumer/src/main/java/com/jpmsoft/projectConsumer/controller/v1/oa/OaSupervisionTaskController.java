package com.jpmsoft.projectConsumer.controller.v1.oa;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Oa.OaSupervisionTaskRequest;
import com.jpmsoft.projectConsumer.feign.OaFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "督办任务", value = "督办任务")
@RequestMapping("/OA/SupervisionTask")
public class OaSupervisionTaskController {
    @Autowired
    private OaFeign oaFeign;

    @ApiOperation(value = "新增或修改督办任务")
    @PostMapping("/createOrUpdateOaSupervisionTask")
    public BaseResponse createOrUpdateOaSupervisionTask(@RequestBody OaSupervisionTaskRequest request) {
        log.info("createOrUpdateOaSupervisionTask");
        return oaFeign.createOrUpdateOaSupervisionTask(request);
    }

    @ApiOperation(value = "分页查询督办任务")
    @PostMapping("/getOaSupervisionTaskList")
    public BaseResponse getOaSupervisionTaskList(@RequestBody OaSupervisionTaskRequest request, @RequestHeader String token) {
        log.info("getOaSupervisionTaskList");
        return oaFeign.getOaSupervisionTaskList(request, token);
    }

    @ApiOperation(value = "ID查询督办任务")
    @GetMapping("/getOaSupervisionTaskByStId")
    public BaseResponse getOaSupervisionTaskByStId(@RequestParam String stId) {
        log.info("getOaSupervisionTaskByStId");
        return oaFeign.getOaSupervisionTaskByStId(stId);
    }

    @ApiOperation(value = "导出督查督办统计")
    @PostMapping("/exportOaSupervisionTaskStatistics")
    public BaseResponse exportOaSupervisionTaskStatistics(@RequestBody OaSupervisionTaskRequest request) {
        log.info("getOaSupervisionTaskByStId");
        return oaFeign.exportOaSupervisionTaskStatistics(request);
    }
}
