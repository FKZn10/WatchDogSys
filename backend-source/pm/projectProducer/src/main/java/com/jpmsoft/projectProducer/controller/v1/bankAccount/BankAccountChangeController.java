package com.jpmsoft.projectProducer.controller.v1.bankAccount;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Account.BankAccountChangeRequest;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.bankAccount.IBankAccountChangeService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/accountChange")
public class BankAccountChangeController {

    @Resource
    private IBankAccountChangeService bankAccountChangeService;

    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createOrUpdateBankAccountChange")
    public BaseResponse createOrUpdateBankAccountChange(@RequestBody BankAccountChangeRequest request) {
        log.info("createOrUpdateBankAccountChange");
        return bankAccountChangeService.createOrUpdateBankAccountChange(request);
    }

    @TenantId
    @PostMapping("/getBankAccountChangeList")
    public BaseResponse getBankAccountChangeList(@RequestBody BankAccountChangeRequest request, @RequestHeader String token) {
        log.info("getBankAccountChangeList");
        return bankAccountChangeService.getBankAccountChangeList(request, redisUtils.getUid(token));
    }

    @GetMapping("/getBankAccountChangeById")
    public BaseResponse getBankAccountChangeById(@RequestParam String bacId) {
        log.info("getBankAccountChangeById");
        return bankAccountChangeService.getBankAccountChangeById(bacId);
    }


    @GetMapping("/getBankAccountChangeByConId")
    public BaseResponse getBankAccountChangeByConId(@RequestParam String conId) {
        log.info("getBankAccountChangeByConId");
        return bankAccountChangeService.getBankAccountChangeByConId(conId);
    }
}
