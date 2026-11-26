package com.jpmsoft.projectConsumer.controller.v1.fund;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.projectConsumer.feign.FundFeign;
import com.jpmsoft.projectConsumer.pojo.dto.fund.GroupFundPlanDto;
import com.jpmsoft.projectConsumer.request.ReturnInitiatorDto;
import com.jpmsoft.projectConsumer.request.fund.GroupFundPlanRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/v1/groupFundPlan")
public class GroupFundPlanController {
    @Autowired
    private FundFeign fundFeign;
    @PostMapping("/createOrUpdateGroupFundPlan")
    public BaseResponse createOrUpdateGroupFundPlan(@RequestBody GroupFundPlanDto dto) {
        return fundFeign.createOrUpdateGroupFundPlan(dto);
    }
    @PostMapping("/getGroupFundPlanList")
    public BaseResponse getGroupFundPlanList(@RequestBody GroupFundPlanRequest request,@RequestHeader String token) {
        return fundFeign.getGroupFundPlanList(request,token);
    }
    @PostMapping("/getGroupFundPlanById")
    public BaseResponse getGroupFundPlanById(@RequestBody GroupFundPlanRequest request) {
        return fundFeign.getGroupFundPlanById(request);
    }
    @PostMapping("/getFundInfo")
    public BaseResponse getFundInfo(@RequestBody GroupFundPlanRequest request) {
        log.info("getFundInfo request:{}",JacksonUtils.toJSONString(request));
        return fundFeign.getFundInfo(request);
    }
    @PostMapping("/getContractFundInfo")
    public BaseResponse getContractFundInfo(@RequestBody GroupFundPlanRequest request) {
        log.info("getContractFundInfo request:{}",JacksonUtils.toJSONString(request));
        return fundFeign.getContractFundInfo(request);
    }
    @PostMapping("/getFundPlanInfo")
    public BaseResponse getFundPlanInfo(@RequestBody GroupFundPlanRequest request) {
        log.info("getFundPlanInfo request:{}",JacksonUtils.toJSONString(request));
        return fundFeign.getFundPlanInfo(request);
    }

    @GetMapping("/getProjectInfo")
    public BaseResponse getProjectInfo(@RequestParam String pId) {
        log.info("getProjectInfo pId:{}", pId);
        return fundFeign.getProjectInfo(pId);
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
        return fundFeign.getGroupFundPlanDetail(request);
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
        return fundFeign.deleteGroupFundPlanById(request);
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
        return fundFeign.refreshHistoryData(dto);
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
        return fundFeign.returnInitiatorToTask(request, token);
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
        return fundFeign.existProject(request);
    }
}

