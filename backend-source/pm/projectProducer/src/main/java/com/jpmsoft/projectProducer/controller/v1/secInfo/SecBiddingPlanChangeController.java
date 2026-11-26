package com.jpmsoft.projectProducer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.SecInfo.SecBiddingPlanChangeRequest;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.secInfo.ISecBiddingPlanChangeService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 招标计划变更
 * Created By:李文涛 aaron
 * Created Time:2022/6/16 20:28
 */
@Slf4j
@RestController
@RequestMapping("/v1/biddingPlanChange")
public class SecBiddingPlanChangeController {
    @Resource
    private ISecBiddingPlanChangeService secBiddingPlanChangeService;
    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createOrUpdateSecBiddingPlanChange")
    public BaseResponse createOrUpdateSecBiddingPlanChange(@RequestBody SecBiddingPlanChangeRequest request, @RequestHeader String token) {
        log.info("createOrUpdateSecBiddingPlanChange");
        return secBiddingPlanChangeService.createOrUpdateSecBiddingPlanChange(request, redisUtils.getUserInfoByToken(token));
    }

    @TenantId
    @PostMapping("/getSecBiddingPlanChangeList")
    public BaseResponse getSecBiddingPlanChangeList(@RequestBody SecBiddingPlanChangeRequest request, @RequestHeader String token) {
        log.info("getSecBiddingPlanChangeList");
        return secBiddingPlanChangeService.getSecBiddingPlanChangeList(request, redisUtils.getUid(token));
    }


    @GetMapping("/getSecBiddingPlanChangeById")
    public BaseResponse getSecBiddingPlanChangeById(@RequestParam String bpcId) {
        log.info("getSecBiddingPlanChangeById");
        return secBiddingPlanChangeService.getSecBiddingPlanChangeById(bpcId);
    }


    @PostMapping("/approval2UpdateSecInfo")
    public BaseResponse approval2UpdateSecInfo(@RequestBody SecBiddingPlanChangeRequest request, @RequestHeader String token) {
        return secBiddingPlanChangeService.approval2UpdateSecInfo(request, redisUtils.getUid(token));
    }

    @GetMapping("getSecInfoChangeListByBpId")
    public BaseResponse getSecInfoChangeListByBpId(@RequestParam("bpId") String bpId) {
        log.info("getSecInfoChangeListByBpId");
        return secBiddingPlanChangeService.getSecInfoChangeListByBpId(bpId);
    }

    /**
     * 获取指定标段是否有流程中的变更
     *
     * @param secId
     * @return
     */
    @GetMapping("/haveChangeSecInfoBySecId")
    public BaseResponse haveChangeSecInfoBySecId(@RequestParam("secId") String secId) {
        log.info("haveChangeSecInfoBySecId");
        return secBiddingPlanChangeService.haveChangeSecInfoBySecId(secId);
    }

    /**
     * 更新指定计划的项目分管领导
     *
     * @param bpcId
     * @param responsibleLeader
     * @return
     */
    @GetMapping("/updateSecBiddingPlanChangeResponsibleLeader")
    public BaseResponse updateSecBiddingPlanChangeResponsibleLeader(@RequestParam("bpcId") String bpcId, @RequestParam("responsibleLeader") String responsibleLeader) {
        log.info("updateSecBiddingPlanChangeResponsibleLeader");
        return secBiddingPlanChangeService.updateSecBiddingPlanChangeResponsibleLeader(bpcId, responsibleLeader);
    }
}
