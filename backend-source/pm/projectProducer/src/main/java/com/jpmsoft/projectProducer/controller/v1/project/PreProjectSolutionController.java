package com.jpmsoft.projectProducer.controller.v1.project;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Project.PreProjectSolutionModel;
import com.jpmsoft.projectProducer.service.project.IPreProjectSolutionService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/v1/projectSolution")
public class PreProjectSolutionController {
    @Autowired
    private IPreProjectSolutionService preProjectSolutionSerice;
    @Autowired
    private RedisUtils redisUtils;
    /**
     * 创建修改
     *
     * @param request
     * @return
     */
    @PostMapping("/createOrUpdatePreProjectSolution")
    public BaseResponse createOrUpdatePreProjectSolution(@RequestBody PreProjectSolutionModel request) {
        log.info("createOrUpdatePreProjectSolution");
        return preProjectSolutionSerice.createOrUpdatePreProjectSolution(request);
    }
    /**
     * 变更
     */
    @PostMapping("/modifyPreProjectSolution")
    public BaseResponse modifyPreProjectSolution(@RequestBody PreProjectSolutionModel request) {
        log.info("modifyPreProjectSolution");
        return preProjectSolutionSerice.modifyPreProjectSolution(request);
    }

    @PostMapping("/deletePreProjectSolution")
    public BaseResponse deletePreProjectSolution(@RequestBody PreProjectSolutionModel request) {
        log.info("deletePreProjectSolution");
        return preProjectSolutionSerice.deletePreProjectSolution(request);
    }
    @PostMapping("/approvalPreProjectSolution")
    public BaseResponse approvalPreProjectSolution(@RequestBody PreProjectSolutionModel request) {
        log.info("approvalPreProjectSolution");
        return preProjectSolutionSerice.approvalPreProjectSolution(request);
    }
    @PostMapping("/getPreProjectSolutionList")
    public BaseResponse getPreProjectSolutionList(@RequestBody PreProjectSolutionModel request, @RequestHeader String token) {
        log.info("getPreProjectSolutionList");
        return preProjectSolutionSerice.getPreProjectSolutionList(request,redisUtils.getUid(token));
    }
    @PostMapping("/getPreProjectSolutionListModifyInfo")
    public BaseResponse getPreProjectSolutionListModifyInfo(@RequestBody PreProjectSolutionModel request, @RequestHeader String token) {
        log.info("getPreProjectSolutionListModifyInfo");
        return preProjectSolutionSerice.getPreProjectSolutionListModifyInfo(request,redisUtils.getUid(token));
    }
    @GetMapping("/getPreProjectSolutionById")
    public BaseResponse getPreProjectSolutionById(@RequestParam("id") String id) {
        log.info("getPreProjectSolutionById");
        return preProjectSolutionSerice.getPreProjectSolutionById(id);
    }
}
