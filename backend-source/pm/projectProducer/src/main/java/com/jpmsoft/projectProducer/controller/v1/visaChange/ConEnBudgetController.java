package com.jpmsoft.projectProducer.controller.v1.visaChange;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.DesignChange.ConEnBudgetRequest;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.visaChange.IConEnBudgetService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/conEnBudget")
public class ConEnBudgetController {
    @Resource
    private IConEnBudgetService conEnBudgetService;

    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createOrUpdateConEnBudget")
    public BaseResponse createOrUpdateConEnBudget(@RequestBody ConEnBudgetRequest request) {
        log.info("createOrUpdateConEnBudget");
        return conEnBudgetService.createOrUpdateConEnBudget(request);
    }

    @TenantId
    @PostMapping("/getConEnBudgetList")
    public BaseResponse getConEnBudgetList(@RequestBody ConEnBudgetRequest request, @RequestHeader String token) {
        log.info("getConEnBudgetList");
        return conEnBudgetService.getConEnBudgetList(request, redisUtils.getUid(token));
    }

    @GetMapping("/getConEnBudgetById")
    public BaseResponse getConEnBudgetById(@RequestParam String enbId) {
        log.info("getConEnBudgetById");
        return conEnBudgetService.getConEnBudgetById(enbId);
    }

    @GetMapping("/getEnbNotContainAmtByConId")
    public BaseResponse getEnbNotContainAmtByConId(@RequestParam String conId) {
        log.info("getEnbNotContainAmtByConId");
        return conEnBudgetService.getNotContainAmtByConId(conId);
    }
}
