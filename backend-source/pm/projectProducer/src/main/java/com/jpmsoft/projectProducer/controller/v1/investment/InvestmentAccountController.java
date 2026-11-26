package com.jpmsoft.projectProducer.controller.v1.investment;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.service.project.IPreProjectService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/InvestmentAccount")
public class InvestmentAccountController {

    @Resource
    private IPreProjectService preProjectService;
    @Resource
    private RedisUtils redisUtils;

    @GetMapping("/getInvestmentDate")
    public BaseResponse getInvestmentDate(@RequestHeader String token) {
        log.info("getInvestmentDate");
        return preProjectService.getInvestmentDate(redisUtils.getTeId(token));
    }
}
