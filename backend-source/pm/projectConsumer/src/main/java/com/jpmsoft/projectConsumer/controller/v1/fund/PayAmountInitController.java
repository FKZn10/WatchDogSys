package com.jpmsoft.projectConsumer.controller.v1.fund;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.feign.FundFeign;
import com.jpmsoft.projectConsumer.request.fund.PayAmountInitRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/v1/payAmountInit")
public class PayAmountInitController {
    @Autowired
    private FundFeign fundFeign;
    @PostMapping("/checkPayAmountInit")
    public BaseResponse checkPayAmountInit(@RequestBody PayAmountInitRequest request,@RequestHeader String token) {
        log.info("checkPayAmountInit");
        return fundFeign.checkPayAmountInit(request,token);
    }

    @PostMapping("/updatePayAmountInit")
    public BaseResponse updatePayAmountInit(@RequestBody PayAmountInitRequest request,@RequestHeader String token) {
        log.info("updatePayAmountInit");
        return fundFeign.updatePayAmountInit(request,token);
    }
    @PostMapping("/getPayAmountInitList")
    public BaseResponse getPayAmountInitList(@RequestBody PayAmountInitRequest request,@RequestHeader String token) {
        log.info("getPayAmountInitList");
        return fundFeign.getPayAmountInitList(request,token);
    }
}
