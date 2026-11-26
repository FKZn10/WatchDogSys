package com.jpmsoft.projectProducer.controller.v1.completion;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Completion.CompletionHandoverRequest;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.completion.ICompletionHandoverService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/handover")
public class CompletionHandoverController {

    @Resource
    private ICompletionHandoverService completionHandoverService;

    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createOrUpdateCompletionHandover")
    public BaseResponse createOrUpdateCompletionHandover(@RequestBody CompletionHandoverRequest request) {
        log.info("createOrUpdateCompletionHandover");
        return completionHandoverService.createOrUpdateCompletionHandover(request);
    }

    @TenantId
    @PostMapping("/getCompletionHandoverList")
    public BaseResponse getCompletionHandoverList(@RequestBody CompletionHandoverRequest request, @RequestHeader String token) {
        log.info("getCompletionHandoverList");
        return completionHandoverService.getCompletionHandoverList(request, redisUtils.getUid(token));
    }

    @GetMapping("/getCompletionHandoverById")
    public BaseResponse getCompletionHandoverById(@RequestParam String chId) {
        log.info("getCompletionHandoverById");
        return completionHandoverService.getCompletionHandoverById(chId);
    }

    /**
     * 同步建筑信息库分项
     */
    @GetMapping("/updateBuildingInfoSub")
    public BaseResponse updateBuildingInfoSub(@RequestParam String chId) {
        log.info("updateBuildingInfoSub");
        return completionHandoverService.updateBuildingInfoSub(chId);
    }

    /**
     * 定时任务轮询接口：重新同步失败的数据
     * 外部服务器定时调用此接口，查询 birState != 1 且 chState = 7 的记录并重新同步
     */
    @GetMapping("/retrySyncFailedBuildingInfo")
    public BaseResponse retrySyncFailedBuildingInfo() {
        log.info("retrySyncFailedBuildingInfo");
        return completionHandoverService.retrySyncFailedBuildingInfo();
    }

    /**
     * 定时任务轮询接口：检查质保过期状态
     */
    @GetMapping("/checkInsuranceStatus")
    public BaseResponse checkInsuranceStatus() {
        log.info("checkInsuranceStatus");
        return completionHandoverService.checkInsuranceStatus();
    }
}

