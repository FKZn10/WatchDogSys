package com.jpmsoft.projectProducer.controller.v1.project;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Project.PreProjectSolutionInfoModel;
import com.jpmsoft.projectProducer.service.project.IPreProjectSolutionInfoService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/v1/projectSolutionInfo")
public class PreProjectSolutionInfoController {
    @Autowired
    private IPreProjectSolutionInfoService preProjectSolutionInfoService;
    @Autowired
    private RedisUtils redisUtils;
    /**
     * 创建修改
     *
     * @param request
     * @return
     */
    @PostMapping("/createOrUpdatePreProjectSolutionInfo")
    public BaseResponse createOrUpdatePreProjectSolutionInfo(@RequestBody PreProjectSolutionInfoModel request) {
        log.info("createOrUpdatePreProjectSolutionInfo");
        return preProjectSolutionInfoService.createOrUpdatePreProjectSolutionInfo(request);
    }
    @PostMapping("/createOrUpdatePreProjectSolutionInfoWithOutFlow")
    public BaseResponse createOrUpdatePreProjectSolutionInfoWithOutFlow(@RequestBody PreProjectSolutionInfoModel request) {
        log.info("createOrUpdatePreProjectSolutionInfoWithOutFlow");
        return preProjectSolutionInfoService.createOrUpdatePreProjectSolutionInfoWithOutFlow(request);
    }
    @PostMapping("/approvalPreProjectSolutionInfo")
    public BaseResponse approvalPreProjectSolution(@RequestBody PreProjectSolutionInfoModel request) {
        log.info("approvalPreProjectSolutionInfo");
        return preProjectSolutionInfoService.approvalPreProjectSolutionInfo(request);
    }
    @PostMapping("/getPreProjectSolutionInfoList")
    public BaseResponse getPreProjectSolutionInfoList(@RequestBody PreProjectSolutionInfoModel request, @RequestHeader String token) {
        log.info("getPreProjectSolutionInfoList");
        return preProjectSolutionInfoService.getPreProjectSolutionInfoList(request,redisUtils.getUid(token));
    }
    @GetMapping("/getPreProjectSolutionInfoById")
    public BaseResponse getPreProjectSolutionInfoById(@RequestParam("id") String id) {
        log.info("getPreProjectSolutionInfoById");
        return preProjectSolutionInfoService.getPreProjectSolutionInfoById(id);
    }
}
