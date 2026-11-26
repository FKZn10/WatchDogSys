package com.jpmsoft.projectConsumer.controller.v1.challengePlan;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.challengePlan.ChallengePlanAnnualRequest;
import com.jpmsoft.common.model.request.challengePlan.ChallengePlanMonthlyPageRequest;
import com.jpmsoft.common.model.request.challengePlan.ChallengePlanMonthlyRequest;
import com.jpmsoft.projectConsumer.feign.BaseFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author tianhelin
 * @date 2025/6/3
 */
@Slf4j
@RestController
@Api(tags = "攻坚任务计划管理", value = "攻坚任务计划管理")
@RequestMapping("/v1/challengePlanMonthly")
public class ChallengePlanMonthlyController {
    @Resource
    private BaseFeign baseFeign;

    /**
     * 创建月度计划
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "创建月度计划")
    @PostMapping("/createChallengePlanMonthly")
    public BaseResponse createChallengePlanMonthly(@RequestBody ChallengePlanAnnualRequest request) {
        log.info("createChallengePlanMonthly");
        return baseFeign.createChallengePlanMonthly(request);
    }

    /**
     * 更新月度计划
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "更新月度计划")
    @PostMapping("/updateChallengePlanMonthly")
    public BaseResponse updateChallengePlanMonthly(@RequestBody ChallengePlanMonthlyRequest request) {
        log.info("updateChallengePlanMonthly");
        return baseFeign.updateChallengePlanMonthly(request);
    }

    /**
     * 获取月度计划列表
     *
     * @param request
     * @param token
     * @return
     */
    @ApiOperation(value = "获取月度计划列表")
    @PostMapping("/getChallengePlanMonthlyList")
    public BaseResponse getChallengePlanMonthlyList(@RequestBody ChallengePlanMonthlyPageRequest request, @RequestHeader String token) {
        log.info("getChallengePlanMonthlyList");
        return baseFeign.getChallengePlanMonthlyList(request, token);
    }

    /**
     * 获取月度计划信息
     *
     * @param request
     * @param token
     * @return
     */
    @ApiOperation(value = "获取月度计划信息")
    @PostMapping("/getChallengePlanMonthlyClassInfo")
    public BaseResponse getChallengePlanMonthlyClassInfo(@RequestBody ChallengePlanMonthlyPageRequest request, @RequestHeader String token) {
        log.info("getChallengePlanMonthlyClassInfo");
        return baseFeign.getChallengePlanMonthlyClassInfo(request, token);
    }

    /**
     * 根据id获取月度计划信息
     *
     * @param id
     * @param token
     * @return
     */
    @ApiOperation(value = "根据id获取月度计划信息")
    @GetMapping("/getChallengePlanMonthlyById")
    public BaseResponse getChallengePlanMonthlyById(@RequestParam("id") String id, @RequestHeader String token) {
        log.info("getChallengePlanMonthlyById");
        return baseFeign.getChallengePlanMonthlyById(id, token);
    }

    /**
     * 批量更新状态
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "批量更新状态")
    @PostMapping("/batchUpdateStatus")
    public BaseResponse batchUpdateStatus(@RequestBody ChallengePlanMonthlyRequest request) {
        log.info("batchUpdateStatus");
        return baseFeign.batchUpdateStatus(request);
    }
}
