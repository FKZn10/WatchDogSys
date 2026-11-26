package com.jpmsoft.projectConsumer.controller.v1.fund;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.pojo.dto.fund.FundNumberInfoDto;
import com.jpmsoft.projectConsumer.feign.FundFeign;
import com.jpmsoft.projectConsumer.request.fund.FundNumberInfoRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/v1/fundNumberInfo")
public class FundNumberInfoController {
    @Autowired
    private FundFeign fundFeign;
    @PostMapping("/createOrUpdateFundNumberInfo")
    public BaseResponse createOrUpdateFundNumberInfo(@RequestBody FundNumberInfoDto request) {
        log.info("createOrUpdateFundNumberInfo");
        return fundFeign.createOrUpdateFundNumberInfo(request);
    }
    @PostMapping("/getFundNumberInfoList")
    public BaseResponse getFundNumberInfoList(@RequestBody FundNumberInfoRequest request,@RequestHeader String token) {
        log.info("getFundNumberInfoList");
        return fundFeign.getFundNumberInfoList(request,token);
    }
    @PostMapping("/getFundNumberInfoById")
    public BaseResponse getFundNumberInfoById(@RequestBody FundNumberInfoRequest request) {
        log.info("getFundNumberInfoById");
        return fundFeign.getFundNumberInfoById(request);
    }
}
