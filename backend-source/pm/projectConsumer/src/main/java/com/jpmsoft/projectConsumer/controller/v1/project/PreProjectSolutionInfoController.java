package com.jpmsoft.projectConsumer.controller.v1.project;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Project.PreProjectSolutionInfoModel;
import com.jpmsoft.projectConsumer.feign.PreSolutionFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/v1/projectSolutionInfo")
public class PreProjectSolutionInfoController {
    @Autowired
    private PreSolutionFeign preSolutionFeign;

    /**
     * 创建修改
     *
     * @param request
     * @return
     */
    @PostMapping("/createOrUpdatePreProjectSolutionInfo")
    public BaseResponse createOrUpdatePreProjectSolutionInfo(@RequestBody PreProjectSolutionInfoModel request) {
        log.info("createOrUpdatePreProjectSolutionInfo");
        return preSolutionFeign.createOrUpdatePreProjectSolutionInfo(request);
    }
    @PostMapping("/createOrUpdatePreProjectSolutionInfoWithOutFlow")
    public BaseResponse createOrUpdatePreProjectSolutionInfoWithOutFlow(@RequestBody PreProjectSolutionInfoModel request) {
        log.info("createOrUpdatePreProjectSolutionInfoWithOutFlow");
        return preSolutionFeign.createOrUpdatePreProjectSolutionInfoWithOutFlow(request);
    }
    @PostMapping("/approvalPreProjectSolutionInfo")
    public BaseResponse approvalPreProjectSolution(@RequestBody PreProjectSolutionInfoModel request) {
        log.info("approvalPreProjectSolutionInfo");
        return preSolutionFeign.approvalPreProjectSolutionInfo(request);
    }
    @PostMapping("/getPreProjectSolutionInfoList")
    public BaseResponse getPreProjectSolutionInfoList(@RequestBody PreProjectSolutionInfoModel request, @RequestHeader String token) {
        log.info("getPreProjectSolutionInfoList");
        return preSolutionFeign.getPreProjectSolutionInfoList(request,token);
    }
    @GetMapping("/getPreProjectSolutionInfoById")
    public BaseResponse getPreProjectSolutionInfoById(@RequestParam("id") String id) {
        log.info("getPreProjectSolutionInfoById");
        return preSolutionFeign.getPreProjectSolutionInfoById(id);
    }
}
