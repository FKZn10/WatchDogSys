package com.jpmsoft.projectProducer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.service.secInfo.ISecInfoBudgetService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/secInfoBudget")
public class SecInfoBudgetController {

    @Resource
    private ISecInfoBudgetService secInfoBudgetService;
    @Resource
    private RedisUtils redisUtils;

    @GetMapping("/getSecInfoBudgetList")
    public BaseResponse getSecInfoBudgetList(@RequestParam String sibCost, @RequestHeader String token) {
        log.info("getSecInfoBudgetList");
        return secInfoBudgetService.getSecInfoBudgetList(sibCost,redisUtils.getTeId(token));
    }
}
