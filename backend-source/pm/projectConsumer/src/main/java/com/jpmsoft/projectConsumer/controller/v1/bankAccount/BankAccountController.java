package com.jpmsoft.projectConsumer.controller.v1.bankAccount;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Account.BankAccountRequest;
import com.jpmsoft.projectConsumer.feign.BaseFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "单位账户-单位账户管理", value = "单位账户-单位账户管理")
@RequestMapping("/account/v1/bankAccount")
public class BankAccountController {
    @Autowired
    private BaseFeign baseFeign;

    @ApiOperation(value = "新增或修改单位账户管理")
    @PostMapping("/createOrUpdateBankAccount")
    public BaseResponse createOrUpdateBankAccount(@RequestBody BankAccountRequest request) {
        log.info("createOrUpdateBankAccount");
        return baseFeign.createOrUpdateBankAccount(request);
    }

    @ApiOperation(value = "分页查询单位账户管理")
    @PostMapping("/getBankAccountList")
    public BaseResponse getBankAccountList(@RequestBody BankAccountRequest request) {
        log.info("getBankAccountList");
        return baseFeign.getBankAccountList(request);
    }

    @ApiOperation(value = "分页查询单位账户管理(鉴权)")
    @PostMapping("/getBankAccountAuthList")
    public BaseResponse getBankAccountAuthList(@RequestBody BankAccountRequest request, @RequestHeader String token) {
        log.info("getBankAccountAuthList");
        return baseFeign.getBankAccountAuthList(request, token);
    }

    @ApiOperation(value = "ID查询单位账户管理")
    @GetMapping("/getBankAccountById")
    public BaseResponse getBankAccountById(@RequestParam String baId) {
        log.info("getBankAccountById");
        return baseFeign.getBankAccountById(baId);
    }
}
