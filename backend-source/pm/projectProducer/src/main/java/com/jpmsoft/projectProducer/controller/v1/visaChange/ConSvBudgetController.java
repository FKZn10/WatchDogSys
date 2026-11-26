package com.jpmsoft.projectProducer.controller.v1.visaChange;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.pojo.dto.visaChange.ConSvBudgetDTO;
import com.jpmsoft.projectProducer.service.visaChange.IConSvBudgetService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/conSvBudget")
public class ConSvBudgetController {
    @Resource
    private IConSvBudgetService conSvBudgetService;

    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createOrUpdateConSvBudget")
    public BaseResponse createOrUpdateConSvBudget(@RequestBody ConSvBudgetDTO request) {
        log.info("createOrUpdateConSvBudget");
        return conSvBudgetService.createOrUpdateConSvBudget(request);
    }

    @TenantId
    @PostMapping("/getConSvBudgetList")
    public BaseResponse getConSvBudgetList(@RequestBody ConSvBudgetDTO request, @RequestHeader String token) {
        log.info("getConSvBudgetList");
        return conSvBudgetService.getConSvBudgetList(request, redisUtils.getUid(token));
    }

    @GetMapping("/getConSvBudgetById")
    public BaseResponse getConSvBudgetById(@RequestParam String id) {
        log.info("getConSvBudgetById");
        return conSvBudgetService.getConSvBudgetById(id);
    }

    @GetMapping("/getCsbNotContainAmtByConId")
    public BaseResponse getCsbNotContainAmtByConId(@RequestParam String conId) {
        log.info("getCsbNotContainAmtByConId");
        return conSvBudgetService.getNotContainAmtByConId(conId);
    }
}
