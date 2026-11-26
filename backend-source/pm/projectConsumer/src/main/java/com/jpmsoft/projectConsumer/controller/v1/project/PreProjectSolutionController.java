package com.jpmsoft.projectConsumer.controller.v1.project;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Project.PreProjectSolutionModel;
import com.jpmsoft.projectConsumer.feign.PreSolutionFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/v1/projectSolution")
public class PreProjectSolutionController {
    @Autowired
    private PreSolutionFeign preSolutionFeign;

    /**
     * 创建修改
     *
     * @param request
     * @return
     */
    @PostMapping("/createOrUpdatePreProjectSolution")
    public BaseResponse createOrUpdatePreProjectSolution(@RequestBody PreProjectSolutionModel request) {
        log.info("createOrUpdatePreProjectSolution");
        return preSolutionFeign.createOrUpdatePreProjectSolution(request);
    }
    @PostMapping("/modifyPreProjectSolution")
    public BaseResponse modifyPreProjectSolution(@RequestBody PreProjectSolutionModel request) {
        log.info("modifyPreProjectSolution");
        return preSolutionFeign.modifyPreProjectSolution(request);
    }
    @PostMapping("/deletePreProjectSolution")
    public BaseResponse deletePreProjectSolution(@RequestBody PreProjectSolutionModel request) {
        log.info("deletePreProjectSolution");
        return preSolutionFeign.deletePreProjectSolution(request);
    }
    @PostMapping("/approvalPreProjectSolution")
    public BaseResponse approvalPreProjectSolution(@RequestBody PreProjectSolutionModel request) {
        log.info("approvalPreProjectSolution");
        return preSolutionFeign.approvalPreProjectSolution(request);
    }
    @PostMapping("/getPreProjectSolutionList")
    public BaseResponse getPreProjectSolutionList(@RequestBody PreProjectSolutionModel request, @RequestHeader String token) {
        log.info("getPreProjectSolutionList");
        return preSolutionFeign.getPreProjectSolutionList(request,token);
    }
    @PostMapping("/getPreProjectSolutionListModifyInfo")
    public BaseResponse getPreProjectSolutionListModifyInfo(@RequestBody PreProjectSolutionModel request, @RequestHeader String token) {
        log.info("getPreProjectSolutionListModifyInfo");
        return preSolutionFeign.getPreProjectSolutionListModifyInfo(request,token);
    }
    @GetMapping("/getPreProjectSolutionById")
    public BaseResponse getPreProjectSolutionById(@RequestParam("id") String id) {
        log.info("getPreProjectSolutionById");
        return preSolutionFeign.getPreProjectSolutionById(id);
    }
}
