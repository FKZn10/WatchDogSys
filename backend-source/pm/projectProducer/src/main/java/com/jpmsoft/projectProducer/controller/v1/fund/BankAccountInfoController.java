package com.jpmsoft.projectProducer.controller.v1.fund;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.request.fund.BankAccountInfoRequest;
import com.jpmsoft.projectProducer.service.fund.IBankAccountInfoService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/v1/bankAccountInfo")
public class BankAccountInfoController {

    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private IBankAccountInfoService bankAccountService;
    @PostMapping("/getBankAccountInfoList")
    public BaseResponse getBankAccountInfoList(@RequestBody BankAccountInfoRequest request,@RequestHeader String token) {
        log.info("getBankAccountInfoList");
        return bankAccountService.getBankAccountInfoList(request,redisUtils.getUid(token));
    }


    @PostMapping("/getBankAccountInfoByID")
    public BaseResponse getBankAccountInfoByID(@RequestBody BankAccountInfoRequest request,@RequestHeader String token) {
        log.info("getBankAccountInfoByID");
        return bankAccountService.getBankAccountInfoByID(request,redisUtils.getUid(token));
    }
}
