package com.jpmsoft.projectProducer.controller.v1.fund;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.request.fund.BankAccountInfoRequest;
import com.jpmsoft.projectProducer.pojo.request.fund.PayAmountInitRequest;
import com.jpmsoft.projectProducer.service.fund.IPayAmountInitService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/v1/payAmountInit")
public class PayAmountInitController {
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private IPayAmountInitService payAmountInitService;
    @PostMapping("/checkPayAmountInit")
    public BaseResponse checkPayAmountInit(@RequestBody PayAmountInitRequest request,@RequestHeader String token) {
        log.info("checkPayAmountInit");
        return payAmountInitService.checkPayAmountInit(request,redisUtils.getUid(token));
    }

    @PostMapping("/updatePayAmountInit")
    public BaseResponse updatePayAmountInit(@RequestBody PayAmountInitRequest request,@RequestHeader String token) {
        log.info("updatePayAmountInit");
        return payAmountInitService.updatePayAmountInit(request,redisUtils.getUid(token));
    }

    @PostMapping("/getPayAmountInitList")
    public BaseResponse getPayAmountInitList(@RequestBody PayAmountInitRequest request,@RequestHeader String token) {
        log.info("getPayAmountInitList");
        return payAmountInitService.getPayAmountInitList(request,redisUtils.getUid(token));
    }
}
