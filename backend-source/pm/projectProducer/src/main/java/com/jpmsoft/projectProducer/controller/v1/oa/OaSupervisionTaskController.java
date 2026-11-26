package com.jpmsoft.projectProducer.controller.v1.oa;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Oa.OaSupervisionTaskRequest;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.oa.IOaSupervisionTaskService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/OA/SupervisionTask")
public class OaSupervisionTaskController {

    @Resource
    private IOaSupervisionTaskService oaSupervisionTaskService;
    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createOrUpdateOaSupervisionTask")
    public BaseResponse createOrUpdateOaSupervisionTask(@RequestBody OaSupervisionTaskRequest request) {
        log.info("createOrUpdateOaSupervisionTask");
        return oaSupervisionTaskService.createOrUpdateOaSupervisionTask(request);
    }

    @TenantId
    @PostMapping("/getOaSupervisionTaskList")
    public BaseResponse getOaSupervisionTaskList(@RequestBody OaSupervisionTaskRequest request, @RequestHeader String token) {
        log.info("getOaSupervisionTaskList");
        return oaSupervisionTaskService.getOaSupervisionTaskList(request, redisUtils.getUid(token));
    }

    @GetMapping("/getOaSupervisionTaskByStId")
    public BaseResponse getOaSupervisionTaskByStId(@RequestParam String stId) {
        log.info("getOaSupervisionTaskByStId");
        return oaSupervisionTaskService.getOaSupervisionTaskByStId(stId);
    }

    @TenantId
    @PostMapping("/exportOaSupervisionTaskStatistics")
    public BaseResponse exportOaSupervisionTaskStatistics(@RequestBody OaSupervisionTaskRequest request) {
        log.info("exportOaSupervisionTaskStatistics");
        return oaSupervisionTaskService.exportOaSupervisionTaskStatistics(request);
    }
}
