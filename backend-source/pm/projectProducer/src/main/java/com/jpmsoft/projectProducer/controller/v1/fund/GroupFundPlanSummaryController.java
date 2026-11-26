package com.jpmsoft.projectProducer.controller.v1.fund;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.fund.GroupFundPlanSummaryDetailDto;
import com.jpmsoft.projectProducer.pojo.dto.fund.GroupFundPlanSummaryDto;
import com.jpmsoft.projectProducer.pojo.request.fund.GroupFundPlanSummaryRequest;
import com.jpmsoft.projectProducer.service.fund.IGroupFundPlanSummaryService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/v1/groupFundPlanSummary")
public class GroupFundPlanSummaryController {
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private IGroupFundPlanSummaryService groupFundPlanSummaryService;
    @PostMapping("/createOrUpdateGroupFundPlanSummary")
    public BaseResponse createOrUpdateGroupFundPlanSummary(@RequestBody GroupFundPlanSummaryDto dto) {
        return groupFundPlanSummaryService.createOrUpdateGroupFundPlanSummary(dto);
    }
    @PostMapping("/getGroupFundPlanSummaryList")
    public BaseResponse getGroupFundPlanSummaryList(@RequestBody GroupFundPlanSummaryRequest request, @RequestHeader String token) {
        return groupFundPlanSummaryService.getGroupFundPlanSummaryList(request,redisUtils.getUid(token));
    }
    @PostMapping("/getGroupFundPlanSummaryById")
    public BaseResponse getGroupFundPlanSummaryById(@RequestBody GroupFundPlanSummaryRequest request) {
        return groupFundPlanSummaryService.getGroupFundPlanSummaryById(request.getId());
    }

    @GetMapping("/getCompanyGroupFundPlanSummaryById")
    public BaseResponse getCompanyGroupFundPlanSummaryById(@RequestParam String id) {
        log.info("getCompanyGroupFundPlanSummaryById");
        return groupFundPlanSummaryService.getCompanyGroupFundPlanSummaryById(id);
    }

    @GetMapping("/getBlocFundPlanSummaryById")
    public BaseResponse getBlocFundPlanSummaryById(@RequestParam String id) {
        log.info("getBlocFundPlanSummaryById");
        return groupFundPlanSummaryService.getBlocFundPlanSummaryById(id);
    }

    @PostMapping("/approvalData")
    public BaseResponse approvalData(@RequestBody GroupFundPlanSummaryRequest request) {
        return groupFundPlanSummaryService.approvalData(request);
    }

    @PostMapping("/getGroupFundPlanSummaryInfo")
    public BaseResponse getGroupFundPlanSummaryInfo(@RequestBody GroupFundPlanSummaryRequest request, @RequestHeader String token) {
        return groupFundPlanSummaryService.getGroupFundPlanSummaryInfo(request,redisUtils.getUid(token));
    }

    @PostMapping("/rollbackData")
    public BaseResponse rollbackData(@RequestBody GroupFundPlanSummaryRequest request) {
        return groupFundPlanSummaryService.rollbackData(request);
    }

    /**
     * 获取成员企业下各个事业部/分支机构的项目资金计划信息
     *
     * @param request
     * @param token
     * @return
     */
    @PostMapping("/getCompanyProjectFundPlan")
    public BaseResponse getCompanyProjectFundPlan(@RequestBody GroupFundPlanSummaryRequest request, @RequestHeader String token) {
        log.info("getCompanyProjectFundPlan");
        return groupFundPlanSummaryService.getCompanyProjectFundPlan(request, redisUtils.getUserInfoByToken(token));
    }

    /**
     * 获取集团下各个成员企业的项目资金计划信息
     *
     * @param request
     * @return
     */
    @PostMapping("/getGroupProjectFundPlan")
    public BaseResponse getGroupProjectFundPlan(@RequestBody GroupFundPlanSummaryRequest request) {
        log.info("getGroupProjectFundPlan");
        return groupFundPlanSummaryService.getGroupProjectFundPlan(request);
    }

    /**
     * 根据pid获取集团资金计划汇总详情
     *
     * @param request
     * @return
     */
    @PostMapping("/getGroupFundPlanSummaryDetailByPid")
    public BaseResponse getGroupFundPlanSummaryDetailByPid(@RequestBody GroupFundPlanSummaryDetailDto request) {
        log.info("getGroupFundPlanSummaryDetailByPid");
        return groupFundPlanSummaryService.getGroupFundPlanSummaryDetailByPid(request);
    }

    /**
     * 刷新历史数据
     *
     * @param request
     * @return
     */
    @PostMapping("/refreshHistoryData")
    public BaseResponse refreshHistoryData(@RequestBody GroupFundPlanSummaryDto request) {
        log.info("refreshHistoryData");
        return groupFundPlanSummaryService.refreshHistoryData(request);
    }

    /**
     * 根据id删除集团资金计划汇总
     *
     * @param request
     * @return
     */
    @PostMapping("/deleteGroupFundPlanSummaryById")
    public BaseResponse deleteGroupFundPlanSummaryById(@RequestBody GroupFundPlanSummaryRequest request) {
        log.info("deleteGroupFundPlanSummaryById");
        return groupFundPlanSummaryService.deleteGroupFundPlanSummaryById(request.getId());
    }

    /**
     * 判断是否能够提交
     *
     * @param request
     * @return
     */
    @PostMapping("/existProject")
    public BaseResponse existProject(@RequestBody GroupFundPlanSummaryRequest request) {
        log.info("existProject");
        return groupFundPlanSummaryService.existProject(request);
    }
}
