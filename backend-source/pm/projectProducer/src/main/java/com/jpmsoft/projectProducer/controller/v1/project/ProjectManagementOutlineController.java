package com.jpmsoft.projectProducer.controller.v1.project;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.project.ProjectManagementOutlineDto;
import com.jpmsoft.projectProducer.service.project.IProjectManagementOutlineService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/v1/projectManagementOutline")
public class ProjectManagementOutlineController {
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private IProjectManagementOutlineService projectManagementOutlineService;

    @PostMapping("/createOrUpdateProjectManagementOutline")
    public BaseResponse createOrUpdateProjectManagementOutline(@RequestBody ProjectManagementOutlineDto request) {
        log.info("createOrUpdateProjectManagementOutline");
        return projectManagementOutlineService.createOrUpdateProjectManagementOutline(request);
    }
    @PostMapping("/getProjectManagementOutlineList")
    public BaseResponse getProjectManagementOutlineList(@RequestBody ProjectManagementOutlineDto request, @RequestHeader String token) {
        log.info("getProjectManagementOutlineList");
        return projectManagementOutlineService.getProjectManagementOutlineList(request,redisUtils.getUid(token));
    }

    @PostMapping("/getProjectManagementOutlineClassInfo")
    public BaseResponse getProjectManagementOutlineClassInfo(@RequestBody ProjectManagementOutlineDto request, @RequestHeader String token) {
        log.info("getProjectManagementOutlineClassInfo");
        return projectManagementOutlineService.getProjectManagementOutlineClassInfo(request,redisUtils.getUid(token));
    }

    @GetMapping("/deleteProjectManagementOutlineById")
    public BaseResponse deleteProjectManagementOutlineById(@RequestParam String id) {
        log.info("deleteProjectManagementOutlineById");
        return projectManagementOutlineService.deleteProjectManagementOutlineById(id);
    }

    @GetMapping("/getProjectManagementOutlineById")
    public BaseResponse getProjectManagementOutlineById(@RequestParam String id) {
        log.info("getProjectManagementOutlineById");
        return projectManagementOutlineService.getProjectManagementOutlineById(id);
    }
}
