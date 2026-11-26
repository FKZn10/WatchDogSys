package com.jpmsoft.projectProducer.controller.v1.investment;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.InvControlManageModel;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.investment.IInvControlManageService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/invControlManage")
public class InvControlManageController {
    @Resource
    private IInvControlManageService invControlManageService;
    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createOrUpdateInvControlManage")
    public BaseResponse createOrUpdateInvControlManage(@RequestBody InvControlManageModel request) {
        log.info("createOrUpdateInvControlManage");
        return invControlManageService.createOrUpdateInvControlManage(request);
    }

    @TenantId
    @PostMapping("/getInvControlManageList")
    public BaseResponse getInvControlManageList(@RequestBody InvControlManageModel request, @RequestHeader String token) {
        log.info("getInvControlManageList");
        return invControlManageService.getInvControlManageList(request, redisUtils.getUid(token));
    }

    @GetMapping("/getInvControlManageById")
    public BaseResponse getInvControlManageById(@RequestParam String icmId) {
        log.info("getInvControlManageById");
        return invControlManageService.getInvControlManageById(icmId);
    }
}
