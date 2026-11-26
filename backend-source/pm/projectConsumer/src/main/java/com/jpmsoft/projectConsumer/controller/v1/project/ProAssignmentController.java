package com.jpmsoft.projectConsumer.controller.v1.project;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Project.ProAssignmentModel;
import com.jpmsoft.projectConsumer.feign.PreFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@Api(tags = "项目分派单", value = "项目分派单")
@RequestMapping("/v1/proAssignment")
public class ProAssignmentController {
    @Autowired
    private PreFeign preFeign;


    @ApiOperation(value = "创建修改项目分派单")
    @PostMapping("/createOrUpdateProAssignment")
    public BaseResponse createOrUpdateProAssignment(@RequestBody ProAssignmentModel request) {
        log.info("createOrUpdateProAssignment");
        return preFeign.createOrUpdateProAssignment(request);
    }

    @ApiOperation(value = "分页查询项目分派单")
    @PostMapping("/getProAssignmentList")
    public BaseResponse getProAssignmentList(@RequestBody ProAssignmentModel request, @RequestHeader String token) {
        log.info("getProAssignmentList");
        return preFeign.getProAssignmentList(request, token);
    }

    @ApiOperation(value = "ID查询项目分派单")
    @GetMapping("/getProAssignmentById")
    public BaseResponse getProAssignmentById(@RequestParam String paId) {
        log.info("getProAssignmentById");
        return preFeign.getProAssignmentById(paId);
    }


    @ApiOperation(value = "同步项目的 主管部门 P_Competent_Department")
    @GetMapping("/syncPCompetentDepartment")
    public BaseResponse syncPCompetentDepartment(@RequestParam String paId) {
        log.info("syncPCompetentDepartment");
        return preFeign.syncPCompetentDepartment(paId);
    }
}
