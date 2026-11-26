package com.jpmsoft.projectProducer.controller.v1.project;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.pojo.dto.project.ProjectReportViolationsDTO;
import com.jpmsoft.projectProducer.service.project.IProjectReportViolationsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/ProjectReportViolations")
public class ProjectReportViolationsController {

    @Resource
    private IProjectReportViolationsService projectReportViolationsService;

    @TenantId
    @PostMapping("/createOrUpdateProjectReportViolations")
    public BaseResponse createOrUpdateProjectReportViolations(@RequestBody ProjectReportViolationsDTO request) {
        log.info("createOrUpdateProjectReportViolations");
        return projectReportViolationsService.createOrUpdateProjectReportViolations(request);
    }
    @TenantId
    @PostMapping("/createOrUpdateProjectReportViolationsBySms")
    public BaseResponse createOrUpdateProjectReportViolationsBySms(@RequestBody ProjectReportViolationsDTO request) {
        log.info("createOrUpdateProjectReportViolationsBySms");
        return projectReportViolationsService.createOrUpdateProjectReportViolationsBySms(request);
    }

    @TenantId
    @GetMapping("/getProjectReportViolationsById")
    public BaseResponse getProjectReportViolationsById(@RequestParam("id") String id) {
        log.info("getProjectReportViolationsById");
        return projectReportViolationsService.getProjectReportViolationsById(id);

    }
    /**
     * 分页查询
     */
    @TenantId
    @PostMapping("/getProjectReportViolationsList")
    public BaseResponse getProjectReportViolationsList(@RequestBody ProjectReportViolationsDTO request) {
        log.info("getProjectReportViolationsList");
        return projectReportViolationsService.getProjectReportViolationsList(request);
    }


}
