package com.jpmsoft.projectProducer.controller.v1.project;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Project.PreProApprovalModel;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.project.IPreProApprovalService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/proApproval")
public class PreProApprovalController {

    @Resource
    private IPreProApprovalService preProApprovalService;

    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createOrUpdatePreProApproval")
    public BaseResponse createOrUpdatePreProApproval(@RequestBody PreProApprovalModel request) {
        log.info("createOrUpdatePreProApproval");
        return preProApprovalService.createOrUpdatePreProApproval(request);
    }

    @TenantId
    @PostMapping("/getPreProApprovalList")
    public BaseResponse getPreProApprovalList(@RequestBody PreProApprovalModel request, @RequestHeader String token) {
        log.info("getPreProApprovalList");
        return preProApprovalService.getPreProApprovalList(request, redisUtils.getUid(token));
    }

    @TenantId
    @PostMapping("/getPreProApprovalStatisticsList")
    public BaseResponse getPreProApprovalStatisticsList(@RequestBody PreProApprovalModel request, @RequestHeader String token) {
        log.info("getPreProApprovalStatisticsList");
        return preProApprovalService.getPreProApprovalStatisticsList(request, redisUtils.getUid(token));
    }

    @PostMapping("/getPreProApprovalDtlByPIds")
    public BaseResponse getPreProApprovalDtlByPIds(@RequestBody List<String> pIds) {
        log.info("getPreProApprovalDtlByPIds");
        return preProApprovalService.getPreProApprovalDtlByPIds(pIds);
    }

    @GetMapping("/getPreProApprovalById")
    public BaseResponse getPreProApprovalById(@RequestParam String paId) {
        log.info("getPreProApprovalById");
        return preProApprovalService.getPreProApprovalById(paId);
    }

    @GetMapping("/getPreProApprovalByPid")
    public BaseResponse getPreProApprovalByPid(@RequestParam String pId) {
        log.info("getPreProApprovalByPid");
        return preProApprovalService.getPreProApprovalByPid(pId);
    }

    @GetMapping("/getPreProApprovalWYById")
    public BaseResponse getPreProApprovalWYById(@RequestParam String paId) {
        log.info("getPreProApprovalWYById");
        return preProApprovalService.getPreProApprovalWYById(paId);
    }
}
