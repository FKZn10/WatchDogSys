package com.jpmsoft.projectConsumer.controller.v1.project;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.feign.PreSolutionFeign;
import com.jpmsoft.projectConsumer.request.project.SingleProjectSolutionInfoRequest;
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
    private PreSolutionFeign preSolutionFeign;


    @PostMapping("/getSingleProjectSolutionInfo")
    public BaseResponse getSingleProjectSolutionInfo(@RequestBody SingleProjectSolutionInfoRequest request) {
        log.info("getSingleProjectSolutionInfo");
        return preSolutionFeign.getSingleProjectSolutionInfo(request);
    }
}
