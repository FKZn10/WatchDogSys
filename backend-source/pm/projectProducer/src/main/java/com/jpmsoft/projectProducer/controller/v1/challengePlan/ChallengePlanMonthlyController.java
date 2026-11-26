package com.jpmsoft.projectProducer.controller.v1.challengePlan;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.challengePlan.ChallengePlanAnnualRequest;
import com.jpmsoft.common.model.request.challengePlan.ChallengePlanMonthlyPageRequest;
import com.jpmsoft.common.model.request.challengePlan.ChallengePlanMonthlyRequest;
import com.jpmsoft.projectProducer.service.challengePlan.IChallengePlanMonthlyService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author tianhelin
 * @date 2025/5/30
 */
@Slf4j
@RestController
@RequestMapping("/v1/challengePlanMonthly")
public class ChallengePlanMonthlyController {
    @Resource
    private IChallengePlanMonthlyService challengePlanMonthlyService;
    @Resource
    private RedisUtils redisUtils;

    /**
     * 创建月度计划
     *
     * @param request
     * @return
     */
    @PostMapping("/createChallengePlanMonthly")
    public BaseResponse createChallengePlanMonthly(@RequestBody ChallengePlanAnnualRequest request) {
        log.info("createChallengePlanMonthly");
        return challengePlanMonthlyService.createChallengePlanMonthly(request);
    }

    /**
     * 更新月度计划
     *
     * @param request
     * @return
     */
    @PostMapping("/updateChallengePlanMonthly")
    public BaseResponse updateChallengePlanMonthly(@RequestBody ChallengePlanMonthlyRequest request) {
        log.info("updateChallengePlanMonthly");
        return challengePlanMonthlyService.updateChallengePlanMonthly(request);
    }

    /**
     * 获取月度计划列表
     *
     * @param request
     * @param token
     * @return
     */
    @PostMapping("/getChallengePlanMonthlyList")
    public BaseResponse getChallengePlanMonthlyList(@RequestBody ChallengePlanMonthlyPageRequest request, @RequestHeader String token) {
        log.info("getChallengePlanMonthlyList");
        return challengePlanMonthlyService.getChallengePlanMonthlyList(request, redisUtils.getUid(token));
    }

    /**
     * 获取月度计划信息
     *
     * @param request
     * @param token
     * @return
     */
    @PostMapping("/getChallengePlanMonthlyClassInfo")
    public BaseResponse getChallengePlanMonthlyClassInfo(@RequestBody ChallengePlanMonthlyPageRequest request, @RequestHeader String token) {
        log.info("getChallengePlanMonthlyClassInfo");
        return challengePlanMonthlyService.getChallengePlanMonthlyClassInfo(request, redisUtils.getUid(token));
    }

    /**
     * 根据id获取月度计划信息
     *
     * @param id
     * @param token
     * @return
     */
    @GetMapping("/getChallengePlanMonthlyById")
    public BaseResponse getChallengePlanMonthlyById(@RequestParam("id") String id, @RequestHeader String token) {
        log.info("getChallengePlanMonthlyById");
        return challengePlanMonthlyService.getChallengePlanMonthlyById(id, redisUtils.getUid(token));
    }

    /**
     * 批量更新状态
     *
     * @param request
     * @return
     */
    @PostMapping("/batchUpdateStatus")
    public BaseResponse batchUpdateStatus(@RequestBody ChallengePlanMonthlyRequest request,@RequestHeader String token) {
        log.info("batchUpdateStatus");
        return challengePlanMonthlyService.batchUpdateStatus(request,token);
    }
}
