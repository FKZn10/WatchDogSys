package com.jpmsoft.projectConsumer.feign;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Project.PreProjectSolutionInfoModel;
import com.jpmsoft.common.model.requestAndResponse.Project.PreProjectSolutionModel;
import com.jpmsoft.projectConsumer.request.project.SingleProjectSolutionInfoRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "project-producer", contextId = "projectSolution")
public interface PreSolutionFeign {
    @PostMapping("/v1/projectSolution/createOrUpdatePreProjectSolution")
    BaseResponse createOrUpdatePreProjectSolution(@RequestBody PreProjectSolutionModel request);
    @PostMapping("/v1/projectSolution/deletePreProjectSolution")
    BaseResponse deletePreProjectSolution(@RequestBody PreProjectSolutionModel request);
    @PostMapping("/v1/projectSolution/getPreProjectSolutionList")
    BaseResponse getPreProjectSolutionList(@RequestBody PreProjectSolutionModel request, @RequestHeader String token);
    @GetMapping("/v1/projectSolution/getPreProjectSolutionById")
    BaseResponse getPreProjectSolutionById(@RequestParam("id") String id);
    @PostMapping("/v1/projectSolution/approvalPreProjectSolution")
    BaseResponse approvalPreProjectSolution(@RequestBody PreProjectSolutionModel request);
    @PostMapping("/v1/projectSolutionInfo/createOrUpdatePreProjectSolutionInfo")
    BaseResponse createOrUpdatePreProjectSolutionInfo(@RequestBody PreProjectSolutionInfoModel request);
    @PostMapping("/v1/projectSolutionInfo/approvalPreProjectSolutionInfo")
    BaseResponse approvalPreProjectSolutionInfo(@RequestBody PreProjectSolutionInfoModel request);
    @PostMapping("/v1/projectSolutionInfo/getPreProjectSolutionInfoList")
    BaseResponse getPreProjectSolutionInfoList(@RequestBody PreProjectSolutionInfoModel request,@RequestHeader String token);
    @GetMapping("/v1/projectSolutionInfo/getPreProjectSolutionInfoById")
    BaseResponse getPreProjectSolutionInfoById(@RequestParam("id")String id);
    @PostMapping("/v1/singleProjectSolutionInfo/getSingleProjectSolutionInfo")
    BaseResponse getSingleProjectSolutionInfo(@RequestBody SingleProjectSolutionInfoRequest request);
    @PostMapping("/v1/projectSolution/modifyPreProjectSolution")
    BaseResponse modifyPreProjectSolution(@RequestBody PreProjectSolutionModel request);
    @PostMapping("/v1/projectSolution/getPreProjectSolutionListModifyInfo")
    BaseResponse getPreProjectSolutionListModifyInfo(@RequestBody PreProjectSolutionModel request,@RequestHeader String token);
    @PostMapping("/v1/projectSolutionInfo/createOrUpdatePreProjectSolutionInfoWithOutFlow")
    BaseResponse createOrUpdatePreProjectSolutionInfoWithOutFlow(@RequestBody PreProjectSolutionInfoModel request);
}
