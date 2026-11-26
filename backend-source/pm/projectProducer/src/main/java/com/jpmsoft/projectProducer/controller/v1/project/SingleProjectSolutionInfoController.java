package com.jpmsoft.projectProducer.controller.v1.project;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.request.project.SingleProjectSolutionInfoRequest;
import com.jpmsoft.projectProducer.service.project.SingleProjectSolutionInfoService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1/singleProjectSolutionInfo")
public class SingleProjectSolutionInfoController {
    @Autowired
    private SingleProjectSolutionInfoService projectSolutionInfoService;
    @Autowired
    private RedisUtils redisUtils;

    @PostMapping("/getSingleProjectSolutionInfo")
    public BaseResponse getSingleProjectSolutionInfo(@RequestBody SingleProjectSolutionInfoRequest request) {
        log.info("getSingleProjectSolutionInfo");
        return projectSolutionInfoService.getSingleProjectSolutionInfo(request);
    }
}
