package com.jpmsoft.projectProducer.controller.v1.challengePlan;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.challengePlan.ChallengePlanAnnualRequest;
import com.jpmsoft.projectProducer.service.challengePlan.IChallengePlanAnnualService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author tianhelin
 * @date 2025/5/30
 */
@Slf4j
@RestController
@RequestMapping("/v1/challengePlanAnnual")
public class ChallengePlanAnnualController {
    @Resource
    private IChallengePlanAnnualService challengePlanAnnualService;

    /**
     * 创建或更新年度计划
     *
     * @param request
     * @return
     */
    @PostMapping("/createOrUpdateChallengePlanAnnual")
    public BaseResponse createOrUpdateChallengePlanAnnual(@RequestBody ChallengePlanAnnualRequest request) {
        log.info("createOrUpdateChallengePlanAnnual");
        return challengePlanAnnualService.createOrUpdateChallengePlanAnnual(request);
    }

    /**
     * 删除年度计划
     *
     * @param request
     * @return
     */
    @PostMapping("/deleteChallengePlanAnnualById")
    public BaseResponse deleteChallengePlanAnnualById(@RequestBody ChallengePlanAnnualRequest request) {
        log.info("deleteChallengePlanAnnualById");
        return challengePlanAnnualService.deleteChallengePlanAnnualById(request);
    }

    /**
     * 获取年度计划列表
     *
     * @param request
     * @return
     */
    @PostMapping("/getChallengePlanAnnualList")
    public BaseResponse getChallengePlanAnnualList(@RequestBody ChallengePlanAnnualRequest request) {
        log.info("getChallengePlanAnnualList");
        return challengePlanAnnualService.getChallengePlanAnnualList(request);
    }

    /**
     * 获取年度计划详情
     *
     * @param id
     * @return
     */
    @GetMapping("/getChallengePlanAnnualById")
    public BaseResponse getChallengePlanAnnualById(@RequestParam("id") String id) {
        log.info("getChallengePlanAnnualById");
        return challengePlanAnnualService.getChallengePlanAnnualById(id);
    }
}
