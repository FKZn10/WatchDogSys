package com.jpmsoft.projectConsumer.controller.v1.qualitySafety;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SafeResponsibility.SafeResponsibilityPostPersonDTO;
import com.jpmsoft.projectConsumer.feign.BaseFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 安全责任书岗位人员
 */
@Slf4j
@RestController
@Api(tags = "安全责任书岗位人员", value = "安全责任书岗位人员")
@RequestMapping("/safety/v1/safeResponsibilityPostPerson")
public class SafeResponsibilityPostPersonController {
    @Resource
    private BaseFeign baseFeign;

    /**
     * 新增或修改安全责任书岗位人员
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "新增或修改安全责任书岗位人员")
    @PostMapping("/createOrUpdatePostPerson")
    public BaseResponse createOrUpdatePostPerson(@RequestBody SafeResponsibilityPostPersonDTO request) {
        log.info("createOrUpdatePostPerson");
        return baseFeign.createOrUpdatePostPerson(request);
    }

    /**
     * 获取安全责任书岗位人员列表
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "获取安全责任书岗位人员列表")
    @PostMapping("/getPostPersonList")
    public BaseResponse getPostPersonList(@RequestBody SafeResponsibilityPostPersonDTO request) {
        log.info("getPostPersonList");
        return baseFeign.getPostPersonList(request);
    }

    /**
     * 获取安全责任书岗位人员
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "获取安全责任书岗位人员")
    @GetMapping("/getPostPersonById")
    public BaseResponse getPostPersonById(@RequestParam String id) {
        log.info("getPostPersonById");
        return baseFeign.getPostPersonById(id);
    }

    /**
     * 删除安全责任书岗位人员
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "删除安全责任书岗位人员")
    @GetMapping("/deletePostPersonById")
    public BaseResponse deletePostPersonById(@RequestParam String id) {
        log.info("deletePostPersonById");
        return baseFeign.deletePostPersonById(id);
    }

    /**
     * 同步上报人员
     *
     * @param rpId
     * @return
     */
    @ApiOperation(value = "同步上报人员")
    @GetMapping("/syncReportedPersonnel")
    public BaseResponse syncReportedPersonnel(@RequestParam String rpId) {
        log.info("syncReportedPersonnel");
        return baseFeign.syncReportedPersonnel(rpId);
    }
}
