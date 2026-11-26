package com.jpmsoft.projectProducer.controller.v1.bankAccount;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Account.BankAccountRequest;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.bankAccount.IBankAccountService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/bankAccount")
public class BankAccountController {

    @Resource
    private IBankAccountService bankAccountService;
    @Resource
    private RedisUtils redisUtils;


    @TenantId
    @PostMapping("/createOrUpdateBankAccount")
    public BaseResponse createOrUpdateBankAccount(@RequestBody BankAccountRequest request) {
        log.info("createOrUpdateBankAccount");
        return bankAccountService.createOrUpdateBankAccount(request);
    }

    @TenantId
    @PostMapping("/getBankAccountList")
    public BaseResponse getBankAccountList(@RequestBody BankAccountRequest request) {
        log.info("getBankAccountList");
        return bankAccountService.getBankAccountList(request);
    }

    @TenantId
    @PostMapping("/getBankAccountAuthList")
    public BaseResponse getBankAccountAuthList(@RequestBody BankAccountRequest request, @RequestHeader String token) {
        log.info("getBankAccountAuthList");
        return bankAccountService.getBankAccountAuthList(request, redisUtils.getUid(token));
    }

    @GetMapping("/getBankAccountById")
    public BaseResponse getBankAccountById(@RequestParam String baId) {
        log.info("getBankAccountById");
        return bankAccountService.getBankAccountById(baId);
    }
}
