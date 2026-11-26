package com.jpmsoft.projectProducer.controller.v1.investment;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.pojo.dto.investment.InvBudgetDeclareAddDTO;
import com.jpmsoft.projectProducer.service.investment.IInvBudgetDeclareAddService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/3/29 15:31
 */
@Slf4j
@RestController
@RequestMapping("/v1/budgetAdd")
public class InvBudgetDeclareAddController {

    @Resource
    private IInvBudgetDeclareAddService invBudgetDeclareAddService;

    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createOrUpdateInvBudgetDeclareAdd")
    public BaseResponse createOrUpdateInvBudgetDeclareAdd(@RequestBody InvBudgetDeclareAddDTO request) {
        log.info("createOrUpdateInvBudgetDeclareAdd");
        return invBudgetDeclareAddService.createOrUpdate(request);
    }

    @TenantId
    @PostMapping("/getInvBudgetDeclareAddList")
    public BaseResponse getInvBudgetDeclareAddList(@RequestBody InvBudgetDeclareAddDTO request, @RequestHeader String token) {
        log.info("getInvBudgetDeclareAddList");
        return invBudgetDeclareAddService.getInvBudgetDeclareAddList(request, redisUtils.getUid(token));
    }

    @GetMapping("/getInvBudgetDeclareAddById")
    public BaseResponse getInvBudgetDeclareAddById(@RequestParam String id) {
        log.info("getInvBudgetDeclareAddById");
        return invBudgetDeclareAddService.getInvBudgetDeclareAddById(id);
    }

    @GetMapping("/approve2AddProBudget")
    public BaseResponse approve2AddProBudget(@RequestParam String id) {
        log.info("approve2AddProBudget");
        return invBudgetDeclareAddService.approve2AddProBudget(id);
    }
}
