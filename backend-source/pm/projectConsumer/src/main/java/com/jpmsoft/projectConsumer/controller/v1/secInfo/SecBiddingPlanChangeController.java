package com.jpmsoft.projectConsumer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.SecInfo.SecBiddingPlanChangeRequest;
import com.jpmsoft.projectConsumer.feign.SecFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 招标计划变更
 * Created By:李文涛 aaron
 * Created Time:2022/6/16 20:28
 */
@Slf4j
@Api(tags = "招标计划变更", value = "招标计划变更")
@RestController
@RequestMapping("/v1/biddingPlanChange")
public class SecBiddingPlanChangeController {
    @Autowired
    private SecFeign secFeign;


    @ApiOperation(value = "创建或修改招标计划变更")
    @PostMapping("/createOrUpdateSecBiddingPlanChange")
    public BaseResponse createOrUpdateSecBiddingPlanChange(@RequestBody SecBiddingPlanChangeRequest request, @RequestHeader String token) {
        log.info("createOrUpdateSecBiddingPlanChange");
        return secFeign.createOrUpdateSecBiddingPlanChange(request,token);
    }

    @ApiOperation(value = "分页查询招标计划变更")
    @PostMapping("/getSecBiddingPlanChangeList")
    public BaseResponse getSecBiddingPlanChangeList(@RequestBody SecBiddingPlanChangeRequest request, @RequestHeader String token) {
        log.info("getSecBiddingPlanChangeList");
        return secFeign.getSecBiddingPlanChangeList(request, token);
    }

    @ApiOperation(value = "审批通过更新标段信息(需要传递标段流程创建相关字段(除流程文档relationId))")
    @PostMapping("/approval2UpdateSecInfo")
    public BaseResponse approval2UpdateSecInfo(@RequestBody SecBiddingPlanChangeRequest request, @RequestHeader String token) {
        return secFeign.approval2UpdateSecInfo(request, token);
    }

    @ApiOperation(value = "ID查询招标计划变更")
    @GetMapping("/getSecBiddingPlanChangeById")
    public BaseResponse getSecBiddingPlanChangeById(@RequestParam String bpcId) {
        log.info("getSecBiddingPlanChangeById");
        return secFeign.getSecBiddingPlanChangeById(bpcId);
    }

    @ApiOperation("根据bpId查询变更列表")
    @GetMapping("/getSecInfoChangeListByBpId")
    public BaseResponse getSecInfoChangeListByBpId(@RequestParam("bpId") String bpId) {
        log.info("getSecInfoChangeListByBpId");
        return secFeign.getSecInfoChangeListByBpId(bpId);
    }

    /**
     * 获取指定标段是否有流程中的变更
     *
     * @param secId
     * @return
     */
    @ApiOperation("获取指定标段是否有流程中的变更")
    @GetMapping("/haveChangeSecInfoBySecId")
    public BaseResponse haveChangeSecInfoBySecId(@RequestParam("secId") String secId) {
        log.info("haveChangeSecInfoBySecId");
        return secFeign.haveChangeSecInfoBySecId(secId);
    }

    /**
     * 更新指定计划的项目分管领导
     *
     * @param bpcId
     * @param responsibleLeader
     * @return
     */
    @ApiOperation("更新指定计划变更的项目分管领导")
    @GetMapping("/updateSecBiddingPlanChangeResponsibleLeader")
    public BaseResponse updateSecBiddingPlanChangeResponsibleLeader(@RequestParam("bpcId") String bpcId, @RequestParam("responsibleLeader") String responsibleLeader) {
        log.info("updateSecBiddingPlanChangeResponsibleLeader");
        return secFeign.updateSecBiddingPlanChangeResponsibleLeader(bpcId, responsibleLeader);
    }
}
