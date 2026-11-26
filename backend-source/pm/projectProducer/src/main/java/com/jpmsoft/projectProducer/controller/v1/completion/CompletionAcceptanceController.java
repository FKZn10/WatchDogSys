package com.jpmsoft.projectProducer.controller.v1.completion;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Construction.CompletionAcceptanceRequest;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.completion.ICompletionAcceptanceService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@Slf4j
@RestController
@RequestMapping("/v1/acceptance")
public class CompletionAcceptanceController {
    @Resource
    private ICompletionAcceptanceService completionAcceptanceService;

    @Resource
    private RedisUtils redisUtils;


    @TenantId
    @PostMapping("/createOrUpdateCompletionAcceptance")
    public BaseResponse createOrUpdateCompletionAcceptance(@RequestBody CompletionAcceptanceRequest request) {
        log.info("createOrUpdateCompletionAcceptance");
        return completionAcceptanceService.createOrUpdateCompletionAcceptance(request);
    }

    @TenantId
    @PostMapping("/getCompletionAcceptanceList")
    public BaseResponse getCompletionAcceptanceList(@RequestBody CompletionAcceptanceRequest request, @RequestHeader String token) {
        log.info("getCompletionAcceptanceList");
        return completionAcceptanceService.getCompletionAcceptanceList(request, redisUtils.getUid(token));
    }

    @TenantId
    @PostMapping("/getCompletionAcceptanceStatisticsList")
    public BaseResponse getCompletionAcceptanceStatisticsList(@RequestBody CompletionAcceptanceRequest request, @RequestHeader String token) {
        log.info("getCompletionAcceptanceStatisticsList");
        return completionAcceptanceService.getCompletionAcceptanceStatisticsList(request, redisUtils.getUid(token));
    }

    @TenantId
    @PostMapping("/getCompletionAcceptanceListNoAuth")
    public BaseResponse getCompletionAcceptanceListNoAuth(@RequestBody CompletionAcceptanceRequest request) {
        log.info("getCompletionAcceptanceListNoAuth");
        return completionAcceptanceService.getCompletionAcceptanceListNoAuth(request);
    }

    @PostMapping("/getCompletionAcceptanceLastByPidAndType")
    public BaseResponse getCompletionAcceptanceLastByPidAndType(@RequestBody CompletionAcceptanceRequest request) {
        log.info("getCompletionAcceptanceLastByPidAndType");
        return completionAcceptanceService.getCompletionAcceptanceLastByPidAndType(request);
    }

    @GetMapping("/getCompletionAcceptanceById")
    public BaseResponse getCompletionAcceptanceById(@RequestParam String caId) {
        log.info("getCompletionAcceptanceById");
        return completionAcceptanceService.getCompletionAcceptanceById(caId);
    }


    @GetMapping("/approve2RefreshProStage")
    public BaseResponse approve2RefreshProStage(@RequestParam String caId) {
        log.info("approve2RefreshProStage");
        return completionAcceptanceService.approve2RefreshProStage(caId);
    }

    /**
     * 校验项目合同是否能竣工
     * 传递conId , 判断是否这个合同是否在走变更，如果在走变更就不能竣工
     */
    @GetMapping("/checkCompletionAcceptance")
    public BaseResponse checkCompletionAcceptance(@RequestParam String conId) {
        log.info("checkCompletionAcceptance");
        return completionAcceptanceService.checkCompletionAcceptance(conId);
    }

}
