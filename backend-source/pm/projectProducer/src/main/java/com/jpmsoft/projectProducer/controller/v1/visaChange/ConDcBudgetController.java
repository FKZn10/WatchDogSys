package com.jpmsoft.projectProducer.controller.v1.visaChange;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.DesignChange.ConDcBudgetRequest;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.construction.IConDcBudgetService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/conDcBudget")
public class ConDcBudgetController {

    @Resource
    private IConDcBudgetService conDcBudgetService;
    @Resource
    private RedisUtils redisUtils;


    @TenantId
    @PostMapping("/createOrUpdateConDcBudget")
    public BaseResponse createOrUpdateConDcBudget(@RequestBody ConDcBudgetRequest request) {
        log.info("createOrUpdateConDcBudget");
        return conDcBudgetService.createOrUpdateConDcBudget(request);
    }

    @TenantId
    @PostMapping("/getConDcBudgetList")
    public BaseResponse getConDcBudgetList(@RequestBody ConDcBudgetRequest request, @RequestHeader String token) {
        log.info("getConDcBudgetList");
        return conDcBudgetService.getConDcBudgetList(request, redisUtils.getUid(token));
    }

    @GetMapping("/getConDcBudgetByDcbId")
    public BaseResponse getConDcBudgetByDcbId(@RequestParam String dcbId) {
        log.info("getConDcBudgetByDcbId");
        return conDcBudgetService.getConDcBudgetByDcbId(dcbId);
    }

    @GetMapping("/getDcbNotContainAmtByConId")
    public BaseResponse getDcbNotContainAmtByConId(@RequestParam String conId) {
        log.info("getDcbNotContainAmtByConId");
        return conDcBudgetService.getNotContainAmtByConId(conId);
    }
}
