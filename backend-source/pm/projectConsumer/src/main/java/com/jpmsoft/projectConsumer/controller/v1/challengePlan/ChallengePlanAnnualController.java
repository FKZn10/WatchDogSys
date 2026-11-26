package com.jpmsoft.projectConsumer.controller.v1.challengePlan;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.challengePlan.ChallengePlanAnnualRequest;
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
@RequestMapping("/v1/challengePlanAnnual")
public class ChallengePlanAnnualController {
    @Resource
    private BaseFeign baseFeign;

    /**
     * 创建或更新年度计划
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "创建或更新年度计划")
    @PostMapping("/createOrUpdateChallengePlanAnnual")
    public BaseResponse createOrUpdateChallengePlanAnnual(@RequestBody ChallengePlanAnnualRequest request) {
        log.info("createOrUpdateChallengePlanAnnual");
        return baseFeign.createOrUpdateChallengePlanAnnual(request);
    }

    /**
     * 删除年度计划
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "删除年度计划")
    @PostMapping("/deleteChallengePlanAnnualById")
    public BaseResponse deleteChallengePlanAnnualById(@RequestBody ChallengePlanAnnualRequest request) {
        log.info("deleteChallengePlanAnnualById");
        return baseFeign.deleteChallengePlanAnnualById(request);
    }

    /**
     * 获取年度计划列表
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "获取年度计划列表")
    @PostMapping("/getChallengePlanAnnualList")
    public BaseResponse getChallengePlanAnnualList(@RequestBody ChallengePlanAnnualRequest request) {
        log.info("getChallengePlanAnnualList");
        return baseFeign.getChallengePlanAnnualList(request);
    }

    /**
     * 获取年度计划详情
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "获取年度计划详情")
    @GetMapping("/getChallengePlanAnnualById")
    public BaseResponse getChallengePlanAnnualById(@RequestParam("id") String id) {
        log.info("getChallengePlanAnnualById");
        return baseFeign.getChallengePlanAnnualById(id);
    }
}
