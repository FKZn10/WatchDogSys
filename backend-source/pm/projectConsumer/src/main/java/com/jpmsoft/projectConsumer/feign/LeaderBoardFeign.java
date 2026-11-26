package com.jpmsoft.projectConsumer.feign;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.loadBoard.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Component
@FeignClient(name = "project-producer", contextId = "leaderBoard")
public interface LeaderBoardFeign {
    @PostMapping("/v1/yw/leaderBoard/getPreProjectInfo")
    BaseResponse getPreProjectInfo(@RequestBody ProjectLoadRequest request);
    @PostMapping("/v1/yw/leaderBoard/getPreProjectInfoList")
    BaseResponse getPreProjectInfoList(@RequestBody ProjectLoadRequest request);

    @PostMapping("/v1/yw/leaderBoard/getContractInfo")
    BaseResponse getContractInfo(@RequestBody ContractLoadRequest request);
    @PostMapping("/v1/yw/leaderBoard/getContractInfoList")
    BaseResponse getContractInfoList(@RequestBody ContractLoadRequest request);
    @PostMapping("/v1/yw/leaderBoard/getSiteManagementInfo")
    BaseResponse getSiteManagementInfo(@RequestBody SiteManagementRequest request);
    @PostMapping("/v1/yw/leaderBoard/getSiteManagementInfoCompare")
    BaseResponse getSiteManagementInfoCompare(@RequestBody SiteManagementRequest request);
    @PostMapping("/v1/yw/leaderBoard/getSiteManagementInfoList")
    BaseResponse getSiteManagementInfoList(@RequestBody SiteManagementRequest request);
    @PostMapping("/v1/yw/leaderBoard/getPayInfo")
    BaseResponse getPayInfo(@RequestBody PayLoadRequest request);
    @PostMapping("/v1/yw/leaderBoard/getPayInfoList")
    BaseResponse getPayInfoList(@RequestBody PayLoadRequest request);
    @PostMapping("/v1/yw/leaderBoard/getPlanInfo")
    BaseResponse getPlanInfo(@RequestBody PlanLoadRequest request);
    @PostMapping("/v1/yw/leaderBoard/getPlanInfoList")
    BaseResponse getPlanInfoList(@RequestBody PlanLoadRequest request);
    @PostMapping("/v1/zdyxy/leaderBoard/getAnnualMaintenanceBudgetInfo")
    BaseResponse getAnnualMaintenanceBudgetInfo(@RequestBody ProjectLoadRequest request);
    @PostMapping("/v1/zdyxy/leaderBoard/getAnnualMaintenanceBudgetInfoList")
    BaseResponse getAnnualMaintenanceBudgetInfoList(@RequestBody ProjectLoadRequest request);
}