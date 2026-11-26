package com.jpmsoft.projectProducer.controller.v1.riskWarn;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.RiskWarn.RiskWarnRequest;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.riskWarn.IRiskWarnService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/riskWarn")
public class RiskWarnController {

    @Resource
    private IRiskWarnService riskWarnService;
    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/getRiskWarnList")
    public BaseResponse getRiskWarnList(@RequestBody RiskWarnRequest request, @RequestHeader String token) {
        log.info("getRiskWarnList");
        return riskWarnService.getRiskWarnList(request, redisUtils.getUserInfoByToken(token));
    }

    @GetMapping("/getRiskWarnById")
    public BaseResponse getRiskWarnById(@RequestParam String rwId) {
        log.info("getRiskWarnById");
        return riskWarnService.getRiskWarnById(rwId);
    }

    @TenantId
    @PostMapping("/getRiskWarnListOutline")
    public BaseResponse getRiskWarnListOutline(@RequestBody RiskWarnRequest request, @RequestHeader String token) {
        log.info("getRiskWarnListOutline");
        return riskWarnService.getRiskWarnListOutline(request, redisUtils.getUserInfoByToken(token));
    }

    /**
     * 在当前租户和用户权限范围内查询预警排名，仅按 size 限制条数。
     *
     * @param request 筛选条件和排名条数
     * @param token 登录令牌
     * @return 按数量倒序排列的预警名称与数量 Map
     */
    @TenantId
    @PostMapping("/getRiskWarnRanking")
    public BaseResponse getRiskWarnRanking(@RequestBody RiskWarnRequest request, @RequestHeader String token) {
        log.info("getRiskWarnRanking");
        return riskWarnService.getRiskWarnRanking(request, redisUtils.getUserInfoByToken(token));
    }
}
