package com.jpmsoft.projectConsumer.controller.v1.bankAccount;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Account.BankAccountChangeRequest;
import com.jpmsoft.projectConsumer.feign.BaseFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "单位账户-合同账户变更", value = "单位账户-合同账户变更")
@RequestMapping("/account/v1/accountChange")
public class BankAccountChangeController {
    @Autowired
    private BaseFeign baseFeign;

    @ApiOperation(value = "新增或修改合同账户变更")
    @PostMapping("/createOrUpdateBankAccountChange")
    public BaseResponse createOrUpdateBankAccountChange(@RequestBody BankAccountChangeRequest request) {
        log.info("createOrUpdateBankAccountChange");
        return baseFeign.createOrUpdateBankAccountChange(request);
    }

    @ApiOperation(value = "分页查询合同账户变更")
    @PostMapping("/getBankAccountChangeList")
    public BaseResponse getBankAccountChangeList(@RequestBody BankAccountChangeRequest request, @RequestHeader String token) {
        log.info("getBankAccountChangeList");
        return baseFeign.getBankAccountChangeList(request, token);
    }

    @ApiOperation(value = "ID查询合同账户变更")
    @GetMapping("/getBankAccountChangeById")
    public BaseResponse getBankAccountChangeById(@RequestParam String bacId) {
        log.info("getBankAccountChangeById");
        return baseFeign.getBankAccountChangeById(bacId);
    }

    @ApiOperation(value = "合同id查询最近一条合同账户变更")
    @GetMapping("/getBankAccountChangeByConId")
    public BaseResponse getBankAccountChangeByConId(@RequestParam String conId) {
        log.info("getBankAccountChangeByConId");
        return baseFeign.getBankAccountChangeByConId(conId);
    }
}
