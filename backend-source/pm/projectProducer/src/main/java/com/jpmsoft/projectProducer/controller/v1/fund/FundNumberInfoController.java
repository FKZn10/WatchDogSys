package com.jpmsoft.projectProducer.controller.v1.fund;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.fund.FundNumberInfoDto;
import com.jpmsoft.projectProducer.pojo.request.fund.FundNumberInfoRequest;
import com.jpmsoft.projectProducer.service.fund.IFundNumberInfoService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/v1/fundNumberInfo")
public class FundNumberInfoController {
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private IFundNumberInfoService fundsService;

    @PostMapping("/createOrUpdateFundNumberInfo")
    public BaseResponse createOrUpdateFundNumberInfo(@RequestBody FundNumberInfoDto request) {
        log.info("createOrUpdateFundNumberInfo");
        return fundsService.createOrUpdateFundNumberInfo(request);
    }
    @PostMapping("/getFundNumberInfoList")
    public BaseResponse getFundNumberInfoList(@RequestBody FundNumberInfoRequest request,@RequestHeader String token) {
        log.info("getFundNumberInfoList");
        return fundsService.getFundNumberInfoList(request,redisUtils.getUid(token));
    }
    @PostMapping("/getFundNumberInfoById")
    public BaseResponse getFundNumberInfoById(@RequestBody FundNumberInfoRequest request) {
        log.info("getFundNumberInfoById");
        return fundsService.getFundNumberInfoById(request.getId());
    }
}
