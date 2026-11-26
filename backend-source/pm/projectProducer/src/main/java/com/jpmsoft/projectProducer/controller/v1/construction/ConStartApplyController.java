package com.jpmsoft.projectProducer.controller.v1.construction;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Construction.ConStartApplyModel;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.construction.IConStartApplyService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/conStartApply")
public class ConStartApplyController {

    @Resource
    private IConStartApplyService conStartApplyService;

    @Resource
    private RedisUtils redisUtils;


    @TenantId
    @PostMapping("/createOrUpdateConStartApply")
    public BaseResponse createOrUpdateConStartApply(@RequestBody ConStartApplyModel request) {
        log.info("createOrUpdateConStartApply");
        return conStartApplyService.createOrUpdateConStartApply(request);
    }

    @TenantId
    @PostMapping("/getConStartApplyList")
    public BaseResponse getConStartApplyList(@RequestBody ConStartApplyModel request, @RequestHeader String token) {
        log.info("getConStartApplyList");
        return conStartApplyService.getConStartApplyList(request, redisUtils.getUid(token));
    }

    @GetMapping("/getConStartApplyById")
    public BaseResponse getConStartApplyById(@RequestParam String saId) {
        log.info("getConStartApplyById");
        return conStartApplyService.getConStartApplyById(saId);
    }

    @GetMapping("/syncDataToContract")
    public BaseResponse syncDataToContract(@RequestParam String saId) {
        log.info("syncDataToContract");
        return conStartApplyService.syncDataToContract(saId);
    }
}
