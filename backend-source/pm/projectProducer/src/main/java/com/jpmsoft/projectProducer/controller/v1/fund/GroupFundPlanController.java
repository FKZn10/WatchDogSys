package com.jpmsoft.projectProducer.controller.v1.fund;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.projectProducer.pojo.dto.fund.GroupFundPlanDto;
import com.jpmsoft.projectProducer.pojo.request.ReturnInitiatorDto;
import com.jpmsoft.projectProducer.pojo.request.fund.GroupFundPlanRequest;
import com.jpmsoft.projectProducer.service.fund.IGroupFundPlanService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/***
 *
 * 集团资金计划
 */
@Slf4j
@RestController
@RequestMapping("/v1/groupFundPlan")
public class GroupFundPlanController {
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private IGroupFundPlanService groupFundPlanService;

    @PostMapping("/createOrUpdateGroupFundPlan")
    public BaseResponse createOrUpdateGroupFundPlan(@RequestBody GroupFundPlanDto dto) {
        return groupFundPlanService.createOrUpdateGroupFundPlan(dto);
    }

    @PostMapping("/getGroupFundPlanList")
    public BaseResponse getGroupFundPlanList(@RequestBody GroupFundPlanRequest request, @RequestHeader String token) {
        return groupFundPlanService.getGroupFundPlanList(request, redisUtils.getUid(token));
    }

    @PostMapping("/getGroupFundPlanById")
    public BaseResponse getGroupFundPlanById(@RequestBody GroupFundPlanRequest request) {
        return groupFundPlanService.getGroupFundPlanById(request.getId());
    }

    /***
     * 获取资金信息
     */
    @PostMapping("/getFundInfo")
    public BaseResponse getFundInfo(@RequestBody GroupFundPlanRequest request) {
        log.info("getFundInfo request:{}", JacksonUtils.toJSONString(request));
        return groupFundPlanService.getFundInfo(request);
    }

    @PostMapping("/getContractFundInfo")
    public BaseResponse getContractFundInfo(@RequestBody GroupFundPlanRequest request) {
        log.info("getContractFundInfo request:{}", JacksonUtils.toJSONString(request));
        return groupFundPlanService.getContractFundInfo(request);
    }

    @PostMapping("/getFundPlanInfo")
    public BaseResponse getFundPlanInfo(@RequestBody GroupFundPlanRequest request) {
        log.info("getFundPlanInfo request:{}", JacksonUtils.toJSONString(request));
        return groupFundPlanService.getFundPlanInfo(request);
    }

    /**
     * 获取项目的信息
     *
     * @param pId
     * @return
     */
    @GetMapping("/getProjectInfo")
    public BaseResponse getProjectInfo(@RequestParam String pId) {
        log.info("getProjectInfo pId:{}", pId);
        return groupFundPlanService.getProjectInfo(pId);
    }

    /**
     * 获取集团资金计划详情
     *
     * @param request
     * @return
     */
    @PostMapping("/getGroupFundPlanDetail")
    public BaseResponse getGroupFundPlanDetail(@RequestBody GroupFundPlanRequest request) {
        log.info("getGroupFundPlanDetail");
        return groupFundPlanService.getGroupFundPlanDetail(request);
    }

    /**
     * 根据ID删除集团资金申请
     *
     * @param request
     * @return
     */
    @PostMapping("/deleteGroupFundPlanById")
    public BaseResponse deleteGroupFundPlanById(@RequestBody GroupFundPlanRequest request) {
        log.info("deleteGroupFundPlanById");
        return groupFundPlanService.deleteGroupFundPlanById(request);
    }

    /**
     * 变更流程结束后刷新历史数据
     *
     * @param dto
     * @return
     */
    @PostMapping("/refreshHistoryData")
    public BaseResponse refreshHistoryData(@RequestBody GroupFundPlanDto dto) {
        log.info("refreshHistoryData");
        return groupFundPlanService.refreshHistoryData(dto);
    }

    /**
     * 流程退回到发起人
     *
     * @param request
     * @param token
     * @return
     */
    @PostMapping("/returnInitiatorToTask")
    public BaseResponse returnInitiatorToTask(@RequestBody ReturnInitiatorDto request, @RequestHeader String token) {
        log.info("returnInitiatorToTask");
        return groupFundPlanService.returnInitiatorToTask(request, token);
    }

    /**
     * 判断是否能够提交
     *
     * @param request
     * @return
     */
    @PostMapping("/existProject")
    public BaseResponse existProject(@RequestBody GroupFundPlanRequest request) {
        log.info("existProject");
        return groupFundPlanService.existProject(request);
    }
}
