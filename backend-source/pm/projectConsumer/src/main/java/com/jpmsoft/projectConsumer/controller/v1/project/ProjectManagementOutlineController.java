package com.jpmsoft.projectConsumer.controller.v1.project;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.feign.BaseFeign;
import com.jpmsoft.projectConsumer.pojo.dto.project.ProjectManagementOutlineDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/v1/projectManagementOutline")
public class ProjectManagementOutlineController {
    @Autowired
    private BaseFeign baseFeign;

    @PostMapping("/createOrUpdateProjectManagementOutline")
    public BaseResponse createOrUpdateProjectManagementOutline(@RequestBody ProjectManagementOutlineDto request) {
        log.info("createOrUpdateProjectManagementOutline");
        return baseFeign.createOrUpdateProjectManagementOutline(request);
    }

    @PostMapping("/getProjectManagementOutlineList")
    public BaseResponse getProjectManagementOutlineList(@RequestBody ProjectManagementOutlineDto request) {
        log.info("getProjectManagementOutlineList");
        return baseFeign.getProjectManagementOutlineList(request);
    }

    @PostMapping("/getProjectManagementOutlineClassInfo")
    public BaseResponse getProjectManagementOutlineClassInfo(@RequestBody ProjectManagementOutlineDto request) {
        log.info("getProjectManagementOutlineClassInfo");
        return baseFeign.getProjectManagementOutlineClassInfo(request);
    }

    @GetMapping("/getProjectManagementOutlineById")
    public BaseResponse getProjectManagementOutlineById(@RequestParam String id) {
        log.info("getProjectManagementOutlineById");
        return baseFeign.getProjectManagementOutlineById(id);
    }


    @GetMapping("/deleteProjectManagementOutlineById")
    public BaseResponse deleteProjectManagementOutlineById(@RequestParam String id) {
        log.info("deleteProjectManagementOutlineById");
        return baseFeign.deleteProjectManagementOutlineById(id);
    }

}
