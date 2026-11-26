package com.jpmsoft.projectConsumer.controller.v1.project;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.feign.BaseFeign;

import com.jpmsoft.projectConsumer.pojo.dto.project.ProjectReportViolationsDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@Api(tags = "项目问题举报", value = "项目问题举报")
@RequestMapping("/v1/ProjectReportViolations")
public class ProjectReportViolationsController {

    @Resource
    private BaseFeign baseFeign;


    //新增更新
    @ApiOperation(value = "新增修改")
    @PostMapping("/createOrUpdateProjectReportViolations")
    public BaseResponse createOrUpdateProjectReportViolations(@RequestBody ProjectReportViolationsDTO request) {
        log.info("createOrUpdateProjectReportViolations");
        return baseFeign.createOrUpdateProjectReportViolations(request);
    }


    @ApiOperation(value = "短信验证提交表单")
    @PostMapping("/createOrUpdateProjectReportViolationsBySms")
    public BaseResponse createOrUpdateProjectReportViolationsBySms(@RequestBody ProjectReportViolationsDTO request) {
        log.info("createOrUpdateProjectReportViolationsBySms");
        return baseFeign.createOrUpdateProjectReportViolationsBySms(request);
    }


    @ApiOperation(value = "ID查询")
    @GetMapping("/getProjectReportViolationsById")
    public BaseResponse getProjectReportViolationsById(@RequestParam("id") String id) {
        log.info("getProjectReportViolationsById");
        return baseFeign.getProjectReportViolationsById(id);

    }
    /**
     * 分页查询
     */
    @ApiOperation(value = "分页查询")
    @PostMapping("/getProjectReportViolationsList")
    public BaseResponse getProjectReportViolationsList(@RequestBody ProjectReportViolationsDTO request) {
        log.info("getProjectReportViolationsList");
        return baseFeign.getProjectReportViolationsList(request);
    }


}
