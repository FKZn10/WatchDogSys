package com.jpmsoft.projectConsumer.controller.v1.fund;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.projectConsumer.feign.FundFeign;
import com.jpmsoft.projectConsumer.pojo.dto.fund.GroupFundPlanSummaryDetailDto;
import com.jpmsoft.projectConsumer.pojo.dto.fund.GroupFundPlanSummaryDto;
import com.jpmsoft.projectConsumer.request.fund.GroupFundPlanSummaryRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/v1/groupFundPlanSummary")
public class GroupFundPlanSummaryController {
    @Autowired
    private FundFeign fundFeign;
   @PostMapping("/createOrUpdateGroupFundPlanSummary")
    public BaseResponse createOrUpdateGroupFundPlanSummary(@RequestBody GroupFundPlanSummaryDto dto) {
        return fundFeign.createOrUpdateGroupFundPlanSummary(dto);
    }
    @PostMapping("/getGroupFundPlanSummaryList")
    public BaseResponse getGroupFundPlanSummaryList(@RequestBody GroupFundPlanSummaryRequest request, @RequestHeader String token) {
        return fundFeign.getGroupFundPlanSummaryList(request,token);
    }
    @PostMapping("/getGroupFundPlanSummaryById")
    public BaseResponse getGroupFundPlanSummaryById(@RequestBody GroupFundPlanSummaryRequest request) {
        return fundFeign.getGroupFundPlanSummaryById(request);
    }

    @GetMapping("/getCompanyGroupFundPlanSummaryById")
    public BaseResponse getCompanyGroupFundPlanSummaryById(@RequestParam String id) {
        log.info("getCompanyGroupFundPlanSummaryById");
        return fundFeign.getCompanyGroupFundPlanSummaryById(id);
    }

    @GetMapping("/getBlocFundPlanSummaryById")
    public BaseResponse getBlocFundPlanSummaryById(@RequestParam String id) {
        log.info("getBlocFundPlanSummaryById");
        return fundFeign.getBlocFundPlanSummaryById(id);
    }

    @PostMapping("/approvalData")
    public BaseResponse approvalData(@RequestBody GroupFundPlanSummaryRequest request) {
        log.info("approvalData request:{}", JacksonUtils.toJSONString(request));
        return fundFeign.approvalData(request);
    }

    @PostMapping("/getGroupFundPlanSummaryInfo")
    public BaseResponse getGroupFundPlanSummaryInfo(@RequestBody GroupFundPlanSummaryRequest request, @RequestHeader String token) {
        return fundFeign.getGroupFundPlanSummaryInfo(request,token);
    }

    @PostMapping("/rollbackData")
    public BaseResponse rollbackData(@RequestBody GroupFundPlanSummaryRequest request) {
        return fundFeign.rollbackData(request);
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
        return fundFeign.getCompanyProjectFundPlan(request, token);
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
        return fundFeign.getGroupProjectFundPlan(request);
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
        return fundFeign.getGroupFundPlanSummaryDetailByPid(request);
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
        return fundFeign.refreshHistoryData2(request);
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
        return fundFeign.deleteGroupFundPlanSummaryById(request);
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
        return fundFeign.existProject2(request);
    }
}
