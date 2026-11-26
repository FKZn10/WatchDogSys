package com.jpmsoft.projectProducer.controller.v1.qualitySafety;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SafeResponsibility.SafeResponsibilityPostPersonDTO;
import com.jpmsoft.projectProducer.service.qualitySafety.ISafeResponsibilityPostPersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 安全责任书岗位人员
 */
@Slf4j
@RestController
@RequestMapping("/v1/safeResponsibilityPostPerson")
public class SafeResponsibilityPostPersonController {
    @Resource
    private ISafeResponsibilityPostPersonService safeResponsibilityPostPersonService;

    /**
     * 新增或修改安全责任书岗位人员
     *
     * @param request
     * @return
     */
    @PostMapping("/createOrUpdatePostPerson")
    public BaseResponse createOrUpdatePostPerson(@RequestBody SafeResponsibilityPostPersonDTO request) {
        log.info("createOrUpdatePostPerson");
        return safeResponsibilityPostPersonService.createOrUpdatePostPerson(request);
    }

    /**
     * 获取安全责任书岗位人员列表
     *
     * @param request
     * @return
     */
    @PostMapping("/getPostPersonList")
    public BaseResponse getPostPersonList(@RequestBody SafeResponsibilityPostPersonDTO request) {
        log.info("getPostPersonList");
        return safeResponsibilityPostPersonService.getPostPersonList(request);
    }

    /**
     * 获取安全责任书岗位人员
     *
     * @param id
     * @return
     */
    @GetMapping("/getPostPersonById")
    public BaseResponse getPostPersonById(@RequestParam String id) {
        log.info("getPostPersonById");
        return safeResponsibilityPostPersonService.getPostPersonById(id);
    }

    /**
     * 删除安全责任书岗位人员
     *
     * @param id
     * @return
     */
    @GetMapping("/deletePostPersonById")
    public BaseResponse deletePostPersonById(@RequestParam String id) {
        log.info("deletePostPersonById");
        return safeResponsibilityPostPersonService.deletePostPersonById(id);
    }

    /**
     * 同步上报人员
     *
     * @param rpId
     * @return
     */
    @GetMapping("/syncReportedPersonnel")
    public BaseResponse syncReportedPersonnel(@RequestParam String rpId) {
        log.info("syncReportedPersonnel");
        return safeResponsibilityPostPersonService.syncReportedPersonnel(rpId);
    }
}