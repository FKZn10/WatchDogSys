package com.jpmsoft.projectConsumer.controller.v1.fund;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.feign.FundFeign;
import com.jpmsoft.projectConsumer.request.fund.BankAccountInfoRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/v1/bankAccountInfo")
public class BankAccountInfoController {
    @Autowired
    private FundFeign fundFeign;
    @PostMapping("/getBankAccountInfoList")
    public BaseResponse getBankAccountInfoList(@RequestBody BankAccountInfoRequest request,@RequestHeader String token) {
        log.info("getBankAccountInfoList");
        return fundFeign.getBankAccountInfoList(request,token);
    }
    @PostMapping("/getBankAccountInfoByID")
    public BaseResponse getBankAccountInfoByID(@RequestBody BankAccountInfoRequest request,@RequestHeader String token) {
        log.info("getBankAccountInfoByID");
        return fundFeign.getBankAccountInfoByID(request,token);
    }
}
